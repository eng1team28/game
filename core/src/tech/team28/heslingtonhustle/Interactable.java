package tech.team28.heslingtonhustle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents an abstract interactable entity in the game, extending {@link Entity}. Interactable
 * entities are objects with which the player can interact.
 */
public abstract class Interactable extends Entity {
    protected String name;
    protected Sound interactSound;
    protected float interactTimeDuration;
    protected float interactEnergyCost;

    /**
     * Constructs an Interactable entity with the specified sprite and spawn position.
     *
     * @param sprite The sprite representing the interactable entity.
     * @param spawnPosX The X coordinate of the spawn position.
     * @param spawnPosY The Y coordinate of the spawn position.
     */
    public Interactable(
            String name,
            Sound sound,
            Sprite sprite,
            float spawnPosX,
            float spawnPosY,
            float interactTimeDuration,
            float interactEnergyCost) {
        super(sprite, spawnPosX, spawnPosY);
        this.name = name;
        this.interactSound = sound;
        this.interactTimeDuration = interactTimeDuration;
        this.interactEnergyCost = interactEnergyCost;
    }

    /**
     * Defines the behavior when the player interacts with this interactable entity.
     *
     * @param player The player interacting with the entity.
     */
    public void interact(Player player) {
        if (tryInteract(player)) {
            interactSuccess(player);
        } else {
            // todo fail sound
        }
    }

    private boolean tryInteract(Player player) {
        GameManager gameManager = GameManager.getInstance();
        if (!player.spendEnergy(interactEnergyCost)) {
            return false;
        } else if (!gameManager.incrementTime(interactTimeDuration)) {
            return false;
        } else {
            return true;
        }
    }

    private void interactSuccess(Player player) {
        interactSound.play();
        interactEffect(player);
    }

    public abstract void interactEffect(Player player);
}
