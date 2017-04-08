package api.rendering;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract interface Renderable {
    /**
     * @return the Sprite for this.
     */
    public abstract Sprite getSprite();

    /**
     * @return the x ordinate of this.
     */
    public abstract float getX();

    /**
     * @return the y ordinate of this.
     */
    public abstract float getY();

    /**
     * @return the rotation of this.
     */
    public abstract float getRotation();

    /**
     * Draws this to the batch.
     *
     * @param batch   the sprite batch to render to
     * @param xOffset the x offset of this
     * @param yOffset the y offset of this
     */
    public void render(SpriteBatch batch, float xOffset, float yOffset);
}
