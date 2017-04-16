package afroman.game.gui.components;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Samson on 2017-04-14.
 */
public class IconButton extends ImageButton {

    public IconButton(Skin skin, Texture icon) {
        this(skin, icon, icon, icon);
    }

    public IconButton(Skin skin, Drawable icon) {
        this(skin, icon, icon, icon);
    }

    public IconButton(Skin skin, String styleName, Texture icon) {
        this(skin, styleName, icon, icon, icon);
    }

    public IconButton(Skin skin, String styleName, Drawable icon) {
        this(skin, styleName, icon, icon, icon);
    }

    public IconButton(Skin skin, Texture iconUp, Texture iconDown, Texture iconOver) {
        this(skin, "default", iconUp, iconDown, iconOver);
    }

    public IconButton(Skin skin, Drawable iconUp, Drawable iconDown, Drawable iconOver) {
        this(skin, "default", iconUp, iconDown, iconOver);
    }

    public IconButton(Skin skin, String styleName, Texture iconUp, Texture iconDown, Texture iconOver) {
        this(skin, styleName, new TextureRegionDrawable(new TextureRegion(iconUp)), new TextureRegionDrawable(new TextureRegion(iconDown)), new TextureRegionDrawable(new TextureRegion(iconOver)));
    }

    public IconButton(Skin skin, String styleName, Drawable iconUp, Drawable iconDown, Drawable iconOver) {
        super(new IconButtonStyle(skin, styleName, iconUp, iconDown, iconOver));
    }
}

class IconButtonStyle extends ImageButton.ImageButtonStyle {
    protected IconButtonStyle(Skin skin, String styleName, Drawable iconUp, Drawable iconDown, Drawable iconOver) {
        Button.ButtonStyle buttonStyle = skin.get(styleName, Button.ButtonStyle.class);
        up = buttonStyle.up;
        down = buttonStyle.down;
        over = buttonStyle.over;
        imageUp = iconUp;
        imageDown = iconDown;
        imageOver = iconOver;
    }
}
