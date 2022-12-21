package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.PiazzaPanicGame;

import java.util.HashMap;

public class ButtonManager {

  public enum ButtonColour {
    BLUE,
    GREEN,
    GREY,
    RED,
    YELLOW
  }

  HashMap<ButtonColour, TextButton.TextButtonStyle> textButtonStyles;

  public ButtonManager() {
    textButtonStyles = new HashMap<>();
    for (ButtonColour buttonColour : ButtonColour.values()) {
      String basePath = "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/";
      TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(
          new TextureRegionDrawable(
              new Texture(Gdx.files.internal(basePath + buttonColour.name().toLowerCase() + "_button_flat_up.png"))),
          new TextureRegionDrawable(
              new Texture(Gdx.files.internal(basePath + buttonColour.name().toLowerCase() + "_button_flat_down.png"))),
          null,
          PiazzaPanicGame.getFontManager().getLabelFont()
      );
      if (buttonColour == ButtonColour.GREY || buttonColour == ButtonColour.YELLOW) {
        style.fontColor = Color.BLACK;
      }
      textButtonStyles.put(buttonColour, style);
    }
  }

  public TextButton createTextButton(String text, ButtonColour colour) {
    return new TextButton(text, textButtonStyles.get(colour));
  }

  public void dispose() {
    for (TextButton.TextButtonStyle style : textButtonStyles.values()) {
      ((TextureRegionDrawable) style.up).getRegion().getTexture().dispose();
      ((TextureRegionDrawable) style.down).getRegion().getTexture().dispose();
    }
  }
}
