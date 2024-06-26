package tech.team28.heslingtonhustle;

import com.badlogic.gdx.Gdx;

/** Provides utility methods for input handling and other common tasks. */
public class Util {
    /**
     * Checks if all specified keys are currently pressed.
     *
     * @param keys The keys to check, from {@link com.badlogic.gdx.Input.Keys}
     * @return True if all keys are pressed, False otherwise.
     */
    static boolean allKeysPressed(int... keys) {
        for (int key : keys) {
            if (!Gdx.input.isKeyPressed(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any of the specified keys are currently pressed.
     *
     * @param keys The keys to check, from {@link com.badlogic.gdx.Input.Keys}
     * @return True if any one of the keys is pressed, False otherwise.
     */
    static boolean anyKeyPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any of the specified keys are just pressed.
     *
     * @param keys The keys to check, from {@link com.badlogic.gdx.Input.Keys}
     * @return True if any one of the keys is just pressed, False otherwise.
     */
    static boolean anyKeyJustPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyJustPressed(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes a single word title case.
     *
     * @param str the String to re-case
     * @return The String with the first character uppercased and the rest lowercased
     */
    static String capitaliseString(String str) {
        String firstLetter = str.substring(0, 1);
        String remainder = str.substring(1);
        return firstLetter.toUpperCase() + remainder.toLowerCase();
    }
}
