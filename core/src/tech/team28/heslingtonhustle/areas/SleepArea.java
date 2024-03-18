package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

public class SleepArea extends Interactable {

    private final float sleepDuration;
    private final float sleepEnergyRecovery;
    private final GameManager gameManager;

    public SleepArea(
            String name,
            Sound sound,
            Sprite sprite,
            float spawnPosX,
            float spawnPosY,
            float sleepDuration,
            float sleepEnergyRecovery) {
        super(name, sound, sprite, spawnPosX, spawnPosY);
        this.sleepDuration = sleepDuration;
        this.sleepEnergyRecovery = sleepEnergyRecovery;
        this.gameManager = GameManager.getInstance();
    }

    public SleepArea(String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY) {
        this(name, sound, sprite, spawnPosX, spawnPosY, 5, 100);
    }

    @Override
    public void interact(Player player) {
        gameManager
                .getAreaCounter()
                .setSleepAreaCounter(gameManager.getAreaCounter().getSleepAreaCounter() + 1);

        applySleepEffect(player);
        interactSound.play();
    }

    private void applySleepEffect(Player player) {
        player.setEnergy(player.getEnergy() + sleepEnergyRecovery);
        float current_time = gameManager.getTime();

        if (current_time <= 12 && current_time >= 7) {
            gameManager.incrementTime(sleepDuration);
        } else if (current_time < 7) {
            gameManager.incrementTime(7 - current_time);
        } else {
            gameManager.incrementTime(30 - current_time);
        }
    }
}
