package afroman.game.gui;

import afroman.game.FinalConstants;
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
    private Fixture leftFixture;
    private Fixture rightFixture;
    private boolean isTouchingGround = false;
    private boolean isTouchingLeft = false;
    private boolean isTouchingRight = false;

    private ScreenViewport viewport;
    private SpriteBatch batch;

    private Stage stage;
    private Label label;
    private IconButton controlsButton;
    private IconButton settingsButton;
    private TextButton stopButton;

    private static void addRect(World world, float x, float y, float width, float height) {
        // Now create a BodyDefinition. This defines the physics objects keyboardType
        // and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PhysicsUtil.PIXELS_PER_METER, y / PhysicsUtil.PIXELS_PER_METER);

        Body staticBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2F) / PhysicsUtil.PIXELS_PER_METER, (height / 2F) / PhysicsUtil.PIXELS_PER_METER);

        // The defining features of the Fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1F;
        fixtureDef.restitution = 0F;
        fixtureDef.friction = 2F;

        staticBody.createFixture(fixtureDef);

        shape.dispose();
    }

    public PlayScreen() {
        world = new World(new Vector2(0, gravityAcceleration), true);
        rayHandler = new RayHandler(world);
        rayHandler.setBlurNum(1);
        debugRenderer = new Box2DDebugRenderer();

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA() == jumpFixture || contact.getFixtureB() == jumpFixture) {
                    isTouchingGround = true;
                } else if (contact.getFixtureA() == leftFixture || contact.getFixtureB() == leftFixture) {
                    isTouchingLeft = true;
                } else if (contact.getFixtureA() == rightFixture || contact.getFixtureB() == rightFixture) {
                    isTouchingRight = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (contact.getFixtureA() == jumpFixture || contact.getFixtureB() == jumpFixture) {
                    isTouchingGround = false;
                } else if (contact.getFixtureA() == leftFixture || contact.getFixtureB() == leftFixture) {
                    isTouchingLeft = false;
                } else if (contact.getFixtureA() == rightFixture || contact.getFixtureB() == rightFixture) {
                    isTouchingRight = false;
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

        addRect(world, -20, 65, 20, 5);
        addRect(world, -60, 95, 20, 5);

        // Ground
        addRect(world, 40, 20, 520, 6);
        // Wall
        addRect(world, 100, 80, 6, 120);

        // Creates player
        {
            // Now create a BodyDefinition. This defines the physics objects keyboardType
            // and position in the simulation
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.fixedRotation = true;
            bodyDef.position.set(40 / PhysicsUtil.PIXELS_PER_METER, 70 / PhysicsUtil.PIXELS_PER_METER);

            body = world.createBody(bodyDef);

            float halfWidth = 5;
            float halfHeight = 6;
            float sensorThickness = 0.3F;

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(halfWidth / PhysicsUtil.PIXELS_PER_METER, halfHeight / PhysicsUtil.PIXELS_PER_METER);

            // The defining features of the Fixture
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 7F;
            fixtureDef.restitution = 0.01F;
            fixtureDef.friction = 1F;

            body.createFixture(fixtureDef);

            // Jump detector
            PolygonShape shape2 = new PolygonShape();
            shape2.setAsBox((halfWidth - 0.2F) / PhysicsUtil.PIXELS_PER_METER, sensorThickness / PhysicsUtil.PIXELS_PER_METER, new Vector2(0, -halfHeight / PhysicsUtil.PIXELS_PER_METER), 0);
            FixtureDef jumpFix = new FixtureDef();
            jumpFix.shape = shape2;
            jumpFix.isSensor = true;

            jumpFixture = body.createFixture(jumpFix);

            // left detector
            PolygonShape shape3 = new PolygonShape();
            shape3.setAsBox(sensorThickness / PhysicsUtil.PIXELS_PER_METER, (halfHeight - 0.5F) / PhysicsUtil.PIXELS_PER_METER, new Vector2(-halfWidth / PhysicsUtil.PIXELS_PER_METER, 0), 0);
            FixtureDef leftFix = new FixtureDef();
            leftFix.shape = shape3;
            leftFix.isSensor = true;

            leftFixture = body.createFixture(leftFix);

            // right detector
            PolygonShape shape4 = new PolygonShape();
            shape4.setAsBox(sensorThickness / PhysicsUtil.PIXELS_PER_METER, (halfHeight - 0.5F) / PhysicsUtil.PIXELS_PER_METER, new Vector2(halfWidth / PhysicsUtil.PIXELS_PER_METER, 0), 0);
            FixtureDef rightFix = new FixtureDef();
            rightFix.shape = shape4;
            rightFix.isSensor = true;

            rightFixture = body.createFixture(rightFix);

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

        /*
        // If the player is touching the ground and has been since the last tick
        // (Allows player to perform repeated jumps while maintaining some of the previous momentum)
        if (isTouchingGround && lastIsTouchingGround) {
            body.setLinearVelocity(additiveXVelocity, body.getLinearVelocity().y);
        }
        additiveXVelocity = 0;
        */

        // lastIsTouchingGround = isTouchingGround;

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

    private boolean jumped = false;
    private float jumpAccumulator = 0F;

    /*
    private static final float maxXVelocity = 5; // m/s
    private static final float airAcceleration = 12; // m/s^2*/

    // Vertical movement
    private static final float gravityAcceleration = -50F;//-9.81F; // m/s^2
    private static final float jumpTime = 0.135F; // s
    private static final float jumpHeight = 60 / PhysicsUtil.PIXELS_PER_METER; // m
    // Uses quadratic formula to solve the jump speed based on the desired height, jump time, and acceleration
    // d1 + d2 = d                          Total displacement is known. It is the sum of the displacement during constant velocity and gravitational acceleration.
    // t(v) + (-1/2a)(v^2) = d              Using kinematic equations, we can set d1 to the constant velocity displacement, and d2 to the uniform acceleration velocity.
    // - (1/2a)(v^2) + t(v) - d = 0           Rearranging the equation into quadratic form
    // v = (-a)(-t +/- sqrt(t^2 - 2*d/a))   Simplified Quadratic formula
    private static final float jumpSpeed = (float) ((-gravityAcceleration) * (-jumpTime + Math.sqrt((jumpTime * jumpTime) - (2 * jumpHeight / gravityAcceleration)))); // m/s
    private static final float minJumpReleaseVelocity = jumpSpeed / 2F; // m/s

    // Horizontal movement
    private static final float accelerationTime = 0.06F; // s
    private static final float xSpeed = (120 / PhysicsUtil.PIXELS_PER_METER); // m/s
    private static final float xAcceleration = xSpeed / accelerationTime; // m/s^2
    private static final float xAirAcceleration = xAcceleration / 4; // m/s^2

    public void jump(float upPress) {
        if (!isShowingEscMenu) {
            // If jump is being toggled
            if (Math.abs(upPress) > FinalConstants.analogueTriggerThreshold) {
                if (jumped) {
                    jumpAccumulator += Gdx.graphics.getDeltaTime();
                    if (jumpAccumulator >= jumpTime) {
                        jumped = false;
                        jumpAccumulator = 0;
                    }
                }

                // If the player isn't jumping, but they're touching the ground, make 'em jump
                if (isTouchingGround) {
                    isTouchingGround = false;
                    jumped = true;
                    System.out.println("Is touching ground");
                }

                // If already jumping
                if (jumped) {
                    // Continue setting the velocity to the max jump velocity until the player lets go of the key
                    body.setLinearVelocity(body.getLinearVelocity().x, jumpSpeed);
                }
            }
            // If the button was released while the player was jumping
            else if (jumped) {
                body.setLinearVelocity(body.getLinearVelocity().x, Math.max(minJumpReleaseVelocity, jumpSpeed * (jumpAccumulator / jumpTime)));
                jumped = false;
                jumpAccumulator = 0;
            } else {
                jumped = false;
                jumpAccumulator = 0;
            }
        }
    }

    public void left(float leftPress) {
        if (!isShowingEscMenu) {
            if (Math.abs(leftPress) > FinalConstants.analogueTriggerThreshold) {
                if (isTouchingRight && !isTouchingGround) {
                    isTouchingRight = false;
                    body.setLinearVelocity(body.getLinearVelocity().x - (jumpSpeed / 3F), Math.min(body.getLinearVelocity().y + jumpSpeed, jumpSpeed));
                } else if (body.getLinearVelocity().x > -xSpeed) {
                    body.setLinearVelocity(body.getLinearVelocity().add(-(isTouchingGround ? xAcceleration : xAirAcceleration) * Gdx.graphics.getDeltaTime(), 0));
                }
            }
        }
    }

    public void right(float rightPress) {
        if (!isShowingEscMenu) {
            if (Math.abs(rightPress) > FinalConstants.analogueTriggerThreshold) {
                if (isTouchingLeft && !isTouchingGround) {
                    isTouchingLeft = false;
                    body.setLinearVelocity(body.getLinearVelocity().x + (jumpSpeed / 3F), Math.min(body.getLinearVelocity().y + jumpSpeed, jumpSpeed));
                } else if (body.getLinearVelocity().x < xSpeed) {
                    body.setLinearVelocity(body.getLinearVelocity().add((isTouchingGround ? xAcceleration : xAirAcceleration) * Gdx.graphics.getDeltaTime() * Math.abs(rightPress), 0));
                }
            }
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
