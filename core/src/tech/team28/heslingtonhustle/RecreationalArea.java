package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class RecreationalArea implements Interactable {
    @Override
    public void Interact(Player player) {
        GameManager.getInstance().incrementTime(1);

        applyRecreationalEffect(player);
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public TextureAtlas.AtlasRegion getImage() {
        return null;
    }

    private void applyRecreationalEffect(Player player) {
        return;
    }
}
