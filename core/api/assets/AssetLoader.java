package api.assets;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import net.qwertysam.api.resource.IDisposable;
import net.qwertysam.main.MyGdxGame;

public abstract class AssetLoader implements IDisposable {
    protected MyGdxGame game;
    private final AssetManager manager;
    private List<Asset> assets;

    public AssetLoader(MyGdxGame game) {
        this.game = game;
        manager = new AssetManager();
        assets = new ArrayList<Asset>();
    }

    /**
     * Queues all the assets to be loaded by the manager.
     */
    protected abstract void queueLoad();

    public void load() {
        // Set the loaders for the generator and the fonts themselves
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        queueLoad();

        for (Asset asset : assets) {
            manager.load(asset.path, asset.type);
        }

        System.out.println("Loading Assets...");

        int queued = manager.getQueuedAssets();

        int previouslyLoaded = -1;

        long startTime = System.currentTimeMillis();

        while (!manager.update()) {
            int loaded = manager.getLoadedAssets();

            if (previouslyLoaded != loaded) {
                System.out.println(loaded + "/" + queued + " (" + (int) (manager.getProgress() * 100) + "%)");
                previouslyLoaded = loaded;
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println(queued + "/" + queued + " (100%) Assets loaded. Took: " + (endTime - startTime) + " milliseconds.");

        long startTime2 = System.currentTimeMillis();

        initialize();

        long endTime2 = System.currentTimeMillis();

        System.out.println("Objects initialized. Took: " + (endTime2 - startTime2) + " milliseconds.");
    }

    /**
     * Loads all the to their variables.
     */
    public abstract void initialize();

    /**
     * Adds an asset to be loaded.
     *
     * @param asset the asset to add
     */
    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    /**
     * Gets the asset with the specified key.
     *
     * @param key the key used to identify the asset
     * @return the asset with the specified key. <b>null</b> if there's no asset with that key.
     */
    public Asset getAsset(String key) {
        for (Asset asset : assets) {
            if (asset.key.equals(key)) return asset;
        }

        return null;
    }

    @Override
    public void dispose() {
        manager.dispose();
        assets.clear();
    }

    /**
     * Gets the AssetManager of this.
     *
     * @return the AssetManager of this
     */
    public AssetManager getManager() {
        return manager;
    }

    /**
     * Gets the game instance of this.
     *
     * @return the game instance of this.
     */
    public MyGdxGame getGame() {
        return game;
    }
}
