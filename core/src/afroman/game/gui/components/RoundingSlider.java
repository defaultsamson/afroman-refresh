package afroman.game.gui.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Samson on 2017-04-09.
 */
public class RoundingSlider extends Slider {
    // Used so that the knob doesn't go off the end of the bar
    private static final float GRAPHIC_OFFSET = 0.00001F;

    private float roundToNearest;

    public RoundingSlider(float min, float max, float stepSize, float roundToNearest, boolean vertical, Skin skin) {
        super(min, max + GRAPHIC_OFFSET, stepSize, vertical, skin);

        addChangeListener(max);
        this.roundToNearest = roundToNearest;
    }

    public RoundingSlider(float min, float max, float stepSize, float roundToNearest, boolean vertical, Skin skin, String styleName) {
        super(min, max + GRAPHIC_OFFSET, stepSize, vertical, skin, styleName);

        addChangeListener(max);
        this.roundToNearest = roundToNearest;
    }

    public RoundingSlider(float min, float max, float stepSize, float roundToNearest, boolean vertical, SliderStyle style) {
        super(min, max + GRAPHIC_OFFSET, stepSize, vertical, style);

        addChangeListener(max);
        this.roundToNearest = roundToNearest;
    }

    private void addChangeListener(final float max) {
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (RoundingSlider.super.getValue() > max) {
                    setValue(max);
                }
            }
        });
    }

    @Override
    public float getValue() {
        return Math.round(super.getValue() * roundToNearest) / roundToNearest;
    }
}
