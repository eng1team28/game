package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

/** Represents a type of interactable where the player will sleep to gain energy. */
public class SleepArea extends Interactable {
    public SleepArea(String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY) {
        super(name, sound, sprite, spawnPosX, spawnPosY, 0, 0);
    }

    @Override
    public void interactEffect(Player player) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.getAreaCounter().getSleepAreaCounter().incrementCount();

        boolean sleepSuccess = gameManager.incrementDay();
        if (sleepSuccess) {
            gameManager.resetTime();
            player.resetEnergy();
        }
    }
}
