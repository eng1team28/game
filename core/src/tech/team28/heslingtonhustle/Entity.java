package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity extends Sprite {
    private float width = 256;
    private float height = 256;

    public Entity(Sprite sprite, float spawnPosX, float spawnPosY) {
        super();
        set(sprite);
        setSize(width, height);
        setPosition(spawnPosX, spawnPosY);
    }

    Rectangle getCollider() {
        return getBoundingRectangle();
    }

    TextureRegion getImage() {
        return this;
    }
}
