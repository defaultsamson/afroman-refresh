package afroman.game.io;

import afroman.game.FinalConstants;
import afroman.game.MainGame;
import afroman.game.gui.PlayScreen;
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
                if (MainGame.game.getScreen() instanceof PlayScreen) {
                    ((PlayScreen) MainGame.game.getScreen()).jump(analogueValue);
                }
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
        });

        ControlInputConfig left = new ControlInputConfig(ControlMapType.LEFT);
        left.updateConstantly = true;
        left.axisExpectingNegative = true;
        controlInputs.add(new ControlInput(left) {
            @Override
            public void performAction(float analogueValue) {
                // System.out.println("Moving Left: " + analogueValue);
                if (MainGame.game.getScreen() instanceof PlayScreen) {
                    ((PlayScreen) MainGame.game.getScreen()).left(analogueValue);
                }
            }
        });

        ControlInputConfig right = new ControlInputConfig(ControlMapType.RIGHT);
        right.updateConstantly = true;
        right.axisExpectingNegative = false;
        controlInputs.add(new ControlInput(right) {
            @Override
            public void performAction(float analogueValue) {
                //System.out.println("Moving Right: " + analogueValue);
                if (MainGame.game.getScreen() instanceof PlayScreen) {
                    ((PlayScreen) MainGame.game.getScreen()).right(analogueValue);
                }
            }
        });

        ControlInputConfig interact = new ControlInputConfig(ControlMapType.INTERACT);
        controlInputs.add(new ControlInput(interact) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Interact");
            }
        });

        ControlInputConfig nextItem = new ControlInputConfig(ControlMapType.NEXT_ITEM);
        controlInputs.add(new ControlInput(nextItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Next Item");
            }
        });

        ControlInputConfig prevItem = new ControlInputConfig(ControlMapType.PREV_ITEM);
        controlInputs.add(new ControlInput(prevItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Prev Item");
            }
        });

        ControlInputConfig dropItem = new ControlInputConfig(ControlMapType.DROP_ITEM);
        controlInputs.add(new ControlInput(dropItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Drop Item");
            }
        });

        ControlInputConfig useItem = new ControlInputConfig(ControlMapType.USE_ITEM);
        controlInputs.add(new ControlInput(useItem) {
            @Override
            public void performAction(float analogueValue) {
                System.out.println("Use Item");
            }
        });

        triggerIDs = new ArrayList<Integer>();
    }

    public List<Integer> triggerIDs;

    public float getSafeAxisValue(int axisID) {
        if (isUsingController()) {
            float value = getController().getAxis(axisID);

            for (int id : triggerIDs) {
                if (axisID == id) {
                    value = (value + 1F) / 2F;
                    break;
                }
            }

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

    private void detectTriggerAxis() {
        if (isUsingController()) {
            triggerIDs.clear();
            // Test for controller axis controls
            for (int i = 0; i < 255; i++) {
                float axisValue = getController().getAxis(i);
                if (Math.abs(axisValue) > 0.5) {
                    // Setup as a keybind
                    triggerIDs.add(i);
                }
            }
        }
    }

    public void update() {
        if (isUsingController() && !isUsingControllerScheme) {
            // Switch to controller scheme
            isUsingControllerScheme = true;
            for (ControlInput c : controlInputs) {
                c.hasBeenPressed = false;
            }

            // Wait two seconds before checking for axis
            new Thread() {
                @Override
                public void run() {
                    super.run();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    detectTriggerAxis();
                }
            }.start();

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
                                c.performAction(1);
                            } else if (!c.hasBeenPressed) {
                                c.hasBeenPressed = true;
                                c.performAction(1);
                            }
                        } else {
                            c.hasBeenPressed = false;
                            if (c.isUpdateConstantly()) {
                                c.performAction(0);
                            }
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
                            if (Math.abs(value) > FinalConstants.analogueTriggerThreshold) { // Dead zone for detextion of
                                if (!c.hasBeenPressed) {
                                    c.hasBeenPressed = true;
                                    c.performAction(1);
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
                                c.performAction(1);
                            } else if (!c.hasBeenPressed) {
                                c.hasBeenPressed = true;
                                c.performAction(1);
                            }
                        } else {
                            c.hasBeenPressed = false;
                            if (c.isUpdateConstantly()) {
                                c.performAction(0);
                            }
                        }
                        break;
                    case MOUSE_BUTTON:
                        if (Gdx.input.isButtonPressed(c.getKeyboardID())) {
                            if (c.isUpdateConstantly()) {
                                c.performAction(1);
                            } else if (!c.hasBeenPressed) {
                                c.hasBeenPressed = true;
                                c.performAction(1);
                            }
                        } else {
                            c.hasBeenPressed = false;
                            if (c.isUpdateConstantly()) {
                                c.performAction(0);
                            }
                        }
                        break;
                }
            }
        }
    }

    public void dispose() {
        controlInputs.clear();
        Controllers.clearListeners();
    }
}