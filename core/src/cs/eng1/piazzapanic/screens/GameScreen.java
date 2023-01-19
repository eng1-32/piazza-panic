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
import cs.eng1.piazzapanic.ui.StationActionButtons;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.HashMap;

public class GameScreen implements Screen {

  private final Stage stage;
  private final Stage uiStage;
  private final ChefManager chefManager;
  private final OrthogonalTiledMapRenderer tileMapRenderer;
  private final StationUIController stationUIController;

  public GameScreen(final PiazzaPanicGame game) {
    TiledMap map = new TmxMapLoader().load("big-map.tmx");
    int sizeX = map.getProperties().get("width", Integer.class);
    int sizeY = map.getProperties().get("height", Integer.class);
    float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);

    // Initialise stage and camera
    OrthographicCamera camera = new OrthographicCamera();
    ExtendViewport viewport = new ExtendViewport(sizeX, sizeY, camera); // Number of tiles
    this.stage = new Stage(viewport);

    ScreenViewport uiViewport = new ScreenViewport();
    this.uiStage = new Stage(uiViewport);
    this.stationUIController = new StationUIController(uiStage, game);

    // Initialise tilemap
    this.tileMapRenderer = new OrthogonalTiledMapRenderer(map, tileUnitSize);
    MapLayer objectLayer = map.getLayers().get("Stations");
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");

    chefManager = new ChefManager(tileUnitSize * 2.5f, collisionLayer);
    // Add tile objects
    initialiseStations(tileUnitSize, objectLayer);
    chefManager.addChefsToStage(stage);
  }

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
      StationActionButtons.ActionAlignment alignment = StationActionButtons.ActionAlignment.valueOf(
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
    // Initialise screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.getCamera().update();

    // Render background
    tileMapRenderer.setView((OrthographicCamera) stage.getCamera());
    tileMapRenderer.render();

    // Render stage
    stage.setDebugAll(true); // TODO: remove after testing
//    uiStage.setDebugAll(true);
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
    System.out.println("Hide game screen");
  }

  @Override
  public void dispose() {
    stage.dispose();
    uiStage.dispose();
    tileMapRenderer.dispose();
    System.out.println("Dispose game screen");
  }
}
