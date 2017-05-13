package afroman.game.io;

/**
 * Created by Samson on 2017-04-24.
 */
public abstract class ControlInput {
    protected boolean hasBeenPressed = false;

    private boolean updateConstantly;
    private boolean expectingAxisNegative;
    private final ControlMapType mapType;
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

    public abstract void performAction(float analogueValue);

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

    public void setControllerId(int controllerId) {
        this.controllerId = controllerId;
    }

    public void setExpectingAxisNegative(boolean expectingAxisNegative) {
        this.expectingAxisNegative = expectingAxisNegative;
    }

    public void setKeyboardId(int keyboardId) {
        this.keyboardId = keyboardId;
    }

    public void setControllerInputType(ControlInputType controllerInputType) {
        this.controllerInputType = controllerInputType;
    }

    public void setKeyboardInputType(ControlInputType keyboardInputType) {
        this.keyboardInputType = keyboardInputType;
    }
}
