package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class HeslingtonHustle extends Game {
    private AssetManager manager;
    public SpriteBatch batch;
    public TextureAtlas atlas;
    private static final String ATLAS_NAME = "pack.atlas";


    @Override
    public void create() {
        

        // Asset manager loading
        manager = new AssetManager();
        manager.load(ATLAS_NAME, TextureAtlas.class);
        manager.finishLoading();

        // Create shared resources
        batch = new SpriteBatch();
        atlas = manager.get(ATLAS_NAME, TextureAtlas.class);
        this.setScreen(new GameScreen(this));
    }

    public void examCutscene(boolean winBool){
        this.setScreen(new ExamPresenter(this, winBool));
    }

    @Override
    public void dispose() {
        batch.dispose();
        // Disposing of the manager disposes of all its assets
        manager.dispose();
    }


}
