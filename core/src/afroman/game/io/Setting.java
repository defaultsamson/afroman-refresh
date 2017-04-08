package afroman.game.io;

/**
 * Created by Samson on 2017-04-08.
 */
public enum Setting {
    SCALE("scale");

    private String key;

    Setting(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
