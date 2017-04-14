package afroman.game.gui;

import afroman.game.MainGame;
import com.badlogic.gdx.Screen;

/**
 * Created by Samson on 2017-04-14.
 */
public class HierarchicalMenu {
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
