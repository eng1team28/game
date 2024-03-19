package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

/** Represents a type of interactable where the player will eat to gain energy. */
public class EatArea extends Interactable {
    /** */
    private final float eatDuration;

    private final float eatEnergyGain;

    private final GameManager gameManager;

    public EatArea(
            String name,
            Sound sound,
            Sprite sprite,
            float spawnPosX,
            float spawnPosY,
            float eatDuration,
            float eatEnergyGain) {
        super(name, sound, sprite, spawnPosX, spawnPosY);
        this.eatDuration = eatDuration;
        this.eatEnergyGain = eatEnergyGain;
        this.gameManager = GameManager.getInstance();
    }

    public EatArea(String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY) {
        this(name, sound, sprite, spawnPosX, spawnPosY, 1, 10);
    }

    @Override
    public void interact(Player player) {
        gameManager.incrementTime(eatDuration);
        gameManager.getAreaCounter().incrementEatAreaCounter();

        applyEatEffect(player);
        interactSound.play();
    }

    private void applyEatEffect(Player player) {
        player.setEnergy(player.getEnergy() + eatEnergyGain);
    }
}
