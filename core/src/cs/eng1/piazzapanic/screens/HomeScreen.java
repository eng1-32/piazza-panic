package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import cs.eng1.piazzapanic.ui.SettingsOverlay;
import cs.eng1.piazzapanic.ui.TutorialOverlay;

public class HomeScreen implements Screen {

  private final Stage uiStage;

  public HomeScreen(final PiazzaPanicGame game) {
    // Initialize the root UI stage and table
    ScreenViewport uiViewport = new ScreenViewport();
    uiStage = new Stage(uiViewport);
    Table table = new Table();
    table.setFillParent(true);
    uiStage.addActor(table);

    final TutorialOverlay tutorialOverlay = game.getTutorialOverlay();
    tutorialOverlay.addToStage(uiStage);

    final SettingsOverlay settingsOverlay = game.getSettingsOverlay();
    settingsOverlay.addToStage(uiStage);

    Label welcomeLabel = new Label("Welcome to Piazza Panic!",
        new Label.LabelStyle(game.getFontManager().getTitleFont(), null));

    // Initialize buttons and callbacks
    TextButton startButton = game.getButtonManager()
        .createTextButton("Start", ButtonManager.ButtonColour.BLUE);
    startButton.sizeBy(3f);
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.loadGameScreen();
      }
    });
    TextButton tutorialButton = game.getButtonManager()
        .createTextButton("Tutorial", ButtonManager.ButtonColour.BLUE);
    tutorialButton.sizeBy(3f);
    tutorialButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        tutorialOverlay.show();
      }
    });
    TextButton settingsButton = game.getButtonManager()
        .createTextButton("Settings", ButtonManager.ButtonColour.BLUE);
    settingsButton.sizeBy(3f);
    settingsButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        settingsOverlay.show();
      }
    });
    TextButton quitButton = game.getButtonManager()
        .createTextButton("Exit to Desktop", ButtonManager.ButtonColour.RED);
    quitButton.sizeBy(3f);
    quitButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
      }
    });

    // Add UI elements to the table and position them
    table.add(welcomeLabel).padBottom(100f);
    table.row();
    table.add(startButton).padBottom(20f);
    table.row();
    table.add(tutorialButton).padBottom(20f);
    table.row();
    table.add(settingsButton).padBottom(20f);
    table.row();
    table.add(quitButton);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(uiStage);
  }

  @Override
  public void render(float delta) {
    // Initialize screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    uiStage.getCamera().update();

    // Render stage
    uiStage.act(delta);
    uiStage.draw();
  }

  @Override
  public void resize(int width, int height) {
    this.uiStage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    uiStage.dispose();
  }
}
