package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Interactable extends Entity {
    public Interactable(Sprite sprite, float spawnPosX, float spawnPosY) {
        super(sprite, spawnPosX, spawnPosY);
    }

    public abstract void interact(Player player);
}
