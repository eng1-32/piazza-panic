package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.ui.FontManager;

public class PiazzaPanicGame extends Game {
	private static FontManager fontManager;
	@Override
	public void create () {
		fontManager = new FontManager();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose () {
		fontManager.dispose();
	}

	public static FontManager getFontManager() {
		return fontManager;
	}
}
