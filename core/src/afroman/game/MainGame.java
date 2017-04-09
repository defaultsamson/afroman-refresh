package afroman.game;

import afroman.game.gui.MainMenu;
import afroman.game.io.Setting;
import afroman.game.io.Settings;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGame extends Game {

    private static MainGame game;
    public static Settings settings = null;
    public static float SCALE; // TODO get from file
    public static final int CAMERA_WIDTH = 240;
    public static final int CAMERA_HEIGHT = CAMERA_WIDTH * 9 / 16;

    private static boolean isYInverted = false;

    BitmapFont font;

    public static Viewport createStandardViewport() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(isYInverted);
        ScreenViewport viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(1 / SCALE);
        return viewport;
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

        SCALE = settings.getFloat(Setting.SCALE, 3F);

        Gdx.graphics.setWindowedMode((int) (CAMERA_WIDTH * SCALE), (int) (CAMERA_HEIGHT * SCALE));

        // batch = new SpriteBatch();
        font = new BitmapFont();
        // img = new Texture("badlogic.jpg");

        setScreen(new MainMenu());
    }

    /*
        public int getScreenWidth() {
            return viewport.getScreenWidth();
        }

        public int getScreenHeight() {
            return viewport.getScreenHeight();
        }

        public float getWorldWidth() {
            return viewport.getWorldWidth();
        }

        public float getWorldHeight() {
            return viewport.getWorldHeight();
        }
    */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //viewport.update(width, height);
        //viewport.getCamera().update();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();

        /*
        batch.setTransformMatrix(getCamera().view);
        batch.setProjectionMatrix(getCamera().projection);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();*/
    }

    @Override
    public void dispose() {
        super.dispose();

        settings.save();
        //batch.dispose();
        font.dispose();
        //img.dispose();
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

    /*public ScreenViewport getViewport() {
        return viewport;
    }*/

   /* public OrthographicCamera getCamera() {
        return (OrthographicCamera) viewport.getCamera();
    }*/
}
