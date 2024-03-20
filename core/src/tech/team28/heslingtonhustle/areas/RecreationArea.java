package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

/** Represents a type of interactable where the player will gain happiness. */
public class RecreationArea extends Interactable {
    public RecreationArea(
            String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY) {
        super(name, sound, sprite, spawnPosX, spawnPosY, 1, 10);
    }

    @Override
    public void interactEffect(Player player) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.getAreaCounter().getRecreationAreaCounter().incrementCount();
    }
}
