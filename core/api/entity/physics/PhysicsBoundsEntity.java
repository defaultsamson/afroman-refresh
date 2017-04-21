package api.entity.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import net.qwertysam.api.util.PhysicsUtil;

public class PhysicsBoundsEntity extends PhysicsEntity {
    /**
     * The specified ordinate for this.
     */
    private float x1, y1, x2, y2;

    /**
     * Invisible walls in the shape of a box.
     *
     * @param world     the physics menuWorld that is in
     * @param x1        the first x ordinate
     * @param y1        the first y ordinate
     * @param x2        the seconds x ordinate
     * @param y2        the seconds y ordinate
     * @param isStatic  if this is capable of having motion
     * @param canRotate if this is capable of rotating
     */
    public PhysicsBoundsEntity(World world, float x1, float y1, float x2, float y2, boolean isStatic, boolean canRotate) {
        super(createBody(world, x1, y1, x2, y2, isStatic, canRotate));

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Creates a bounds body with the specified parameters.
     *
     * @param world     the physics menuWorld that is in
     * @param x1        the first x ordinate
     * @param y1        the first y ordinate
     * @param x2        the seconds x ordinate
     * @param y2        the seconds y ordinate
     * @param isStatic  if this is capable of having motion
     * @param canRotate if this is capable of rotating
     * @return a bounds body with the specified parameters.
     */
    private static Body createBody(World world, float x1, float y1, float x2, float y2, boolean isStatic, boolean canRotate) {
        // Now create a BodyDefinition. This defines the physics objects type
        // and position in the simulation
        BodyDef bodyDef = new BodyDef();

        if (isStatic) {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        } else {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }

        bodyDef.fixedRotation = !canRotate;

        // Set physics body to specified position in the physics engine in
        // meters
        bodyDef.position.set(x1 / PhysicsUtil.PIXELS_PER_METER, y1 / PhysicsUtil.PIXELS_PER_METER);

        // Create a body in the menuWorld using our definition
        Body body = world.createBody(bodyDef);

        // Adds the 4 side fixtures to the body
        for (int c = 0; c < 4; c++) {
            float v1x, v1y, v2x, v2y;

            switch (c) {
                case 0: // Creates the bottom line
                    v1x = x1;
                    v1y = y1;
                    v2x = x2;
                    v2y = y1;
                    break;
                case 1: // Creates the right line
                    v1x = x2;
                    v1y = y1;
                    v2x = x2;
                    v2y = y2;
                    break;
                case 2: // Creates the top line
                    v1x = x2;
                    v1y = y2;
                    v2x = x1;
                    v2y = y2;
                    break;
                case 3:
                default: // Creates the left line
                    v1x = x1;
                    v1y = y2;
                    v2x = x1;
                    v2y = y1;
                    break;
            }

            // The shape for the fixture
            EdgeShape shape = new EdgeShape();
            // Set the dimensions of the shape in meters to that of the
            // specified
            // width and height
            shape.set(v1x / PhysicsUtil.PIXELS_PER_METER, v1y / PhysicsUtil.PIXELS_PER_METER, v2x / PhysicsUtil.PIXELS_PER_METER, v2y / PhysicsUtil.PIXELS_PER_METER);

            // The defining features of the Fixture
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1F;
            fixtureDef.restitution = 0F;
            fixtureDef.friction = 1F;

            body.createFixture(fixtureDef);

            // Shape is the only disposable of the lot, so get rid of it
            shape.dispose();
        }

        return body;
    }

    /**
     * Gets the x ordinate of the first point.
     *
     * @return the x ordinate of the first point.
     */
    public float getX1() {
        return x1;
    }

    /**
     * Gets the y ordinate of the first point.
     *
     * @return the y ordinate of the first point.
     */
    public float getY1() {
        return y1;
    }

    /**
     * Gets the x ordinate of the second point.
     *
     * @return the x ordinate of the second point.
     */
    public float getX2() {
        return x2;
    }

    /**
     * Gets the y ordinate of the second point.
     *
     * @return the y ordinate of the second point.
     */
    public float getY2() {
        return y2;
    }

    /**
     * Gets the second point as a Vector1.
     *
     * @return the first point.
     */
    public Vector2 getPoint1() {
        return new Vector2(getX1(), getY1());
    }

    /**
     * Gets the second point as a Vector2.
     *
     * @return the second point.
     */
    public Vector2 getPoint2() {
        return new Vector2(getX1(), getY1());
    }

    /**
     * Creates a Rectangle from this.
     *
     * @return a Rectangle.
     */
    public Rectangle toRectangle() {
        return new Rectangle(getX1(), getY1(), getWidth(), getHeight());
    }

    /**
     * Gets the height of this in pixels.
     *
     * @return the height of this in pixels.
     */
    public float getHeight() {
        return getY2() - getY1();
    }

    /**
     * Gets the width of this in pixels.
     *
     * @return the width of this in pixels.
     */
    public float getWidth() {
        return getX2() - getX1();
    }
}
