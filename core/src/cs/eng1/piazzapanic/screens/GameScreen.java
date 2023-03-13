package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import cs.eng1.piazzapanic.MapLoader;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.stations.*;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UIOverlay;

/**
 * The screen which can be used to load the tilemap and keep track of everything
 * happening in the
 * game. It does all the initialization and then lets each actor do its actions
 * based on the current
 * frame.
 */
public class GameScreen implements Screen {

  private final Stage stage;
  private final Stage uiStage;
  private final ChefManager chefManager;
  private final OrthogonalTiledMapRenderer tileMapRenderer;
  private final StationUIController stationUIController;
  private final UIOverlay uiOverlay;
  private final FoodTextureManager foodTextureManager;
  private final CustomerManager customerManager;
  private boolean isFirstFrame = true;
  private final Box2DDebugRenderer box2dDebugRenderer;
  private final World world;

  public GameScreen(final PiazzaPanicGame game, int totalCustomers) {
    world = new World(new Vector2(0, 0), true);
    box2dDebugRenderer = new Box2DDebugRenderer();

    MapLoader mapLoader = new MapLoader("main-game-map.tmx");

    // Initialize stage and camera
    OrthographicCamera camera = new OrthographicCamera();
    ExtendViewport viewport = new ExtendViewport(mapLoader.mapSize.x, mapLoader.mapSize.y, camera); // Number of tiles
    this.stage = new Stage(viewport);

    ScreenViewport uiViewport = new ScreenViewport();
    this.uiStage = new Stage(uiViewport);
    this.stationUIController = new StationUIController(uiStage, game);
    uiOverlay = new UIOverlay(uiStage, game);

    // Initialize tilemap
    this.tileMapRenderer = mapLoader.createMapRenderer();

    foodTextureManager = new FoodTextureManager();

    chefManager = new ChefManager(mapLoader.unitScale * 2.5f, uiOverlay, world);
    customerManager = new CustomerManager(uiOverlay, totalCustomers);

    mapLoader.createStations("Stations", "Sensors", chefManager, 
    stage, stationUIController, foodTextureManager, customerManager);
    // Add box2d colliders
    mapLoader.createBox2DBodies("Obstacles", world);
    chefManager.addChefsToStage(stage);
  }

  @Override
  public void show() {
    InputMultiplexer multiplexer = new InputMultiplexer();
    multiplexer.addProcessor(uiStage);
    multiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(multiplexer);
    uiOverlay.init();
    chefManager.init();
    customerManager.init(foodTextureManager);

    for (Actor actor : stage.getActors().items) {
      if (actor instanceof Station) {
        ((Station) actor).reset();
      }
    }
    isFirstFrame = true;
  }

  @Override
  public void render(float delta) {
    // Initialize screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.getCamera().update();
    uiStage.getCamera().update();

    // Render background
    tileMapRenderer.setView((OrthographicCamera) stage.getCamera());
    tileMapRenderer.render();

    // Render stage
    stage.act(delta);
    uiStage.act(delta);

    stage.draw();
    uiStage.draw();
    box2dDebugRenderer.render(world, stage.getCamera().combined);
    world.step(delta, 6, 2);

    if (isFirstFrame) {
      uiOverlay.updateRecipeUI(customerManager.getFirstOrder());
      isFirstFrame = false;
    }
  }

  @Override
  public void resize(int width, int height) {
    this.stage.getViewport().update(width, height, true);
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
    stage.dispose();
    uiStage.dispose();
    tileMapRenderer.dispose();
    foodTextureManager.dispose();
    chefManager.dispose();
  }
}
