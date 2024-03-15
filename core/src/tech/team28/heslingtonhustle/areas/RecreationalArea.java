package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

public class RecreationalArea extends Interactable {
    private final float recreationDuration;
    private final double recreationHappinessGain;
    private final float recreationEnergyCost;

    private final GameManager gameManager;

    public RecreationalArea(
            TextureAtlas atlas,
            float recreationDuration,
            double recreationHappinessGain,
            float recreationEnergyCost,
            float spawnPosX,
            float spawnPosY) {
        super(atlas.createSprite("Yellow512x512"), spawnPosX, spawnPosY);
        this.recreationDuration = recreationDuration;
        this.recreationHappinessGain = recreationHappinessGain;
        this.recreationEnergyCost = recreationEnergyCost;
        this.gameManager = GameManager.getInstance();
    }

    public RecreationalArea(TextureAtlas atlas) {
        this(atlas, 1, 0.3, 10, GameManager.GAME_WIDTH / 2, GameManager.GAME_HEIGHT / 100);
    }

    @Override
    public void interact(Player player) {
        gameManager.incrementTime(recreationDuration);
        gameManager
                .getAreaCounter()
                .setRecreationalAreaCounter(
                        gameManager.getAreaCounter().getRecreationalAreaCounter() + 1);

        applyRecreationalEffect(player);
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
