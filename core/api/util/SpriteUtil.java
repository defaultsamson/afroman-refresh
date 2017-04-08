package api.util;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteUtil {
    public static Sprite getScaledSprite(Sprite spriteToScale, float scale) {
        Sprite toReturn = new Sprite(spriteToScale);
        toReturn.setSize(toReturn.getWidth() * scale, toReturn.getHeight() * scale);
        toReturn.setOrigin(toReturn.getWidth() / 2, toReturn.getHeight() / 2);
        return toReturn;
    }
}
