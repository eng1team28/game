package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class StudyArea implements Interactable {

    private final float studyDuration;
    private final float studyEnergyCost;
    private final Rectangle collider;
    private final TextureAtlas.AtlasRegion image;

    public StudyArea(
            TextureAtlas atlas,
            float studyDuration,
            float studyEnergyCost,
            float spawnPosX,
            float spawnPosY) {
        this.studyDuration = studyDuration;
        this.studyEnergyCost = studyEnergyCost;

        this.collider = new Rectangle();
        this.collider.width = 512;
        this.collider.height = 512;
        this.collider.x = spawnPosX;
        this.collider.y = spawnPosY;

        this.image = atlas.findRegion("Blue512x512");
    }

    public StudyArea(TextureAtlas atlas) {
        this(
                atlas,
                1,
                1,
                (float) GameManager.SCREEN_WIDTH / 2,
                (float) GameManager.SCREEN_HEIGHT / 2);
    } // Overriding Constructor for default values

    @Override
    public void interact(Player player) {
        if (player.getEnergy() < studyEnergyCost
                || !GameManager.getInstance().incrementTime(studyDuration)) {
            return;
        }
        applyStudyEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public TextureAtlas.AtlasRegion getImage() {
        return image;
    }

    private void applyStudyEffect(Player player) {

        player.setEnergy(player.getEnergy() - studyEnergyCost);
        // Increase intelligence by 10, this can be altered to more/less
        player.setIntelligence(player.getIntelligence() + 10);
    }
}
