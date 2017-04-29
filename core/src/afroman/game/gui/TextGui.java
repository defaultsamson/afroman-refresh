package afroman.game.gui;

import afroman.game.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static afroman.game.gui.components.GuiConstants.skin;

/**
 * Created by Samson on 2017-04-24.
 */
public class TextGui implements Screen {

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;

    private ShapeRenderer shapeRenderer;
    private Color bgColour;

    private Label title;

    public void setText(String newText) {
        title.setText(newText);
    }

    public TextGui(String text, String cancelText, EventListener buttonListener) {
        final ScreenViewport viewport = MainGame.createStandardViewport();

        stageAbove = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        shapeRenderer = new ShapeRenderer();
        bgColour = new Color(0, 0, 0, 0.7F);

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -42;
        final int buttonSpacing = 6;

        title = new Label(text, skin);
        title.setSize(0, buttonHeight);
        title.setPosition(0, buttonYOffset + (2.3F * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        TextButton backButton = new TextButton(cancelText, skin);
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(-buttonWidth / 2, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        backButton.addListener(buttonListener);
        stageAbove.addActor(backButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageAbove);
    }

    @Override
    public void render(float delta) {
        Camera camera = stageAbove.getCamera();
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        Viewport viewport = stageAbove.getViewport();
        // Draws darkness in the background
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(bgColour);
        shapeRenderer.rect(camera.position.x - (viewport.getWorldWidth() / 2), camera.position.y - (viewport.getWorldHeight() / 2), viewport.getWorldWidth(), viewport.getWorldHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL30.GL_BLEND);

        MainGame.game.drawVignette(stageAbove.getBatch(), stageAbove.getCamera(), stageAbove.getViewport());

        stageAbove.act(delta);
        stageAbove.draw();
    }

    @Override
    public void resize(int width, int height) {
        stageAbove.getViewport().update(width, height);
        stageAbove.getViewport().getCamera().update();
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
        stageAbove.dispose();
        shapeRenderer.dispose();
    }
}
