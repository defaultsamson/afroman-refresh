package afroman.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by samson on 11/04/17.
 */
public class CleanTextField extends TextField{
    public CleanTextField(String text, Skin skin) {
        super(text, skin);
    }

    public CleanTextField(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public CleanTextField(String text, TextFieldStyle style) {
        super(text, style);
    }

    /** Draws selection rectangle **/
    protected void drawSelection (Drawable selection, Batch batch, BitmapFont font, float x, float y) {
        // prevents the selection box from being drawn when nothing is being selected
        if (getSelection().length() >0) super.drawSelection(selection, batch, font, x, y);
    }
}
