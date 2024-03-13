package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SleepArea extends Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;

    public SleepArea(
            TextureAtlas atlas,
            float sleepDuration,
            float sleepEnergyRecovery,
            float spawnPosX,
            float spawnPosY) {
        super(atlas.createSprite("Red512x512"), spawnPosX, spawnPosY);
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;
    }

    public SleepArea(TextureAtlas atlas) {
        this(atlas, 8, 100, GameManager.GAME_WIDTH / 100, GameManager.GAME_HEIGHT / 2);
    }

    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(sleepDuration);

        applySleepEffect(player);
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
    }
}
