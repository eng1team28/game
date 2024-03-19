package tech.team28.heslingtonhustle.areas;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import tech.team28.heslingtonhustle.GameManager;
import tech.team28.heslingtonhustle.Interactable;
import tech.team28.heslingtonhustle.Player;

/**
 * Represents a type of interactable where the player will expend energy and happiness to gain
 * intelligence.
 */
public class StudyArea extends Interactable {

    private final float studyDuration;
    private final float studyEnergyCost;
    private final GameManager gameManager;

    public StudyArea(
            String name,
            Sound sound,
            Sprite sprite,
            float spawnPosX,
            float spawnPosY,
            float studyDuration,
            float studyEnergyCost) {
        super(name, sound, sprite, spawnPosX, spawnPosY);
        this.studyDuration = studyDuration;
        this.studyEnergyCost = studyEnergyCost;
        this.gameManager = GameManager.getInstance();
    }

    public StudyArea(String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY) {
        this(name, sound, sprite, spawnPosX, spawnPosY, 1, 10);
    } // Overriding Constructor for default values

    @Override
    public void interact(Player player) {
        if (player.getEnergy() < studyEnergyCost || !gameManager.incrementTime(studyDuration)) {
            return;
        }
        gameManager.getAreaCounter().incrementStudyAreaCounter();
        applyStudyEffect(player);
        interactSound.play();
    }

    private void applyStudyEffect(Player player) {

        player.setEnergy(player.getEnergy() - studyEnergyCost);
        // Increase intelligence by 10, this can be altered to more/less
        player.setIntelligence(player.getIntelligence() + (10 * player.getHappiness()));
        player.setHappiness(player.getHappiness() - 0.3);
    }
}
