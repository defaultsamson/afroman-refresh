package afroman.game.gui.components;

import afroman.game.MainGame;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by samson on 21/04/17.
 */
public class NoisyClickListener extends ClickListener {
    public static Sound buttonUp;
    public static Sound buttonDown;

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);
        MainGame.game.playSound(buttonUp, 1);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        MainGame.game.playSound(buttonDown, 1);
        return super.touchDown(event, x, y, pointer, button);
    }
}
