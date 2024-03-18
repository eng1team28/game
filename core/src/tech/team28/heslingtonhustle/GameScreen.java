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
import tech.team28.heslingtonhustle.areas.EatArea;
import tech.team28.heslingtonhustle.areas.RecreationalArea;
import tech.team28.heslingtonhustle.areas.SleepArea;
import tech.team28.heslingtonhustle.areas.StudyArea;

/**
 * Represents the main game screen where the game logic and rendering occur.
 * Implements the {@link com.badlogic.gdx.Screen} interface.
 */
public class GameScreen implements Screen {

    final HeslingtonHustle game; // Reference to the main game instance
    private final OrthographicCamera camera; //Camera for rendering
    private final Viewport viewport; //Viewport for rendering
    private final Player player; //Player Character
    private Sprite map; //Sprite for representing game map
    private Stage stage; //Stage for UI
    private Label dayLabel; //Label for displaying the day 
    private Label timeLabel; //Label for displaying the time
    private Label energyLabel; //Label for displaying the player's energy
    private Label intelligenceLabel; //Label for displaying the player's intellignece
    private Label happinessLabel; //Label for displaying the player's happiness
    private final GameManager gameManager; //Instance of game manager

    /**
     * Constructs a new GameScreen instance with the specified game.
     * @param game The main game instance.
     */
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
        /**
         *The next couple of lines adds interactables to the game manager
         */
        gameManager.addInteractable(new StudyArea(game.atlas));
        gameManager.addInteractable(new SleepArea(game.atlas));
        gameManager.addInteractable(new RecreationalArea(game.atlas));
        gameManager.addInteractable(new EatArea(game.atlas));

        // For Debugging Interaction
        gameManager.addInteractable(
                new EatArea(
                        game.atlas,
                        1f,
                        1f,
                        GameManager.GAME_WIDTH / 2.6f,
                        GameManager.GAME_HEIGHT / 100));
        /**
         * This is the map setup
         */
        map = game.atlas.createSprite("placeholder_map");
        map.setSize(GameManager.GAME_WIDTH, GameManager.GAME_HEIGHT);

        // Scene2D stage and UI
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();

        Label.LabelStyle sillyStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        /**
         * The next couple of lines initialises labels for displaying game information
         */
        dayLabel = new Label("", sillyStyle);
        timeLabel = new Label("", sillyStyle);
        energyLabel = new Label("", sillyStyle);
        happinessLabel = new Label("", sillyStyle);
        intelligenceLabel = new Label("", sillyStyle);
        // Add labels to the table(the UI layout)
        for (Label label :
                new Label[] {dayLabel, timeLabel, energyLabel, happinessLabel, intelligenceLabel}) {
            table.row().left();
            table.add(label);
        }
        /**
         * Adds a UI element represented by a table to the stage
         * a Table is a LibGDX feature which organises and layouts UI elements 
         * in a structured manner
         * All UI elements within the table will be rendered and managed by stage
         */
        stage.addActor(table);
    }

     /**
     * Renders the game screen.
     * @param delta The time elapsed since the last frame.
     */
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
        //Draw the map
        map.draw(game.batch);
        //Draw interactables
        for (Interactable interactable : GameManager.getInstance().getInteractables()) {
            interactable.draw(game.batch);
        }

        // Draw player on top of world
        player.draw(game.batch);

        game.batch.end();

        // Draw UI on top of everything else
        dayLabel.setText(gameManager.getDayFormatted());
        timeLabel.setText(gameManager.getTimeFormatted());
        energyLabel.setText(player.getEnergyFormatted());
        happinessLabel.setText(player.getHappinessFormatted());
        intelligenceLabel.setText(player.getIntelligenceFormatted());
        stage.act(delta);
        stage.draw();
    }

    /**
     * Updates the viewport when the screen is resized.
     *
     * @param width  The new width of the screen.
     * @param height The new height of the screen.
     */
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

    /**
     * Gets rid of resources when the screen is no longer needed.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
