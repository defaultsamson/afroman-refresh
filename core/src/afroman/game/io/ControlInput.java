package afroman.game.io;

/**
 * Created by Samson on 2017-04-24.
 */
public abstract class ControlInput {
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
