package api.entity.physics;

import com.badlogic.gdx.physics.box2d.*;
import net.qwertysam.api.util.PhysicsUtil;

public class PhysicsBoxEntity extends PhysicsEntity {
    /**
     * The width of this in pixels.
     */
    private float width;
    /**
     * The width of this in pixels.
     */
    private float height;

    /**
     * An Entity with a square physics Body.
     *
     * @param world       the physics world that is in
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
    public PhysicsBoxEntity(World world, float friction, float restitution, float mass, float x, float y, float width, float height, boolean isStatic, boolean canRotate) {
        super(createBoxBody(world, friction, restitution, mass, x, y, width, height, isStatic, canRotate));

        this.width = width;
        this.height = height;
    }

    /**
     * Creates a physics Body with the specified parameters.
     *
     * @param world       the world that the physics body is in
     * @param friction    the coefficient of friction for this
     * @param restitution the restitution (bounciness) for this
     * @param mass        the mass of this in grams
     * @param x           the x ordinate of the physics body in pixels
     * @param y           the y ordinate of the physics body in pixels
     * @param width       the width of the physics body in pixels
     * @param height      the height of the physics body in pixels
     * @param isStatic    if the physics body is capable of having motion
     * @param canRotate   if the physics body is capable of rotating
     * @return a physics Body with the specified parameters.
     */
    private static Body createBoxBody(World world, float friction, float restitution, float mass, float x, float y, float width, float height, boolean isStatic, boolean canRotate) {
        // Defines the physics object in the simulation
        BodyDef bodyDef = new BodyDef();

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.fixedRotation = !canRotate;

        // Set physics body to specified position in the physics engine in
        // meters
        bodyDef.position.set((x + width / 2) / PhysicsUtil.PIXELS_PER_METER, (y + height / 2) / PhysicsUtil.PIXELS_PER_METER);

        // Creates a body in the world using our definition
        Body body = world.createBody(bodyDef);

        // The shape for the fixture
        PolygonShape shape = new PolygonShape();
        // Set the dimensions of the shape in meters to that of the specified
        // width and height
        shape.setAsBox((width / 2) / PhysicsUtil.PIXELS_PER_METER, (height / 2) / PhysicsUtil.PIXELS_PER_METER);

        // The defining features of the Fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        // Calculates the density of the fixture from the specified mass and
        // area
        fixtureDef.density = mass / ((width / PhysicsUtil.PIXELS_PER_METER) * (height / PhysicsUtil.PIXELS_PER_METER)); // d=m/v
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        // Adds the fixture to the physics body
        body.createFixture(fixtureDef);

        // Dispose the shape instance
        shape.dispose();

        return body;
    }

    /**
     * Gets the height of this in pixels.
     *
     * @return the height of this in pixels.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Gets the width of this in pixels.
     *
     * @return the width of this in pixels.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Gets the bottom left x ordinate of this.
     *
     * @return the bottom left x ordinate of this
     */
    public float getX() {
        return getCenterX() - (getWidth() / 2);
    }

    /**
     * Gets the bottom left y ordinate of this.
     *
     * @return the bottom left y ordinate of this
     */
    public float getY() {
        return getCenterY() - (getHeight() / 2);
    }

    /**
     * Tells if this is fully contained within the specified BoundsEntity.
     *
     * @param bounds the BoundsEntity to test for
     * @return if this is fully contained within <b>bounds</b>.
     */
    public boolean isInWorldBounds(PhysicsBoundsEntity bounds) {
        return !(getX() < bounds.getX1() || getX() + getWidth() > bounds.getX2() || getY() < bounds.getY1() || getY() + getHeight() > bounds.getY2());
    }
}
