package afroman.game.util;

import com.badlogic.gdx.physics.box2d.World;

public class PhysicsUtil {
    /**
     * The number of pixels which are in each meter.
     * <p>
     * When converting from <b>pixels</b> to <b>meters</b>, <i>divide</i> by this.
     * <p>
     * When converting from <b>meters</b> to <b>pixels</b>, <i>multiply</i> by this.
     */
    public static final float PIXELS_PER_METER = 16F;

    public static void stepWorld(World world, float delta) {
        world.step(delta, 6, 2);
    }
}
