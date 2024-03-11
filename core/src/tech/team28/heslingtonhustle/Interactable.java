package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public interface Interactable {
    void Interact(Player player);

    Rectangle getCollider();

    TextureAtlas.AtlasRegion getImage();
}
