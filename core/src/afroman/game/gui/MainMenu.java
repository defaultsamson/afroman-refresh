package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.IconButton;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.util.DeviceUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

import static afroman.game.gui.components.GuiConstants.*;

/**
 * Created by Samson on 2017-04-08.
 */
public class MainMenu implements Screen {

    public final JoinMenu joinMenu;
    public final HostMenu hostMenu;

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;
    /**
     * The stage below the lighting.
     */
    private Stage stageBelow;

    private Music music;

    public MainMenu() {
        joinMenu = new JoinMenu(this);
        hostMenu = new HostMenu(this);

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
        /*AnimationActor anim = new AnimationActor(new Animation<TextureRegion>(0.3f, MainGame.game.getAssets().getTextureAtlas(Asset.PLAYER).findRegions("player1moveDown"), Animation.PlayMode.LOOP_PINGPONG));
        anim.setSize(20, 20);
        anim.setPosition(-5, 18);
        stageBelow.addActor(anim);*/

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -49;
        int buttonSpacing = 6;

        music = MainGame.game.getAssets().getMusic(Asset.MENU_MUSIC);
        music.setLooping(true);
        music.setVolume(1.0F);

        Label title = new Label("The Adventures of Afro Man", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        TextButton joinButton = new TextButton("Join", skin, "default");
        joinButton.setSize(buttonWidth, buttonHeight);
        joinButton.setPosition(-buttonWidth / 2, buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        joinButton.addListener(new NoisyClickListener(joinButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(joinMenu);
            }
        });
        stageAbove.addActor(joinButton);

        TextButton hostButton = new TextButton("Host", skin, "default");
        hostButton.setSize(buttonWidth, buttonHeight);
        hostButton.setPosition(-buttonWidth / 2, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        hostButton.addListener(new NoisyClickListener(hostButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(hostMenu);
            }
        });
        stageAbove.addActor(hostButton);

        TextButton testButton = new TextButton("Test", skin);
        testButton.setSize(buttonWidth, buttonHeight);
        testButton.setPosition((buttonWidth / 2) + buttonSpacing, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        testButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(new PlayScreen());
            }
        });
        stageAbove.addActor(testButton);

        Texture settingsIcon = MainGame.game.getAssets().getTexture(Asset.SETTINGS_ICON);
        IconButton settingsButton = new IconButton(skin, settingsIcon);
        settingsButton.setSize(buttonHeight, buttonHeight);
        settingsButton.setPosition((-buttonWidth / 2) - buttonHeight - buttonSpacing, buttonYOffset + ((DeviceUtil.isAndroid() ? 2 : 1) * (buttonHeight + buttonSpacing)));
        settingsButton.addListener(new NoisyClickListener(settingsButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getSettingsMenu().setParent(MainMenu.this);
                MainGame.game.setScreen(MainGame.game.getSettingsMenu());
            }
        });
        stageAbove.addActor(settingsButton);

        Texture controlsIcon = MainGame.game.getAssets().getTexture(Asset.CONTROLLER_ICON);
        IconButton controlsButton = new IconButton(skin, controlsIcon);
        controlsButton.setSize(buttonHeight, buttonHeight);
        controlsButton.setPosition((-buttonWidth / 2) - buttonHeight - buttonSpacing, buttonYOffset + ((DeviceUtil.isAndroid() ? 1 : 0) * (buttonHeight + buttonSpacing)));
        controlsButton.addListener(new NoisyClickListener(controlsButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ControlsMenu cm = MainGame.game.getControlsMenu();
                cm.setParent(MainMenu.this);
                MainGame.game.setScreen(cm);
            }
        });
        stageAbove.addActor(controlsButton);

        if (DeviceUtil.isDesktop()) {
            TextButton exitButton = new TextButton("Exit", skin, "default");
            exitButton.setSize(buttonWidth, buttonHeight);
            exitButton.setPosition(-buttonWidth / 2, buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
            exitButton.addListener(new NoisyClickListener(exitButton) {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });
            stageAbove.addActor(exitButton);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageAbove);

        if (!music.isPlaying()) {
            if (new Random().nextInt(1000) == 0) {
                Music shite = MainGame.game.getAssets().getMusic(Asset.SHITE);
                shite.setOnCompletionListener(new MainGame.RemovableOnCompletionListener() {
                    @Override
                    public void onCompletion(Music garbag) {
                        super.onCompletion(music);
                        MainGame.game.playMusic(music);
                    }
                });
                MainGame.game.playMusic(shite);
            } else {
                MainGame.game.playMusic(music);
            }
        }

        if (DeviceUtil.isAndroid()) {
            viewport.getCamera().position.x = 0;
            viewport.getCamera().position.y = 13;
        }
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
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;
    }

    @Override
    public void dispose() {
        stageAbove.dispose();
        stageBelow.dispose();
        music.dispose();

        joinMenu.dispose();
        hostMenu.dispose();
    }
}
