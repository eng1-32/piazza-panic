package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import cs.eng1.piazzapanic.Ingredient;
import cs.eng1.piazzapanic.stations.ChoppingStation;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.stations.IngredientStation;
import cs.eng1.piazzapanic.stations.Station;

public class GameScreen implements Screen {

  private final Stage stage;
  private final OrthogonalTiledMapRenderer renderer;

  public GameScreen() {
    TiledMap map = new TmxMapLoader().load("big-map.tmx");
    int sizeX = map.getProperties().get("width", Integer.class);
    int sizeY = map.getProperties().get("height", Integer.class);
    float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);

    // Initialise stage and camera
    OrthographicCamera camera = new OrthographicCamera();
    ExtendViewport viewport = new ExtendViewport(sizeX, sizeY, camera); // Number of tiles
    this.stage = new Stage(viewport);

    // Initialise tilemap
    this.renderer = new OrthogonalTiledMapRenderer(map, tileUnitSize);
    MapLayer objectLayer = map.getLayers().get("Stations");

    // Add tile objects
    for (MapObject object : objectLayer.getObjects()) {
      if (object instanceof TiledMapTileMapObject) {
        TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
        if (tileObject.getProperties().containsKey("stationType")) {
          Station thisStation;
          String ingredients = tileObject.getProperties().get("ingredients", String.class);
          switch (tileObject.getProperties().get("stationType", String.class)) {
            case "cookingStation":
              thisStation = new CookingStation(tileObject.getTextureRegion(), Ingredient.arrayFromString(ingredients));
              break;
            case "ingredientStation":
              thisStation = new IngredientStation(tileObject.getTextureRegion(), new Ingredient(ingredients));
              break;
            case "choppingStation":
              thisStation = new ChoppingStation(tileObject.getTextureRegion(), Ingredient.arrayFromString(ingredients));
              break;
            default:
              thisStation = new Station(tileObject.getTextureRegion());
          }
          thisStation.setBounds(tileObject.getX() * tileUnitSize, tileObject.getY() * tileUnitSize, 1, 1);
          stage.addActor(thisStation);
        }
      }
    }
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    // Initialise screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Render background
    renderer.setView((OrthographicCamera) stage.getCamera());
    renderer.render();

    // Render stage
    stage.getCamera().update();
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    this.stage.getViewport().update(width, height, true);
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
  }
}
