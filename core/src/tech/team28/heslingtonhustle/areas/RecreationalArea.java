package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.GameScreen;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

public class RecreationalArea extends Interactable {
    private final float recreationDuration;
    private final double recreationHappinessGain;
    private final float recreationEnergyCost;

    private final GameManager gameManager;
    GameScreen gameScreen;

    public RecreationalArea(
            String name,
            Sound sound,
            Sprite sprite,
            float spawnPosX,
            float spawnPosY,
            float recreationDuration,
            double recreationHappinessGain,
            float recreationEnergyCost) {
        super(name, sound, sprite, spawnPosX, spawnPosY);
        this.recreationDuration = recreationDuration;
        this.recreationHappinessGain = recreationHappinessGain;
        this.recreationEnergyCost = recreationEnergyCost;
        this.gameManager = GameManager.getInstance();
    }

    public RecreationalArea(
            String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY, GameScreen gameScreen) {
        this(name, sound, sprite, spawnPosX, spawnPosY, 1, 0.3, 10);
        this.gameScreen = gameScreen;
    }

    @Override
    public void interact(Player player) {
        gameManager.incrementTime(recreationDuration);
        gameManager
                .getAreaCounter()
                .setRecreationalAreaCounter(
                        gameManager.getAreaCounter().getRecreationalAreaCounter() + 1);

        applyRecreationalEffect(player);
        interactSound.play();
        gameScreen.yourFatherWantedYouToHaveThis();
    }

    private void applyRecreationalEffect(Player player) {
        player.setEnergy(player.getEnergy() - recreationEnergyCost);
        if (player.getHappiness() < 1.2) {
            player.setHappiness(player.getHappiness() + recreationHappinessGain);
        } else {
            player.setHappiness(1.5);
        }
    }
}
