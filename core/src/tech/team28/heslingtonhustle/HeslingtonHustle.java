package tech.team28.heslingtonhustle;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

public class HeslingtonHustle extends ApplicationAdapter {

	private OrthographicCamera camera;
	SpriteBatch batch;

	Texture playerImage;
	private Rectangle player;
	private float playerSpeed = 500;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);

		playerImage = new Texture("player.png");
		player = new Rectangle();
		player.x = 1920 / 2 - 32/ 2;
		player.y = 1080 / 2;
		player.width = 32;
		player.height = 64;

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(playerImage, player.x, player.y);
		batch.end();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.x -= playerSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.x += playerSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) player.y += playerSpeed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.y -= playerSpeed * Gdx.graphics.getDeltaTime();

		if(player.x < 0) player.x = 0;
		if(player.x > 1920 - player.width) player.x = 1920 - player.width;
		if(player.y < 0) player.y = 0;
		if(player.y > 1080 - player.height) player.y = 1080 - player.height;

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
	}
}
