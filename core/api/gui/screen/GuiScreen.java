package api.gui.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.qwertysam.api.gui.GuiButton;
import net.qwertysam.api.rendering.RenderableHolder;
import net.qwertysam.api.resource.IDisposable;
import net.qwertysam.api.util.InputUtil;
import net.qwertysam.main.MyGdxGame;

public abstract class GuiScreen extends RenderableHolder<GuiButton> implements Screen, IDisposable {
    public static final int MAX_TOUCHES = 4; // Typically hardware limit is 10

    private boolean isTouched;
    private List<Vector2> touches;

    protected MyGdxGame game;
    protected Viewport viewport;
    protected SpriteBatch batch;

    /**
     * The x offset of the buttons.
     */
    private float xButtonOffset;
    /**
     * The y offset of the buttons.
     */
    private float yButtonOffset;

    public GuiScreen(MyGdxGame game) {
        super();

        isTouched = false;
        touches = new ArrayList<Vector2>();

        this.game = game;

        batch = new SpriteBatch();

        init();

        game.getCamera().setToOrtho(game.isInverted(), MyGdxGame.CAMERA_WIDTH, MyGdxGame.CAMERA_HEIGHT);
        viewport = new ScalingViewport(Scaling.fillX, MyGdxGame.CAMERA_WIDTH, MyGdxGame.CAMERA_HEIGHT, game.getCamera());

    }

    public void init() {
    }

    @Override
    public void show() {

    }

    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        tick(delta);

        // Resizes sprite batch to the screen size
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Clears the screen
        Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begins the drawing phase
        batch.begin();

        float cameraXOffset = game.getCamera().position.x - (getWidth() / 2);
        float cameraYOffset = game.getCamera().position.y - (getHeight() / 2);

        // Draws the actual shiz in the screen
        drawScreen(delta, cameraXOffset, cameraYOffset);

        // Ends the drawing phase
        batch.end();

        // Updates the camera
        game.getCamera().update();
    }

    public void tick(float delta) {
        touches.clear();

        isTouched = Gdx.input.isTouched();

        touches = InputUtil.getTouches(MAX_TOUCHES, viewport);

        if (!isEmpty())
            buttonTick(touches, game.getCamera().position.x - (game.getCamera().viewportWidth / 2), game.getCamera().position.y - (game.getCamera().viewportHeight / 2));
    }

    /**
     * Draws the screen.
     *
     * @param delta the time between the last time the screen was drawn and the time that this is currently being drawn
     */
    public abstract void drawScreen(float delta, float cameraXOffset, float cameraYOffset);

    @Override
    public void resize(int width, int height) {
        System.out.println("Resizing ViewPort: (" + width + ", " + height + ")");
        viewport.update(width, height, true);
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
        batch.dispose();
        touches.clear();
    }

    public float getWidth() {
        return game.getCamera().viewportWidth;
    }

    public float getHeight() {
        return game.getCamera().viewportHeight;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public MyGdxGame getGame() {
        return game;
    }

    /**
     * Ticks all buttons in this.
     *
     * @param touch the touch position on the screen
     */
    public void buttonTick(List<Vector2> touch) {
        buttonTick(touch, 0F, 0F);
    }

    /**
     * Ticks all buttons in this.
     *
     * @param touches the touches' positions on the screen
     * @param xOffset the bottom left x ordinate of the camera position relative to the world
     * @param yOffset the bottom left y ordinate of the camera position relative to the world
     */
    public void buttonTick(List<Vector2> touches, float xOffset, float yOffset) {
        xButtonOffset = xOffset;
        yButtonOffset = yOffset;

        if (touches.isEmpty()) {
            updateButtons(null);
        } else {
            // Need this so that the original touches don't get modified.
            // Modifying the original touches will screw with other game elements that rely on them.
            List<Vector2> outTouches = new ArrayList<Vector2>();

            for (Vector2 touch : touches) {
                outTouches.add(touch.cpy().add(-xButtonOffset, -yButtonOffset));
            }

            updateButtons(outTouches);
        }
    }

    /**
     * Operates in the same way as renderEntries(SpriteBatch batch);
     *
     * @param batch the SpriteBatch to draw the buttons to
     */
    public void renderButtons(SpriteBatch batch) {
        renderEntries(batch);
    }

    @Override
    public void renderEntries(SpriteBatch batch) {
        renderEntries(batch, xButtonOffset, yButtonOffset);
    }

    /**
     * Updates all the buttons in this.
     *
     * @param touch the touch to pass to the buttons.
     */
    private void updateButtons(List<Vector2> touches) {
        for (GuiButton entry : getEntries()) {
            entry.updateTouches(touches);
        }
    }

    /**
     * Fires whenever a button is pressed.
     *
     * @param buttonID the id of the button
     */
    public abstract void pressAction(int buttonID);

    /**
     * Fires whenever a button is released.
     *
     * @param buttonID the id of the button
     */
    public abstract void releaseAction(int buttonID);

    /**
     * @return if this GuiScreen is currently touched.
     */
    public boolean isTouched() {
        return isTouched;
    }

    /**
     * @return the screen touches.
     * <p>
     * <b>WARNING:</b> Use isTouched() to ensure that the screen is being touched before trying to use this method.
     */
    public List<Vector2> getTouches() {
        return touches;
    }
}
