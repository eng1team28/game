package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

    // TEXTURES
    private static final String PLAYER_TEXTURE = "player.png";
    private final Texture playerImage;

    // COLLIDERS
    private final Rectangle collider;
    private final Rectangle interactCollider;

    // COMPONENTS
    private final MoveComponent moveComponent;

    // PLAYER STATS
    private float energy;
    private final float maxEnergy;
    private float intelligence;
    private final float maxIntelligence;

    // Not final as they can change (maybe)
    private float playerSpeed = 1000;
    private float playerAcceleration = 25;
    private float interactRange = 150;

    public Player() {
        energy = 100;
        maxEnergy = 100;
        intelligence = 0;
        maxIntelligence = 100;

        moveComponent = new MoveComponent(playerAcceleration, playerSpeed);

        playerImage = new Texture(PLAYER_TEXTURE);
        collider = new Rectangle();
        collider.x = (float) GameManager.SCREEN_WIDTH / 2 - (float) collider.width / 2;
        collider.y = (float) GameManager.SCREEN_HEIGHT / 2 - (float) collider.height / 2;
        collider.width = 32;
        collider.height = 64;

        interactCollider = new Rectangle(0, 0, interactRange, interactRange);
    }

    public Texture getPlayerImage() {
        return playerImage;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = MathUtils.clamp(energy, 0, maxEnergy);
    }

    public float getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(float intelligence) {
        this.intelligence = MathUtils.clamp(intelligence, 0, maxIntelligence);
    }

    // Runs every frame
    void update(float delta) {
        Vector2 inputVector = getNormalizedInputVector();

        // Move Player using moveComponent
        moveComponent.MoveTowards(inputVector, collider, delta);
        interactCollider.setPosition(new Vector2(collider.x - interactRange/2, collider.y - interactRange/2));

        // Clamp player to screen
        collider.x = MathUtils.clamp(collider.x, 0, GameManager.SCREEN_WIDTH - collider.width);
        collider.y = MathUtils.clamp(collider.y, 0, GameManager.SCREEN_HEIGHT - collider.height);

        // Player Interact
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            for(Interactable interactable: GameManager.getInstance().getInteractables()) {
                if(interactCollider.overlaps(interactable.getCollider())){
                    interactable.Interact(this);
                    Gdx.app.log("MyTag", String.valueOf(getEnergy()));
                }
            }
        }

    }

    private Vector2 getNormalizedInputVector() {
        Vector2 inputVector = new Vector2();
        inputVector.setZero();
        inputVector.add(
                Gdx.input.isKeyPressed(Input.Keys.LEFT) ? -1 : 0,
                Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0);
        inputVector.add(
                Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : 0,
                Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -1 : 0);
        // Normalize vector so that diagonal movement has the same magnitude
        return inputVector.nor();
    }

    void dispose() {
        playerImage.dispose();
    }
}
