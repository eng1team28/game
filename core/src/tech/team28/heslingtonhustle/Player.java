package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = MathUtils.clamp(energy, 0, maxEnergy);
    }

    String getEnergyFormatted() {
        return String.format("Energy: %s", getEnergy());
    }

    public double getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(double intelligence) {
        this.intelligence = MathUtils.clamp(intelligence, 0, maxIntelligence);
    }

    String getIntelligenceFormatted() {
        return String.format("Intelligence: %s", getIntelligence());
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = MathUtils.clamp(happiness, 0, maxHappiness);
    }

    String getHappinessFormatted() {
        return String.format("Happiness: %s", getHappiness());
    }

    // Runs every frame
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
            for (Interactable interactable : GameManager.getInstance().getInteractables()) {
                if (interactCollider.overlaps(interactable.getCollider())) {
                    interactable.interact(this);
                    Gdx.app.log("MyTag", String.valueOf(getEnergy()));
                }
            }
        }
    }

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
