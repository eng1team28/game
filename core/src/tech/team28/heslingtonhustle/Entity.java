/**
 * {@link com.badlogic.gdx.graphics.g2d.Sprite} for managing graphical entities.
 * {@link com.badlogic.gdx.graphics.g2d.TextureRegion} for representing texture area within a larger texture.
 * {@link com.badlogic.gdx.math.Rectangle} for representing a rectangle wihen using floating point coordinates.
 */

package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


/**
 * Represents an abstract entity in the game, extending the sprite import.
 * All entities in the game inherit from this class.
 */
public abstract class Entity extends Sprite {
    private float width = 256;
    private float height = 256;

    /**
     * Constructs an entity with the provided sprite and spawn position.
     * @param sprite The sprite representing the entity.
     * @param spawnPosX, @param spawnPoxY The X and Y coordinate of the spawn position.
     */
    public Entity(Sprite sprite, float spawnPosX, float spawnPosY) {
        super();
        set(sprite);
        setSize(width, height);
        setPosition(spawnPosX, spawnPosY);
    }

    /**
     * Yeah so  entity extends sprite
     * getBoundingRectangle returns a rectangle with 
     * the same size as the sprite texture 
     * @return returns the bounding recatangle
     */
    Rectangle getCollider() {
        return getBoundingRectangle();
    }

    /**
     * Retrieves the texture region representing the entity's image.
     * @return The texture region of the entity's image.
     */
    TextureRegion getImage() {
        return this;
    }
}
