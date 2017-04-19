package afroman.game.gui;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by samson on 19/04/17.
 */
public class AnimationActor extends Actor {
    Animation<? extends TextureRegion> animation;
    TextureRegion currentRegion;

    float time = 0F;

    public AnimationActor(Animation<? extends TextureRegion> animation) {
        this.animation = animation;
    }

    @Override
    public void act(float delta) {
        time += delta;

        currentRegion = animation.getKeyFrame(time);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentRegion, getX(), getY());
    }
}
