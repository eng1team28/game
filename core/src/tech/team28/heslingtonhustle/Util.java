package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;

public class Util {
    static boolean allKeysPressed(int... keys) {
        for (int key : keys) {
            if (!Gdx.input.isKeyPressed(key)) {
                return false;
            }
        }
        return true;
    }

    static boolean anyKeyPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }
}
