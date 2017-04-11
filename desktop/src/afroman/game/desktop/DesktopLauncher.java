package afroman.game.desktop;

import afroman.game.MainGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
    private static final int CAMERA_WIDTH = 240;//1280;
    private static final int CAMERA_HEIGHT = CAMERA_WIDTH * 9 / 16;//720;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = CAMERA_WIDTH; // TODO get from options
        config.height = CAMERA_HEIGHT;

        new LwjglApplication(new MainGame(), config);
    }
}
