package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class TutorialOverlay {

  private final Table table;

  public TutorialOverlay(final PiazzaPanicGame game) {
    // Initialize table
    this.table = new Table();
    table.setFillParent(true);
    table.setVisible(false);
    Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
    bgPixmap.setColor(Color.LIGHT_GRAY);
    bgPixmap.fill();
    TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(
        new Texture(bgPixmap));
    table.setBackground(textureRegionDrawableBg);

    // Initialize movement instructions label
    LabelStyle labelStyle = new LabelStyle(game.getFontManager().getHeaderFont(), Color.BLACK);
    Label chefMovement = new Label(
        "Left click on a chef to select them. Then use WASD or the arrow keys to move around.",
        labelStyle);
    chefMovement.setWrap(true);

    // Initialize station usage label
    Label stationUsage = new Label(
        "Move towards a station to get possible actions to appear as buttons including placing ingredients, picking up ingredients, and dealing with the ingredients.",
        labelStyle);
    stationUsage.setWrap(true);

    // Initialize recipe creation label
    Label recipeLabel = new Label(
        "Take the ingredients and process them as required for the recipe shown on the right. Then take them to the counter (table with the orange tablecloth) to build the recipe and complete the order.",
        labelStyle);
    recipeLabel.setWrap(true);

    TextButton backButton = game.getButtonManager()
        .createTextButton("Done", ButtonManager.ButtonColour.GREY);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        hide();
      }
    });

    // Add items to table
    table.pad(100f);
    table.add(chefMovement).fillX().expandX().pad(20f).padTop(0f);
    table.row();
    table.add(stationUsage).fillX().expandX().pad(20f).padTop(0f);
    table.row();
    table.add(recipeLabel).fillX().expandX().pad(20f).padTop(0f);
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
