package afroman.game.gui;

import afroman.game.FinalConstants;
import afroman.game.MainGame;
import afroman.game.gui.components.CleanTextField;
import afroman.game.gui.components.GuiConstants;
import afroman.game.gui.components.HierarchicalMenu;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.net.objects.RequestPassword;
import afroman.game.util.DeviceUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import static afroman.game.gui.components.GuiConstants.skin;
import static afroman.game.gui.components.GuiConstants.viewport;

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

    private CleanTextField passwordInput;
    private TextButton enterButton;
    private Label passwordLabel;
    private String defaultPassLabelText = "Enter Server Password";

    public PasswordGui() {
        super(MainGame.game.getMainMenu().joinMenu);

        stageAbove = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        shapeRenderer = new ShapeRenderer();
        bgColour = new Color(0, 0, 0, 0.7F);

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -46;
        final int buttonSpacing = 6;

        passwordInput = new CleanTextField("", skin);
        passwordInput.setSize(buttonWidth * 2, buttonHeight);
        passwordInput.setPosition(-passwordInput.getWidth() / 2, buttonYOffset + (2F * (buttonHeight + buttonSpacing)));
        passwordInput.setTextFieldFilter(GuiConstants.usernameFilter);
        passwordInput.setMaxLength(FinalConstants.maxUsernameLength);
        passwordInput.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                updateEnterButton();
                resetMessage();
                return super.keyTyped(event, character);
            }
        });
        passwordInput.addListener(new CleanTextField.TextFieldOrientFocusListener(viewport.getCamera(), buttonSpacing));
        stageAbove.addActor(passwordInput);

        passwordLabel = new Label(defaultPassLabelText, skin);
        passwordLabel.setSize(0, buttonHeight);
        passwordLabel.setAlignment(Align.center);
        passwordLabel.setTouchable(Touchable.disabled);
        passwordLabel.setPosition(0, buttonYOffset + (3F * (buttonHeight + buttonSpacing)));
        stageAbove.addActor(passwordLabel);

        TextButton backControls = new TextButton("Back", skin);
        backControls.setSize(buttonWidth, buttonHeight);
        backControls.setPosition(buttonSpacing / 2, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        backControls.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                back();
            }
        });
        stageAbove.addActor(backControls);

        enterButton = new TextButton("Enter", skin);
        enterButton.setSize(buttonWidth, buttonHeight);
        enterButton.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        enterButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendPassword();
            }
        });
        stageAbove.addActor(enterButton);

        // If anything is touched that's not a TextField, remove text input focus
        stageAbove.getRoot().addCaptureListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!(event.getTarget() instanceof TextField)) {
                    stageAbove.setKeyboardFocus(null);
                    Gdx.input.setOnscreenKeyboardVisible(false);
                    viewport.getCamera().position.y = 0;
                }
                return false;
            }
        });
    }

    private void back() {
        MainGame.game.getNetworkManager().preventFromSendingToMainMenu(true);
        MainGame.game.getNetworkManager().killClient();
        gotoParentScreen();
    }

    public void setMessage(String message) {
        passwordLabel.setText(defaultPassLabelText + "\n" + message);
    }

    public void resetMessage() {
        passwordLabel.setText(defaultPassLabelText);
    }

    private boolean canSend() {
        return passwordInput.getText().length() > 0;
    }

    private void updateEnterButton() {
        enterButton.setDisabled(!canSend());
    }

    private void sendPassword() {
        if (canSend()) {
            final TextGui gui = new TextGui("Sending Password\nPlease wait...", "Cancel", new NoisyClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    MainGame.game.getNetworkManager().killClient();
                    gotoParentScreen();
                }
            });

            MainGame.game.setScreen(gui);
            RequestPassword req = new RequestPassword();
            req.pass = passwordInput.getText();
            MainGame.game.getNetworkManager().getClient().sendTCP(req);
        }
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (DeviceUtil.isDesktop()) {
                sendPassword();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) back();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.getCamera().update();
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
