package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.util.PhysicsUtil;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Samson on 2017-05-11.
 */
public class PlayScreen implements Screen {

    private World world;
    private ScreenViewport viewport;
    private SpriteBatch batch;

    public PlayScreen() {
        world = new World(new Vector2(0, -9.81F), true);
        viewport = MainGame.createStandardViewport();
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        PhysicsUtil.stepWorld(world, delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
