package tech.team28.heslingtonhustle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    final HeslingtonHustle game;
    private final OrthographicCamera camera;
    private final Player player;
    private Stage stage;
    private Label dayLabel;
    private Label timeLabel;
    private final GameManager gameManager;
    Actor hehe;
    Sound jingle;

    public GameScreen(final HeslingtonHustle game) {
        this.game = game;
        gameManager = GameManager.getInstance();

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameManager.SCREEN_WIDTH, GameManager.SCREEN_HEIGHT);

        // Player and interactables
        player = new Player(game.atlas);
        gameManager.setPlayer(player);
        gameManager.addInteractable(new StudyArea(game.atlas));
        gameManager.addInteractable(new SleepArea(game.atlas));
        gameManager.addInteractable(new RecreationalArea(game.atlas, this));

        // Scene2D stage and UI
        stage = new Stage(new ScreenViewport(camera), game.batch);
        Label.LabelStyle sillyStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        dayLabel = new Label("", sillyStyle);
        timeLabel = new Label("", sillyStyle);
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        table.row();
        table.add(timeLabel);
        table.row();
        table.add(dayLabel);
        stage.addActor(table);

        TextureAtlas.AtlasRegion cristalRegion =
                    game.atlas.findRegion("sigil");
        jingle = Gdx.audio.newSound(Gdx.files.internal("jingle.mp3"));
        class MyActor extends Actor {
            public TextureAtlas.AtlasRegion region = cristalRegion;

            @Override
            public void draw(Batch batch, float parentAlpha) {
                Color color = getColor();
                batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
                batch.draw(
                        region,
                        getX(),
                        getY(),
                        getOriginX(),
                        getOriginY(),
                        getWidth(),
                        getHeight(),
                        getScaleX(),
                        getScaleY(),
                        getRotation());
                batch.setColor(Color.WHITE);
            }

        }
        hehe = new MyActor();
        hehe.setSize(cristalRegion.originalWidth, cristalRegion.originalHeight);
        hehe.setOrigin(Align.center);
        hehe.setX((float) 1920/2, Align.center);
        hehe.setY((float) 1080/2, Align.center);
        hehe.setVisible(false);
        stage.addActor(hehe);
    }

    void yourFatherWantedYouToHaveThis() {
        jingle.stop();
        jingle.play();
        hehe.clearActions();
        hehe.setScale(0.2f, 0.2f);
        hehe.setColor(1, 1, 1, 0);
        hehe.setVisible(true);
        hehe.addAction(
                sequence(
                        parallel(fadeIn(1), scaleTo(1f, 1f, 4)),
                        delay(2),
                        fadeOut(1),
                        Actions.hide()
                )
        );
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        player.update(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(player.getPlayerImage(), player.getCollider().x, player.getCollider().y);

        for (Interactable interactable : GameManager.getInstance().getInteractables()) {
            game.batch.draw(
                    interactable.getImage(),
                    interactable.getCollider().x,
                    interactable.getCollider().y);
        }

        game.batch.end();

        dayLabel.setText(gameManager.getDayFormatted());
        timeLabel.setText(gameManager.getTimeFormatted());
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

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
