package afroman.game.gui.components;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

/**
 * Created by samson on 18/04/17.
 */
public class FlickeringLight extends PointLight {

    private Animation<Float> animation;
    private float state = 0;

    public FlickeringLight(float frameDuration, float smallerRadius, float largerRadius, RayHandler rayHandler, int rays, Color color, float x, float y) {
        super(rayHandler, rays, color, largerRadius, x, y);

        animation = new Animation<Float>(frameDuration, generateKeyFrames(smallerRadius, largerRadius), Animation.PlayMode.LOOP_PINGPONG);
    }

    private static Array<Float> generateKeyFrames(float smaller, float larger) {
        int radiusDifference = (int) (larger - smaller);

        Array<Float> radi = new Array<Float>(radiusDifference);

        for (int i = 0; i < radiusDifference; i++) {
            // Creates the radius for the frame based on a cosine function
            // Uses the first PI/2 of the function to map out all the values, then dsimply ping-pongs back and forth
            radi.add((float) (smaller + (radiusDifference * ((Math.cos((i * Math.PI) / radiusDifference) / 2D) + 0.5))));
        }

        return radi;
    }

    public Animation<Float> getAnimation() {
        return animation;
    }

    @Override
    public void update() {
        super.update();
        state += Gdx.graphics.getDeltaTime();
        setDistance(animation.getKeyFrame(state));
    }
}
