package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;

/**
 * A utility which generates UI buttons with the correct background images given specific text,
 * images and colours.
 */
public class ButtonManager implements Disposable {

  public enum ButtonColour {
    BLUE, GREEN, GREY, RED, YELLOW
  }

  private final HashMap<ButtonColour, TextButton.TextButtonStyle> textButtonStyles;
  private final HashMap<ButtonColour, Button.ButtonStyle> imageButtonBaseStyles;
  private final HashMap<ButtonColour, CheckBox.CheckBoxStyle> checkBoxStyles;

  private final Texture checkboxUnchecked;

  /**
   * @param fontManager the fontManager from which this class can get the right fonts required.
   */
  public ButtonManager(FontManager fontManager) {
    textButtonStyles = new HashMap<>();
    imageButtonBaseStyles = new HashMap<>();
    checkBoxStyles = new HashMap<>();

    String basePath = "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/";
    checkboxUnchecked = new Texture(
        Gdx.files.internal(basePath + "grey_box.png"));

    for (ButtonColour buttonColour : ButtonColour.values()) {

      // Generate all the different base colour styles from images and store them in a HashMap
      TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
          new TextureRegionDrawable(new Texture(Gdx.files.internal(
              basePath + buttonColour.name().toLowerCase() + "_button_flat_up.png"))),
          new TextureRegionDrawable(new Texture(Gdx.files.internal(
              basePath + buttonColour.name().toLowerCase() + "_button_flat_down.png"))),
          null,
          fontManager.getLabelFont());
      if (buttonColour == ButtonColour.GREY || buttonColour == ButtonColour.YELLOW) {
        textButtonStyle.fontColor = Color.BLACK;
      }
      textButtonStyles.put(buttonColour, textButtonStyle);

      // Generate all the base image button colour styles
      Button.ButtonStyle imageButtonBaseStyle = new Button.ButtonStyle(
          new TextureRegionDrawable(new Texture(Gdx.files.internal(
              basePath + buttonColour.name().toLowerCase() + "_button_square_flat_up.png"))),
          new TextureRegionDrawable(new Texture(Gdx.files.internal(
              basePath + buttonColour.name().toLowerCase() + "_button_square_flat_down.png"))),
          null);
      imageButtonBaseStyles.put(buttonColour, imageButtonBaseStyle);

      // Generate all the base checkbox colour styles
      CheckBoxStyle checkBoxStyle = new CheckBoxStyle(
          new TextureRegionDrawable(checkboxUnchecked),
          new TextureRegionDrawable(new Texture(Gdx.files.internal(
              basePath + buttonColour.name().toLowerCase() + "_boxCheckmark.png"))),
          fontManager.getLabelFont(),
          Color.BLACK
      );
      checkBoxStyles.put(buttonColour, checkBoxStyle);
    }
  }

  /**
   * Create a text button with certain parameters.
   *
   * @param text   The string to display on the button.
   * @param colour The colour of the base button.
   * @return The text button constructed based on the input.
   */
  public TextButton createTextButton(String text, ButtonColour colour) {
    return new TextButton(text, textButtonStyles.get(colour));
  }

  /**
   * Create an image button with certain parameters.
   *
   * @param image          The image to display on top of the button. It can have transparency.
   * @param colour         The colour of the base button.
   * @param yPressedOffset The amount to move the top image when the button has been pressed.
   * @return The image button constructed based on the input with a default of 0 padding.
   */
  public ImageButton createImageButton(Drawable image, ButtonColour colour, float yPressedOffset) {
    ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle(
        imageButtonBaseStyles.get(colour));
    btnStyle.imageUp = image;
    btnStyle.pressedOffsetY = yPressedOffset;
    return new ImageButton(btnStyle);
  }

  /**
   * Create a text button with certain parameters.
   *
   * @param text   The string to display on the button.
   * @param colour The colour of the base button.
   * @return The text button constructed based on the input.
   */
  public CheckBox createCheckbox(String text, ButtonColour colour) {
    return new CheckBox(text, checkBoxStyles.get(colour));
  }

  /**
   * Properly dispose of loaded textures from memory.
   */
  @Override
  public void dispose() {
    for (TextButton.TextButtonStyle style : textButtonStyles.values()) {
      ((TextureRegionDrawable) style.up).getRegion().getTexture().dispose();
      ((TextureRegionDrawable) style.down).getRegion().getTexture().dispose();
    }

    for (Button.ButtonStyle style : imageButtonBaseStyles.values()) {
      ((TextureRegionDrawable) style.up).getRegion().getTexture().dispose();
      ((TextureRegionDrawable) style.down).getRegion().getTexture().dispose();
    }

    checkboxUnchecked.dispose();
    for (CheckBoxStyle style : checkBoxStyles.values()) {
      ((TextureRegionDrawable) style.checkboxOn).getRegion().getTexture().dispose();
    }
  }
}
