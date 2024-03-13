package tech.team28.heslingtonhustle;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The {@code MoveComponent} class handles the movement of a collider(Rectangle) within the game
 * world. It utilizes a target velocity to smoothly interpolate the current velocity towards it,
 * simulating acceleration.
 */
public class MoveComponent {

    private Vector2 velocity;
    private Vector2 targetVelocity;

    private float maxSpeed;
    private float acceleration;

    /**
     * Constructs a new {@code MoveComponent} with specified acceleration and maximum speed.
     *
     * @param acceleration The rate of acceleration for velocity interpolation.
     * @param maxSpeed The maximum speed the object can move.
     */
    public MoveComponent(float acceleration, float maxSpeed) {
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.velocity = new Vector2(0, 0);
        this.targetVelocity = new Vector2(0, 0);
    }

    /**
     * Moves a collider(Rectangle) towards a given direction by interpolating its velocity and
     * updating its position.
     *
     * @param direction The direction in which the object should move.
     * @param collider The collider representing the object's bounds in the game world.
     * @param delta The time elapsed since the last frame, used for frame rate independent movement.
     */
    public void moveTowards(Vector2 direction, Rectangle collider, float delta) {
        // Set the target velocity based on the direction and maximum speed
        targetVelocity.set(direction).scl(maxSpeed);

        // Interpolate the current velocity towards the target velocity with smooth interpolation
        // mode
        velocity.interpolate(targetVelocity, delta * acceleration, Interpolation.smooth);

        // Calculate the new position based on the current velocity
        Vector2 position = new Vector2();
        collider.getPosition(position);
        position.add(velocity.x * delta, velocity.y * delta);

        // Update collider position
        collider.setPosition(position);
    }
}
