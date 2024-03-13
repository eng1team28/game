package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class RecreationalArea extends Interactable {
    private final float recreationDuration;
    private final double recreationHappinessGain;
    private final float recreationEnergyCost;

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
    }

    public RecreationalArea(TextureAtlas atlas) {
        this(atlas, 1, 0.3, 10, GameManager.GAME_WIDTH / 2, GameManager.GAME_HEIGHT / 100);
    }

    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(recreationDuration);

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
