package api.util;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputUtil {
    public static List<Vector2> getTouches(int maxTouches, Viewport viewport) {
        List<Vector2> touches = new ArrayList<Vector2>();
        ;

        for (int i = 0; i < maxTouches; i++) {
            if (Gdx.input.isTouched(i)) {
                Vector3 vector3 = viewport.unproject(new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0F));
                touches.add(new Vector2(vector3.x, vector3.y));
            }
        }

        return touches;
    }
}
