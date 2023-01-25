package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.*;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UiOverlay;

import java.util.HashMap;

/**
 * The screen which can be used to load the tilemap and keep track of everything happening in the
 * game. It does all the initialization and then lets each actor do its actions based on the current
 * frame.
 */
public class GameScreen implements Screen {

  private final Stage stage;
  private final Stage uiStage;
  private final ChefManager chefManager;
  private final OrthogonalTiledMapRenderer tileMapRenderer;
  private final StationUIController stationUIController;
  private final UiOverlay uiOverlay;

  public GameScreen(final PiazzaPanicGame game) {
    TiledMap map = new TmxMapLoader().load("big-map.tmx");
    int sizeX = map.getProperties().get("width", Integer.class);
    int sizeY = map.getProperties().get("height", Integer.class);
    float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);

    // Initialize stage and camera
    OrthographicCamera camera = new OrthographicCamera();
    ExtendViewport viewport = new ExtendViewport(sizeX, sizeY, camera); // Number of tiles
    this.stage = new Stage(viewport);

    ScreenViewport uiViewport = new ScreenViewport();
    this.uiStage = new Stage(uiViewport);
    this.stationUIController = new StationUIController(uiStage, game);

    // Initialize tilemap
    this.tileMapRenderer = new OrthogonalTiledMapRenderer(map, tileUnitSize);
    MapLayer objectLayer = map.getLayers().get("Stations");
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");

    chefManager = new ChefManager(tileUnitSize * 2.5f, collisionLayer);
    // Add tile objects
    initialiseStations(tileUnitSize, objectLayer);
    chefManager.addChefsToStage(stage);
    this.uiOverlay = new UiOverlay(uiStage, game);
    uiStage.setDebugAll(true);
  }

  /**
   * @param tileUnitSize The ratio of world units over the pixel width of a single tile/station
   * @param objectLayer  The layer on the TMX tilemap which contains all the information about the
   *                     stations and station colliders including position, bounds and station
   *                     capabilities.
   */
  private void initialiseStations(float tileUnitSize, MapLayer objectLayer) {
    Array<TiledMapTileMapObject> tileObjects = objectLayer.getObjects()
        .getByType(TiledMapTileMapObject.class);
    Array<RectangleMapObject> colliderObjects = objectLayer.getObjects()
        .getByType(RectangleMapObject.class);
    HashMap<Integer, StationCollider> colliders = new HashMap<>();

    for (RectangleMapObject colliderObject : new Array.ArrayIterator<>(colliderObjects)) {
      Integer id = colliderObject.getProperties().get("id", Integer.class);
      StationCollider collider = new StationCollider(chefManager);
      Rectangle bounds = colliderObject.getRectangle();
      collider.setBounds(bounds.getX() * tileUnitSize, bounds.getY() * tileUnitSize,
          bounds.getWidth() * tileUnitSize, bounds.getHeight() * tileUnitSize);
      stage.addActor(collider);
      colliders.put(id, collider);
    }

    for (TiledMapTileMapObject tileObject : new Array.ArrayIterator<>(tileObjects)) {
      // Check if it is actually a station
      if (!tileObject.getProperties().containsKey("stationType")) {
        continue;
      }

      Station station;
      int id = tileObject.getProperties().get("id", Integer.class);
      String ingredients = tileObject.getProperties().get("ingredients", String.class);
      StationActionUI.ActionAlignment alignment = StationActionUI.ActionAlignment.valueOf(
          tileObject.getProperties().get("actionAlignment", "TOP", String.class));

      switch (tileObject.getProperties().get("stationType", String.class)) {
        case "cookingStation":
          station = new CookingStation(id, tileObject.getTextureRegion(), stationUIController,
              alignment, Ingredient.arrayFromString(ingredients));
          break;
        case "ingredientStation":
          station = new IngredientStation(id, tileObject.getTextureRegion(), stationUIController,
              alignment, Ingredient.fromString(ingredients));
          break;
        case "choppingStation":
          station = new ChoppingStation(id, tileObject.getTextureRegion(), stationUIController,
              alignment, Ingredient.arrayFromString(ingredients));
          break;
        default:
          station = new Station(id, tileObject.getTextureRegion(), stationUIController, alignment);
      }
      station.setBounds(tileObject.getX() * tileUnitSize, tileObject.getY() * tileUnitSize, 1, 1);
      stage.addActor(station);

      Integer colliderID = tileObject.getProperties().get("collisionObjectID", Integer.class);
      StationCollider collider = colliders.get(colliderID);
      if (collider != null) {
        collider.register(station);
      }
    }
  }

  @Override
  public void show() {
    InputMultiplexer multiplexer = new InputMultiplexer();
    multiplexer.addProcessor(uiStage);
    multiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(multiplexer);
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
  }
}
