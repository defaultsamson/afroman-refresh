package api.gui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.qwertysam.api.gui.screen.GuiScreen;
import net.qwertysam.api.input.TouchableArea;
import net.qwertysam.api.rendering.RenderUtil;
import net.qwertysam.api.rendering.Renderable;
import net.qwertysam.api.util.SpriteUtil;

import java.util.List;

public class GuiButton extends TouchableArea implements Renderable {
    private GuiScreen screen;

    private int id;

    private Sprite normal;
    private Sprite depressed;

    public GuiButton(GuiScreen screen, List<Sprite> sprites, int id, float x, float y) {
        this(screen, sprites, 1F, id, x, y, 0F);
    }

    public GuiButton(GuiScreen screen, List<Sprite> sprites, float scale, int id, float x, float y) {
        this(screen, sprites, scale, id, x, y, 0F);
    }

    public GuiButton(GuiScreen screen, List<Sprite> sprites, int id, float x, float y, float touchRadius) {
        this(screen, sprites, 1F, id, x, y, touchRadius, 0F);
    }

    public GuiButton(GuiScreen screen, List<Sprite> sprites, float scale, int id, float x, float y, float touchRadius) {
        this(screen, sprites, scale, id, x, y, touchRadius, 0F);
    }

    public GuiButton(GuiScreen screen, List<Sprite> sprites, int id, float x, float y, float touchRadius, float rotation) {
        this(screen, sprites, 1F, id, x, y, touchRadius, rotation);
    }

    public GuiButton(GuiScreen screen, List<Sprite> sprites, float scale, int id, float x, float y, float touchRadius, float rotation) {
        super(x, y, sprites.get(0).getWidth() * scale, sprites.get(0).getHeight() * scale, touchRadius, rotation);

        this.screen = screen;
        this.id = id;

        normal = SpriteUtil.getScaledSprite(sprites.get(0), scale);
        depressed = SpriteUtil.getScaledSprite(sprites.get(1), scale);
    }

    @Override
    public boolean canHold() {
        return false;
    }

    @Override
    protected void onPressed() {
        screen.pressAction(id);

    }

    @Override
    protected void onRelease() {
        screen.releaseAction(id);
    }

    @Override
    public Sprite getSprite() {
        return (isTouched ? depressed : normal);
    }

    @Override
    public void render(SpriteBatch batch, float xOffset, float yOffset) {
        RenderUtil.drawSprite(batch, getSprite(), getX(), getY(), xOffset, yOffset, getRotation());
    }

    @Override
    public float getRotation() {
        return super.getRotation();
    }
}
