package afroman.game.gui;

import afroman.game.FinalConstants;
import afroman.game.MainGame;
import afroman.game.gui.components.CleanTextField;
import afroman.game.gui.components.GuiConstants;
import afroman.game.gui.components.HierarchicalMenu;
import afroman.game.gui.components.NoisyClickListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static afroman.game.gui.components.GuiConstants.skin;

/**
 * Created by Samson on 2017-04-24.
 */
public class PasswordGui extends HierarchicalMenu {

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;

    private ShapeRenderer shapeRenderer;
    private Color bgColour;

    private CleanTextField usernameInput;

    public PasswordGui() {
        super(MainGame.game.getMainMenu().joinMenu);
        final ScreenViewport viewport = MainGame.createStandardViewport();

        stageAbove = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        shapeRenderer = new ShapeRenderer();
        bgColour = new Color(0, 0, 0, 0.7F);

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -46;
        final int buttonSpacing = 6;

        usernameInput = new CleanTextField("", skin);
        usernameInput.setSize(buttonWidth * 2, buttonHeight);
        usernameInput.setPosition(-usernameInput.getWidth() / 2, buttonYOffset + (2F * (buttonHeight + buttonSpacing)));
        usernameInput.setTextFieldFilter(GuiConstants.usernameFilter);
        usernameInput.setMaxLength(FinalConstants.maxUsernameLength);
        usernameInput.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {

            }
        });
        usernameInput.addListener(new CleanTextField.TextFieldOrientFocusListener(viewport.getCamera(), buttonSpacing));
        stageAbove.addActor(usernameInput);

        Label usernameLabel = new Label("Enter Server Password", skin);
        usernameLabel.setSize(0, buttonHeight);
        usernameLabel.setAlignment(Align.center);
        usernameLabel.setTouchable(Touchable.disabled);
        usernameLabel.setPosition(0, buttonYOffset + (3F * (buttonHeight + buttonSpacing)));
        stageAbove.addActor(usernameLabel);

        TextButton resetControls = new TextButton("Back", skin);
        resetControls.setSize(buttonWidth, buttonHeight);
        resetControls.setPosition(buttonSpacing / 2, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        resetControls.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getNetworkManager().killClient();
                gotoParentScreen();
            }
        });
        stageAbove.addActor(resetControls);

        TextButton backButton = new TextButton("Enter", skin);
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        backButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
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
