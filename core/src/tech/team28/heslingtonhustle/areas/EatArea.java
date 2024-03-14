package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

public class EatArea extends Interactable {

    private final float eatDuration;
    private final float eatEnergyGain;

    public EatArea(
            TextureAtlas atlas,
            float eatDuration,
            float eatEnergyGain,
            float spawnPosX,
            float spawnPosY) {
        super(atlas.createSprite("Green512x512"), spawnPosX, spawnPosY);
        this.eatDuration = eatDuration;
        this.eatEnergyGain = eatEnergyGain;
    }

    public EatArea(TextureAtlas atlas) {
        this(atlas, 1, 10, GameManager.GAME_WIDTH / 100, GameManager.GAME_HEIGHT / 100);
    }

    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(eatDuration);

        applyEatEffect(player);
    }

    private void applyEatEffect(Player player) {
        player.setEnergy(player.getEnergy() + eatEnergyGain);
    }
}
