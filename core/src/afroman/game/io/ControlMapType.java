package afroman.game.io;

import com.badlogic.gdx.Input;

/**
 * Created by Samson on 2017-04-23.
 */
public enum ControlMapType {
    UP(Setting.CONTROLS_KEYBOARD_UP_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_UP_ID, Input.Keys.W, Setting.CONTROLS_CONTROLLER_UP_TYPE, ControlInputType.CONTROLLER_AXIS, Setting.CONTROLS_CONTROLLER_UP_ID, ControllerMap.Axis.AXIS_LEFT_Y),
    DOWN(Setting.CONTROLS_KEYBOARD_DOWN_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_DOWN_ID, Input.Keys.S, Setting.CONTROLS_CONTROLLER_DOWN_TYPE, ControlInputType.CONTROLLER_AXIS, Setting.CONTROLS_CONTROLLER_DOWN_ID, ControllerMap.Axis.AXIS_LEFT_Y),
    LEFT(Setting.CONTROLS_KEYBOARD_LEFT_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_LEFT_ID, Input.Keys.A, Setting.CONTROLS_CONTROLLER_LEFT_TYPE, ControlInputType.CONTROLLER_AXIS, Setting.CONTROLS_CONTROLLER_LEFT_ID, ControllerMap.Axis.AXIS_LEFT_X),
    RIGHT(Setting.CONTROLS_KEYBOARD_RIGHT_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_RIGHT_ID, Input.Keys.D, Setting.CONTROLS_CONTROLLER_RIGHT_TYPE, ControlInputType.CONTROLLER_AXIS, Setting.CONTROLS_CONTROLLER_RIGHT_ID, ControllerMap.Axis.AXIS_LEFT_X),
    INTERACT(Setting.CONTROLS_KEYBOARD_INTERACT_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_INTERACT_ID, Input.Keys.E, Setting.CONTROLS_CONTROLLER_INTERACT_TYPE, ControlInputType.CONTROLLER_BUTTON, Setting.CONTROLS_CONTROLLER_INTERACT_ID, ControllerMap.Buttons.A),
    NEXT_ITEM(Setting.CONTROLS_KEYBOARD_NEXT_ITEM_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_NEXT_ITEM_ID, Input.Keys.R, Setting.CONTROLS_CONTROLLER_NEXT_ITEM_TYPE, ControlInputType.CONTROLLER_BUTTON, Setting.CONTROLS_CONTROLLER_NEXT_ITEM_ID, ControllerMap.Buttons.RB),
    PREV_ITEM(Setting.CONTROLS_KEYBOARD_PREV_ITEM_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_PREV_ITEM_ID, Input.Keys.Q, Setting.CONTROLS_CONTROLLER_PREV_ITEM_TYPE, ControlInputType.CONTROLLER_BUTTON, Setting.CONTROLS_CONTROLLER_PREV_ITEM_ID, ControllerMap.Buttons.LB),
    DROP_ITEM(Setting.CONTROLS_KEYBOARD_DROP_ITEM_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_DROP_ITEM_ID, Input.Keys.X, Setting.CONTROLS_CONTROLLER_DROP_ITEM_TYPE, ControlInputType.CONTROLLER_BUTTON, Setting.CONTROLS_CONTROLLER_DROP_ITEM_ID, ControllerMap.Buttons.Y),
    USE_ITEM(Setting.CONTROLS_KEYBOARD_USE_ITEM_TYPE, ControlInputType.KEYBOARD_BUTTON, Setting.CONTROLS_KEYBOARD_USE_ITEM_ID, Input.Keys.SPACE, Setting.CONTROLS_CONTROLLER_USE_ITEM_TYPE, ControlInputType.CONTROLLER_BUTTON, Setting.CONTROLS_CONTROLLER_USE_ITEM_ID, ControllerMap.Buttons.X);

    private Setting keyboardTypeSetting;
    private ControlInputType defaultKeyboardType;
    private Setting keyboardIDSetting;
    private int defaultKeyboardID;
    private Setting controllerTypeSetting;
    private ControlInputType defaultControllerType;
    private Setting controllerIDSetting;
    private int defaultControllerID;

    ControlMapType(Setting keyboardType, ControlInputType defaultKeyboardType, Setting keyboardID, int defaultKeyboardID, Setting controllerType, ControlInputType defaultControllerType, Setting controllerID, int defaultControllerID) {
        this.keyboardTypeSetting = keyboardType;
        this.defaultKeyboardType = defaultKeyboardType;
        this.keyboardIDSetting = keyboardID;
        this.defaultKeyboardID = defaultKeyboardID;
        this.controllerTypeSetting = controllerType;
        this.defaultControllerType = defaultControllerType;
        this.controllerIDSetting = controllerID;
        this.defaultControllerID = defaultControllerID;
    }

    public Setting getKeyboardTypeSetting() {
        return keyboardTypeSetting;
    }

    public ControlInputType getDefaultKeyboardType() {
        return defaultKeyboardType;
    }

    public Setting getKeyboardIDSetting() {
        return keyboardIDSetting;
    }

    public int getDefaultKeyboardID() {
        return defaultKeyboardID;
    }

    public Setting getControllerTypeSetting() {
        return controllerTypeSetting;
    }

    public ControlInputType getDefaultControllerType() {
        return defaultControllerType;
    }

    public Setting getControllerIDSetting() {
        return controllerIDSetting;
    }

    public int getDefaultControllerID() {
        return defaultControllerID;
    }
}
