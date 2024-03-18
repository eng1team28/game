package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the player entity in the game.
 * Extends the {@link Entity} class.
 */

public class Player extends Entity {

    // TEXTURES
    private static final String PLAYER_TEXTURE = "player";

    // COLLIDERS
    private float width = 32;
    private float height = 64;
    private final Rectangle interactCollider;

    // COMPONENTS
    private final MoveComponent moveComponent;

    // PLAYER STATS
    private float energy;
    private final float maxEnergy;
    private double intelligence;
    private final double maxIntelligence;
    private double happiness;
    private final double maxHappiness;

    // Not final as they can change (maybe)
    private float playerSpeed = 1000;
    private float playerAcceleration = 25;
    private float interactRange = 150;

    /**
     * Constructor for the Player class.
     * Initializes player attributes and components.
     */

    public Player(TextureAtlas atlas) {
        super(
                atlas.createSprite(PLAYER_TEXTURE),
                GameManager.GAME_WIDTH / 2,
                GameManager.GAME_HEIGHT / 2);
        // todo make this work properly with inheritance
        // I'm too used to python and it works differently
        setSize(width, height);
        energy = 100;
        maxEnergy = 100;
        intelligence = 0;
        maxIntelligence = 100;
        happiness = 1.5;
        maxHappiness = 1.5;

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
     * @param energy The energy level to set.
     */
    public void setEnergy(float energy) {
        this.energy = MathUtils.clamp(energy, 0, maxEnergy);
    }

    /**
    * Retrieves a formatted string representation of the player's energy level.
    * by formatting with the word "Energy:" followed by the current energy level.
    *
    * @return A string representing the formatted energy level 
    */
    String getEnergyFormatted() {
        return String.format("Energy: %s", getEnergy());
    }
    
    /**
     * Getter for intelligence
     */
    public double getIntelligence() {
        return intelligence;
    }
    /**
     * Sets the intelligence level of the player.
     *
     * @param intelligence The intelligence level to set.
     */
    public void setIntelligence(double intelligence) {
        this.intelligence = MathUtils.clamp(intelligence, 0, maxIntelligence);
    }

    /**
    * Retrieves a formatted string representation of the player's energy level.
    * by formatting with the word "Intelligence:" followed by the current energy level.
    *
    * @return A string representing the formatted energy level 
    */
    String getIntelligenceFormatted() {
        return String.format("Intelligence: %s", getIntelligence());
    }

    /**
     * Gets the current happiness level of the player.
     *
     * @return The happiness level as a double value.
     */

    public double getHappiness() {
        return happiness;
    }

    /**
     * Sets the happiness level of the player.
     *
     * @param happiness The happiness level to set.
     */

    public void setHappiness(double happiness) {
        this.happiness = MathUtils.clamp(happiness, 0, maxHappiness);
    }

    String getHappinessFormatted() {
        return String.format("Happiness: %s", getHappiness());
    }

    //public void setPosition(int pos_x, int pos_y){
    //    setPosition(pos_x, pos_y);
    //}

    // Runs every frame
    /**
     * Updates the player's position and handles interactions.
     *
     * @param delta The time elapsed since the last update.
     */
    void update(float delta) {
        Rectangle collider = getCollider();
        Vector2 inputVector = getNormalizedInputVector();

        // Move collider using moveComponent
        moveComponent.moveTowards(inputVector, collider, delta);

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

        //FOR TESTING, TRIGGERS END OF WEEK
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            GameManager.getInstance().setEndDay();
        }

        //FOR TESTING, TRIGGERS END OF WEEK, WITH WINNING
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            this.intelligence = 70;
            GameManager.getInstance().setEndDay();
        }

        //FOR TESTING, TRIGGERS END OF WEEK, WITH FAILING
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            this.intelligence = 45;
            GameManager.getInstance().setEndDay();
        }

    }

    /**
     * Retrieves the interactable object closest to the player's position.
     *
     * @return The closest interactable object or null if none available.
     */


    Interactable getInteractableTouched() {
        /**
        * Retrieves the interactable object closest to the player's position, which will be activated if the "E" key is pressed.
        * Returns null if no interactable object is available.
        *
        * @return The closest interactable object to the player's position, or null if none available.
        */
        Interactable closestInteractable = null;
        float closestDistance = Float.MAX_VALUE;
        Rectangle collider = getCollider();
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
                    && interactCollider.overlaps(interactable.getCollider())) {
                closestDistance = distance;
                closestInteractable = interactable;
            }
        }

        return closestInteractable;
    }


    /**
    * Retrieves a normalized vector representing the player's input direction.
    * The vector is normalized to ensure consistent movement speed regardless of direction.
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
