package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.io.Setting;
import box2dLight.PointLight;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Samson on 2017-04-08.
 */
public class OptionsMenu extends HierarchicalMenu implements CameraScreen {

    private boolean enableDebug = false;

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;
    /**
     * The stage below the lighting.
     */
    private Stage stageBelow;

    private Label fpsCounter;
    private Image img;

    private PointLight pointLight;

    public OptionsMenu(final Screen parentScreen) {
        super(parentScreen);

        //Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        Skin skin = new Skin(Gdx.files.internal("assets/skin/afro.json"));

        final ScreenViewport viewport = MainGame.createStandardViewport();

        stageAbove = new Stage(viewport);
        stageBelow = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        int buttonWidth = 72;
        int buttonHeight = 16;

        int buttonYOffset = -46;
        int buttonSpacing = 6;

        fpsCounter = new Label("FPS: 0", skin);
        fpsCounter.setSize(buttonWidth, buttonHeight);
        fpsCounter.setPosition(-100, 0);
        stageAbove.addActor(fpsCounter);

        final Label title = new Label("The Adventures of Afro Man", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (4 * (buttonHeight + buttonSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        final Label label = new Label("Scale: ", skin);
        label.setSize(buttonWidth, buttonHeight);
        label.setPosition(-buttonWidth / 2, buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        label.setAlignment(Align.center);

        final RoundingSlider slider = new RoundingSlider(1.0F, 10.0F, 0.1F, 10F, false, skin);
        slider.setSize(buttonWidth, buttonHeight);
        slider.setTouchable(Touchable.disabled);
        slider.setPosition(-buttonWidth / 2, buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        slider.setValue(MainGame.settings.getFloat(Setting.SCALE));
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                label.setText("Scale: " + slider.getValue());
                MainGame.game.setScale(slider.getValue());
            }
        });

        CleanTextField textField = new CleanTextField("Ripperoni", skin);
        textField.setSize(buttonWidth, buttonHeight);
        textField.setPosition((-buttonWidth / 2) - buttonWidth - buttonSpacing, buttonYOffset + (3 * (buttonHeight + buttonSpacing)));
        stageAbove.addActor(textField);

        // Upon dragging the text (what will appear to be dragging the bar)
        // Linearly scale the game based on how far the user drags their pointer
        // from the left to the right
        label.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Upon first touching the button, it will get the initial scaling value to prevent choppiness in scaling
                startingScaleValue = scaleValue(x) - MainGame.settings.getFloat(Setting.SCALE);
                return super.touchDown(event, x, y, pointer, button);
            }

            private float startingScaleValue = 0;

            private float scaleValue(float x) {
                // The net x value for in-world coordinates
                float netX = (x + label.getX() + viewport.getCamera().position.x + (viewport.getWorldWidth() / 2));
                // Converts the in-world x ordinate to an on-screen ordinate
                float screenNet = netX / viewport.getUnitsPerPixel();
                // Sets the slider to (max-min)*percent + min
                float sliderValue = ((slider.getMaxValue() - slider.getMinValue()) * (screenNet / (float) Gdx.graphics.getWidth())) + slider.getMinValue();

                return sliderValue;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);

                float sliderValue = scaleValue(x) - startingScaleValue;

                slider.setValue(sliderValue);
            }
        });

        label.setText("Scale: " + slider.getValue());
        stageAbove.addActor(slider);
        stageAbove.addActor(label);

        TextButton joinButton = new TextButton("Join", skin, "default");
        joinButton.setSize(buttonWidth, buttonHeight);
        joinButton.setPosition(-buttonWidth / 2, buttonYOffset + (2 * (buttonHeight + buttonSpacing)));
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
            }
        });
        stageAbove.addActor(joinButton);

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

        stageBelow.act(delta);
        stageBelow.draw();

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
    }

    @Override
    public OrthographicCamera getCamera() {
        return (OrthographicCamera) stageAbove.getCamera();
    }

    @Override
    public ScreenViewport getViewport() {
        return (ScreenViewport) stageAbove.getViewport();
    }
}
