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
import cs.eng1.piazzapanic.TestActor;

public class GameScreen implements Screen {

  private final Stage stage;
  private final OrthogonalTiledMapRenderer renderer;

  public GameScreen() {
    float sizeX = 10f;
    float sizeY = 10f;
    float tileUnitSize = 1 / 16f;

    // Initialise stage and camera
    OrthographicCamera camera = new OrthographicCamera();
    ExtendViewport viewport = new ExtendViewport(sizeX, sizeY, camera); // Number of tiles
    this.stage = new Stage(viewport);

    // Initialise tilemap
    TiledMap map = new TmxMapLoader().load("test-map.tmx");
    this.renderer = new OrthogonalTiledMapRenderer(map, tileUnitSize);
    MapLayer objectLayer = map.getLayers().get("Stations");

    // Add tile objects
    for (MapObject object : objectLayer.getObjects()) {
      if (object instanceof TiledMapTileMapObject) {
        TiledMapTileMapObject tileObject = (TiledMapTileMapObject) object;
        TestActor testActor = new TestActor(tileObject.getTextureRegion());
        testActor.setBounds(tileObject.getX() * tileUnitSize, tileObject.getY() * tileUnitSize, 1, 1);
        stage.addActor(testActor);
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
