package tech.team28.heslingtonhustle;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ExamImage extends Entity{
    public ExamImage(TextureAtlas atlas, float spawnPosX, float spawnPosY, String result) {
        super(atlas.createSprite("win"), spawnPosX, spawnPosY);
        
    }

}
