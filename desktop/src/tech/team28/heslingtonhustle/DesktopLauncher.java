package tech.team28.heslingtonhustle;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM
// argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // default: 0 (unlimited)
        config.setForegroundFPS(60);
        config.setMaximized(true);
        config.setTitle("Heslington Hustle");
        new Lwjgl3Application(new HeslingtonHustle(), config);
    }
}
