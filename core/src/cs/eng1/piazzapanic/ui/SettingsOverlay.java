package cs.eng1.piazzapanic.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.ButtonManager.ButtonColour;

public class SettingsOverlay {

  private final Table table;
  PiazzaPanicGame game;


  public SettingsOverlay(final PiazzaPanicGame game) {
    this.game = game;
    table = new Table();
    table.setFillParent(true);
    table.setVisible(false);
    table.center();
    Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
    bgPixmap.setColor(Color.LIGHT_GRAY);
    bgPixmap.fill();
    TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(
        new Texture(bgPixmap));
    table.setBackground(textureRegionDrawableBg);

    TextButton backButton = game.getButtonManager()
        .createTextButton("Back", ButtonManager.ButtonColour.GREY);
    backButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        hide();
      }
    });

    final CheckBox fullscreenCheckbox = game.getButtonManager()
        .createCheckbox("Fullscreen", ButtonColour.BLUE);
    fullscreenCheckbox.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (fullscreenCheckbox.isChecked()) {
          Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
          Gdx.graphics.setWindowedMode(1920 / 2, 1080 / 2);
        }
      }
    });
    table.add(fullscreenCheckbox);
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
