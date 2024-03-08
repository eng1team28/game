package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private static final String PLAYER_TEXTURE = "player.png";
    private final Texture playerImage;

    private final Rectangle collider;
    private final MoveComponent moveComponent;
    private final float interactRadius = 150;

    private float energy;
    private final float maxEnergy;
    private float intelligence;
    private final float playerSpeed;

    public Texture getPlayerImage() {
        return playerImage;
    }

    public Player() {
        energy = 100;
        maxEnergy = 100;
        intelligence = 0;
        playerSpeed = 1000;

        moveComponent = new MoveComponent();
        playerImage = new Texture(PLAYER_TEXTURE);
        collider = new Rectangle();
        collider.x = (float) GameManager.SCREEN_WIDTH / 2 - (float) collider.width / 2;
        collider.y = (float) GameManager.SCREEN_HEIGHT / 2 - (float) collider.height / 2;
        collider.width = 32;
        collider.height = 64;
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

    // Runs every frame
    void update(float delta) {
        // Move the player based on input and player_speed
        Vector2 displacementVector = get_normalized_input_vector().scl(playerSpeed * Gdx.graphics.getDeltaTime());
        collider.setPosition(collider.getPosition(new Vector2()).add(displacementVector));

        // Clamp player to screen
        collider.x = MathUtils.clamp(collider.x, 0, GameManager.SCREEN_WIDTH - collider.width);
        collider.y = MathUtils.clamp(collider.y, 0, GameManager.SCREEN_HEIGHT - collider.height);
    }

    private Vector2 get_normalized_input_vector() {
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
}
