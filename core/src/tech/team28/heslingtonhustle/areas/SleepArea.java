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
        this(atlas, 5, 100, GameManager.GAME_WIDTH / 100, GameManager.GAME_HEIGHT / 2);
    }

    @Override
    public void interact(Player player) {
        gameManager
                .getAreaCounter()
                .setSleepAreaCounter(gameManager.getAreaCounter().getSleepAreaCounter() + 1);

        applySleepEffect(player);
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
        float current_time = gameManager.getTime();

        if (current_time <= 12 && current_time >= 7){
            gameManager.incrementTime(sleepDuration);
        }
        else if (current_time < 7){
            gameManager.incrementTime(7 - current_time);
        }
        else {
            gameManager.incrementTime(30 - current_time);
        }
    }
}
