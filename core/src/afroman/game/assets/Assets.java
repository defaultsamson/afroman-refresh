package afroman.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Samson on 2017-04-16.
 */
public class Assets extends AssetManager {

    public Assets() {
        super();
        queueAssets();
    }

    public Assets(FileHandleResolver resolver) {
        super(resolver);
        queueAssets();
    }

    public Assets(FileHandleResolver resolver, boolean defaultLoaders) {
        super(resolver, defaultLoaders);
        queueAssets();
    }

    /**
     * Where all the assets are to be queued up
     */
    private void queueAssets() {
        for (Asset a : Asset.values()) load(a);
    }

    public void load(Asset asset) {
        load(asset.getPath(), asset.getType(), asset.getParameters());
    }

    public Sprite getSprite(Asset asset) {
        Class<Sprite> requiredType = Sprite.class;
        if (asset.getType() == requiredType) {
            return get(asset.getPath(), requiredType);
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    public Texture getTexture(Asset asset) {
        Class<Texture> requiredType = Texture.class;
        if (asset.getType() == requiredType) {
            return get(asset.getPath(), requiredType);
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    public Skin getSkin(Asset asset) {
        Class<Skin> requiredType = Skin.class;
        if (asset.getType() == requiredType) {
            return get(asset.getPath(), requiredType);
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    private static void incorrectType(Asset asset, Class<?> requiredType) {
        System.out.println("Asset attempted to be loaded is the incorrect type.");
        System.out.println("Asset (" + asset.name() + ", " + asset.getType() + " must be type " + requiredType);
    }
}
