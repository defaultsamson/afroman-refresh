package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.gui.components.HierarchicalMenu;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.io.ControlInput;
import afroman.game.io.ControlInputType;
import afroman.game.io.ControlMapType;
import afroman.game.io.ControllerMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import static afroman.game.gui.components.GuiConstants.skin;
import static afroman.game.gui.components.GuiConstants.viewport;

/**
 * Created by Samson on 2017-04-08.
 */
public class ControlsMenu extends HierarchicalMenu {

    private ControlResetConfirm confirmPage;

    /**
     * The stage above the lighting.
     */
    private Stage stageAbove;

    private ShapeRenderer shapeRenderer;
    private Color bgColour;

    private Label title;

    private TextButton nextItemButton;
    private TextButton prevItemButton;
    private TextButton dropItemButton;
    private TextButton useItemButton;
    private TextButton resetControls;

    private TextButton upButton;
    private TextButton downButton;
    private TextButton leftButton;
    private TextButton rightButton;
    private TextButton interactButton;

    private TextButton exitButton;
    private TextButton cancelButton;

    private boolean isUsingControllerScheme;

    public ControlsMenu(Screen parentScreen) {
        super(parentScreen);
        stageAbove = new Stage(viewport);
        viewport.getCamera().position.x = 0;
        viewport.getCamera().position.y = 0;

        shapeRenderer = new ShapeRenderer();
        bgColour = new Color(0, 0, 0, 0.7F);

        int buttonWidth = 112;
        int buttonHeight = 14;

        int buttonYOffset = -61;
        int buttonYSpacing = 4;
        int buttonXSpacing = 6;

        final boolean allowButtonHotswitching = true;

        title = new Label("Controls", skin);
        title.setSize(buttonWidth, buttonHeight);
        title.setPosition(-buttonWidth / 2, buttonYOffset + (6 * (buttonHeight + buttonYSpacing)));
        title.setAlignment(Align.center);
        stageAbove.addActor(title);

        nextItemButton = new TextButton("Next Item: ", skin);
        nextItemButton.setSize(buttonWidth, buttonHeight);
        nextItemButton.setPosition(buttonXSpacing / 2, buttonYOffset + (5 * (buttonHeight + buttonYSpacing)));
        nextItemButton.addListener(new NoisyClickListener(nextItemButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !nextItemButton.isDisabled()) {
                    getNewControl(ControlMapType.NEXT_ITEM, nextItemButton);
                }
            }
        });
        stageAbove.addActor(nextItemButton);

        prevItemButton = new TextButton("Prev Item: ", skin);
        prevItemButton.setSize(buttonWidth, buttonHeight);
        prevItemButton.setPosition(buttonXSpacing / 2, buttonYOffset + (4 * (buttonHeight + buttonYSpacing)));
        prevItemButton.addListener(new NoisyClickListener(prevItemButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !prevItemButton.isDisabled()) {
                    getNewControl(ControlMapType.PREV_ITEM, prevItemButton);
                }
            }
        });
        stageAbove.addActor(prevItemButton);

        dropItemButton = new TextButton("Drop Item: ", skin);
        dropItemButton.setSize(buttonWidth, buttonHeight);
        dropItemButton.setPosition(buttonXSpacing / 2, buttonYOffset + (3 * (buttonHeight + buttonYSpacing)));
        dropItemButton.addListener(new NoisyClickListener(dropItemButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !dropItemButton.isDisabled()) {
                    getNewControl(ControlMapType.DROP_ITEM, dropItemButton);
                }
            }
        });
        stageAbove.addActor(dropItemButton);

        useItemButton = new TextButton("Use Item: ", skin);
        useItemButton.setSize(buttonWidth, buttonHeight);
        useItemButton.setPosition(buttonXSpacing / 2, buttonYOffset + (2 * (buttonHeight + buttonYSpacing)));
        useItemButton.addListener(new NoisyClickListener(useItemButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !useItemButton.isDisabled()) {
                    getNewControl(ControlMapType.USE_ITEM, useItemButton);
                }
            }
        });
        stageAbove.addActor(useItemButton);

        resetControls = new TextButton("Reset Controls", skin);
        resetControls.setSize(buttonWidth, buttonHeight);
        resetControls.setPosition(buttonXSpacing / 2, buttonYOffset + (1 * (buttonHeight + buttonYSpacing)));
        resetControls.addListener(new NoisyClickListener(resetControls) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!resetControls.isDisabled()) {
                    MainGame.game.setScreen(confirmPage);
                }
            }
        });
        stageAbove.addActor(resetControls);

        upButton = new TextButton("Up: ", skin);
        upButton.setSize(buttonWidth, buttonHeight);
        upButton.setPosition(-buttonWidth - (buttonXSpacing / 2), buttonYOffset + (5 * (buttonHeight + buttonYSpacing)));
        upButton.addListener(new NoisyClickListener(upButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !upButton.isDisabled()) {
                    getNewControl(ControlMapType.UP, upButton);
                }
            }
        });
        stageAbove.addActor(upButton);

        downButton = new TextButton("Down: ", skin);
        downButton.setSize(buttonWidth, buttonHeight);
        downButton.setPosition(-buttonWidth - (buttonXSpacing / 2), buttonYOffset + (4 * (buttonHeight + buttonYSpacing)));
        downButton.addListener(new NoisyClickListener(downButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !downButton.isDisabled()) {
                    getNewControl(ControlMapType.DOWN, downButton);
                }
            }
        });
        stageAbove.addActor(downButton);

        leftButton = new TextButton("Left: ", skin);
        leftButton.setSize(buttonWidth, buttonHeight);
        leftButton.setPosition(-buttonWidth - (buttonXSpacing / 2), buttonYOffset + (3 * (buttonHeight + buttonYSpacing)));
        leftButton.addListener(new NoisyClickListener(leftButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !leftButton.isDisabled()) {
                    getNewControl(ControlMapType.LEFT, leftButton);
                }
            }
        });
        stageAbove.addActor(leftButton);

        rightButton = new TextButton("Right: ", skin);
        rightButton.setSize(buttonWidth, buttonHeight);
        rightButton.setPosition(-buttonWidth - (buttonXSpacing / 2), buttonYOffset + (2 * (buttonHeight + buttonYSpacing)));
        rightButton.addListener(new NoisyClickListener(rightButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !rightButton.isDisabled()) {
                    getNewControl(ControlMapType.RIGHT, rightButton);
                }
            }
        });
        stageAbove.addActor(rightButton);

        interactButton = new TextButton("Interact: ", skin);
        interactButton.setSize(buttonWidth, buttonHeight);
        interactButton.setPosition(-buttonWidth - (buttonXSpacing / 2), buttonYOffset + (1 * (buttonHeight + buttonYSpacing)));
        interactButton.addListener(new NoisyClickListener(interactButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (allowButtonHotswitching || !interactButton.isDisabled()) {
                    getNewControl(ControlMapType.INTERACT, interactButton);
                }
            }
        });
        stageAbove.addActor(interactButton);

        exitButton = new TextButton("Back", skin);
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(-buttonWidth / 2, buttonYOffset + (0 * (buttonHeight + buttonYSpacing)));
        exitButton.addListener(new NoisyClickListener(exitButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!exitButton.isDisabled()) {
                    gotoParentScreen();
                }
            }
        });
        stageAbove.addActor(exitButton);

        cancelButton = new TextButton("Cancel", skin);
        cancelButton.setVisible(false);
        cancelButton.setSize(buttonWidth, buttonHeight);
        cancelButton.setPosition(-buttonWidth / 2, buttonYOffset + (0 * (buttonHeight + buttonYSpacing)));
        cancelButton.addListener(new NoisyClickListener(cancelButton) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                endNewControlSetting();
            }
        });
        stageAbove.addActor(cancelButton);

        confirmPage = new ControlResetConfirm(this);
    }

    private String getBindingText(ControlInput input) {
        int id = isUsingControllerScheme ? input.getControllerID() : input.getKeyboardID();
        String toRet;

        switch (isUsingControllerScheme ? input.getControllerInputType() : input.getKeyboardInputType()) {
            case CONTROLLER_AXIS:
                toRet = ControllerMap.Axis.toString(id);
                return (input.isExpectingAxisNegative() ? "- " : "+ ") + (toRet != null ? toRet : "" + id);
            case CONTROLLER_BUTTON:
                toRet = ControllerMap.Buttons.toString(id);
                return toRet != null ? toRet : "" + id;
            case KEYBOARD_BUTTON:
                toRet = Input.Keys.toString(id);
                return toRet != null ? toRet : "" + id;
            case MOUSE_BUTTON:
                switch (id) {
                    case Input.Buttons.LEFT:
                        return "Left MB";
                    case Input.Buttons.RIGHT:
                        return "Right MB";
                    case Input.Buttons.MIDDLE:
                        return "Middle MB";
                    case Input.Buttons.BACK:
                        return "Back MB";
                    case Input.Buttons.FORWARD:
                        return "Forward MB";
                }
                return "Unknown MB";
        }
        return null;
    }

    private void updateButtonText() {
        title.setText("Controls (" + (isUsingControllerScheme && MainGame.game.getControls().isUsingController() ? MainGame.game.getControls().getController().getName() : "Keyboard") + ")");

        nextItemButton.setText("Next Item: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.NEXT_ITEM)));
        prevItemButton.setText("Prev Item: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.PREV_ITEM)));
        dropItemButton.setText("Drop Item: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.DROP_ITEM)));
        useItemButton.setText("Use Item: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.USE_ITEM)));

        upButton.setText("Up: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.UP)));
        downButton.setText("Down: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.DOWN)));
        leftButton.setText("Left: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.LEFT)));
        rightButton.setText("Right: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.RIGHT)));
        interactButton.setText("Interact: " + getBindingText(MainGame.game.getControls().getControlInput(ControlMapType.INTERACT)));
    }

    private void endNewControlSetting() {
        for (Actor a : stageAbove.getActors()) {
            if (a instanceof Button) {
                ((Button) a).setDisabled(false);
            }
        }

        updatingControlMap = null;

        exitButton.setVisible(true);
        exitButton.setDisabled(false);
        cancelButton.setVisible(false);
    }

    private ControlMapType updatingControlMap = null;

    private void getNewControl(ControlMapType type, Button button) {
        for (Actor a : stageAbove.getActors()) {
            if (a instanceof Button) {
                ((Button) a).setDisabled(true);
            }
        }

        updatingControlMap = type;

        button.setDisabled(false);
        cancelButton.setVisible(true);
        cancelButton.setDisabled(false);
        exitButton.setVisible(false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stageAbove);
        isUsingControllerScheme = MainGame.game.getControls().isUsingController();
        updateButtonText();
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

        if (MainGame.game.getControls().isUsingController() && !isUsingControllerScheme) {
            // Switch to controller scheme
            isUsingControllerScheme = true;
            updateButtonText();
        } else if (!MainGame.game.getControls().isUsingController() && isUsingControllerScheme) {
            // Switch to Keyboard scheme
            isUsingControllerScheme = false;
            updateButtonText();
        }

        if (updatingControlMap != null) {

            // If a button's being clicked, don't try and set a control to Left Mouse Button
            boolean isButtonPressed = false;
            for (Actor a : stageAbove.getActors()) {
                if (a instanceof Button) {
                    if (((Button) a).isPressed()) {
                        isButtonPressed = true;
                        break;
                    }
                }
            }

            if (!isButtonPressed) updateControlSettingProcess();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gotoParentScreen();
        }

        stageAbove.act(delta);
        stageAbove.draw();
    }

    public void setParent(Screen screen) {
        this.parentScreen = screen;
    }

    private void updateControlSettingProcess() {
        // Test for keyboard controls
        for (int i = 0; i < 256; i++) {
            if (Gdx.input.isKeyJustPressed(i)) {
                // Setup as a keybind
                ControlInput input = MainGame.game.getControls().getControlInput(updatingControlMap);
                if (isUsingControllerScheme) {
                    input.setControllerInputType(ControlInputType.KEYBOARD_BUTTON);
                    input.setControllerId(i);
                } else {
                    input.setKeyboardInputType(ControlInputType.KEYBOARD_BUTTON);
                    input.setKeyboardId(i);
                }
                updatingControlMap = null;
                endNewControlSetting();
                updateButtonText();
                return;
            }
        }

        // Test for mouse controls
        for (int i = 0; i < 8; i++) {
            if (Gdx.input.isButtonPressed(i)) {
                // Setup as a keybind
                ControlInput input = MainGame.game.getControls().getControlInput(updatingControlMap);
                if (isUsingControllerScheme) {
                    input.setControllerInputType(ControlInputType.MOUSE_BUTTON);
                    input.setControllerId(i);
                } else {
                    input.setKeyboardInputType(ControlInputType.MOUSE_BUTTON);
                    input.setKeyboardId(i);
                }
                updatingControlMap = null;
                endNewControlSetting();
                updateButtonText();
                return;
            }
        }

        // If a controller is plugged in
        if (MainGame.game.getControls().isUsingController()) {
            // Test for controller button controls
            for (int i = 0; i < 256; i++) {
                if (MainGame.game.getControls().getController().getButton(i)) {
                    // Setup as a keybind
                    ControlInput input = MainGame.game.getControls().getControlInput(updatingControlMap);
                    if (isUsingControllerScheme) {
                        input.setControllerInputType(ControlInputType.CONTROLLER_BUTTON);
                        input.setControllerId(i);
                    } else {
                        input.setKeyboardInputType(ControlInputType.CONTROLLER_BUTTON);
                        input.setKeyboardId(i);
                    }
                    updatingControlMap = null;
                    endNewControlSetting();
                    updateButtonText();
                    return;
                }
            }

            // Test for controller axis controls
            for (int i = 0; i < 255; i++) {
                float axisValue = MainGame.game.getControls().getSafeAxisValue(i);
                if (Math.abs(axisValue) > 0.5) {
                    // Setup as a keybind
                    ControlInput input = MainGame.game.getControls().getControlInput(updatingControlMap);
                    if (isUsingControllerScheme) {
                        input.setControllerInputType(ControlInputType.CONTROLLER_AXIS);
                        input.setControllerId(i);
                    } else {
                        input.setKeyboardInputType(ControlInputType.CONTROLLER_AXIS);
                        input.setKeyboardId(i);
                    }
                    input.setExpectingAxisNegative(axisValue < 0);
                    updatingControlMap = null;
                    endNewControlSetting();
                    updateButtonText();
                    return;
                }
            }
        }
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
        // Saves the controls
        for (ControlMapType type : ControlMapType.values()) {
            ControlInput input = MainGame.game.getControls().getControlInput(type);
            MainGame.game.getSettings().putString(type.getKeyboardTypeSetting(), input.getKeyboardInputType().name());
            MainGame.game.getSettings().putInteger(type.getKeyboardIDSetting(), input.getKeyboardID());
            MainGame.game.getSettings().putString(type.getControllerTypeSetting(), input.getControllerInputType().name());
            MainGame.game.getSettings().putInteger(type.getControllerIDSetting(), input.getControllerID());
        }
    }

    @Override
    public void dispose() {
        stageAbove.dispose();
        shapeRenderer.dispose();

        confirmPage.dispose();
    }
}
