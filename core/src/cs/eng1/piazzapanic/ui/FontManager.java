package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontManager {
  private final FreeTypeFontGenerator fontGenerator;
  private final BitmapFont titleFont;
  private final BitmapFont headerFont;
  private final BitmapFont labelFont;

  public FontManager() {
    fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Kenney Bold.ttf"));

    FreeTypeFontParameter titleFontParameters = new FreeTypeFontParameter();
    titleFontParameters.size = 32;
    titleFont = fontGenerator.generateFont(titleFontParameters);

    FreeTypeFontParameter headerFontParameters = new FreeTypeFontParameter();
    headerFontParameters.size = 24;
    headerFont = fontGenerator.generateFont(headerFontParameters);

    FreeTypeFontParameter labelFontParameters = new FreeTypeFontParameter();
    labelFontParameters.size = 10;
    labelFont = fontGenerator.generateFont(labelFontParameters);
  }

  public BitmapFont getTitleFont() {
    return titleFont;
  }

  public BitmapFont getHeaderFont() {
    return headerFont;
  }

  public BitmapFont getLabelFont() {
    return labelFont;
  }

  public BitmapFont generateFont(FreeTypeFontParameter parameter) {
    return fontGenerator.generateFont(parameter);
  }

  public void dispose() {
    fontGenerator.dispose();
  }
}
