package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public interface Interactable{
    abstract void Interact(Player player);
    abstract Rectangle getCollider();
    abstract Texture getImage();

}
