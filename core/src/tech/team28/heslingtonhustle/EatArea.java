package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class EatArea implements Interactable {
    @Override
    public void interact(Player player) {
        GameManager.getInstance().incrementTime(1);

        applyEatEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public TextureRegion getImage() {
        return null;
    }

    private void applyEatEffect(Player player) {}
}
