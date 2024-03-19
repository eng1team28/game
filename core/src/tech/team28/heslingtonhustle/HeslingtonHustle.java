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

    @Override
    public void create() {

        // Asset manager loading
        manager = new AssetManager();
        manager.load(AssetNames.TEXTURE_ATLAS, TextureAtlas.class);
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(AssetNames.TILE_MAP, TiledMap.class);
        manager.load(AssetNames.BITMAP_FONT, BitmapFont.class);
        manager.load(AssetNames.SOUND_EATING, Sound.class);
        manager.load(AssetNames.SOUND_QUACK, Sound.class);
        manager.load(AssetNames.SOUND_STUDY, Sound.class);
        manager.load(AssetNames.SOUND_SLEEP, Sound.class);
        manager.finishLoading();

        // Create shared resources
        batch = new SpriteBatch();
        atlas = manager.get(AssetNames.TEXTURE_ATLAS);
        font = manager.get(AssetNames.BITMAP_FONT);
        this.setScreen(new GameScreen(this));
    }

    public void examCutscene(boolean winBool) {
        this.setScreen(new ResultScreen(this, winBool));
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
