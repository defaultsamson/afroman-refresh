package afroman.game.gui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Samson on 2017-04-09.
 */
public interface CameraScreen extends Screen {
    OrthographicCamera getCamera();

    ScreenViewport getViewport();
}
