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
import cs.eng1.piazzapanic.ui.UIOverlay;

public class HomeScreen implements Screen {
  public static int mode = 0;
  private final Stage uiStage;
  public static int difficulty = 1;
  public static boolean load = false;

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
    TextButton EasyButton = game.getButtonManager()
        .createTextButton("Easy", ButtonManager.ButtonColour.BLUE);
    EasyButton.sizeBy(3f);
    EasyButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        mode = 0;
        difficulty = 0;
        load = false;
        game.loadGameScreen();
      }
    });

    TextButton startButton = game.getButtonManager()
        .createTextButton("Normal", ButtonManager.ButtonColour.BLUE);
    startButton.sizeBy(3f);
    startButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        mode = 0;
        difficulty = 1;
        load = false;

        game.loadGameScreen();
      }
    });

    TextButton hardButton = game.getButtonManager()
        .createTextButton("Hard", ButtonManager.ButtonColour.BLUE);
    hardButton.sizeBy(3f);
    hardButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        mode = 0;
        difficulty = 2;
        load = false;

        game.loadGameScreen();
      }
    });

    TextButton loadButton = game.getButtonManager()
        .createTextButton("Load", ButtonManager.ButtonColour.BLUE);
    loadButton.sizeBy(3f);
    loadButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        load = true;
        game.loadGameScreen();
      }
    });
    TextButton endlessButton = game.getButtonManager()
        .createTextButton("Endless", ButtonManager.ButtonColour.BLUE);
    endlessButton.sizeBy(3f);
    endlessButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        mode = 1;
        game.loadGameScreen();
        load = false;

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
    table.add(welcomeLabel).padBottom(60f);
    table.row();
    table.add(EasyButton).padBottom(20f);
    table.row();
    table.add(startButton).padBottom(20f);
    table.row();
    table.add(hardButton).padBottom(20f);
    table.row();
    table.add(loadButton).padBottom(20f);
    table.row();
    table.add(endlessButton).padBottom(20f);
    table.row();
    table.add(tutorialButton).padBottom(20f);
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
