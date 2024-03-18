package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents an abstract interactable entity in the game, extending {@link Entity}. Interactable
 * entities are objects with which the player can interact.
 */
public abstract class Interactable extends Entity {
    /**
     * Constructs an Interactable entity with the specified sprite and spawn position.
     *
     * @param sprite The sprite representing the interactable entity.
     * @param spawnPosX The X coordinate of the spawn position.
     * @param spawnPosY The Y coordinate of the spawn position.
     */
    public Interactable(Sprite sprite, float spawnPosX, float spawnPosY) {
        super(sprite, spawnPosX, spawnPosY);
    }

    /**
     * Defines the behavior when the player interacts with this interactable entity.
     *
     * @param player The player interacting with the entity.
     */
    public abstract void interact(Player player);
}
