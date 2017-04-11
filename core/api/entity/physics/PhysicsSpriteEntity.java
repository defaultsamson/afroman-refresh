package api.entity.physics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import net.qwertysam.api.rendering.RenderUtil;
import net.qwertysam.api.rendering.Renderable;
import net.qwertysam.api.util.SpriteUtil;

public class PhysicsSpriteEntity extends PhysicsBoxEntity implements Renderable {
    private Sprite sprite;

    /**
     * An Entity with a square physics Body.
     *
     * @param world       the physics world that is in
     * @param sprite      the sprite for this
     * @param friction    the coefficient of friction for this
     * @param restitution the restitution (bounciness) for this
     * @param mass        the mass of this in grams
     * @param x           the x ordinate of this in pixels
     * @param y           the y ordinate of this in pixels
     * @param isStatic    if this is capable of having motion
     * @param canRotate   if this is capable of rotating
     */
    public PhysicsSpriteEntity(World world, Sprite sprite, float friction, float restitution, float mass, float x, float y, boolean isStatic, boolean canRotate) {
        this(1F, world, sprite, friction, restitution, mass, x, y, sprite.getWidth(), sprite.getHeight(), isStatic, canRotate);
    }

    /**
     * An Entity with a square physics Body.
     *
     * @param world       the physics world that is in
     * @param sprite      the sprite for this
     * @param friction    the coefficient of friction for this
     * @param restitution the restitution (bounciness) for this
     * @param mass        the mass of this in grams
     * @param x           the x ordinate of this in pixels
     * @param y           the y ordinate of this in pixels
     * @param width       the width of this in pixels
     * @param height      the height of this in pixels
     * @param isStatic    if this is capable of having motion
     * @param canRotate   if this is capable of rotating
     */
    public PhysicsSpriteEntity(World world, Sprite sprite, float friction, float restitution, float mass, float x, float y, float width, float height, boolean isStatic, boolean canRotate) {
        this(width / sprite.getWidth(), world, sprite, friction, restitution, mass, x, y, width, height, isStatic, canRotate);
    }

    /**
     * An Entity with a square physics Body.
     *
     * @param scale       the scale of this
     * @param world       the physics world that is in
     * @param sprite      the sprite for this
     * @param friction    the coefficient of friction for this
     * @param restitution the restitution (bounciness) for this
     * @param mass        the mass of this in grams
     * @param x           the x ordinate of this in pixels
     * @param y           the y ordinate of this in pixels
     * @param isStatic    if this is capable of having motion
     * @param canRotate   if this is capable of rotating
     */
    public PhysicsSpriteEntity(float scale, World world, Sprite sprite, float friction, float restitution, float mass, float x, float y, boolean isStatic, boolean canRotate) {
        this(scale, world, sprite, friction, restitution, mass, x, y, sprite.getWidth(), sprite.getHeight(), isStatic, canRotate);
    }

    /**
     * An Entity with a square physics Body.
     *
     * @param scale       the scale of this
     * @param world       the physics world that is in
     * @param sprite      the sprite for this
     * @param friction    the coefficient of friction for this
     * @param restitution the restitution (bounciness) for this
     * @param mass        the mass of this in grams
     * @param x           the x ordinate of this in pixels
     * @param y           the y ordinate of this in pixels
     * @param width       the width of this in pixels
     * @param height      the height of this in pixels
     * @param isStatic    if this is capable of having motion
     * @param canRotate   if this is capable of rotating
     */
    public PhysicsSpriteEntity(float scale, World world, Sprite sprite, float friction, float restitution, float mass, float x, float y, float width, float height, boolean isStatic, boolean canRotate) {
        super(world, friction, restitution, mass, x, y, width * scale, height * scale, isStatic, canRotate);

        this.sprite = SpriteUtil.getScaledSprite(sprite, scale);
    }

    /**
     * Gets the Sprite of this.
     *
     * @return the Sprite of this
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void render(SpriteBatch batch, float xOffset, float yOffset) {
        RenderUtil.drawSprite(batch, getSprite(), getX(), getY(), xOffset, yOffset, getRotation());
    }
}
