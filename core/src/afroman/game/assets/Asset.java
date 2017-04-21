package afroman.game.assets;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Samson on 2017-04-16.
 */
public enum Asset {
    AFRO_SKIN("assets/skin/afro.json", Skin.class),
    SETTINGS_ICON("assets/textures/gui/settings.png", Texture.class),
    VIGNETTE("assets/textures/vignette.png", Texture.class),
    PLAYER("assets/textures/player/player.txt", TextureAtlas.class),
    MENU_MUSIC("assets/audio/music/menu.ogg", Music.class);

    /**
     * The internal file path to this.
     */
    private String path;
    /**
     * The class type of this.
     */
    private Class<?> type;
    /**
     * Any parameters used for creating an asset. (e.g. a new TextureLoader.TextureParameter();)
     */
    private AssetLoaderParameters parameters;

    Asset(String path, Class<?> type) {
        this(path, type, null);
    }

    Asset(String path, Class<?> type, AssetLoaderParameters parameters) {
        this.path = path;
        this.type = type;
        this.parameters = parameters;
    }

    public String getPath() {
        return path;
    }

    public Class<?> getType() {
        return type;
    }

    public AssetLoaderParameters getParameters() {
        return parameters;
    }
}
