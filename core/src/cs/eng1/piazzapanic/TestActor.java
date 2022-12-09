package cs.eng1.piazzapanic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TestActor extends Actor {

//  Texture image;
  TextureRegion region;
  public TestActor(TextureRegion region) {
    this.region = region;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
//    batch.draw(image, getX(), getY(), getWidth(), getHeight());
    batch.draw(region, getX(), getY(), getWidth(), getHeight());
  }
}
