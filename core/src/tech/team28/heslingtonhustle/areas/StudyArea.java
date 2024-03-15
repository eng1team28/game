package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

public class StudyArea extends Interactable {

    private final float studyDuration;
    private final float studyEnergyCost;
    private final GameManager gameManager;

    public StudyArea(
            TextureAtlas atlas,
            float studyDuration,
            float studyEnergyCost,
            float spawnPosX,
            float spawnPosY) {
        super(atlas.createSprite("Blue512x512"), spawnPosX, spawnPosY);
        this.studyDuration = studyDuration;
        this.studyEnergyCost = studyEnergyCost;
        this.gameManager = GameManager.getInstance();
    }

    public StudyArea(TextureAtlas atlas) {
        this(atlas, 1, 10, GameManager.GAME_WIDTH / 2, GameManager.GAME_HEIGHT / 2);
    } // Overriding Constructor for default values

    @Override
    public void interact(Player player) {
        if (player.getEnergy() < studyEnergyCost || !gameManager.incrementTime(studyDuration)) {
            return;
        }
        gameManager
                .getAreaCounter()
                .setStudyAreaCounter(gameManager.getAreaCounter().getStudyAreaCounter() + 1);
        applyStudyEffect(player);
    }

    private void applyStudyEffect(Player player) {

        player.setEnergy(player.getEnergy() - studyEnergyCost);
        // Increase intelligence by 10, this can be altered to more/less
        player.setIntelligence(player.getIntelligence() + (10 * player.getHappiness()));
        player.setHappiness(player.getHappiness() - 0.3);
    }
}
