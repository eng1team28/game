/**
 * {@link com.badlogic.gdx.graphics.g2d.Sprite} for managing graphical entities. {@link
 * com.badlogic.gdx.graphics.g2d.TextureRegion} for representing texture area within a larger
 * texture. {@link com.badlogic.gdx.math.Rectangle} for representing a rectangle when using floating
 * point coordinates.
 */
package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represents an abstract entity in the game, extending the sprite import. All entities in the game
 * inherit from this class.
 */
public abstract class Entity extends Sprite {

    /**
     * Constructs an entity with the provided sprite and spawn position.
     *
     * @param sprite The sprite representing the entity.
     * @param spawnPosX, @param spawnPoxY The X and Y coordinate of the spawn position.
     */
    public Entity(Sprite sprite, float spawnPosX, float spawnPosY) {
        super();
        set(sprite);
        float width = 256;
        float height = 256;
        setSize(width, height);
        setPosition(spawnPosX, spawnPosY);
    }

    /**
     * Returns a {@code Rectangle} with the same dimensions as the {@code Sprite}
     *
     * @return returns the bounding rectangle
     */
    Rectangle getCollider() {
        return getBoundingRectangle();
    }
}
