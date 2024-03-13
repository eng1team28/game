package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    final HeslingtonHustle game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Player player;
    private Sprite map;
    private Stage stage;
    private Label dayLabel;
    private Label timeLabel;
    private Label energyLabel;
    private Label intelligenceLabel;
    private Label happinessLabel;
    private final GameManager gameManager;

    public GameScreen(final HeslingtonHustle game) {
        this.game = game;
        gameManager = GameManager.getInstance();

        // Camera
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(0, GameManager.VIEW_HEIGHT, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Player and interactables
        player = new Player(game.atlas);
        gameManager.setPlayer(player);
        gameManager.addInteractable(new StudyArea(game.atlas));
        gameManager.addInteractable(new SleepArea(game.atlas));
        gameManager.addInteractable(new RecreationalArea(game.atlas));
        gameManager.addInteractable(new EatArea(game.atlas));

        map = game.atlas.createSprite("placeholder_map");
        map.setSize(GameManager.GAME_WIDTH, GameManager.GAME_HEIGHT);

        // Scene2D stage and UI
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        Label.LabelStyle sillyStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        dayLabel = new Label("", sillyStyle);
        timeLabel = new Label("", sillyStyle);
        energyLabel = new Label("", sillyStyle);
        happinessLabel = new Label("", sillyStyle);
        intelligenceLabel = new Label("", sillyStyle);
        for (Label label :
                new Label[] {dayLabel, timeLabel, energyLabel, happinessLabel, intelligenceLabel}) {
            table.row().left();
            table.add(label);
        }

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        // Fast quit keybinding
        if (Util.allKeysPressed(Input.Keys.CONTROL_LEFT, Input.Keys.Q)
                || Util.allKeysPressed(Input.Keys.CONTROL_RIGHT, Input.Keys.Q)) {
            Gdx.app.exit();
        }

        // Clear screen ready for new frame
        ScreenUtils.clear(Color.BLACK);

        // Player movement
        player.update(delta);

        // Camera follows player
        Vector2 cameraPosition = new Vector2();
        Rectangle playerCollider = player.getCollider();
        playerCollider.getPosition(cameraPosition);
        cameraPosition.add(playerCollider.width / 2, playerCollider.height / 2);
        float xBorder = camera.viewportWidth / 2;
        float yBorder = camera.viewportHeight / 2;
        cameraPosition.x =
                MathUtils.clamp(cameraPosition.x, xBorder, GameManager.GAME_WIDTH - xBorder);
        cameraPosition.y =
                MathUtils.clamp(cameraPosition.y, yBorder, GameManager.GAME_HEIGHT - yBorder);
        camera.position.set(cameraPosition, 0);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        map.draw(game.batch);

        for (Interactable interactable : GameManager.getInstance().getInteractables()) {
            interactable.draw(game.batch);
        }

        // Draw player on top of world
        game.batch.draw(player.getPlayerImage(), player.getCollider().x, player.getCollider().y);

        game.batch.end();

        // Draw UI on top of everything else
        dayLabel.setText(gameManager.getDayFormatted());
        timeLabel.setText(gameManager.getTimeFormatted());
        energyLabel.setText(player.getEnergyFormatted());
        happinessLabel.setText(player.getHappinessFormatted());
        intelligenceLabel.setText(player.getIntelligenceFormatted());
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
