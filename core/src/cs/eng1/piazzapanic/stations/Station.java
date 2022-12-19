package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import cs.eng1.piazzapanic.Chef;
import cs.eng1.piazzapanic.observable.Observer;
import cs.eng1.piazzapanic.observable.Subject;

public class Station extends Actor implements Observer<Chef> {
  protected TextureRegion stationImage;
  protected Boolean inUse = false;

  protected Subject<Chef> chefSubject = null;

  public Station(TextureRegion image) {
    stationImage = image; // Texture of the object
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(stationImage, getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public void drawDebug(ShapeRenderer shapes) {
    Color oldColor = shapes.getColor();

    shapes.setColor(Color.RED);
    shapes.rect(getX(), getY(), getWidth(), getHeight());

    if (chefSubject != null && chefSubject instanceof Actor) {
      Actor collider = (Actor) chefSubject;
      Vector2 start = new Vector2(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
      Vector2 end = new Vector2(collider.getX() + collider.getWidth() / 2f,
          collider.getY() + collider.getHeight() / 2f);
      shapes.setColor(Color.BLUE);
      shapes.line(start, end);
    }
    shapes.setColor(oldColor);
  }

  @Override
  public void update(Chef chef) {
    // TODO: display possible actions on UI when chef is in range
  }

  @Override
  public void setSubject(Subject<Chef> chefSubject) {
    this.chefSubject = chefSubject;
  }

  @Override
  public Subject<Chef> getSubject() {
    return this.chefSubject;
  }
}
