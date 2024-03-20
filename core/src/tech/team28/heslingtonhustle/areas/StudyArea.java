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
    public StudyArea(String name, Sound sound, Sprite sprite, float spawnPosX, float spawnPosY) {
        super(name, sound, sprite, spawnPosX, spawnPosY, 2, 40);
    } // Overriding Constructor for default values

    @Override
    public void interactEffect(Player player) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.getAreaCounter().incrementStudyAreaCounter();
    }
}
