package api.assets;

public class Asset {
    /**
     * The key used to identify this.
     */
    public String key;
    /**
     * The internal file path to this.
     */
    public String path;
    /**
     * The class type of this.
     */
    public Class<?> type;

    /**
     * A new asset object.
     *
     * @param key  the key used to identify this
     * @param path the internal file path to this
     * @param type the class type of this
     */
    public Asset(String key, String path, Class<?> type) {
        this.key = key;
        this.path = path;
        this.type = type;
    }
}
