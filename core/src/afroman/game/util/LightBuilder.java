package afroman.game.util;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

public class LightBuilder {
    /**
     * The default value for x-ray.
     */
    public static final boolean DEFAULT_XRAY = false;
    /**
     * The default softness of a light's edge.
     */
    public static final float DEFAULT_SOFTNESS = 1F;
    /**
     * The default amount of rays to calculate for.
     */
    public static final int DEFAULT_RAYS = 200;

    /**
     * Creates a PointLight object.
     *
     * @param rayHandler     the RayHandler for the PointLight
     * @param rays           the ray count for the PointLight
     * @param softnessLength the ray softness for the PointLight
     * @param colour         the colour for the PointLight
     * @param distance       the distance of the light for the PointLight
     * @param xRay           if the light can pass through objects or not
     * @param body           the physics Body that the PointLight is attached to.
     * @return a PointLight with the given parameters.
     */
    public static PointLight createPointLight(RayHandler rayHandler, int rays, float softnessLength, Color colour, float distance, boolean xRay, Body body) {
        return createPointLight(rayHandler, rays, softnessLength, colour, distance, xRay, body, 0F, 0F);
    }

    /**
     * Creates a PointLight object.
     *
     * @param rayHandler     the RayHandler for the PointLight
     * @param rays           the ray count for the PointLight
     * @param softnessLength the ray softness for the PointLight
     * @param colour         the colour for the PointLight
     * @param distance       the distance of the light for the PointLight
     * @param xRay           if the light can pass through objects or not
     * @param x              the x ordinate of the light in pixels
     * @param y              the y ordinate of the light in pixels
     * @return a PointLight with the given parameters.
     */
    public static PointLight createPointLight(RayHandler rayHandler, int rays, float softnessLength, Color colour, float distance, boolean xRay, float x, float y) {
        return createPointLight(rayHandler, rays, softnessLength, colour, distance, xRay, null, x, y);
    }

    /**
     * Creates a PointLight object.
     *
     * @param rayHandler     the RayHandler for the PointLight
     * @param rays           the ray count for the PointLight
     * @param softnessLength the ray softness for the PointLight
     * @param colour         the colour for the PointLight
     * @param distance       the distance of the light for the PointLight
     * @param xRay           if the light can pass through objects or not
     * @param body           the physics Body that the PointLight is attached to. If not null, will ignore <b>x</b> and <b>y</b> ordinates
     * @param x              the x ordinate of the light in pixels
     * @param y              the y ordinate of the light in pixels
     * @return a PointLight with the given parameters.
     */
    private static PointLight createPointLight(RayHandler rayHandler, int rays, float softnessLength, Color colour, float distance, boolean xRay, Body body, float x, float y) {
        PointLight light = new PointLight(rayHandler, rays, colour, distance / PhysicsUtil.PIXELS_PER_METER, x / PhysicsUtil.PIXELS_PER_METER, y / PhysicsUtil.PIXELS_PER_METER);
        light.setSoftnessLength(softnessLength);
        light.setXray(xRay);
        light.attachToBody(body);

        return light;
    }

    /**
     * Creates a ConeLight object.
     *
     * @param rayHandler the RayHandler for the ConeLight
     * @param rays       the ray count for the ConeLight
     * @param softness   the ray softness for the ConeLight
     * @param colour     the colour for the ConeLight
     * @param distance   the distance of the light for the ConeLight
     * @param xRay       if the light can pass through objects or not
     * @param dir        the direction that the light is aiming in degrees
     * @param cone       spanning width degree of the light (0 degrees - 360 degrees)
     * @param body       the physics Body that the ConeLight is attached to. If not null, will ignore <b>x</b> and <b>y</b> ordinates
     * @return a ConeLight with the given parameters.
     */
    public static ConeLight createConeLight(RayHandler rayHandler, int rays, float softness, Color colour, float distance, boolean xRay, float dir, float cone, Body body) {
        return createConeLight(rayHandler, rays, softness, colour, distance, xRay, dir, cone, body, 0F, 0F);
    }

    /**
     * Creates a ConeLight object.
     *
     * @param rayHandler the RayHandler for the ConeLight
     * @param rays       the ray count for the ConeLight
     * @param softness   the ray softness for the ConeLight
     * @param colour     the colour for the ConeLight
     * @param distance   the distance of the light for the ConeLight
     * @param xRay       if the light can pass through objects or not
     * @param dir        the direction that the light is aiming in degrees
     * @param cone       spanning width degree of the light (0 degrees - 360 degrees)
     * @param x          the x ordinate of the light in pixels
     * @param y          the y ordinate of the light in pixels
     * @return a ConeLight with the given parameters.
     */
    public static ConeLight createConeLight(RayHandler rayHandler, int rays, float softness, Color colour, float distance, boolean xRay, float dir, float cone, float x, float y) {
        return createConeLight(rayHandler, rays, softness, colour, distance, xRay, dir, cone, null, x, y);
    }

    /**
     * Creates a ConeLight object.
     *
     * @param rayHandler the RayHandler for the ConeLight
     * @param rays       the ray count for the ConeLight
     * @param softness   the ray softness for the ConeLight
     * @param colour     the colour for the ConeLight
     * @param distance   the distance of the light for the ConeLight
     * @param xRay       if the light can pass through objects or not
     * @param dir        the direction that the light is aiming in degrees
     * @param cone       spanning width degree of the light (0 degrees - 360 degrees)
     * @param body       the physics Body that the ConeLight is attached to. If not null, will ignore <b>x</b> and <b>y</b> ordinates
     * @param x          the x ordinate of the light in pixels
     * @param y          the y ordinate of the light in pixels
     * @return a ConeLight with the given parameters.
     */
    private static ConeLight createConeLight(RayHandler rayHandler, int rays, float softness, Color colour, float distance, boolean xRay, float dir, float cone, Body body, float x, float y) {
        ConeLight light = new ConeLight(rayHandler, rays, colour, distance / PhysicsUtil.PIXELS_PER_METER, x / PhysicsUtil.PIXELS_PER_METER, y / PhysicsUtil.PIXELS_PER_METER, dir, cone);
        light.setSoftnessLength(softness);
        light.setXray(xRay);
        light.attachToBody(body);

        return light;
    }
}
