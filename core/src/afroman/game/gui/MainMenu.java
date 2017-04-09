package afroman.game.gui;

import afroman.game.MainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Samson on 2017-04-08.
 */
public class MainMenu implements Screen {

    private Stage stage;

    Image img;

    public MainMenu() {
        //Skin skin = new Skin(Gdx.files.internal("skin/craftacular-ui.json"));
        Skin skin = new Skin(Gdx.files.internal("afro/afro.json"));

        stage = new Stage(MainGame.createStandardViewport()); // TODO may need to use some sort of viewport
        Gdx.input.setInputProcessor(stage);

        img = new Image(new Texture("badlogic.jpg"));
        stage.addActor(img);
        img.setPosition(50, 2);

        /*
        Slider slider = new Slider(1.0F, 10F, 0.1F, false, skin);
        slider.setSize(170, 20);
        slider.setPosition(39, 50);
        stage.addActor(slider);*/

        Button button2 = new TextButton("Dank \nmemes", skin, "default");
        button2.setSize(100, 20);
        button2.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touched Up");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Touched Down");
                return true;
            }
        });
        stage.addActor(button2);

        /*
        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        Table main = new Table();
        main.setFillParent(true);

        // Create the tab buttons
        HorizontalGroup group = new HorizontalGroup();
        final Button tab1 = new TextButton("Tab1", skin, "toggle");
        final Button tab2 = new TextButton("Tab2", skin, "toggle");
        final Button tab3 = new TextButton("Tab3", skin, "toggle");
        group.addActor(tab1);
        group.addActor(tab2);
        group.addActor(tab3);
        main.add(group);
        main.row();*/
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();

        float x = img.getX();
        float y = img.getY();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= 1;
        img.setPosition(x, y);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            System.out.println("dankmeme22222s");
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getViewport().getCamera().update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
