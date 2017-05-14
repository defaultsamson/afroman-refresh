package afroman.game.gui;

import afroman.game.MainGame;
import afroman.game.assets.Asset;
import afroman.game.gui.components.GuiConstants;
import afroman.game.gui.components.IconButton;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.io.ControlMapType;
import afroman.game.util.PhysicsUtil;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static afroman.game.gui.components.GuiConstants.skin;

/**
 * Created by Samson on 2017-05-11.
 */
public class PlayScreen implements Screen {

    private World world;
    private RayHandler rayHandler;
    private Box2DDebugRenderer debugRenderer;

    private Body body;
    private Fixture jumpFixture;
    private boolean lastIsTouchingGround = false;
    private boolean isTouchingGround = false;

    private ScreenViewport viewport;
    private SpriteBatch batch;

    private Stage stage;
    private Label label;
    private IconButton controlsButton;
    private IconButton settingsButton;
    private TextButton stopButton;

    public PlayScreen() {
        world = new World(new Vector2(0, standardGravityAcceleration), true);
        rayHandler = new RayHandler(world);
        rayHandler.setBlurNum(1);
        debugRenderer = new Box2DDebugRenderer();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA() == jumpFixture || contact.getFixtureB() == jumpFixture) {
                    System.out.println("Colliding");
                    isTouchingGround = true;
                    //contact.getWorldManifold().getNormal()
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (contact.getFixtureA() == jumpFixture || contact.getFixtureB() == jumpFixture) {
                    System.out.println("Uncolliding");
                    isTouchingGround = false;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        viewport = MainGame.createStandardViewport();
        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);
        label = new Label("Pos: 0, 0", GuiConstants.skin);
        label.setPosition(20, 20);
        stage.addActor(label);

        int buttonWidth = 76;
        int buttonHeight = 16;

        int buttonYOffset = -42;
        final int buttonSpacing = 6;

        Texture settingsIcon = MainGame.game.getAssets().getTexture(Asset.SETTINGS_ICON);
        settingsButton = new IconButton(skin, settingsIcon);
        settingsButton.setDisabled(true);
        settingsButton.setVisible(false);
        settingsButton.setSize(buttonHeight, buttonHeight);
        settingsButton.setPosition((-buttonWidth / 2) - (2 * (buttonHeight + buttonSpacing)), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        settingsButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getSettingsMenu().setParent(PlayScreen.this);
                MainGame.game.safelySetScreen(MainGame.game.getSettingsMenu());
            }
        });
        stage.addActor(settingsButton);

        Texture controlsIcon = MainGame.game.getAssets().getTexture(Asset.CONTROLLER_ICON);
        controlsButton = new IconButton(skin, controlsIcon);
        controlsButton.setDisabled(true);
        controlsButton.setVisible(false);
        controlsButton.setSize(buttonHeight, buttonHeight);
        controlsButton.setPosition((-buttonWidth / 2) - (1 * (buttonHeight + buttonSpacing)), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        controlsButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.game.getControlsMenu().setParent(PlayScreen.this);
                MainGame.game.safelySetScreen(MainGame.game.getControlsMenu());
            }
        });
        stage.addActor(controlsButton);

        stopButton = new TextButton(MainGame.game.getNetworkManager().isHostingServer() ? "Stop Server" : "Disconnect", skin);
        stopButton.setDisabled(true);
        stopButton.setVisible(false);
        stopButton.setSize(buttonWidth, buttonHeight);
        stopButton.setPosition(-(buttonWidth / 2), buttonYOffset + (0 * (buttonHeight + buttonSpacing)));
        stopButton.addListener(new NoisyClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        stage.addActor(stopButton);

        // Creates ground
        {
            // Now create a BodyDefinition. This defines the physics objects keyboardType
            // and position in the simulation
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.fixedRotation = true;
            bodyDef.position.set(40 / PhysicsUtil.PIXELS_PER_METER, 20 / PhysicsUtil.PIXELS_PER_METER);

            Body staticBody = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(60 / PhysicsUtil.PIXELS_PER_METER, 3 / PhysicsUtil.PIXELS_PER_METER);

            // The defining features of the Fixture
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1F;
            fixtureDef.restitution = 0F;
            fixtureDef.friction = 2F;

            staticBody.createFixture(fixtureDef);
        }

        // Creates wall
        {
            // Now create a BodyDefinition. This defines the physics objects keyboardType
            // and position in the simulation
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.fixedRotation = true;
            bodyDef.position.set(100 / PhysicsUtil.PIXELS_PER_METER, 80 / PhysicsUtil.PIXELS_PER_METER);

            Body staticBody = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(3 / PhysicsUtil.PIXELS_PER_METER, 60 / PhysicsUtil.PIXELS_PER_METER);

            // The defining features of the Fixture
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1F;
            fixtureDef.restitution = 0F;
            fixtureDef.friction = 0.03F;

            staticBody.createFixture(fixtureDef);
        }

        // Creates player
        {
            // Now create a BodyDefinition. This defines the physics objects keyboardType
            // and position in the simulation
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.fixedRotation = true;
            bodyDef.position.set(40 / PhysicsUtil.PIXELS_PER_METER, 70 / PhysicsUtil.PIXELS_PER_METER);

            body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(5 / PhysicsUtil.PIXELS_PER_METER, 6 / PhysicsUtil.PIXELS_PER_METER);

            // The defining features of the Fixture
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 7F;
            fixtureDef.restitution = 0.01F;
            fixtureDef.friction = 1F;

            body.createFixture(fixtureDef);

            // Jump detector
            PolygonShape shape2 = new PolygonShape();
            shape2.setAsBox(4F / PhysicsUtil.PIXELS_PER_METER, 0.3F / PhysicsUtil.PIXELS_PER_METER, new Vector2(0, -6F / PhysicsUtil.PIXELS_PER_METER), 0);
            FixtureDef jumpFix = new FixtureDef();
            jumpFix.shape = shape2;
            jumpFix.isSensor = true;

            jumpFixture = body.createFixture(jumpFix);

            // Shape is the only disposable of the lot, so get rid of it
            shape.dispose();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(isShowingEscMenu ? stage : null);
    }

    @Override
    public void render(float delta) {

        MainGame.game.getControls().getControlInput(ControlMapType.UP);

        label.setText("Pos: " + body.getPosition().x + ", " + body.getPosition().y);

        rayHandler.updateAndRender();
        /*rayHandler.setCombinedMatrix(camera.combined.cpy().scale(PhysicsUtil.PIXELS_PER_METER,
                PhysicsUtil.PIXELS_PER_METER, PhysicsUtil.PIXELS_PER_METER));*/
        debugRenderer.render(world, viewport.getCamera().combined.cpy().scale(PhysicsUtil.PIXELS_PER_METER,
                PhysicsUtil.PIXELS_PER_METER, PhysicsUtil.PIXELS_PER_METER));

        // If the player is touching the ground and has been since the last tick
        // (Allows player to perform repeated jumps while maintaining some of the previous momentum)
        if (isTouchingGround && lastIsTouchingGround) {
            body.setLinearVelocity(additiveXVelocity, body.getLinearVelocity().y);
        }
        additiveXVelocity = 0;

        lastIsTouchingGround = isTouchingGround;

        PhysicsUtil.stepWorld(world, delta);

        Vector3 cameraPos = viewport.getCamera().position;
        // Focuses camera onto main body
        cameraPos.x = body.getPosition().x * PhysicsUtil.PIXELS_PER_METER;
        cameraPos.y = body.getPosition().y * PhysicsUtil.PIXELS_PER_METER;

        // Align Gui to camera and render
        stage.getRoot().setPosition(cameraPos.x, cameraPos.y);
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isShowingEscMenu = !isShowingEscMenu;

            controlsButton.setDisabled(!isShowingEscMenu);
            controlsButton.setVisible(isShowingEscMenu);

            settingsButton.setDisabled(!isShowingEscMenu);
            settingsButton.setVisible(isShowingEscMenu);

            stopButton.setDisabled(!isShowingEscMenu);
            stopButton.setVisible(isShowingEscMenu);

            Gdx.input.setInputProcessor(isShowingEscMenu ? stage : null);
        }
    }

    private boolean isShowingEscMenu = false;
    private static final float standardGravityAcceleration = -36F;//-9.81F; // m/s^2
    private static final float maxXVelocity = 5; // m/s
    private static final float airAcceleration = 12; // m/s^2
    private static final float jumpVelocity = 10; // m/s
    private static final float jumpVelocitySquared = jumpVelocity * jumpVelocity; // m^2/s^2
    private static final float jumpReleaseVelocity = 8; // m/s
    private static final float minJumpReleaseVelocity = jumpReleaseVelocity / 2F; // m/s
    private static final float maximumMaxJumpTime = 0.15F; // s

    private boolean jumpTimeTriggered = false;
    private boolean jumped = false;
    private float jumpAccumulator = 0F;

    public void jump(float upPress) {
        if (!isShowingEscMenu) {
            // If jump is being toggled
            if (Math.abs(upPress) > 0.1F) {

                if (jumped) {
                    jumpAccumulator += Gdx.graphics.getDeltaTime();
                    if (jumpAccumulator >= maximumMaxJumpTime) {
                        jumped = false;
                        jumpTimeTriggered = true;
                        jumpAccumulator = 0;
                    }
                }

                // If the player isn't jumping, but they're touching the ground, make 'em jump
                if (isTouchingGround) {
                    if (!lastIsTouchingGround) {

                    }
                    jumped = true;
                }

                // If already jumping
                if (jumped) {
                    float xVel = body.getLinearVelocity().x;
                    float yVel = (float) Math.sqrt(jumpVelocitySquared - (xVel * xVel));
                    // Continue setting the velocity to the max jump velocity until the player lets go of the key
                    body.setLinearVelocity(xVel, yVel);
                }
            }
            // If the player released the button but was stopped by the jump time trigger
            else if (jumpTimeTriggered) {
                // If the vertical velocity is still greater than the minimum
                if (body.getLinearVelocity().y > minJumpReleaseVelocity) {
                    body.setLinearVelocity(body.getLinearVelocity().x, minJumpReleaseVelocity);
                }
                jumpTimeTriggered = false;
                jumped = false;
                jumpAccumulator = 0;
            }
            // If the button was released while the player was jumping
            else if (jumped) {
                body.setLinearVelocity(body.getLinearVelocity().x, Math.max(minJumpReleaseVelocity, jumpReleaseVelocity * (jumpAccumulator / maximumMaxJumpTime)));
                jumped = false;
                jumpAccumulator = 0;
            } else {
                jumped = false;
                jumpAccumulator = 0;
            }
        }
    }


    // The desired max velocities for when the left or right controls are pressed
    private float additiveXVelocity = 0;

    public void left(float leftPress) {
        if (!isShowingEscMenu) {
            // If touching the ground
            if (isTouchingGround) {
                // Immediately start moving
                additiveXVelocity -= Math.abs(maxXVelocity * leftPress);
            } else {
                // If in the air, allow to accelerate somewhat
                body.setLinearVelocity(body.getLinearVelocity().add(-Math.abs(airAcceleration * leftPress) * Gdx.graphics.getDeltaTime(), 0));
            }
        }
    }

    public void right(float rightPress) {
        if (!isShowingEscMenu) {
            if (isTouchingGround) {
                additiveXVelocity += Math.abs(maxXVelocity * rightPress);
            } else {
                body.setLinearVelocity(body.getLinearVelocity().add(airAcceleration * rightPress * Gdx.graphics.getDeltaTime(), 0));
            }
            /*
            if (body.getLinearVelocity().x < maxXVelocity) {
                float acc = isTouchingGround ? acceleration : airAcceleration;
                body.setLinearVelocity(body.getLinearVelocity().add(acc * Gdx.graphics.getDeltaTime(), 0));
            }*/
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.getCamera().update();
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
        rayHandler.dispose();
        world.dispose();
    }
}
