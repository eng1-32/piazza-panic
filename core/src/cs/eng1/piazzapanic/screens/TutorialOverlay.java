package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class TutorialOverlay {

  private final Table table;

  PiazzaPanicGame game;


  public TutorialOverlay(final PiazzaPanicGame game) {
    this.game = game;
    this.table = new Table();
    table.setFillParent(true);
    table.setVisible(false);
    Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
    bgPixmap.setColor(Color.LIGHT_GRAY);
    bgPixmap.fill();
    TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(
        new TextureRegion(new Texture(bgPixmap)));
    table.setBackground(textureRegionDrawableBg);

    LabelStyle labelStyle = new LabelStyle(game.getFontManager().getHeaderFont(), Color.BLACK);
    Label chefMovement = new Label(
        "Left click on a chef to select them. Then use WASD or the arrow keys to move around.",
        labelStyle);
    chefMovement.setWrap(true);
    chefMovement.setWidth(1000f);

    Label stationUsage = new Label(
        "Move towards a station to get possible actions to appear as buttons including placing ingredients, picking up ingredients, and dealing with the ingredients.",
        labelStyle);
    stationUsage.setWrap(true);
    stationUsage.setWidth(1000f);

    TextButton backButton = game.getButtonManager()
        .createTextButton("Done", ButtonManager.ButtonColour.GREY);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        hide();
      }
    });

    table.add(chefMovement).width(1000f).padBottom(20f);
    table.row();
    table.add(stationUsage).width(1000f).padBottom(20f);
    table.row();
    table.add(backButton).padTop(20f);
  }

  public void addToStage(Stage uiStage) {
    uiStage.addActor(table);
  }

  public void show() {
    table.setVisible(true);
  }

  public void hide() {
    table.setVisible(false);
  }
}
