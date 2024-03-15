package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WinnerImage extends Entity{
    public WinnerImage(
            TextureAtlas atlas,
            float spawnPosX,
            float spawnPosY) {
        super(atlas.createSprite("win_placeholder"), spawnPosX, spawnPosY);
    }

}
