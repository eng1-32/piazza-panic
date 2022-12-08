package cs.eng1.piazzapanic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TestActor extends Actor {

  Texture image;
  float speed = 25.0f;
  public TestActor() {
    image = new Texture(Gdx.files.internal("badlogic.jpg"));
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(image, getX(), getY(), getWidth(), getHeight());
  }
}
