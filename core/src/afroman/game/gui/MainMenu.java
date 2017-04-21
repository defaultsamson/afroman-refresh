package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.FlickeringLight;
import afroman.game.gui.components.IconButton;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.util.PhysicsUtil;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

/**
 * Created by Samson on 2017-04-08.
 */
public class MainMenu implements Screen {

    public static FlickeringLight menuLight;
    public static RayHandler menuRayHandler;
    public static World menuWorld;

    private final OptionsMenu settingsMenu;
    private final JoinMenu joinMenu;

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;
    /**
     * The stage below the lighting.
     */
    private Stage stageBelow;

    private Music music;
    private Label fpsCounter;

    public MainMenu() {
        menuWorld = new World(new Vector2(0, 0F), true);
        menuRayHandler = new RayHandler(menuWorld);
        menuRayHandler.setBlurNum(1);
        menuRayHandler.setAmbientLight(0.3F);
        menuLight = new FlickeringLight(0.03F, 80, 100, menuRayHandler, 10, new Color(0F, 0F, 0F, 1F), 0, 20);

        settingsMenu = new OptionsMenu(this);
        joinMenu = new JoinMenu(this);

        Skin skin = MainGame.game.getAssets().getSkin(Asset.AFRO_SKIN);

        final ScreenViewport viewport = MainGame.createStandardViewport();

        stageAbove = new Stage(viewport);
        stageBelow = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        int manY = 18;
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

        int buttonYOffset = -48;
        int buttonSpacing = 6;

        music = MainGame.game.getAssets().getMusic(Asset.MENU_MUSIC);
        music.setLooping(true);
        music.setVolume(1.0F);

        fpsCounter = new Label("FPS: 0", skin);
        fpsCounter.setSize(buttonWidth, buttonHeight);
        fpsCounter.setPosition(-100, 0);
        stageAbove.addActor(fpsCounter);

        final Label title = new Label("The Adventures of Afro Man", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        final Sound buttonUp = MainGame.game.getAssets().getSound(Asset.BUTTON_UP);
        final Sound buttonDown = MainGame.game.getAssets().getSound(Asset.BUTTON_DOWN);

        TextButton joinButton = new TextButton("Join", skin, "default");
        joinButton.setSize(buttonWidth, buttonHeight);
        joinButton.setPosition(-buttonWidth / 2, buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        joinButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(joinMenu);
            }
        });
        stageAbove.addActor(joinButton);

        TextButton hostButton = new TextButton("Host", skin, "default");
        hostButton.setSize(buttonWidth, buttonHeight);
        hostButton.setPosition(-buttonWidth / 2, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        hostButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
            }
        });
        stageAbove.addActor(hostButton);

        Texture settingsIcon = MainGame.game.getAssets().getTexture(Asset.SETTINGS_ICON);
        IconButton settingsButton = new IconButton(skin, settingsIcon);
        settingsButton.setSize(buttonHeight, buttonHeight);
        settingsButton.setPosition((-buttonWidth / 2) - buttonHeight - buttonSpacing, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        settingsButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(settingsMenu);
            }
        });
        stageAbove.addActor(settingsButton);

        TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(-buttonWidth / 2, buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        exitButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stageAbove.addActor(exitButton);
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
    }

    @Override
    public void render(float delta) {

        fpsCounter.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

        stageBelow.act(delta);
        stageBelow.draw();

        PhysicsUtil.stepWorld(menuWorld, delta);

        menuRayHandler.setCombinedMatrix((OrthographicCamera) stageAbove.getCamera());
        menuRayHandler.updateAndRender();

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
        stageBelow.dispose();
        menuWorld.dispose();
        menuRayHandler.dispose();
        music.dispose();
        settingsMenu.dispose();
    }
}
