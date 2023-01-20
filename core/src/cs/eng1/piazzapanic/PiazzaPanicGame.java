package cs.eng1.piazzapanic;

import com.badlogic.gdx.Game;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.FontManager;

public class PiazzaPanicGame extends Game {

  private static FontManager fontManager;
  private static ButtonManager buttonManager;

  @Override
  public void create() {
    fontManager = new FontManager();
    buttonManager = new ButtonManager();
    setScreen(new GameScreen());
  }

  @Override
  public void dispose() {
    fontManager.dispose();
    buttonManager.dispose();
  }

  public static FontManager getFontManager() {
    return fontManager;
  }

  public static ButtonManager getButtonManager() {
    return buttonManager;
  }

}
