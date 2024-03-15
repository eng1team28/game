package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ExamPresenter implements Screen{
    final HeslingtonHustle game;
    private Stage stage;
    private Sprite result;
	
	public ExamPresenter(HeslingtonHustle hustleGame, boolean winBool){
		game = hustleGame;
		
		/// create stage and set it as input processor
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

        if (winBool){
            result = game.atlas.createSprite("win_placeholder");
        }else {
            result = game.atlas.createSprite("lose_placeholder");
        }
        
        result.setSize(GameManager.GAME_WIDTH, GameManager.GAME_HEIGHT);
	}
 
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		// Fast quit keybinding
        if (Util.allKeysPressed(Input.Keys.CONTROL_LEFT, Input.Keys.Q)
                || Util.allKeysPressed(Input.Keys.CONTROL_RIGHT, Input.Keys.Q)) {
            Gdx.app.exit();
        }

        // Clear screen ready for new frame
        ScreenUtils.clear(Color.BLACK);

        this.result.draw(game.batch);
	}
 
	@Override
	public void resize(int width, int height) {
		// change the stage's viewport when teh screen size is changed
		stage.getViewport().update(width, height, true);
	}
 
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
 
	@Override
	public void dispose() {
		// dispose of assets when not needed anymore
		stage.dispose();
	}
}
