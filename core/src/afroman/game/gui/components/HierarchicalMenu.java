package afroman.game.gui.components;

import afroman.game.MainGame;
import com.badlogic.gdx.Screen;

/**
 * Created by Samson on 2017-04-14.
 */
public abstract class HierarchicalMenu implements Screen {
    protected Screen parentScreen;

    public HierarchicalMenu(Screen parent) {
        parentScreen = parent;
    }

    public Screen getParentScreen() {
        return parentScreen;
    }

    protected void gotoParentScreen() {
        MainGame.game.setScreen(parentScreen);
    }
}
