package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;

public class GameScreen implements Screen {

  private final PiazzaPanicGame game;
  private final Stage stage;
  private final OrthogonalTiledMapRenderer renderer;

  public GameScreen(PiazzaPanicGame game) {
    this.game = game;
    OrthographicCamera camera = new OrthographicCamera();
    ExtendViewport viewport = new ExtendViewport(10f, 10f, camera);
    this.stage = new Stage(viewport);

    TiledMap map = new TmxMapLoader().load("test-map.tmx");
    this.renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);
//    MapLayer stationLayer = map.getLayers().get("Stations");
//    MapObjects objects = stationLayer.getObjects(); // Contains list of stations

//    TestActor testActor = new TestActor();
//    testActor.setBounds(5, 5, 200, 200);
//    testActor.setColor(1, 0, 0, 1);
//    testActor.addAction(Actions.moveTo(1000, 50, 5.0f));
//    this.stage.addActor(testActor);
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.getCamera().update();
    stage.act(delta);
    stage.draw();
    renderer.setView((OrthographicCamera) stage.getCamera());
    renderer.render();
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
