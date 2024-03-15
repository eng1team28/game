package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

public class SleepArea extends Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;
    private final GameManager gameManager;

    public SleepArea(
            TextureAtlas atlas,
            float sleepDuration,
            float sleepEnergyRecovery,
            float spawnPosX,
            float spawnPosY) {
        super(atlas.createSprite("Red512x512"), spawnPosX, spawnPosY);
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;
        this.gameManager = GameManager.getInstance();
    }

    public SleepArea(TextureAtlas atlas) {
        this(atlas, 8, 100, GameManager.GAME_WIDTH / 100, GameManager.GAME_HEIGHT / 2);
    }

    @Override
    public void interact(Player player) {
        gameManager.incrementTime(sleepDuration);
        gameManager
                .getAreaCounter()
                .setSleepAreaCounter(gameManager.getAreaCounter().getSleepAreaCounter() + 1);

        applySleepEffect(player);
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
    }
}
