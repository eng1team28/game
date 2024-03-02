package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	final HeslingtonHustle game;

	private OrthographicCamera camera;

	Texture playerImage;
	private Rectangle player;

	private static final int SCREEN_WIDTH = 1920;
	private static final int SCREEN_HEIGHT = 1080;

	// TODO - Move all player logic into a Player class, create and assign movementComponent (Composition)
	private static final int PLAYER_WIDTH = 32;
	private static final int PLAYER_HEIGHT = 64;
	private static final float PLAYER_SPEED = 1000;
	private static final String PLAYER_TEXTURE = "player.png";

	public GameScreen (final HeslingtonHustle game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		playerImage = new Texture(PLAYER_TEXTURE);
		player = new Rectangle();
		player.x = (float) SCREEN_WIDTH / 2 - (float) PLAYER_WIDTH / 2;
		player.y = (float) SCREEN_HEIGHT / 2 - (float) PLAYER_HEIGHT /2;
		player.width = PLAYER_WIDTH;
		player.height = PLAYER_HEIGHT;
	}

	@Override
	public void render (float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(playerImage, player.x, player.y);
		game.batch.end();

		Vector2 inputVector = new Vector2();
		inputVector.setZero();
		inputVector.add(Gdx.input.isKeyPressed(Input.Keys.LEFT) ? -1 : 0, Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0);
		inputVector.add(Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : 0, Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -1 : 0);
		// Normalize vector so that diagonal movement has the same magnitude
		inputVector.nor();

		// Move the player based on input and PLAYER_SPEED
		//  * deltaTime -> Frame-rate independent
		Vector2 displacementVector = inputVector.scl(PLAYER_SPEED * Gdx.graphics.getDeltaTime());
		player.setPosition(player.getPosition(new Vector2()).add(displacementVector));

		// Clamp player to screen
		player.x = MathUtils.clamp(player.x, 0, SCREEN_WIDTH - PLAYER_WIDTH);
		player.y = MathUtils.clamp(player.y, 0, SCREEN_HEIGHT - PLAYER_HEIGHT);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show () {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		playerImage.dispose();
	}
}
