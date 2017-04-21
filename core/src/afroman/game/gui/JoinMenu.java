package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.HierarchicalMenu;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.util.PhysicsUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

    public JoinMenu(Screen parentScreen) {
        super(parentScreen);

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

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -48;
        int buttonSpacing = 6;

        /*music.play();
        music.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                System.out.println("Dank memes are made of bees");
            }
        });*/


        final Label title = new Label("The Adventures of Afro Man", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        TextButton joinButton = new TextButton("Join", skin, "default");
        joinButton.setSize(buttonWidth, buttonHeight);
        joinButton.setPosition(-buttonWidth / 2, buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        joinButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
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

        TextButton exitButton = new TextButton("Back", skin, "default");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(-buttonWidth / 2, buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        exitButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gotoParentScreen();
            }
        });
        stageAbove.addActor(exitButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageAbove);
    }

    @Override
    public void render(float delta) {

        stageBelow.act(delta);
        stageBelow.draw();

        PhysicsUtil.stepWorld(MainMenu.menuWorld, delta);

        MainMenu.menuRayHandler.setCombinedMatrix((OrthographicCamera) stageAbove.getCamera());
        MainMenu.menuRayHandler.updateAndRender();

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
    }
}
