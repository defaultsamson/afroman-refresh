package afroman.game.util;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.Input.Peripheral;

public class DeviceUtil {
    public static boolean isAccelerometerAvailable() {
        return Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
    }

    public static boolean isCompassAvailable() {
        return Gdx.input.isPeripheralAvailable(Peripheral.Compass);
    }

    public static boolean isHardwareKeyboardAvailable() {
        return Gdx.input.isPeripheralAvailable(Peripheral.HardwareKeyboard);
    }

    public static boolean isOnScreenKeyboardAvailable() {
        return Gdx.input.isPeripheralAvailable(Peripheral.OnscreenKeyboard);
    }

    public static boolean isMultitouchScreenAvailable() {
        return Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen);
    }

    public static boolean isVibratorAvailable() {
        return Gdx.input.isPeripheralAvailable(Peripheral.Vibrator);
    }

    public static Orientation getDeviceOrientation() {
        return Gdx.input.getNativeOrientation();
    }

    public static boolean isDeviceLandscape() {
        return getDeviceOrientation() == Orientation.Landscape;
    }

    public static boolean isDevicePortrait() {
        return getDeviceOrientation() == Orientation.Portrait;
    }

    public static Input getInput() {
        return Gdx.input;
    }

    public static boolean isAndroid() {
        return Gdx.app.getType() == ApplicationType.Android;
    }

    public static boolean isDesktop() {
        return Gdx.app.getType() == ApplicationType.Desktop;
    }

    public static boolean isApple() {
        return Gdx.app.getType() == ApplicationType.iOS;
    }

    public static boolean isExternalStorageAvailable() {
        return Gdx.files.isExternalStorageAvailable();
    }

    public static String getExternalStoragePath() {
        return Gdx.files.getExternalStoragePath();
    }

    public static boolean isLocalStorageAvailable() {
        return Gdx.files.isLocalStorageAvailable();
    }

    public static String getLocalStoragePath() {
        return Gdx.files.getLocalStoragePath();
    }
}
