package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
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
    public Texture getImage() {
        return null;
    }

    @Override
    public void dispose() {

    }

    private void applyRecreationalEffect(Player player) {
        return;
    }
}
