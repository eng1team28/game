package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface Interactable {
    void interact(Player player);

    Rectangle getCollider();

    TextureRegion getImage();
}
