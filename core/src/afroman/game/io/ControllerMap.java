package afroman.game.io;

/**
 * Created by Samson on 2017-04-24.
 */
public class ControllerMap {

    public static class Buttons {

        public static final int A = 0;
        public static final int B = 1;
        public static final int X = 2;
        public static final int Y = 3;
        public static final int LB = 4;
        public static final int RB = 5;
        public static final int BACK = 6;
        public static final int START = 7;
        public static final int LEFT_ANALOGUE = 8;
        public static final int RIGHT_ANALOGUE = 9;
        public static final int UP = 10;
        public static final int RIGHT = 11;
        public static final int DOWN = 12;
        public static final int LEFT = 13;

        public static String toString(int keycode) {
            switch (keycode) {
                default:
                    return null;
                case A:
                    return "A Button";
                case B:
                    return "B Button";
                case X:
                    return "X Button";
                case Y:
                    return "Y Button";
                case LB:
                    return "L Bumper";
                case RB:
                    return "R Bumper";
                case BACK:
                    return "Back";
                case START:
                    return "Start";
                case LEFT_ANALOGUE:
                    return "Left Stick";
                case RIGHT_ANALOGUE:
                    return "Right Stick";
                case UP:
                    return "D-Pad Up";
                case DOWN:
                    return "D-Pad Down";
                case LEFT:
                    return "D-Pad Left";
                case RIGHT:
                    return "D-Pad Right";
            }
        }
    }

    public static class Axis {

        public static final int AXIS_LEFT_X = 0;
        public static final int AXIS_LEFT_Y = 1;
        public static final int AXIS_RIGHT_X = 2;
        public static final int AXIS_RIGHT_Y = 3;
        public static final int AXIS_LEFT_TRIGGER = 4;
        public static final int AXIS_RIGHT_TRIGGER = 5;

        public static String toString(int keycode) {
            switch (keycode) {
                default:
                    return null;
                case AXIS_LEFT_X:
                    return "Left X Axis";
                case AXIS_LEFT_Y:
                    return "Left Y Axis";
                case AXIS_RIGHT_X:
                    return "Right X Axis";
                case AXIS_RIGHT_Y:
                    return "Right Y Axis";
                case AXIS_LEFT_TRIGGER:
                    return "Left Trigger";
                case AXIS_RIGHT_TRIGGER:
                    return "Right Trigger";
            }
        }
    }
}
