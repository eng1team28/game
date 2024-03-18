package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class HeslingtonHustle extends Game {
    public AssetManager manager;
    public SpriteBatch batch;
    public TextureAtlas atlas;
    public static final String ATLAS_NAME = "textures/pack.atlas";
    public static final String MAP_NAME = "map/map.tmx";

    @Override
    public void create() {

        // Asset manager loading
        manager = new AssetManager();
        manager.load(ATLAS_NAME, TextureAtlas.class);
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        manager.load(MAP_NAME, TiledMap.class);
        manager.finishLoading();

        // Create shared resources
        batch = new SpriteBatch();
        atlas = manager.get(ATLAS_NAME);
        this.setScreen(new GameScreen(this));
    }

    public void examCutscene(boolean winBool) {
        this.setScreen(new ExamPresenter(this, winBool));
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Disposing of the manager disposes of all its assets
        manager.dispose();
    }
}
