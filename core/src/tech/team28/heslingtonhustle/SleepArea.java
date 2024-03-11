package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class SleepArea implements Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;

    private final Rectangle collider;
    private final Texture image;

    public SleepArea(float sleepDuration, float sleepEnergyRecovery, float spawnPosX, float spawnPosY) {
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;

        this.collider = new Rectangle();
        this.collider.width = 512;
        this.collider.height = 512;
        this.collider.x = spawnPosX;
        this.collider.y = spawnPosY;

        this.image = new Texture("Red512x512.png");
    }

    public SleepArea() {
        this(8, 100, (float) GameManager.SCREEN_WIDTH / 10, (float) GameManager.SCREEN_HEIGHT / 5);
    }

    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(sleepDuration);

        applySleepEffect(player);
    }
    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public Texture getImage() {
        return image;
    }

    @Override
    public void dispose() {
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
    }
}
