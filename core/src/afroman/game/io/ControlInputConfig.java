package afroman.game.io;

import afroman.game.MainGame;

/**
 * Created by Samson on 2017-04-23.
 */
public class ControlInputConfig {
    public ControlInputConfig() {

    }

    public ControlInputConfig(ControlMapType mapType) {
        this.mapType = mapType;

        keyboardType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(mapType.getKeyboardTypeSetting()));
        keyboardType = keyboardType != null ? keyboardType : mapType.getDefaultKeyboardType(); // Gets a default if it returned null
        keyboardId = MainGame.game.getSettings().getInteger(mapType.getKeyboardIDSetting(), mapType.getDefaultKeyboardID());
        controllerType = ControlInputType.safeValueOf(MainGame.game.getSettings().getString(mapType.getControllerTypeSetting()));
        controllerType = controllerType != null ? controllerType : mapType.getDefaultControllerType(); // Gets a default if it returned null
        controllerId = MainGame.game.getSettings().getInteger(mapType.getControllerIDSetting(), mapType.getDefaultControllerID());
    }

    public ControlMapType mapType;
    public boolean updateConstantly;
    public boolean axisExpectingNegative = false;
    public ControlInputType keyboardType;
    public ControlInputType controllerType;
    public int controllerId;
    public int keyboardId;
}
