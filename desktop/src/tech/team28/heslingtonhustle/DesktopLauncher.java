package tech.team28.heslingtonhustle;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM
// argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(0);
        config.setWindowedMode(1920, 1080);
        config.setTitle("Heslington Hustle");
        new Lwjgl3Application(new HeslingtonHustle(), config);
    }
}
