package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.FlickeringLight;
import afroman.game.gui.components.IconButton;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.net.NetworkManager;
import afroman.game.net.PlayerConnection;
import afroman.game.net.objects.KickPlayer;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import static afroman.game.gui.components.GuiConstants.skin;
import static afroman.game.gui.components.GuiConstants.viewport;

/**
 * Created by Samson on 2017-04-24.
 */
public class LobbyGui implements Screen {

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;
    /**
     * The stage below the lighting.
     */
    private Stage stageBelow;

    private FlickeringLight p1Light;
    private FlickeringLight p2Light;
    private RayHandler menuRayHandler;
    private World menuWorld;

    private Label user1Label;
    private Label user2Label;
    private Label waitingLabel;
    private TextButton stopServer;
    private TextButton kickPlayer2;
    private TextButton startGameButton;
    private TextButton disconnectButton;

    public LobbyGui() {
        int manY = 20;
        int manSpacing = 84;
        int manWidth = 5;

        menuWorld = new World(new Vector2(0, 0F), true);
        menuRayHandler = new RayHandler(menuWorld);
        menuRayHandler.setBlurNum(1);
        menuRayHandler.setAmbientLight(0.3F);
        p1Light = new FlickeringLight(0.04F, 35, 50, menuRayHandler, 20, new Color(0F, 0F, 0F, 1F), -(manSpacing / 2), 27);
        p2Light = new FlickeringLight(0.04F, 35, 50, menuRayHandler, 20, new Color(0F, 0F, 0F, 1F), (manSpacing / 2), 27);

        stageAbove = new Stage(viewport);
        stageBelow = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        Image image = new Image(MainGame.game.getAssets().getTextureAtlas(Asset.PLAYER).findRegion("player1moveDown", 2));
        image.setPosition(-manWidth - (manSpacing / 2), manY);
        stageBelow.addActor(image);

        Image image2 = new Image(MainGame.game.getAssets().getTextureAtlas(Asset.PLAYER).findRegion("player2moveDown", 2));
        image2.setPosition(-manWidth + (manSpacing / 2), manY);
        stageBelow.addActor(image2);

        int buttonWidth = 76;
        int buttonHeight = 16;

        int buttonYOffset = -42;
        final int buttonSpacing = 6;

        Label title = new Label("Server Lobby", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        float user1middle = image.getX() + manWidth;
        float user2middle = image2.getX() + manWidth;

        user1Label = new Label("User 1", skin);
        user1Label.setSize(0, buttonHeight);
        user1Label.setPosition(user1middle, buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        user1Label.setAlignment(Align.center);
        stageAbove.addActor(user1Label);

        user2Label = new Label("User 2", skin);
        user2Label.setSize(0, buttonHeight);
        user2Label.setPosition(user2middle, buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        user2Label.setAlignment(Align.center);
        stageAbove.addActor(user2Label);

        stopServer = new TextButton("Stop Server", skin);
        stopServer.setSize(buttonWidth, buttonHeight);
        stopServer.setPosition(user1middle - (buttonWidth / 2), buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        stopServer.addListener(new NoisyClickListener(stopServer) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getNetworkManager().killClient();
                MainGame.game.getNetworkManager().killServer();
            }
        });
        stageAbove.addActor(stopServer);

        kickPlayer2 = new TextButton("Kick Player", skin);
        kickPlayer2.setSize(buttonWidth, buttonHeight);
        kickPlayer2.setPosition(user2middle - (buttonWidth / 2), buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        kickPlayer2.addListener(new NoisyClickListener(kickPlayer2) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getNetworkManager().getClient().sendTCP(new KickPlayer());
            }
        });
        stageAbove.addActor(kickPlayer2);

        startGameButton = new TextButton("Start Game", skin);
        startGameButton.setSize(buttonWidth, buttonHeight);
        startGameButton.setPosition(-(buttonWidth / 2), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        startGameButton.addListener(new NoisyClickListener(startGameButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO
                MainGame.game.setScreen(new PlayScreen());
            }
        });
        stageAbove.addActor(startGameButton);

        disconnectButton = new TextButton("Disconnect", skin);
        disconnectButton.setSize(buttonWidth, buttonHeight);
        disconnectButton.setPosition(-(buttonWidth / 2), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        disconnectButton.addListener(new NoisyClickListener(disconnectButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getNetworkManager().killClient();
            }
        });
        stageAbove.addActor(disconnectButton);

        waitingLabel = new Label("Waiting for host to\nstart the game...", skin);
        waitingLabel.setSize(0, buttonHeight);
        waitingLabel.setPosition(0, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        waitingLabel.setAlignment(Align.center);
        stageAbove.addActor(waitingLabel);

        Texture settingsIcon = MainGame.game.getAssets().getTexture(Asset.SETTINGS_ICON);
        IconButton settingsButton = new IconButton(skin, settingsIcon);
        settingsButton.setSize(buttonHeight, buttonHeight);
        settingsButton.setPosition((-buttonWidth / 2) - (2 * (buttonHeight + buttonSpacing)), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        settingsButton.addListener(new NoisyClickListener(settingsButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getSettingsMenu().setParent(LobbyGui.this);
                MainGame.game.safelySetScreen(MainGame.game.getSettingsMenu());
            }
        });
        stageAbove.addActor(settingsButton);

        Texture controlsIcon = MainGame.game.getAssets().getTexture(Asset.CONTROLLER_ICON);
        IconButton controlsButton = new IconButton(skin, controlsIcon);
        controlsButton.setSize(buttonHeight, buttonHeight);
        controlsButton.setPosition((-buttonWidth / 2) - (1 * (buttonHeight + buttonSpacing)), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        controlsButton.addListener(new NoisyClickListener(controlsButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getControlsMenu().setParent(LobbyGui.this);
                MainGame.game.safelySetScreen(MainGame.game.getControlsMenu());
            }
        });
        stageAbove.addActor(controlsButton);
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

        if (!p1Light.isActive()) p1Light.update();
        if (!p2Light.isActive()) p2Light.update();
    }

    public void updateGui() {
        NetworkManager nm = MainGame.game.getNetworkManager();
        PlayerConnection user1 = nm.getPlayerConnection(true);
        PlayerConnection user2 = nm.getPlayerConnection(false);

        boolean isHost = nm.getPlayerConnection(nm.getThisPlayerType()).isHost();

        if (isHost) {
            startGameButton.setDisabled(user1 == null || user2 == null);
            kickPlayer2.setDisabled(user2 == null);
            stopServer.setDisabled(false);

            waitingLabel.setVisible(false);
            disconnectButton.setVisible(false);
            disconnectButton.setDisabled(true);
        } else {
            startGameButton.setDisabled(true);
            kickPlayer2.setDisabled(true);
            stopServer.setDisabled(true);

            startGameButton.setVisible(false);
            kickPlayer2.setVisible(false);
            stopServer.setVisible(false);

            waitingLabel.setVisible(true);
            disconnectButton.setVisible(true);
            disconnectButton.setDisabled(false);
        }

        if (user1 != null) {
            user1Label.setText(user1.getUsername());
            user1Label.setVisible(true);
            p1Light.setActive(true);
        } else {
            user1Label.setVisible(false);
            p1Light.setActive(false);
        }

        if (user2 != null) {
            user2Label.setText(user2.getUsername());
            user2Label.setVisible(true);
            p2Light.setActive(true);
        } else {
            user2Label.setVisible(false);
            p2Light.setActive(false);
        }

        //TODO remove debug
        startGameButton.setDisabled(false);
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
