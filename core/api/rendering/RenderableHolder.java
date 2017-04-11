package api.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.qwertysam.api.resource.Holder;

public class RenderableHolder<T> extends Holder<T> {
    /**
     * Renders all the entries in this.
     *
     * @param batch the SpriteBatch to render to
     */
    public void renderEntries(SpriteBatch batch) {
        renderEntries(batch, 0F, 0F);
    }

    /**
     * Renders all the entries in this with a specified offset.
     *
     * @param batch   the SpriteBatch to render to
     * @param xOffset the x offset to draw at
     * @param yOffset the y offset to draw at
     */
    public void renderEntries(SpriteBatch batch, float xOffset, float yOffset) {
        boolean close = false;
        if (!batch.isDrawing()) {
            batch.begin();
            // Tells program to close the batch after it's done drawing.
            close = true;
        }

        for (T entry : getEntries()) {
            if (entry instanceof Renderable) {
                ((Renderable) entry).render(batch, xOffset, yOffset);
            }
        }

        if (close) batch.end();
    }
}
