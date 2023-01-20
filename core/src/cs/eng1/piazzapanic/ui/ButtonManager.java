package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import cs.eng1.piazzapanic.PiazzaPanicGame;

import java.util.HashMap;

public class ButtonManager implements Disposable {

    public enum ButtonColour {
        BLUE, GREEN, GREY, RED, YELLOW
    }

    HashMap<ButtonColour, TextButton.TextButtonStyle> textButtonStyles;
    HashMap<ButtonColour, Button.ButtonStyle> imageButtonBaseStyles;

    public ButtonManager() {
        textButtonStyles = new HashMap<>();
        imageButtonBaseStyles = new HashMap<>();
        for (ButtonColour buttonColour : ButtonColour.values()) {
            String basePath = "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/";

            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(
                    new TextureRegionDrawable(new Texture(Gdx.files.internal(
                            basePath + buttonColour.name().toLowerCase() + "_button_flat_up.png"))),
                    new TextureRegionDrawable(new Texture(Gdx.files.internal(
                            basePath + buttonColour.name().toLowerCase() + "_button_flat_down.png"))),
                    null,
                    PiazzaPanicGame.getFontManager().getLabelFont());
            if (buttonColour == ButtonColour.GREY || buttonColour == ButtonColour.YELLOW) {
                textButtonStyle.fontColor = Color.BLACK;
            }
            textButtonStyles.put(buttonColour, textButtonStyle);

            Button.ButtonStyle imageButtonBaseStyle = new Button.ButtonStyle(
                    new TextureRegionDrawable(new Texture(Gdx.files.internal(
                            basePath + buttonColour.name().toLowerCase() + "_button_square_flat_up.png"))),
                    new TextureRegionDrawable(new Texture(Gdx.files.internal(
                            basePath + buttonColour.name().toLowerCase() + "_button_square_flat_down.png"))),
                    null);
            imageButtonBaseStyles.put(buttonColour, imageButtonBaseStyle);
        }
    }

    public TextButton createTextButton(String text, ButtonColour colour) {
        return new TextButton(text, textButtonStyles.get(colour));
    }

    public ImageButton createImageButton(Drawable image, ButtonColour colour, float yPressedOffset) {
        ImageButton.ImageButtonStyle btnStyle = new ImageButton.ImageButtonStyle(imageButtonBaseStyles.get(colour));
        btnStyle.imageUp = image;
        btnStyle.pressedOffsetY = yPressedOffset;
        return new ImageButton(btnStyle);
    }

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
    }
}
