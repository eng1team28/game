package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/** Represents the player entity in the game. Extends the {@link Entity} class. */
public class Player extends Entity {
    // COLLIDERS
    /** Player width to override the sprite width. */
    private float width = 64;

    /** Player height. */
    private float height = 64;

    /** A larger version of the entity's regular collider, representing the interact range. */
    private final Rectangle interactCollider;

    // COMPONENTS
    /** Component to handle velocity and acceleration during movement. */
    private final MoveComponent moveComponent;

    // PLAYER STATS
    /** Character energy as a percentage, limits the activities that can be taken. */
    private float energy;

    /** Maximum energy bar capacity - 100 as it's a percentage. */
    private static final float MAX_ENERGY = 100;

    // Not final as they can change (maybe)
    private float playerSpeed = 1000;
    private float playerAcceleration = 25;

    /**
     * The character's movement direction, used to determine whether to flip the sprite when
     * drawing.
     */
    public boolean isFacingRight = true;

    /**
     * Whether the character is currently moving or stationary, used to determine whether to draw
     * the standing sprite or the animation frames.
     */
    public boolean isMoving = false;

    /** How close the character has to be to an interactable to use it, in arbitrary units. */
    private float interactRange = 150;

    /** Animation used to draw the character when moving. */
    private Animation<Sprite> walkAnimation;

    /** The current time offset in the walkAnimation in seconds. */
    private float walkDelta = 0f;

    /** How long to halt on an animation frame in seconds. */
    private static final float WALK_FRAME_TIME = 0.01f;

    /** Constructor for the Player class. Initializes player attributes and components. */
    public Player(Sprite sprite, Array<Sprite> animationFrames, float spawnPosX, float spawnPosY) {
        // could give the player a name?
        super(sprite, spawnPosX, spawnPosY);

        for (Sprite frameSprite : animationFrames) {
            frameSprite.setSize(width, height);
        }
        walkAnimation = new Animation<>(WALK_FRAME_TIME, animationFrames, Animation.PlayMode.LOOP);

        setSize(width, height);
        energy = 100;

        moveComponent = new MoveComponent(playerAcceleration, playerSpeed);

        interactCollider = new Rectangle(0, 0, interactRange, interactRange);
    }

    /**
     * Gets the current energy level of the player.
     *
     * @return The energy level as a float value.
     */
    public float getEnergy() {
        return energy;
    }

    /** Resets the energy level of the player to 100%, to start a new day. */
    public void resetEnergy() {
        energy = MAX_ENERGY;
    }

    /**
     * Try to decrement the player's energy, as part of taking an action. This succeeds if the
     * energy cost does not exceed the remaining energy, or take the energy over its maximum value
     * with a negative cost.
     *
     * @param cost amount of energy to remove
     * @return true if energy has been removed, false if not possible
     */
    public boolean spendEnergy(float cost) {
        float newEnergy = energy - cost;
        if (newEnergy < 0 || newEnergy > MAX_ENERGY) {
            return false;
        } else {
            energy = newEnergy;
            return true;
        }
    }

    /**
     * Retrieves a formatted string representation of the player's energy level. by formatting with
     * the word "Energy:" followed by the current energy level.
     *
     * @return A string representing the formatted energy level
     */
    String getEnergyFormatted() {
        return String.format("Energy: %s", getEnergy());
    }

    /**
     * Handles interaction to update the player's position and make interactions. Should be called
     * every frame in the game screen.
     *
     * @param delta The time elapsed since the last update.
     */
    void update(float delta) {
        Rectangle collider = getBoundingRectangle();
        Vector2 inputVector = getNormalizedInputVector();

        // Move collider using moveComponent
        moveComponent.moveTowards(inputVector, collider, delta);

        if (moveComponent.velocity.x < 0) {
            isFacingRight = false;
        } else if (moveComponent.velocity.x > 0) {
            isFacingRight = true;
        }
        setFlip(!isFacingRight, false);

        isMoving = !moveComponent.velocity.isZero(1f);
        if (isMoving) {
            walkDelta += delta;
        } else {
            walkDelta = 0f;
        }

        // Clamp collider to screen
        collider.x = MathUtils.clamp(collider.x, 0, GameManager.GAME_WIDTH - collider.width);
        collider.y = MathUtils.clamp(collider.y, 0, GameManager.GAME_HEIGHT - collider.height);
        interactCollider.setPosition(
                collider.x - interactRange / 2, collider.y - interactRange / 2);
        // Move player
        setPosition(collider.x, collider.y);

        // Player Interact
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            Interactable closestInteractable = getInteractableTouched();

            if (closestInteractable != null) {
                closestInteractable.interact(this);
                Gdx.app.log("MyTag", closestInteractable.toString());
            }
        }

        // FOR TESTING, TRIGGERS END OF WEEK
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            GameManager.getInstance().setEndDay();
        }

        // FOR TESTING, TRIGGERS END OF WEEK, WITH WINNING
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            //            this.intelligence = 70;
            GameManager.getInstance().setEndDay();
        }

        // FOR TESTING, TRIGGERS END OF WEEK, WITH FAILING
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            //            this.intelligence = 45;
            GameManager.getInstance().setEndDay();
        }
    }

    /**
     * Get the current frame of the walking animation. This retrieves the key frame and sets its
     * position and flip. Updating the stateTime is handled in the update method.
     *
     * @return the sprite ready to draw
     */
    public Sprite getMovingFrame() {
        Sprite movingSprite = walkAnimation.getKeyFrame(walkDelta);
        movingSprite.setPosition(getX(), getY());
        movingSprite.setFlip(isFlipX(), false);
        return movingSprite;
    }

    /**
     * Retrieves the interactable object closest to the player's position.
     *
     * @return The closest interactable object or null if none is within range.
     */
    Interactable getInteractableTouched() {
        Interactable closestInteractable = null;
        float closestDistance = Float.MAX_VALUE;
        Rectangle collider = getBoundingRectangle();
        float x1Centre = collider.x + getWidth() / 2;
        float y1Centre = collider.y + getHeight() / 2;

        for (Interactable interactable : GameManager.getInstance().getInteractables()) {
            float distance =
                    Vector2.dst(
                            x1Centre,
                            y1Centre,
                            interactable.getX() + interactable.getWidth() / 2,
                            interactable.getY() + interactable.getHeight() / 2);
            if (distance < closestDistance
                    && interactCollider.overlaps(interactable.getBoundingRectangle())) {
                closestDistance = distance;
                closestInteractable = interactable;
            }
        }

        return closestInteractable;
    }

    /**
     * Retrieves a normalized vector representing the player's input direction. The vector is
     * normalized to ensure consistent movement speed regardless of direction.
     *
     * @return A normalized vector representing the player's input direction.
     */
    private Vector2 getNormalizedInputVector() {
        Vector2 inputVector = new Vector2();
        inputVector.setZero();
        inputVector.add(
                Util.anyKeyPressed(Input.Keys.LEFT, Input.Keys.A) ? -1 : 0,
                Util.anyKeyPressed(Input.Keys.UP, Input.Keys.W) ? 1 : 0);
        inputVector.add(
                Util.anyKeyPressed(Input.Keys.RIGHT, Input.Keys.D) ? 1 : 0,
                Util.anyKeyPressed(Input.Keys.DOWN, Input.Keys.S) ? -1 : 0);
        // Normalize vector so that diagonal movement has the same magnitude
        return inputVector.nor();
    }
}
