package afroman.game.gui;

import afroman.game.FinalConstants;
import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.CleanTextField;
import afroman.game.gui.components.GuiConstants;
import afroman.game.gui.components.HierarchicalMenu;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.util.DeviceUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Align;

import static afroman.game.gui.components.GuiConstants.*;

/**
 * Created by Samson on 2017-04-08.
 */
public class JoinMenu extends HierarchicalMenu implements Screen {
    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;
    /**
     * The stage below the lighting.
     */
    private Stage stageBelow;

    private CleanTextField usernameInput;
    private CleanTextField ipInput;
    private TextButton joinButton;

    public JoinMenu(Screen parentScreen) {
        super(parentScreen);

        stageAbove = new Stage(viewport);
        stageBelow = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        int manY = 20;
        int manSpacing = 20;
        int manWidth = 5;

        Image image = new Image(MainGame.game.getAssets().getTextureAtlas(Asset.PLAYER).findRegion("player1moveDown", 2));
        image.setPosition(-manWidth - (manSpacing / 2), manY);
        stageBelow.addActor(image);

        Image image2 = new Image(MainGame.game.getAssets().getTextureAtlas(Asset.PLAYER).findRegion("player2moveDown", 2));
        image2.setPosition(-manWidth + (manSpacing / 2), manY);
        stageBelow.addActor(image2);

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -48;
        final int buttonSpacing = 6;

        Label title = new Label("Join a Server", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        joinButton = new TextButton("Join", skin, "default");
        joinButton.setSize(buttonWidth, buttonHeight);
        joinButton.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (-0.5F * (buttonHeight + buttonSpacing)));
        joinButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                connectToServer();
            }
        });
        stageAbove.addActor(joinButton);

        usernameInput = new CleanTextField("", skin);
        usernameInput.setSize(buttonWidth * 2, buttonHeight);
        usernameInput.setPosition(-usernameInput.getWidth() / 2, buttonYOffset + (1.8F * (buttonHeight + buttonSpacing)));
        usernameInput.setTextFieldFilter(GuiConstants.usernameFilter);
        usernameInput.setMaxLength(FinalConstants.maxUsernameLength);
        usernameInput.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                updateJoinButton();
            }
        });
        usernameInput.addListener(new CleanTextField.TextFieldOrientFocusListener(viewport.getCamera(), buttonSpacing));
        stageAbove.addActor(usernameInput);

        Label usernameLabel = new Label("Username", skin, "black");
        usernameLabel.setSize(0, buttonHeight);
        usernameLabel.setAlignment(Align.center);
        usernameLabel.setTouchable(Touchable.disabled);
        usernameLabel.setPosition(0, buttonYOffset + (2.4F * (buttonHeight + buttonSpacing)));
        stageAbove.addActor(usernameLabel);

        ipInput = new CleanTextField("", skin);
        ipInput.setSize(buttonWidth * 2, buttonHeight);
        ipInput.setPosition(-ipInput.getWidth() / 2, buttonYOffset + (0.5F * (buttonHeight + buttonSpacing)));
        ipInput.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                updateJoinButton();
            }
        });
        ipInput.addListener(new CleanTextField.TextFieldOrientFocusListener(viewport.getCamera(), buttonSpacing));
        stageAbove.addActor(ipInput);

        Label ipLabel = new Label("Server IP", skin, "black");
        ipLabel.setSize(0, buttonHeight);
        ipLabel.setAlignment(Align.center);
        ipLabel.setTouchable(Touchable.disabled);
        ipLabel.setPosition(0, 1 + buttonYOffset + (1.1F * (buttonHeight + buttonSpacing)));
        stageAbove.addActor(ipLabel);

        TextButton exitButton = new TextButton("Back", skin, "default");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition((buttonSpacing / 2), buttonYOffset + (-0.5F * (buttonHeight + buttonSpacing)));
        exitButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gotoParentScreen();
            }
        });
        stageAbove.addActor(exitButton);

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

        updateJoinButton();
    }

    private boolean canJoin() {
        return !(usernameInput.getText().length() < FinalConstants.minUsernameLength || ipInput.getText().length() < 1);
    }

    private void updateJoinButton() {
        joinButton.setDisabled(!canJoin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageAbove);
    }

    @Override
    public void render(float delta) {

        stageBelow.act(delta);
        stageBelow.draw();

        menuRayHandler.setCombinedMatrix((OrthographicCamera) stageAbove.getCamera());
        menuRayHandler.updateAndRender();

        MainGame.game.drawVignette(stageAbove.getBatch(), stageAbove.getCamera(), stageAbove.getViewport());

        stageAbove.act(delta);
        stageAbove.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (DeviceUtil.isDesktop()) {
                if (!canJoin()) {
                    if (stageAbove.getKeyboardFocus() instanceof TextField) {
                        ((TextField) stageAbove.getKeyboardFocus()).next(UIUtils.shift());
                    }
                } else {
                    connectToServer();
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            gotoParentScreen();
    }

    private Thread thread = null;
    private TextGui gui = null;

    private void connectToServer() {
        MainGame.game.getNetworkManager().preventFromSendingToMainMenu(false);

        gui = new TextGui("Joining Server \n" + ipInput.getText() + "\nPlease wait...", "Cancel", new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!gui.getButton().isDisabled()) {
                    if (thread != null) {
                        thread.stop();
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            MainGame.game.getNetworkManager().preventFromSendingToMainMenu(true);
                            MainGame.game.getNetworkManager().killClient();
                            MainGame.game.safelySetScreen(JoinMenu.this);
                            gui = null;
                        }
                    }.start();
                    gui.setText("Cancelling\nPlease wait...");
                    gui.getButton().setDisabled(true);
                }
            }
        });

        thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    MainGame.game.getNetworkManager().connectToServer(usernameInput.getText(), ipInput.getText());
                } catch (Exception e) {
                    gui.setText("Failed to join server.\n" + e.getMessage());
                    System.err.println("Failed to join server.");
                    e.printStackTrace();
                    MainGame.game.getNetworkManager().killClient();
                }

                thread = null;
            }
        };

        MainGame.game.setScreen(gui);

        thread.start();
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
        stageBelow.dispose();
    }
}
