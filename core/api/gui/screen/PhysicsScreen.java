package api.gui.screen;

import box2dLight.RayHandler;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import net.qwertysam.api.rendering.Box2dDebugRenderer;
import net.qwertysam.api.util.PhysicsUtil;
import net.qwertysam.main.MyGdxGame;

public abstract class PhysicsScreen extends GuiScreen {
    private World world;
    protected RayHandler rayHandler;
    private Box2dDebugRenderer debug;
    private boolean enableDebug;

    public PhysicsScreen(MyGdxGame game) {
        this(game, false);
    }

    public PhysicsScreen(MyGdxGame game, boolean enableDebug) {
        super(game);

        // Create a physics menuWorld, the heart of the simulation. The Vector
        // passed in is gravity
        world = new World(new Vector2(0, -9.81F), true);

        rayHandler = new RayHandler(getWorld());
        rayHandler.setBlurNum(1);

        debug = new Box2dDebugRenderer(this);
        this.enableDebug = enableDebug;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        rayHandler.render();

        // Draws the buttons over everything
        renderEntries(batch);

        if (enableDebug) debug.render();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(float delta) {
        super.tick(delta);

        // Makes the menuWorld physics go
        PhysicsUtil.stepWorld(getWorld(), delta);

        rayHandler.setCombinedMatrix(game.getCamera().combined.cpy().scale(PhysicsUtil.PIXELS_PER_METER, PhysicsUtil.PIXELS_PER_METER, PhysicsUtil.PIXELS_PER_METER));

        rayHandler.update();
    }

    @Override
    public abstract void drawScreen(float delta, float cameraXOffset, float cameraYOffset);

    @Override
    public void dispose() {
        super.dispose();
        getWorld().dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }

    @Override
    public abstract void pressAction(int buttonID);

    @Override
    public abstract void releaseAction(int buttonID);

    public World getWorld() {
        return world;
    }
}
