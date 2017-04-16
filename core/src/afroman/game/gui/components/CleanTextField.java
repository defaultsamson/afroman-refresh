package afroman.game.gui.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by samson on 11/04/17.
 */
public class CleanTextField extends TextField {

    // Sets timing for repeating keys
    static {
        keyRepeatTime = 0.04F;
        keyRepeatInitialTime = 0.5F;
    }

    public CleanTextField(String text, Skin skin) {
        super(text, skin);
        setBlinkTime(0.477F);
    }

    public CleanTextField(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
        setBlinkTime(0.477F);
    }

    public CleanTextField(String text, TextFieldStyle style) {
        super(text, style);
        setBlinkTime(0.477F);
    }

    /**
     * Draws selection rectangle
     **/
    protected void drawSelection(Drawable selection, Batch batch, BitmapFont font, float x, float y) {
        // prevents the selection box from being drawn when nothing is being selected
        if (getSelection().length() > 0) super.drawSelection(selection, batch, font, x, y);
    }
}
