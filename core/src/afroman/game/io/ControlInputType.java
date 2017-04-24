package afroman.game.io;

/**
 * Created by Samson on 2017-04-23.
 */
public enum ControlInputType {
    KEYBOARD_BUTTON,
    MOUSE_BUTTON,
    CONTROLLER_BUTTON,
    CONTROLLER_AXIS;

    public static ControlInputType safeValueOf(String string) {
        try {
            return valueOf(string);
        } catch (Exception e) {
            return null;
        }
    }
}
