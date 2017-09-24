package afroman.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import afroman.game.MainGame;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	//private static final int CAMERA_WIDTH = 240;//1280;
	//private static final int CAMERA_HEIGHT = CAMERA_WIDTH * 9 / 16;//720;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		new Lwjgl3Application(new MainGame(), config);
	}
}
