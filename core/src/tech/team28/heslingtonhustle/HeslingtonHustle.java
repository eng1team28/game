package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class HeslingtonHustle extends Game {
    public AssetManager manager;
    public SpriteBatch batch;
    public TextureAtlas atlas;
    public BitmapFont font;
    public static final String ATLAS_NAME = "textures/pack.atlas";
    public static final String MAP_NAME = "map/map.tmx";
    public static final String FONT_NAME = "lsans-32.fnt";
    public static final String SOUND_QUACK = "sound/duck-quacking.mp3";
    public static final String SOUND_EATING = "sound/eating.mp3";
    public static final String SOUND_STUDY = "sound/library-environment.mp3";
    public static final String SOUND_SLEEP = "sound/sleep.mp3";

    @Override
    public void create() {

        // Asset manager loading
        manager = new AssetManager();
        manager.load(ATLAS_NAME, TextureAtlas.class);
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(MAP_NAME, TiledMap.class);
        manager.load(FONT_NAME, BitmapFont.class);
        manager.load(SOUND_EATING, Sound.class);
        manager.load(SOUND_QUACK, Sound.class);
        manager.load(SOUND_STUDY, Sound.class);
        manager.load(SOUND_SLEEP, Sound.class);
        manager.finishLoading();

        // Create shared resources
        batch = new SpriteBatch();
        atlas = manager.get(ATLAS_NAME);
        font = manager.get(FONT_NAME);
        this.setScreen(new GameScreen(this));
    }

    public void examCutscene(boolean winBool) {
        this.setScreen(new ExamPresenter(this, winBool));
    }

    @Override
    public void render() {
        super.render();
        // Fast quit keybinding
        if (Util.allKeysPressed(Input.Keys.CONTROL_LEFT, Input.Keys.Q)
                || Util.allKeysPressed(Input.Keys.CONTROL_RIGHT, Input.Keys.Q)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Disposing of the manager disposes of all its assets
        manager.dispose();
    }
}
