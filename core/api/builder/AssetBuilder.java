package api.builder;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import net.qwertysam.api.assets.Asset;
import net.qwertysam.api.assets.AssetLoader;

import java.util.ArrayList;
import java.util.List;

public class AssetBuilder {
    /**
     * The default method for texture resize and rotation filtering.
     */
    public static final TextureFilter DEFAULT_TEXTURE_FILTER = TextureFilter.Nearest;
    /**
     * The default method for font resize and rotation filtering.
     */
    public static final TextureFilter DEFAULT_FONT_FILTER = TextureFilter.Linear;
    /**
     * The default font colour.
     */
    public static final Color DEFAULT_FONT_COLOUR = Color.WHITE;

    /**
     * Creates a sprite from a loaded image in an AssetManager.
     *
     * @param assets the AssetLoader instance
     * @param key    the key of the asset
     * @return a sprite from a loaded image.
     */
    public static Sprite createSprite(AssetLoader assets, String key) {
        Asset asset = assets.getAsset(key);
        Class<Texture> requiredType = Texture.class;

        if (asset.type == requiredType) {
            Texture texture = assets.getManager().get(assets.getAsset(key).path, requiredType);
            texture.setFilter(DEFAULT_TEXTURE_FILTER, DEFAULT_TEXTURE_FILTER);

            Sprite sprite = new Sprite(texture);
            sprite.flip(false, assets.getGame().isInverted());

            return sprite;
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    /**
     * Creates a List of sprites from a loaded image in an AssetManager.
     * <p>
     * The image must be a sprite sheet.
     *
     * @param assets the AssetLoader instance
     * @param key    the key of the asset
     * @param size   the width of each sprite in pixels
     * @return a List of sprites from a loaded image.
     */
    public static List<Sprite> createSprites(AssetLoader assets, String key, int size) {
        return createSprites(assets, key, size, size);
    }

    /**
     * Creates a List of sprites from a loaded image in an AssetManager.
     * <p>
     * The image must be a sprite sheet.
     *
     * @param assets the AssetLoader instance
     * @param key    the key of the asset
     * @param xSize  the width of each sprite in pixels
     * @param ySize  the height of each sprite in pixels
     * @return a List of sprites from a loaded image.
     */
    public static List<Sprite> createSprites(AssetLoader assets, String key, int xSize, int ySize) {
        Asset asset = assets.getAsset(key);
        Class<Texture> requiredType = Texture.class;

        if (asset.type == requiredType) {
            List<Sprite> sprites = new ArrayList<Sprite>();

            Texture texture = assets.getManager().get(assets.getAsset(key).path, requiredType);
            texture.setFilter(DEFAULT_TEXTURE_FILTER, DEFAULT_TEXTURE_FILTER);

            // Creates the sprites
            int texturesInColumn = (texture.getHeight() / ySize);
            int texturesInRow = (texture.getWidth() / xSize);

            for (int iy = 0; iy < texturesInColumn; iy++) {
                for (int ix = 0; ix < texturesInRow; ix++) {
                    Sprite sprite = new Sprite(texture, ix * xSize, iy * ySize, xSize, ySize);
                    sprite.flip(false, assets.getGame().isInverted());
                    sprites.add(sprite);
                }
            }
            return sprites;
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    /**
     * Creates a font from a TrueTypeFont file in an AssetManager.
     *
     * @param assets the AssetLoader instance
     * @param path   the path to the TrueTypeFont file
     * @param size   the size of the font
     * @return a font from a loaded TrueTypeFont file.
     */
    public static BitmapFont createFont(AssetLoader assets, String key, int size) {
        return createFont(assets, key, DEFAULT_FONT_COLOUR, size);
    }

    /**
     * Creates a font from a TrueTypeFont file in an AssetManager.
     *
     * @param assets the AssetLoader instance
     * @param path   the path to the TrueTypeFont file
     * @param colour the colour of the font
     * @param size   the size of the font
     * @return a font from a loaded TrueTypeFont file.
     */
    public static BitmapFont createFont(AssetLoader assets, String key, Color colour, int size) {
        Asset asset = assets.getAsset(key);
        Class<FreeTypeFontGenerator> requiredType = FreeTypeFontGenerator.class;

        if (asset.type == requiredType) {
            FreeTypeFontGenerator gen = assets.getManager().get(asset.path, FreeTypeFontGenerator.class);

            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            parameter.size = size;
            parameter.flip = assets.getGame().isInverted();

            BitmapFont font = gen.generateFont(parameter);

            font.getRegion().getTexture().setFilter(DEFAULT_FONT_FILTER, DEFAULT_FONT_FILTER);

            font.setColor(colour);

            return font;
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    /**
     * Creates a sound from a file in an AssetManager.
     *
     * @param assets the AssetLoader instance
     * @param key    the key of the asset
     * @return a sound from a loaded file.
     */
    public static Sound createSound(AssetLoader assets, String key) {
        Asset asset = assets.getAsset(key);
        Class<Sound> requiredType = Sound.class;

        if (asset.type == requiredType) {
            return assets.getManager().get(assets.getAsset(key).path, Sound.class);
        } else {
            incorrectType(asset, requiredType);
            return null;
        }
    }

    /**
     * Sends the incorrect type error message.
     *
     * @param asset        the asset of the incorrect type which was attempted to be loaded
     * @param requiredType the required type to be loaded from that method
     */
    private static void incorrectType(Asset asset, Class<?> requiredType) {
        System.out.println("Asset attempted to be loaded is the incorrect type.");
        System.out.println("Asset (" + asset.type + ", " + asset.type + " must be type " + requiredType);
    }
}
