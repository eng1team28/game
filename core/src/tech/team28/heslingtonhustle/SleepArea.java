package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class SleepArea implements Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;

    public SleepArea(float sleepDuration, float sleepEnergyRecovery) {
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;
    }

    @Override
    public void Interact(Player player) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.incrementDay();
        gameManager.incrementTime(8);

        applySleepEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public TextureAtlas.AtlasRegion getImage() {
        return null;
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
    }
}
