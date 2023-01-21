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

  protected  boolean inUse = false;

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

  /**
   * Draw the outline of the shape of the station as a rectangle and draw a blue line from the
   * centre of this station (which is an Observer) to the centre of the stationCollider that it is
   * linked to (the Subject that this is registered to).
   *
   * @param shapes The renderer to use to draw debugging information
   */
  @Override
  public void drawDebug(ShapeRenderer shapes) {
    Color oldColor = shapes.getColor();

    // Draw bounds of this station
    shapes.setColor(Color.RED);
    shapes.rect(getX(), getY(), getWidth(), getHeight());

    // Draw line to linked station collider
    if (chefSubject != null && chefSubject instanceof Actor) {
      Actor collider = (Actor) chefSubject;
      Vector2 start = new Vector2(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
      Vector2 end = new Vector2(collider.getX() + collider.getWidth() / 2f,
          collider.getY() + collider.getHeight() / 2f);
      shapes.setColor(Color.BLUE);
      shapes.line(start, end);
    }

    // Reset colour
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

  /**
   * @return the list of possible actions that this station based on the current state
   */
  public List<StationAction.ActionType> getActionTypes() {
    return new LinkedList<>();
  }

  /**
   * Given an action, the station should attempt to do that action based on the chef that is nearby
   * or what ingredient(s) are currently on the station.
   *
   * @param action the action that needs to be done by this station if it can.
   */
  public void doStationAction(StationAction.ActionType action) {
  }

  /**
   * @return the direction in which the action buttons should be displayed.
   */
  public StationActionButtons.ActionAlignment getActionAlignment() {
    return actionAlignment;
  }

  public int getId() {
    return id;
  }
}
