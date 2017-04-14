package afroman.game;

import afroman.game.gui.CameraScreen;
import afroman.game.gui.MainMenu;
import afroman.game.io.Setting;
import afroman.game.io.Settings;
import afroman.game.util.DeviceUtil;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class MainGame extends Game {

    public static final int CAMERA_WIDTH = 240;
    public static final int CAMERA_HEIGHT = CAMERA_WIDTH * 9 / 16;
    private static final boolean isYInverted = false;

    public static MainGame game;
    public static Settings settings;

    public MainMenu mainMenu;
    private SpriteBatch batch;
    private Texture vignette;

    public static ScreenViewport createStandardViewport() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(isYInverted);
        ScreenViewport viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(1 / settings.getFloat(Setting.SCALE));
        game.viewportList.add(viewport);
        return viewport;
    }

    private List<ScreenViewport> viewportList;

    public void setScale(float scale) {
        settings.putFloat(Setting.SCALE, scale);
        settings.save();

        for (ScreenViewport port : viewportList) {
            port.setUnitsPerPixel(1 / scale);
            port.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void create() {
        game = this;

        // Sets the game to invoke the keyDown() method for when the android back button has been pressed
        // Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        settings = new Settings(Gdx.app.getPreferences("settings.afro"));

        // settings.putFloat(Setting.SCALE, 3F);
        // settings.save();

        // If on desktop, size the window to fit the default dimensions at the provided scale.
        if (DeviceUtil.isDesktop()) {
            float scale = settings.getFloat(Setting.SCALE, 3F);
            resetScreenSize(scale); // TODO save the screen size from the previous runtime
            settings.putFloat(Setting.SCALE, scale);
            settings.save();
        }
        // If on Android, the default value for scaling will make the available hight being drawn the value of the in-world CAMERA_HEIGHT
        else if (DeviceUtil.isAndroid()) {
            float scale = settings.getFloat(Setting.SCALE, (float) Gdx.graphics.getHeight() / (float) CAMERA_HEIGHT);
            settings.putFloat(Setting.SCALE, scale);
            settings.save();
        } else {
            settings.putFloat(Setting.SCALE, 3F);
        }

        batch = new SpriteBatch();
        vignette = new Texture("assets/textures/vignette.png");
        viewportList = new ArrayList<ScreenViewport>();
        mainMenu = new MainMenu();
        setScreen(mainMenu);

        //Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", 3145, null);
    }

    private void resetScreenSize(float scale) {
        Gdx.graphics.setWindowedMode((int) (CAMERA_WIDTH * scale), (int) (CAMERA_HEIGHT * scale));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    private int windowedWidth;
    private int windowedHeight;

    @Override
    public void render() {
        if (DeviceUtil.isDesktop()) {
            // Debug, rescales the window size
            if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) resetScreenSize(settings.getFloat(Setting.SCALE));
            // Fullscreen
            if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(windowedWidth, windowedHeight);
                } else {
                    windowedWidth = Gdx.graphics.getWidth();
                    windowedHeight = Gdx.graphics.getHeight();
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // TODO draw world elements before vignette

        // TODO dynamically generated vignette noise?
        // Draws vignette
        if (this.screen != null && this.screen instanceof CameraScreen) {
            OrthographicCamera camera = ((CameraScreen) this.screen).getCamera();

            if (this.screen != null && this.screen instanceof CameraScreen) {
                ScreenViewport viewport = ((CameraScreen) this.screen).getViewport();

                if (camera != null && viewport != null) {
                    batch.setProjectionMatrix(camera.combined);
                    batch.begin();
                    batch.draw(vignette, camera.position.x - (viewport.getWorldWidth() / 2), camera.position.y - (viewport.getWorldHeight() / 2), viewport.getWorldWidth(), viewport.getWorldHeight());
                    batch.end();
                }
            }
        }

        // Draws screen elements
        super.render();
    }

    /*
    public ScreenViewport getViewport() {
        if (this.screen != null && this.screen instanceof CameraScreen) {
            return ((CameraScreen) this.screen).getViewport();
        }
        return null;
    }*/

    /*
    public OrthographicCamera getCamera() {
        if (this.screen != null && this.screen instanceof CameraScreen) {
            return ((CameraScreen) this.screen).getCamera();
        }
        return null;
    }*/

    @Override
    public void dispose() {
        super.dispose();

        settings.save();
        batch.dispose();
        vignette.dispose();
    }

    /**
     * Brings the camera to the centre of the screen.
     */
    public void centerCamera() {
        // TODO
    }

    /**
     * @return if the screen is inverted in the Y axis (if the y ordinates start at the bottom or the top).
     */
    public boolean isInverted() {
        return isYInverted;
    }
}
