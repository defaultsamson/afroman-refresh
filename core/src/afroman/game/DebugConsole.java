package afroman.game;

/**
 * Created by Samson on 2017-04-25.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class DebugConsole {
    public static DebugConsole console = null;
    private static MultiOutputStream out = null;
    private static MultiOutputStream err = null;

    /**
     * Initialised the out and err streams.
     */
    public static void initialize() {
        initialize(800, 500, null);
    }

    /**
     * Initialised the out and err streams.
     *
     * @param width the width of the window
     * @param width the height of the window
     */
    public static void initialize(int width, int height) {
        initialize(width, height, null);
    }

    /**
     * Initialised the out and err streams.
     *
     * @param relativeTo the component to set the location of this window to
     */
    public static void initialize(Component relativeTo) {
        initialize(800, 500, relativeTo);
    }

    /**
     * Initialised the out and err streams.
     *
     * @param width      the width of the window
     * @param width      the height of the window
     * @param relativeTo the component to set the location of this window to
     */
    public static void initialize(int width, int height, Component relativeTo) {
        if (console == null) {
            console = new DebugConsole(width, height, relativeTo);
        }

        if (out == null) {
            ConsoleOutputStream o = new ConsoleOutputStream(Color.BLACK);
            out = new MultiOutputStream(o, System.out);
            System.setOut(new PrintStream(out));
        }

        if (err == null) {
            ConsoleOutputStream e = new ConsoleOutputStream(Color.RED);
            err = new MultiOutputStream(e, System.err);
            System.setErr(new PrintStream(err));
        }
    }

    /**
     * @return the MultiOutputStream being used to output to multiple streams at
     * once.
     */
    public static MultiOutputStream getOutputStream() {
        initialize();
        return out;
    }

    /**
     * @return the MultiOutputStream being used to output to multiple streams at
     * once.
     */
    public static MultiOutputStream getErrorStream() {
        initialize();
        return err;
    }

    private JFrame frame;
    private JTextPane text;
    private JPanel panel;

    public static DebugConsole instance() {
        initialize();
        return console;
    }

    public JFrame getJFrame() {
        return frame;
    }

    public JTextPane getJTextArea() {
        return text;
    }

    public JPanel getJPanel() {
        return panel;
    }

    private DebugConsole(int width, int height, Component comp) {
        frame = new JFrame("CONSOLE");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(comp);

        text = new JTextPane();
        text.setBorder(new EmptyBorder(new Insets(0, 4, 2, 2)));
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.add(scrollPane);

        frame.getContentPane().add(panel);
    }

    /**
     * Puts a message in the console.
     *
     * @param msg the message
     */
    public void appendToPane(String msg) {
        appendToPane(msg, Color.BLACK);
    }

    /**
     * Puts a message in the console.
     *
     * @param msg the message
     * @param c   the colour of the message
     */
    public void appendToPane(String msg, Color c) {
        MutableAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(set, -0.16F);
        StyleConstants.setForeground(set, c);

        text.setEditable(true);

        int len = text.getDocument().getLength();
        text.setCaretPosition(len);
        text.setParagraphAttributes(set, false);
        text.replaceSelection(msg);

        text.setEditable(false);
    }

    /**
     * Sets the visibility of the console.
     *
     * @param show whether or not to display the console
     */
    public static void setVisible(boolean show) {
        instance().getJFrame().setVisible(show);
    }

    /**
     * Displays the console.
     */
    public static void show() {
        setVisible(true);
    }

    public static boolean isVisible() {
        return instance().getJFrame().isVisible();
    }

    /**
     * Hides the console.
     */
    public static void hide() {
        setVisible(false);
    }
}

class ConsoleOutputStream extends OutputStream {
    private final StringBuilder sb = new StringBuilder();

    private Color colour;

    public ConsoleOutputStream(Color colour) {
        this.colour = colour;
    }

    @Override
    public void write(int b) throws IOException {

        if (b == '\r')
            return;

        if (b == '\n') {
            final String text = sb.toString() + "\n";
            DebugConsole.instance().appendToPane(text, colour);
            sb.setLength(0);
            return;
        }

        sb.append((char) b);
    }
}

class MultiOutputStream extends OutputStream {
    private List<OutputStream> streams;

    public MultiOutputStream(OutputStream... streams) {
        this.streams = new ArrayList<OutputStream>();

        for (OutputStream out : streams) {
            if (out != null)
                addStream(out);
        }
    }

    public void addStream(OutputStream stream) {
        synchronized (streams) {
            streams.add(stream);
        }
    }

    public List<OutputStream> getStreams() {
        return streams;
    }

    @Override
    public void write(int b) throws IOException {
        synchronized (streams) {
            for (OutputStream stream : streams) {
                stream.write(b);
            }
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        synchronized (streams) {
            for (OutputStream stream : streams) {
                stream.write(b);
            }
        }
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        synchronized (streams) {
            for (OutputStream stream : streams) {
                stream.write(b, off, len);
            }
        }
    }

    @Override
    public void flush() throws IOException {
        synchronized (streams) {
            for (OutputStream stream : streams) {
                stream.flush();
            }
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (streams) {
            for (OutputStream stream : streams) {
                stream.close();
            }
        }
    }
}