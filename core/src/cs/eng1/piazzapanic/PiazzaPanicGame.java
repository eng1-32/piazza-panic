package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.ui.FontManager;

public class PiazzaPanicGame extends Game {
	private FontManager fontManager;
	@Override
	public void create () {
		this.fontManager = new FontManager();
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose () {
		fontManager.dispose();
	}

	public FontManager getFontManager() {
		return fontManager;
	}
}
