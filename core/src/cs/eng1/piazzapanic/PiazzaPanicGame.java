package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import cs.eng1.piazzapanic.screens.GameScreen;

public class PiazzaPanicGame extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen());
	}

	@Override
	public void dispose () {}
}
