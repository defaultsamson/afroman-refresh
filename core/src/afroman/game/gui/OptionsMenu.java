package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.HierarchicalMenu;
import afroman.game.gui.components.RoundingSlider;
import afroman.game.io.Setting;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Samson on 2017-04-08.
 */
public class OptionsMenu extends HierarchicalMenu implements Screen {
    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;

    private ShapeRenderer shapeRenderer;
    private Color bgColour;

    public OptionsMenu(final Screen parentScreen) {
        super(parentScreen);

        Skin skin = MainGame.game.getAssets().getSkin(Asset.AFRO_SKIN);

        final ScreenViewport viewport = MainGame.createStandardViewport();

        stageAbove = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        shapeRenderer = new ShapeRenderer();
        bgColour = new Color(0, 0, 0, 0.7F);

        int buttonWidth = 102;
        int buttonHeight = 16;

        int buttonYOffset = -48;
        int buttonSpacing = 6;

        final Label title = new Label("Settings", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        final Label musicLabel = new Label("Music: ", skin);
        musicLabel.setSize(buttonWidth, buttonHeight);
        musicLabel.setTouchable(Touchable.disabled);
        musicLabel.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        musicLabel.setAlignment(Align.center);

        final RoundingSlider musicSlider = new RoundingSlider(0.0F, 100.0F, 1F, 1F, false, skin);
        musicSlider.setSize(buttonWidth, buttonHeight);
        musicSlider.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        musicSlider.setValue(MainGame.game.getSettings().getFloat(Setting.MUSIC, 1F) * 100F);
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                musicLabel.setText("Music: " + (int) musicSlider.getValue());
                MainGame.game.setMusicVolume(musicSlider.getValue() / 100F);
            }
        });
        musicLabel.setText("Music: " + (int) musicSlider.getValue());
        stageAbove.addActor(musicSlider);
        stageAbove.addActor(musicLabel);


        final Label sfxLabel = new Label("SFX: ", skin);
        sfxLabel.setSize(buttonWidth, buttonHeight);
        sfxLabel.setTouchable(Touchable.disabled);
        sfxLabel.setPosition(buttonSpacing / 2, buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        sfxLabel.setAlignment(Align.center);

        final RoundingSlider sfxSlider = new RoundingSlider(0.0F, 100.0F, 1F, 1F, false, skin);
        sfxSlider.setSize(buttonWidth, buttonHeight);
        sfxSlider.setPosition(buttonSpacing / 2, buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        sfxSlider.setValue(MainGame.game.getSettings().getFloat(Setting.MUSIC, 1F) * 100F);
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sfxLabel.setText("SFX: " + (int) sfxSlider.getValue());
                MainGame.game.setSfxVolume(sfxSlider.getValue() / 100F);
            }
        });
        sfxLabel.setText("SFX: " + (int) sfxSlider.getValue());
        stageAbove.addActor(sfxSlider);
        stageAbove.addActor(sfxLabel);


        /*
        CleanTextField textField = new CleanTextField("Ripperoni", skin);
        textField.setSize(buttonWidth, buttonHeight);
        textField.setPosition((-buttonWidth / 2) - buttonWidth - buttonSpacing, buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        stageAbove.addActor(textField);*/

        final Label scaleLabel = new Label("Scale: ", skin);
        scaleLabel.setSize((buttonWidth * 2) + buttonSpacing, buttonHeight);
        scaleLabel.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        scaleLabel.setAlignment(Align.center);

        final RoundingSlider scaleSlider = new RoundingSlider(1.0F, 10.0F, 0.1F, 10F, false, skin);
        scaleSlider.setSize((buttonWidth * 2) + buttonSpacing, buttonHeight);
        scaleSlider.setTouchable(Touchable.disabled);
        scaleSlider.setPosition(-buttonWidth - (buttonSpacing / 2), buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        scaleSlider.setValue(MainGame.game.getSettings().getFloat(Setting.SCALE));
        scaleSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                scaleLabel.setText("Scale: " + scaleSlider.getValue());
                MainGame.game.setScale(scaleSlider.getValue());
            }
        });

        // Upon dragging the text (what will appear to be dragging the bar)
        // Linearly scale the game based on how far the user drags their pointer
        // from the left to the right
        scaleLabel.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Upon first touching the button, it will get the initial scaling value to prevent choppiness in scaling
                startingScaleValue = scaleValue(x) - MainGame.game.getSettings().getFloat(Setting.SCALE);
                return super.touchDown(event, x, y, pointer, button);
            }

            private float startingScaleValue = 0;

            private float scaleValue(float x) {
                // The net x value for in-menuWorld coordinates
                float netX = (x + scaleLabel.getX() + viewport.getCamera().position.x + (viewport.getWorldWidth() / 2));
                // Converts the in-menuWorld x ordinate to an on-screen ordinate
                float screenNet = netX / viewport.getUnitsPerPixel();
                // Sets the slider to (max-min)*percent + min
                float sliderValue = ((scaleSlider.getMaxValue() - scaleSlider.getMinValue()) * (screenNet / (float) Gdx.graphics.getWidth())) + scaleSlider.getMinValue();

                return sliderValue;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);

                float sliderValue = scaleValue(x) - startingScaleValue;

                scaleSlider.setValue(sliderValue);
            }
        });

        scaleLabel.setText("Scale: " + scaleSlider.getValue());
        stageAbove.addActor(scaleSlider);
        stageAbove.addActor(scaleLabel);

        /*
        TextButton joinButton = new TextButton("Join", skin, "default");
        joinButton.setSize(buttonWidth, buttonHeight);
        joinButton.setPosition(-buttonWidth / 2, buttonYOffset + (1 * (buttonHeight + buttonSpacing)));
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        stageAbove.addActor(joinButton);*/

        TextButton exitButton = new TextButton("Done", skin, "default");
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(-buttonWidth / 2, buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.setScreen(parentScreen);// gotoParentScreen();
            }
        });
        stageAbove.addActor(exitButton);

        /*
        joinButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touched Up");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touched Down");
                return true;
            }
        });*/


        /*
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        Table main = new Table();
        main.setFillParent(true);

        // Create the tab buttons
        HorizontalGroup group = new HorizontalGroup();
        final Button tab1 = new TextButton("Tab1", skin, "toggle");
        final Button tab2 = new TextButton("Tab2", skin, "toggle");
        final Button tab3 = new TextButton("Tab3", skin, "toggle");
        group.addActor(tab1);
        group.addActor(tab2);
        group.addActor(tab3);
        main.add(group);
        main.row();*/
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

        if (stageAbove.getViewport() instanceof ScreenViewport) {
            ScreenViewport viewport = (ScreenViewport) stageAbove.getViewport();
            // Draws darkness in the background
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(bgColour);
            shapeRenderer.rect(camera.position.x - (viewport.getWorldWidth() / 2), camera.position.y - (viewport.getWorldHeight() / 2), viewport.getWorldWidth(), viewport.getWorldHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL10.GL_BLEND);
        }

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
        MainGame.game.getSettings().save();
    }

    @Override
    public void dispose() {
        stageAbove.dispose();
        shapeRenderer.dispose();
    }
}
