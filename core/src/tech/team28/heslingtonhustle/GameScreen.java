package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    final HeslingtonHustle game;
    private final OrthographicCamera camera;
    private final Player player;
    private Stage stage;
    private Label timeLabel;
    private GameManager gameManager;

    public GameScreen(final HeslingtonHustle game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);

        player = new Player();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(player.getPlayerImage(), player.getCollider().x, player.getCollider().y);
        game.batch.end();

        timeLabel.setText(gameManager.getTime());
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        gameManager = GameManager.getInstance();
        stage = new Stage(new ScreenViewport());
        timeLabel =
                new Label(
                        gameManager.getTime(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Table table = new Table();
        table.add(timeLabel);
        stage.addActor(table);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        player.getPlayerImage().dispose();
        stage.dispose();
    }
}
