package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class StudyArea implements Interactable {

    private final float studyDuration;
    private final float studyEnergyCost;
    private final Rectangle collider;
    private final Texture image;

    public StudyArea(float studyDuration, float studyEnergyCost, float spawnPosX, float spawnPosY) {
        this.studyDuration = studyDuration;
        this.studyEnergyCost = studyEnergyCost;

        this.collider = new Rectangle();
        this.collider.width = 512;
        this.collider.height = 512;
        this.collider.x = spawnPosX;
        this.collider.y = spawnPosY;

        this.image = new Texture("Blue512x512.png");
    }

    public StudyArea() {
        this(1, 1, (float) GameManager.SCREEN_WIDTH / 2, (float) GameManager.SCREEN_HEIGHT / 2);
    } // Overriding Constructor for default values

    @Override
    public void Interact(Player player) {
        if(player.getEnergy() < studyEnergyCost || !GameManager.getInstance().incrementTime(studyDuration)){
            return;
        }
        applyStudyEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public Texture getImage() {
        return image;
    }

    @Override
    public void dispose() {
        image.dispose();
    }

    private void applyStudyEffect(Player player) {

        player.setEnergy(player.getEnergy() - studyEnergyCost);
        // Increase intelligence by 10, this can be altered to more/less
        player.setIntelligence(player.getIntelligence() + 10);
    }
}
