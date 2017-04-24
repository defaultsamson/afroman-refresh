package afroman.game.io;

import afroman.game.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samson on 2017-04-23.
 */
public class Controls {

    private boolean isUsingControllerScheme;
    private List<ControlInput> controlInputs;

    public Controls() {
        isUsingControllerScheme = false;
        controlInputs = new ArrayList<ControlInput>();

        ControlInputConfig up = new ControlInputConfig();
        up.mapType = ControlMapType.UP;
        up.updateConstantly = true;
        up.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_UP_TYPE));
        up.keyboardType = up.keyboardType != null ? up.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        up.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_UP_ID, Input.Keys.W);
        up.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_UP_TYPE));
        up.controllerType = up.controllerType != null ? up.controllerType : ControlInputType.CONTROLLER_AXIS; // Gets a default if it returned null
        up.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_UP_ID, 1);
        up.axisExpectingNegative = false;
        controlInputs.add(new ControlInput(up) {
            @Override
            public void performAction(float analogueValue) {
                //System.out.println("Moving Up: " + analogueValue);
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig down = new ControlInputConfig();
        down.mapType = ControlMapType.DOWN;
        down.updateConstantly = true;
        down.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_DOWN_TYPE));
        down.keyboardType = down.keyboardType != null ? down.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        down.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_DOWN_ID, Input.Keys.S);
        down.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_DOWN_TYPE));
        down.controllerType = down.controllerType != null ? down.controllerType : ControlInputType.CONTROLLER_AXIS; // Gets a default if it returned null
        down.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_DOWN_ID, 1);
        down.axisExpectingNegative = true;
        controlInputs.add(new ControlInput(down) {
            @Override
            public void performAction(float analogueValue) {
                //System.out.println("Moving Down: " + analogueValue);
            }

            @Override
            public void performAction() {
                performAction(-1F);
            }
        });

        ControlInputConfig left = new ControlInputConfig();
        left.mapType = ControlMapType.LEFT;
        left.updateConstantly = true;
        left.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_LEFT_TYPE));
        left.keyboardType = left.keyboardType != null ? left.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        left.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_LEFT_ID, Input.Keys.A);
        left.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_LEFT_TYPE));
        left.controllerType = left.controllerType != null ? left.controllerType : ControlInputType.CONTROLLER_AXIS; // Gets a default if it returned null
        left.axisExpectingNegative = true;
        left.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_LEFT_ID, 0);
        controlInputs.add(new ControlInput(left) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Moving Left: " + analogueValue);
            }

            @Override
            public void performAction() {
                performAction(-1F);
            }
        });

        ControlInputConfig right = new ControlInputConfig();
        right.mapType = ControlMapType.RIGHT;
        right.updateConstantly = true;
        right.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_RIGHT_TYPE));
        right.keyboardType = right.keyboardType != null ? right.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        right.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_RIGHT_ID, Input.Keys.D);
        right.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_RIGHT_TYPE));
        right.controllerType = right.controllerType != null ? right.controllerType : ControlInputType.CONTROLLER_AXIS; // Gets a default if it returned null
        right.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_RIGHT_ID, 0);
        right.axisExpectingNegative = false;
        controlInputs.add(new ControlInput(right) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Moving Right: " + analogueValue);
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig interact = new ControlInputConfig();
        interact.mapType = ControlMapType.INTERACT;
        interact.updateConstantly = false;
        interact.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_INTERACT_TYPE));
        interact.keyboardType = interact.keyboardType != null ? interact.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        interact.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_INTERACT_ID, Input.Keys.E);
        interact.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_INTERACT_TYPE));
        interact.controllerType = interact.controllerType != null ? interact.controllerType : ControlInputType.CONTROLLER_BUTTON; // Gets a default if it returned null
        interact.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_INTERACT_ID, 0); // TODO button A
        controlInputs.add(new ControlInput(interact) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Interact: ");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig nextItem = new ControlInputConfig();
        nextItem.mapType = ControlMapType.NEXT_ITEM;
        nextItem.updateConstantly = false;
        nextItem.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_NEXT_ITEM_TYPE));
        nextItem.keyboardType = nextItem.keyboardType != null ? nextItem.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        nextItem.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_NEXT_ITEM_ID, Input.Keys.R);
        nextItem.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_NEXT_ITEM_TYPE));
        nextItem.controllerType = nextItem.controllerType != null ? nextItem.controllerType : ControlInputType.CONTROLLER_BUTTON; // Gets a default if it returned null
        nextItem.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_NEXT_ITEM_ID, 0); // TODO Right bumper
        controlInputs.add(new ControlInput(nextItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Next Item: ");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig prevItem = new ControlInputConfig();
        prevItem.mapType = ControlMapType.PREV_ITEM;
        prevItem.updateConstantly = false;
        prevItem.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_PREV_ITEM_TYPE));
        prevItem.keyboardType = prevItem.keyboardType != null ? prevItem.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        prevItem.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_PREV_ITEM_ID, Input.Keys.Q);
        prevItem.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_PREV_ITEM_TYPE));
        prevItem.controllerType = prevItem.controllerType != null ? prevItem.controllerType : ControlInputType.CONTROLLER_BUTTON; // Gets a default if it returned null
        prevItem.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_PREV_ITEM_ID, 0); // TODO Left bumper
        controlInputs.add(new ControlInput(prevItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Prev Item: ");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig dropItem = new ControlInputConfig();
        dropItem.mapType = ControlMapType.DROP_ITEM;
        dropItem.updateConstantly = false;
        dropItem.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_DROP_ITEM_TYPE));
        dropItem.keyboardType = dropItem.keyboardType != null ? dropItem.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        dropItem.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_DROP_ITEM_ID, Input.Keys.Q);
        dropItem.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_DROP_ITEM_TYPE));
        dropItem.controllerType = dropItem.controllerType != null ? dropItem.controllerType : ControlInputType.CONTROLLER_BUTTON; // Gets a default if it returned null
        dropItem.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_DROP_ITEM_ID, 0); // TODO Y button
        controlInputs.add(new ControlInput(dropItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Prev Item: ");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig useItem = new ControlInputConfig();
        useItem.mapType = ControlMapType.USE_ITEM;
        useItem.updateConstantly = false;
        useItem.keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_KEYBOARD_USE_ITEM_TYPE));
        useItem.keyboardType = useItem.keyboardType != null ? useItem.keyboardType : ControlInputType.KEYBOARD_BUTTON; // Gets a default if it returned null
        useItem.keyboardId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_KEYBOARD_USE_ITEM_ID, Input.Keys.SPACE);
        useItem.controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(Setting.CONTROLS_CONTROLLER_USE_ITEM_TYPE));
        useItem.controllerType = useItem.controllerType != null ? useItem.controllerType : ControlInputType.CONTROLLER_BUTTON; // Gets a default if it returned null
        useItem.controllerId = MainGame.game.getSettings().getInteger(Setting.CONTROLS_CONTROLLER_USE_ITEM_ID, 0); // TODO Right bumper
        controlInputs.add(new ControlInput(useItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Prev Item: ");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });
    }

    public ControlInput getControlInput(ControlMapType mapType) {
        for (ControlInput c : controlInputs) {
            if (c.getMapType() == mapType) return c;
        }
        return null;
    }

    private Controller getController() {
        try {
            return Controllers.getControllers().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isUsingController() {
        return getController() != null;
    }

    public void update() {
        if (isUsingController() && !isUsingControllerScheme) {
            // Switch to controller scheme
            isUsingControllerScheme = true;
            for (ControlInput c : controlInputs) {
                c.hasBeenPressed = false;
            }
            System.out.println("Switching to Controller scheme");
        } else if (!isUsingController() && isUsingControllerScheme) {
            // Switch to Keyboard scheme
            isUsingControllerScheme = false;
            for (ControlInput c : controlInputs) {
                c.hasBeenPressed = false;
            }
            System.out.println("Switching to Keyboard scheme");
        }

        for (ControlInput c : controlInputs) {
            if (isUsingControllerScheme) {
                switch (c.getControllerInputType()) {
                    case CONTROLLER_BUTTON:
                        if (getController().getButton(c.getControllerID())) {
                            if (c.isUpdateConstantly()) {
                                c.performAction();
                            } else if (!c.hasBeenPressed) {
                                c.hasBeenPressed = true;
                                c.performAction();
                            }
                        } else {
                            c.hasBeenPressed = false;
                        }
                        break;
                    case CONTROLLER_AXIS:
                        float value = getController().getAxis(c.getControllerID());

                        if (c.isUpdateConstantly()) {
                            if ((value < 0 && c.isExpectingAxisNegative()) || (value > 0 && !c.isExpectingAxisNegative())) {
                                c.performAction(value);
                            } else {
                                c.performAction(0);
                            }

                        } else {
                            if (Math.abs(value) > 0.4) { // Dead zone for detextion of
                                if (!c.hasBeenPressed) {
                                    c.hasBeenPressed = true;
                                    c.performAction();
                                }
                            } else {
                                c.hasBeenPressed = false;
                            }
                        }
                        break;
                }
            } else {
                switch (c.getKeyboardInputType()) {
                    case KEYBOARD_BUTTON:
                        if (Gdx.input.isKeyPressed(c.getKeyboardID())) {
                            if (c.isUpdateConstantly()) {
                                c.performAction();
                            } else if (!c.hasBeenPressed) {
                                c.hasBeenPressed = true;
                                c.performAction();
                            }
                        } else {
                            c.hasBeenPressed = false;
                        }
                        break;
                    case MOUSE_BUTTON:
                        if (Gdx.input.isButtonPressed(c.getKeyboardID())) {
                            if (c.isUpdateConstantly()) {
                                c.performAction();
                            } else if (!c.hasBeenPressed) {
                                c.hasBeenPressed = true;
                                c.performAction();
                            }
                        } else {
                            c.hasBeenPressed = false;
                        }
                        break;
                }
            }
        }
    }

    private boolean hasConfiguredController = false;

    public boolean hasConfiguredController() {
        return hasConfiguredController;
    }

    public void focusInGameControls() {

    }
}

abstract class ControlInput {
    protected boolean hasBeenPressed = false;

    private boolean updateConstantly;
    private boolean expectingAxisNegative;
    private ControlMapType mapType;
    private ControlInputType keyboardInputType;
    private ControlInputType controllerInputType;
    private int controllerId;
    private int keyboardId;

    public ControlInput(ControlInputConfig config) {
        this(config.updateConstantly, config.axisExpectingNegative, config.mapType, config.keyboardType, config.controllerType, config.controllerId, config.keyboardId);
    }

    public ControlInput(boolean updateConstantly, boolean expectingAxisNegative, ControlMapType mapType, ControlInputType inputType, ControlInputType controllerInputType, int controllerId, int keyboardId) {
        this.updateConstantly = updateConstantly;
        this.expectingAxisNegative = expectingAxisNegative;
        this.mapType = mapType;
        this.keyboardInputType = inputType;
        this.controllerInputType = controllerInputType;
        this.controllerId = controllerId;
        this.keyboardId = keyboardId;
    }

    public ControlInputType getKeyboardInputType() {
        return keyboardInputType;
    }

    public int getKeyboardID() {
        return keyboardId;
    }

    public int getControllerID() {
        return controllerId;
    }

    public void set(ControlInputType type, int controllerId, int keyboardId) {
        this.keyboardInputType = type;
        this.controllerId = controllerId;
        this.keyboardId = keyboardId;
    }

    public abstract void performAction(float analogueValue);

    public abstract void performAction();

    public ControlMapType getMapType() {
        return mapType;
    }

    public boolean isUpdateConstantly() {
        return updateConstantly;
    }

    public ControlInputType getControllerInputType() {
        return controllerInputType;
    }

    public boolean isExpectingAxisNegative() {
        return expectingAxisNegative;
    }
}