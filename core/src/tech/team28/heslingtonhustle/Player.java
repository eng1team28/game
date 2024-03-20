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
    private float width = 64;
    private float height = 64;
    private final Rectangle interactCollider;

    // COMPONENTS
    private final MoveComponent moveComponent;

    // PLAYER STATS
    private float energy;
    private static final float MAX_ENERGY = 100;

    // Not final as they can change (maybe)
    private float playerSpeed = 1000;
    private float playerAcceleration = 25;
    public boolean isFacingRight = true;
    public boolean isMoving = false;
    private float interactRange = 150;
    private Animation<Sprite> walkAnimation;
    private float walkDelta = 0f;
    private float walkFrameTime = 0.01f;

    /** Constructor for the Player class. Initializes player attributes and components. */
    public Player(Sprite sprite, Array<Sprite> animationFrames, float spawnPosX, float spawnPosY) {
        // could give the player a name?
        super(sprite, spawnPosX, spawnPosY);

        for (Sprite frameSprite : animationFrames) {
            frameSprite.setSize(width, height);
        }
        walkAnimation = new Animation<>(walkFrameTime, animationFrames, Animation.PlayMode.LOOP);

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

    /**
     * Sets the energy level of the player.
     *
     */
    public void resetEnergy() {
        energy = MAX_ENERGY;
    }

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

    // Runs every frame
    /**
     * Updates the player's position and handles interactions.
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

    public Sprite getMovingFrame() {
        Sprite movingSprite = walkAnimation.getKeyFrame(walkDelta);
        movingSprite.setPosition(getX(), getY());
        movingSprite.setFlip(isFlipX(), false);
        return movingSprite;
    }

    /**
     * Retrieves the interactable object closest to the player's position.
     *
     * @return The closest interactable object or null if none available.
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
