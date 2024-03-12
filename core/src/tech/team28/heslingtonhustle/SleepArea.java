package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class SleepArea implements Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;

    private final Rectangle collider;
    private final TextureRegion image;

    public SleepArea(
            TextureAtlas atlas,
            float sleepDuration,
            float sleepEnergyRecovery,
            float spawnPosX,
            float spawnPosY) {
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;

        this.collider = new Rectangle();
        this.collider.width = 512;
        this.collider.height = 512;
        this.collider.x = spawnPosX;
        this.collider.y = spawnPosY;

        this.image = atlas.findRegion("Red512x512");
    }

    public SleepArea(TextureAtlas atlas) {
        this(
                atlas,
                8,
                100,
                (float) GameManager.SCREEN_WIDTH / 10,
                (float) GameManager.SCREEN_HEIGHT / 5);
    }

    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(sleepDuration);

        applySleepEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public TextureRegion getImage() {
        return image;
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
    }
}
