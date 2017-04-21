package afroman.game.io;

import com.badlogic.gdx.Preferences;

/**
 * Created by Samson on 2017-04-08.
 */
public class Settings {
    public class Default {
        public static final float MUSIC_VOLUME = 1F;
        public static final float SFX_VOLUME = 1F;
        public static final float SCREEN_SCALE = 3F;
    }

    private Preferences preferences;

    public Settings(Preferences preferences) {
        this.preferences = preferences;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public Preferences putBoolean(Setting key, boolean val) {
        preferences.putBoolean(key.getKey(), val);
        return preferences;
    }

    public Preferences putInteger(Setting key, int val) {
        preferences.putInteger(key.getKey(), val);
        return preferences;
    }

    public Preferences putLong(Setting key, long val) {
        preferences.putLong(key.getKey(), val);
        return preferences;
    }

    public Preferences putFloat(Setting key, float val) {
        preferences.putFloat(key.getKey(), val);
        return preferences;
    }

    public Preferences putString(Setting key, String val) {
        preferences.putString(key.getKey(), val);
        return preferences;
    }

    public boolean getBoolean(Setting key) {
        return preferences.getBoolean(key.getKey());
    }

    public int getInteger(Setting key) {
        return preferences.getInteger(key.getKey());
    }

    public long getLong(Setting key) {
        return preferences.getLong(key.getKey());
    }

    public float getFloat(Setting key) {
        return preferences.getFloat(key.getKey());
    }

    public String getString(Setting key) {
        return preferences.getString(key.getKey());
    }

    public boolean getBoolean(Setting key, boolean defValue) {
        return preferences.getBoolean(key.getKey(), defValue);
    }

    public int getInteger(Setting key, int defValue) {
        return preferences.getInteger(key.getKey(), defValue);
    }

    public long getLong(Setting key, long defValue) {
        return preferences.getLong(key.getKey(), defValue);
    }

    public float getFloat(Setting key, float defValue) {
        return preferences.getFloat(key.getKey(), defValue);
    }

    public String getString(Setting key, String defValue) {
        return preferences.getString(key.getKey(), defValue);
    }

    /*
    /**
     * Returns a read only Map<String, Object> with all the key, objects of the preferences.
     *
    public Map<String, ?> get();
    */

    public boolean contains(Setting key) {
        return preferences.contains(key.getKey());
    }

    public void clear() {
        preferences.clear();
    }

    public void remove(Setting key) {
        preferences.remove(key.getKey());
    }

    /**
     * Makes sure the preferences are persisted.
     */
    public void save() {
        preferences.flush();
    }
}
