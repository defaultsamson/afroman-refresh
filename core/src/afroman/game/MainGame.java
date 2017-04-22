package afroman.game;

import afroman.game.assets.Asset;
import afroman.game.assets.Assets;
import afroman.game.gui.MainMenu;
import afroman.game.gui.components.GuiConstants;
import afroman.game.gui.components.NoisyClickListener;
import afroman.game.io.Setting;
import afroman.game.io.Settings;
import afroman.game.util.DeviceUtil;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class MainGame extends Game {

    public static final int CAMERA_WIDTH = 240;
    public static final int CAMERA_HEIGHT = CAMERA_WIDTH * 9 / 16;
    private static final boolean isYInverted = false;

    public static MainGame game;

    private Socket socket;

    private Settings settings;
    private Assets assets;
    private MainMenu mainMenu;
    private SpriteBatch batch;
    private Texture vignette;

    public static ScreenViewport createStandardViewport() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(isYInverted);
        ScreenViewport viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(1 / game.settings.getFloat(Setting.SCALE));
        game.viewportList.add(viewport);
        return viewport;
    }

    private List<ScreenViewport> viewportList;

    public void setScale(float scale) {
        settings.putFloat(Setting.SCALE, scale);

        for (ScreenViewport port : viewportList) {
            port.setUnitsPerPixel(1 / scale);
            port.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void create() {
        game = this;

        // Loads the assets
        assets = new Assets();
        Texture.setAssetManager(assets);
        System.out.println("Loading Assets...");
        int previouslyLoaded = -1;
        long startTime = System.currentTimeMillis();
        while (!assets.update()) {
            int loaded = assets.getLoadedAssets();
            // Only outputs the percentage if the number of loaded assets has changed
            if (previouslyLoaded != loaded) {
                System.out.println("(" + loaded + ", " + (int) (assets.getProgress() * 100) + "%)");
                previouslyLoaded = loaded;
            }
        }
        System.out.println("(" + assets.getLoadedAssets() + ", 100%) Assets loaded. Took: " + ((System.currentTimeMillis() - startTime) / 1000D) + " seconds.");

        // Sets the game to invoke the keyDown() method for when the android back button has been pressed
        // Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        settings = new Settings(Gdx.app.getPreferences("settings.afro"));

        musicList = new ArrayList<Music>();
        musicVolume = settings.getFloat(Setting.MUSIC, Settings.Default.MUSIC_VOLUME);
        sfxVolume = settings.getFloat(Setting.SFX, Settings.Default.SFX_VOLUME);

        NoisyClickListener.buttonUp = MainGame.game.getAssets().getSound(Asset.BUTTON_UP);
        NoisyClickListener.buttonDown = MainGame.game.getAssets().getSound(Asset.BUTTON_DOWN);

        // If on desktop, size the window to fit the default dimensions at the provided scale.
        if (DeviceUtil.isDesktop()) {
            float scale = settings.getFloat(Setting.SCALE, Settings.Default.SCREEN_SCALE);
            resetScreenSize(scale); // TODO save the screen size from the previous runtime
            settings.putFloat(Setting.SCALE, scale);
            settings.save();
        }
        // If on Android, the default value for scaling will make the available height being drawn the value of the in-menuWorld CAMERA_HEIGHT
        else if (DeviceUtil.isAndroid()) {
            float scale = settings.getFloat(Setting.SCALE, (float) Gdx.graphics.getHeight() / (float) CAMERA_HEIGHT);
            settings.putFloat(Setting.SCALE, scale);
            settings.save();
        } else {
            settings.putFloat(Setting.SCALE, Settings.Default.SCREEN_SCALE);
        }

        batch = new SpriteBatch();
        vignette = assets.getTexture(Asset.VIGNETTE);
        viewportList = new ArrayList<ScreenViewport>();

        GuiConstants.initGuiConstants();
        mainMenu = new MainMenu();
        setScreen(mainMenu);
    }

    public void connectToServer(String ip) {
        int port = FinalConstants.defaultPort;
        if (ip.contains(":")) {
            try {
                String[] split = ip.split("[:]");
                port = Integer.parseInt(split[split.length - 1]);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < split.length - 1; i++) {
                    sb.append(split[i]);
                    if (i < split.length - 2) sb.append(':');
                }
                ip = sb.toString();
            } catch (NumberFormatException e) {
                System.err.println("Error when parsing port from IP");
                e.printStackTrace();
            }
        }

        connectToServer(ip, port);
    }

    public void connectToServer(String ip, int port) {
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, ip, port, new SocketHints());

    }

    private void resetScreenSize(float scale) {
        Gdx.graphics.setWindowedMode((int) (CAMERA_WIDTH * scale), (int) (CAMERA_HEIGHT * scale));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    private int windowedWidth;
    private int windowedHeight;

    @Override
    public void render() {
        if (DeviceUtil.isDesktop()) {
            // Debug, rescales the window size
            if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) resetScreenSize(settings.getFloat(Setting.SCALE));
            // Fullscreen
            if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(windowedWidth, windowedHeight);
                } else {
                    windowedWidth = Gdx.graphics.getWidth();
                    windowedHeight = Gdx.graphics.getHeight();
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws screen elements
        super.render();
    }

    /*
    public void drawVignette(SpriteBatch batch, Camera camera, Viewport viewport) {
        if (!(camera instanceof OrthographicCamera) || !(viewport instanceof ScreenViewport)) return;
        drawVignette(batch, (OrthographicCamera) camera, (ScreenViewport) viewport);
    }*/

    /*
    public void drawVignette(Batch batch, Camera camera, Viewport viewport) {
        if (!(camera instanceof OrthographicCamera)) return;
        drawVignette(batch, (OrthographicCamera) camera, viewport);
    }*/

    public void drawVignette(Batch batch, Camera camera, Viewport viewport) {
        if (batch == null || camera == null || viewport == null) return;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(vignette, camera.position.x - (viewport.getWorldWidth() / 2), camera.position.y - (viewport.getWorldHeight() / 2), viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();
    }

    /*
    public ScreenViewport getViewport() {
        if (this.screen != null && this.screen instanceof CameraScreen) {
            return ((CameraScreen) this.screen).getViewport();
        }
        return null;
    }*/

    /*
    public OrthographicCamera getCamera() {
        if (this.screen != null && this.screen instanceof CameraScreen) {
            return ((CameraScreen) this.screen).getCamera();
        }
        return null;
    }*/

    @Override
    public void dispose() {
        super.dispose();

        mainMenu.dispose();
        settings.save();
        assets.dispose();
        batch.dispose();
        vignette.dispose();
    }

    public Assets getAssets() {
        return assets;
    }

    protected List<Music> musicList;
    private float sfxVolume;
    private float musicVolume;

    public void setMusicVolume(float volume) {
        volume = MathUtils.clamp(volume, 0, 1);
        settings.putFloat(Setting.MUSIC, volume);
        musicVolume = volume;
        for (Music music : musicList) {
            music.setVolume(musicVolume);
        }
    }

    public void setSfxVolume(float volume) {
        volume = MathUtils.clamp(volume, 0, 1);
        settings.putFloat(Setting.SFX, volume);
        sfxVolume = volume;
    }

    /**
     * @param sound
     * @param volume the volume in the range [0,1]
     * @param pitch  the pitch multiplier, 1 == default, >1 == faster, <1 == slower, the value has to be between 0.5 and 2.0
     * @param pan    panning in the range -1 (full left) to 1 (full right). 0 is center position.
     */
    public void playSound(Sound sound, float volume, float pitch, float pan) {
        sound.play(volume * sfxVolume, pitch, pan);
    }

    /**
     * @param sound
     * @param volume the volume in the range [0,1]
     */
    public void playSound(Sound sound, float volume) {
        sound.play(volume * sfxVolume);
    }

    public void playMusic(Music music) {
        musicList.add(music);
        music.setVolume(musicVolume);
        music.play();
    }

    public void pauseAudio() {
        for (Music music : musicList) {
            music.pause();
        }
    }

    public void resumeAudio() {
        for (Music music : musicList) {
            music.play();
        }
    }

    public Settings getSettings() {
        return settings;
    }

    /**
     * Brings the camera to the centre of the screen.
     */
    public void centerCamera() {
        // TODO
    }

    /**
     * @return if the screen is inverted in the Y axis (if the y ordinates start at the bottom or the top).
     */
    public boolean isInverted() {
        return isYInverted;
    }

    public static class RemovableOnCompletionListener implements Music.OnCompletionListener {

        @Override
        public void onCompletion(Music music) {
            MainGame.game.musicList.remove(music);
        }
    }
}