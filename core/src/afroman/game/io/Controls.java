package afroman.game.io;

import com.badlogic.gdx.Gdx;
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

        ControlInputConfig up = new ControlInputConfig(ControlMapType.UP);
        up.updateConstantly = true;
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

        ControlInputConfig down = new ControlInputConfig(ControlMapType.DOWN);
        down.updateConstantly = true;
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

        ControlInputConfig left = new ControlInputConfig(ControlMapType.LEFT);
        left.updateConstantly = true;
        left.axisExpectingNegative = true;
        controlInputs.add(new ControlInput(left) {
            @Override
            public void performAction(float analogueValue) {
                //System.out.println("Moving Left: " + analogueValue);
            }

            @Override
            public void performAction() {
                performAction(-1F);
            }
        });

        ControlInputConfig right = new ControlInputConfig(ControlMapType.RIGHT);
        right.updateConstantly = true;
        right.axisExpectingNegative = false;
        controlInputs.add(new ControlInput(right) {
            @Override
            public void performAction(float analogueValue) {
                //System.out.println("Moving Right: " + analogueValue);
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig interact = new ControlInputConfig(ControlMapType.INTERACT);
        controlInputs.add(new ControlInput(interact) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Interact");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig nextItem = new ControlInputConfig(ControlMapType.NEXT_ITEM);
        controlInputs.add(new ControlInput(nextItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Next Item");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig prevItem = new ControlInputConfig(ControlMapType.PREV_ITEM);
        controlInputs.add(new ControlInput(prevItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Prev Item");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig dropItem = new ControlInputConfig(ControlMapType.DROP_ITEM);
        controlInputs.add(new ControlInput(dropItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Drop Item");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });

        ControlInputConfig useItem = new ControlInputConfig(ControlMapType.USE_ITEM);
        controlInputs.add(new ControlInput(useItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Use Item");
            }

            @Override
            public void performAction() {
                performAction(1F);
            }
        });
    }

    public float getSafeAxisValue(int axisID) {
        if (isUsingController()) {
            float value = getController().getAxis(axisID);

            if (axisID == ControllerMap.Axis.AXIS_LEFT_TRIGGER || axisID == ControllerMap.Axis.AXIS_RIGHT_TRIGGER)
                value = (value + 1F) / 2F;

            return value;
        }

        return 0;
    }

    public ControlInput getControlInput(ControlMapType mapType) {
        for (ControlInput c : controlInputs) {
            if (c.getMapType() == mapType) return c;
        }
        return null;
    }

    public Controller getController() {
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
            System.out.println("Switching to ControllerMap scheme");
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
                        float value = getSafeAxisValue(c.getControllerID());

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
}