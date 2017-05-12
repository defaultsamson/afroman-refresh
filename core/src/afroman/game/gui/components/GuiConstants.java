package afroman.game.gui.components;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by Samson on 2017-04-21.
 */
public class GuiConstants {

    public static FlickeringLight menuLight;
    public static RayHandler menuRayHandler;
    public static World menuWorld;
    public static Skin skin;
    public static TextField.TextFieldFilter usernameFilter;
    public static ScreenViewport viewport;

    public static void dispose() {
        menuLight.dispose();
        menuRayHandler.dispose();
        menuWorld.dispose();
        skin.dispose();
    }

    public static void initGuiConstants() {
        menuWorld = new World(new Vector2(0, 0F), true);
        menuRayHandler = new RayHandler(menuWorld);
        menuRayHandler.setBlurNum(1);
        menuRayHandler.setAmbientLight(0.3F);
        menuLight = new FlickeringLight(0.03F, 80, 100, menuRayHandler, 20, new Color(0F, 0F, 0F, 1F), 0, 27);

        skin = MainGame.game.getAssets().getSkin(Asset.AFRO_SKIN);

        viewport = MainGame.createStandardViewport();

        usernameFilter = new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                switch (c) {
                    default:
                        return false;
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'u':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                    case 'J':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'O':
                    case 'P':
                    case 'Q':
                    case 'R':
                    case 'S':
                    case 'T':
                    case 'U':
                    case 'V':
                    case 'W':
                    case 'X':
                    case 'Y':
                    case 'Z':
                    case '_':
                    case '-':
                    case '=':
                    case '<':
                    case '>':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        return true;
                }
            }
        };
    }
}
