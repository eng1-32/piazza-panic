package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.observable.Observer;
import cs.eng1.piazzapanic.observable.Subject;
import cs.eng1.piazzapanic.ui.StationActionButtons;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class Station extends Actor implements Observer<Chef> {

  protected final int id;
  protected final StationUIController uiController;
  protected final StationActionButtons.ActionAlignment actionAlignment;
  protected TextureRegion stationImage;
  protected Boolean inUse = false;

  protected Subject<Chef> chefSubject = null;
  protected Chef nearbyChef = null;

  public Station(int id, TextureRegion image, StationUIController uiController,
      StationActionButtons.ActionAlignment alignment) {
    this.id = id;
    stationImage = image; // Texture of the object
    actionAlignment = alignment;
    this.uiController = uiController;
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
    if (chef != null && this.nearbyChef != chef) {
      this.nearbyChef = chef;
      uiController.showActions(this, getActionTypes());
    } else if (chef == null && this.nearbyChef != null) {
      this.nearbyChef = null;
      uiController.hideActions(this);
    }
  }

  @Override
  public void setSubject(Subject<Chef> chefSubject) {
    this.chefSubject = chefSubject;
  }

  @Override
  public Subject<Chef> getSubject() {
    return this.chefSubject;
  }

  public List<StationAction.ActionType> getActionTypes() {
    return new LinkedList<>();
  }

  public void doStationAction(StationAction.ActionType action) {
  }

  public StationActionButtons.ActionAlignment getActionAlignment() {
    return actionAlignment;
  }

  public int getId() {
    return id;
  }
}
