package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Game;

public class HeslingtonHustle extends Game {
    @Override
    public void create() {
        this.setScreen(new GameScreen(this));
    }
}
