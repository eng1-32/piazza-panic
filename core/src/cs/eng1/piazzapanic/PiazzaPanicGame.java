package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.screens.HomeScreen;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.FontManager;

public class PiazzaPanicGame extends Game {

    private static FontManager fontManager;
    private static ButtonManager buttonManager;
    private GameScreen gameScreen;
    private HomeScreen homeScreen;

    @Override
    public void create() {
        fontManager = new FontManager();
        buttonManager = new ButtonManager();
        loadHomeScreen();
    }

    @Override
    public void dispose() {
        fontManager.dispose();
        buttonManager.dispose();
    }

    public void loadHomeScreen() {
        if (homeScreen == null) {
            homeScreen = new HomeScreen(this);
        }
        setScreen(homeScreen);
    }

    public void loadGameScreen() {
        if (gameScreen == null) {
            gameScreen = new GameScreen(this);
        }
        setScreen(gameScreen);
    }

    public void quit() {
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        if (homeScreen != null) {
            homeScreen.dispose();
        }
        dispose();
    }

    public static FontManager getFontManager() {
        return fontManager;
    }

    public static ButtonManager getButtonManager() {
        return buttonManager;
    }

}
