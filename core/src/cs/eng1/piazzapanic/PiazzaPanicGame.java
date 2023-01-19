package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.screens.HomeScreen;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.FontManager;

public class PiazzaPanicGame extends Game {

    private FontManager fontManager;
    private ButtonManager buttonManager;
    private GameScreen gameScreen;
    private HomeScreen homeScreen;

    @Override
    public void create() {
        fontManager = new FontManager();
        buttonManager = new ButtonManager(fontManager);
        loadHomeScreen();
    }

    @Override
    public void dispose() {
        if (gameScreen != null) {
            gameScreen.dispose();
        }
        if (homeScreen != null) {
            homeScreen.dispose();
        }
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

    public FontManager getFontManager() {
        return fontManager;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

}
