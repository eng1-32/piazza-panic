package cs.eng1.piazzapanic;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
  public static void main(String[] arg) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setForegroundFPS(60);
    config.setTitle("Piazza Panic");
    config.setWindowSizeLimits(1920 / 2, 1080 / 2, -1, -1);
    new Lwjgl3Application(new PiazzaPanicGame(), config);
  }
}
