package api.gui;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.qwertysam.api.gui.screen.GuiScreen;
import net.qwertysam.api.language.TranslationKey;
import net.qwertysam.api.rendering.RenderUtil;

public class GuiTextButton extends GuiButton {
    private BitmapFont font;
    private String text;

    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, int id, float x, float y, TranslationKey key) {
        this(screen, sprites, font, 1F, id, x, y, 0F, key);
    }

    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, float scale, int id, float x, float y, TranslationKey key) {
        this(screen, sprites, font, scale, id, x, y, 0F, key);
    }

    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, int id, float x, float y, float touchRadius, TranslationKey key) {
        this(screen, sprites, font, 1F, id, x, y, touchRadius, 0F, key);
    }

    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, float scale, int id, float x, float y, float touchRadius, TranslationKey key) {
        this(screen, sprites, font, scale, id, x, y, touchRadius, 0F, key);
    }

    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, int id, float x, float y, float touchRadius, float rotation, TranslationKey key) {
        this(screen, sprites, font, 1F, id, x, y, touchRadius, rotation, key);
    }

    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, float scale, int id, float x, float y, float touchRadius, float rotation, TranslationKey key) {
        super(screen, sprites, scale, id, x, y, touchRadius, rotation);

        this.font = font;
        this.text = screen.getGame().getTranslator().getTranslation(key);
    }

    @Deprecated
    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, int id, float x, float y, String text) {
        this(screen, sprites, font, 1F, id, x, y, 0F, text);
    }

    @Deprecated
    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, float scale, int id, float x, float y, String text) {
        this(screen, sprites, font, scale, id, x, y, 0F, text);
    }

    @Deprecated
    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, int id, float x, float y, float touchRadius, String text) {
        this(screen, sprites, font, 1F, id, x, y, touchRadius, 0F, text);
    }

    @Deprecated
    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, float scale, int id, float x, float y, float touchRadius, String text) {
        this(screen, sprites, font, scale, id, x, y, touchRadius, 0F, text);
    }

    @Deprecated
    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, int id, float x, float y, float touchRadius, float rotation, String text) {
        this(screen, sprites, font, 1F, id, x, y, touchRadius, rotation, text);
    }

    @Deprecated
    public GuiTextButton(GuiScreen screen, List<Sprite> sprites, BitmapFont font, float scale, int id, float x, float y, float touchRadius, float rotation, String text) {
        super(screen, sprites, scale, id, x, y, touchRadius, rotation);

        this.font = font;
        this.text = text;
    }

    /**
     * @return the text being displayed on this button.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text being displayed on this button.
     *
     * @param text the new text
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(SpriteBatch batch, float xOffset, float yOffset) {
        super.render(batch, xOffset, yOffset);
        RenderUtil.drawCenteredFont(batch, font, getX() + (getWidth() / 2), getY() + (getHeight() / 2), xOffset, yOffset, getText());
    }
}
