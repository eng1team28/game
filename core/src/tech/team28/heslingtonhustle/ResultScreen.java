package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** A Screen displaying the final result of the play session. */
public class ResultScreen implements Screen {
    final HeslingtonHustle game;
    private final Viewport viewport;
    private final Stage stage;

    /**
     * Create the game screen to display victory or defeat.
     *
     * @param game the game instance
     * @param winBool true for victory game over screen, false for defeat
     */
    public ResultScreen(HeslingtonHustle game, boolean winBool) {
        this.game = game;

        // Create stage
        viewport = new ScreenViewport();
        stage = new Stage(viewport, game.batch);

        String resultSpriteName = (winBool) ? "win" : "lose";
        Image resultImage = new Image(game.atlas.findRegion(resultSpriteName));

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(resultImage);

        stage.addActor(table);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // dispose of assets when not needed any more
        stage.dispose();
    }
}
