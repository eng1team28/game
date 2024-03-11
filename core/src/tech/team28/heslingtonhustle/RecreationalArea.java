package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class RecreationalArea implements Interactable {
    private final float recreationDuration;
    private final double recreationHappinessGain;
    private final float recreationEnergyCost;
    private final Rectangle collider;
    private final TextureAtlas.AtlasRegion image;

    public RecreationalArea(
            TextureAtlas atlas,
            float recreationDuration,
            double recreationHappinessGain,
            float recreationEnergyCost,
            float spawnPosX,
            float spawnPosY) {
        this.recreationDuration = recreationDuration;
        this.recreationHappinessGain = recreationHappinessGain;
        this.recreationEnergyCost = recreationEnergyCost;

        this.collider = new Rectangle();
        this.collider.width = 512;
        this.collider.height = 512;
        this.collider.x = spawnPosX;
        this.collider.y = spawnPosY;

        this.image = atlas.findRegion("Yellow512x512");
    }

    public RecreationalArea(TextureAtlas atlas) {
        this(
                atlas,
                1,
                0.3,
                1,
                (float) GameManager.SCREEN_WIDTH / 2,
                (float) GameManager.SCREEN_HEIGHT / 100);
    }

    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(recreationDuration);

        applyRecreationalEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public TextureAtlas.AtlasRegion getImage() {
        return image;
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
