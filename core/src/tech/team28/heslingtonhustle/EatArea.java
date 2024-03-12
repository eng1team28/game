package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class EatArea implements Interactable {

    private final float eatDuration;
    private final float eatEnergyGain;
    private final Rectangle collider;
    private final TextureAtlas.AtlasRegion image;

    public EatArea(
            TextureAtlas atlas,
            float eatDuration,
            float eatEnergyGain,
            float spawnPosX,
            float spawnPosY) {
        this.eatDuration = eatDuration;
        this.eatEnergyGain = eatEnergyGain;

        this.collider = new Rectangle();
        this.collider.width = 512;
        this.collider.height = 512;
        this.collider.x = spawnPosX;
        this.collider.y = spawnPosY;

        this.image = atlas.findRegion("Green512x512");
    }

    public EatArea(TextureAtlas atlas) {
        this(
                atlas,
                1,
                10,
                (float) GameManager.SCREEN_WIDTH / 100,
                (float) GameManager.SCREEN_HEIGHT / 100);
    }
    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(eatDuration);

        applyEatEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public TextureAtlas.AtlasRegion getImage() {
        return image;
    }

    private void applyEatEffect(Player player) {
        player.setEnergy(player.getEnergy() + eatEnergyGain);
    }
}
