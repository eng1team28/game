package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tech.team28.heslingtonhustle.areas.EatArea;
import tech.team28.heslingtonhustle.areas.RecreationArea;
import tech.team28.heslingtonhustle.areas.SleepArea;
import tech.team28.heslingtonhustle.areas.StudyArea;

/**
 * Represents the main game screen where the game logic and rendering is all co-ordinated during
 * gameplay.
 */
public class GameScreen implements Screen {
    /** Reference to the main game instance. */
    final HeslingtonHustle game;

    /** Camera for rendering. */
    private final OrthographicCamera camera;

    /** Viewport for managing camera dimensions. */
    private final Viewport viewport;

    /** Player Character */
    private final Player player;

    /** MapRenderer for drawing the background image. */
    private final OrthogonalTiledMapRenderer mapRenderer;

    /** Scene2D stage only used for UI. */
    private final Stage stage;

    // We have many labels
    // Could move these into some other class for UI?
    /** Label for displaying the day of the week. */
    private final Label dayLabel;

    /** Label for displaying the time of day. */
    private final Label timeLabel;

    /** Label for displaying the player's energy. */
    private final Label energyLabel;

    /** Label for displaying the counter values */
    private final Label counterLabel;

    /** Keeping a reference to GameManager's singleton instance for convenience. */
    private final GameManager gameManager;

    /**
     * Constructs a new GameScreen instance with the specified game.
     *
     * <p>This sets up the camera, viewport, and UI, and creates the player object and all the
     * interactables by getting their sprites from the game's texture atlas, along with adding them
     * to the game manager.
     *
     * @param game The main game instance.
     */
    public GameScreen(final HeslingtonHustle game) {
        this.game = game;
        gameManager = GameManager.getInstance();
        gameManager.setGame(game);

        // Camera
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(0, GameManager.VIEW_HEIGHT, camera);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Player and interactables
        Array<Sprite> animationFrames = new Array<>(8);
        for (int spriteNum = 1; spriteNum <= 8; spriteNum++) {
            Sprite frameSprite =
                    game.atlas.createSprite(String.format("player/player%d", spriteNum));

            animationFrames.add(frameSprite);
        }
        player =
                new Player(
                        game.atlas.createSprite("player/player0"),
                        animationFrames,
                        GameManager.GAME_WIDTH * 0.21f,
                        GameManager.GAME_WIDTH * 0.625f);
        gameManager.setPlayer(player);

        // The next couple of lines adds interactables to the game manager
        StudyArea studyArea =
                new StudyArea(
                        "Computer Science Building",
                        game.manager.get(AssetNames.SOUND_STUDY),
                        game.atlas.createSprite("buildings/cs"),
                        GameManager.GAME_WIDTH * 0.10f,
                        GameManager.GAME_HEIGHT * 0.01f);
        studyArea.setScale(0.5f);
        SleepArea sleepArea =
                new SleepArea(
                        "Goodricke College",
                        game.manager.get(AssetNames.SOUND_SLEEP),
                        game.atlas.createSprite("buildings/college"),
                        GameManager.GAME_WIDTH * 0.2f,
                        GameManager.GAME_HEIGHT * 0.55f);
        RecreationArea feedTheDucks =
                new RecreationArea(
                        "Duck Pond",
                        game.manager.get(AssetNames.SOUND_QUACK),
                        game.atlas.createSprite("ducks"),
                        GameManager.GAME_WIDTH * 0.5f,
                        GameManager.GAME_HEIGHT * 0.2f);
        feedTheDucks.setScale(0.4f);
        EatArea eatArea =
                new EatArea(
                        "Pizza Building",
                        game.manager.get(AssetNames.SOUND_EATING),
                        game.atlas.createSprite("buildings/piazza"),
                        GameManager.GAME_WIDTH * 0.60f,
                        GameManager.GAME_HEIGHT * 0.30f);
        eatArea.setScale(0.6f);

        gameManager.addInteractable(studyArea);
        gameManager.addInteractable(sleepArea);
        gameManager.addInteractable(eatArea);
        gameManager.addInteractable(feedTheDucks);

        // This is the map setup
        TiledMap map = game.manager.get(AssetNames.TILE_MAP);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

        // Scene2D stage and UI
        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        table.pad(10f);

        BitmapFont currentFont = game.font;
        Label.LabelStyle sillyStyle = new Label.LabelStyle(currentFont, Color.WHITE);

        // The next couple of lines initialises labels for displaying game information
        dayLabel = new Label("", sillyStyle);
        timeLabel = new Label("", sillyStyle);
        energyLabel = new Label("", sillyStyle);
        counterLabel = new Label("", sillyStyle);
        // Add labels to the table(the UI layout)
        for (Label label :
                new Label[] {
                    dayLabel, timeLabel, energyLabel, counterLabel,
                }) {
            table.row().left();
            table.add(label);
        }

        // Label for copyright notice
        Label disclaimerLabel =
                new Label("Map background (c) OpenStreetMap contributors", sillyStyle);
        Table lowerTable = new Table();
        // todo make this right-aligned?
        lowerTable.bottom().left();
        lowerTable.pad(10f);
        lowerTable.row().left();
        lowerTable.add(disclaimerLabel);

        /*
         Adds a UI element represented by a table to the stage a Table is a LibGDX feature which
         organises and layouts UI elements in a structured manner All UI elements within the table
         will be rendered and managed by stage
        */
        stage.addActor(table);
        stage.addActor(lowerTable);
    }

    /**
     * Renders the game screen.
     *
     * <p>The main update method called every frame. Calls the player's update method, then updates
     * the camera to follow the player, re-draws everything in the game area, and finally calls act
     * and draw on the UI Stage.
     *
     * @param delta The time elapsed since the last frame.
     */
    @Override
    public void render(float delta) {
        // Clear screen ready for new frame
        ScreenUtils.clear(Color.BLACK);

        // Player movement
        player.update(delta);

        // Camera follows player
        Vector2 cameraPosition = new Vector2();
        Rectangle playerCollider = player.getBoundingRectangle();
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

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.begin();
        // Draw the map
        // Draw interactables
        for (Interactable interactable : GameManager.getInstance().getInteractables()) {
            interactable.draw(game.batch);
        }

        // Draw player on top of world
        if (player.isMoving) {
            // If moving, draw current frame of animation
            player.getMovingFrame().draw(game.batch);
        } else {
            // If not moving, draw regular Player sprite
            player.draw(game.batch);
        }

        game.batch.end();

        // Draw UI on top of everything else
        dayLabel.setText(gameManager.getDayFormatted());
        timeLabel.setText(gameManager.getTimeFormatted());
        energyLabel.setText(player.getEnergyFormatted());
        counterLabel.setText(gameManager.getCountersFormatted());
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
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
        mapRenderer.dispose();
    }
}
