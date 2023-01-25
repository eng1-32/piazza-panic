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
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class Station extends Actor implements Observer<Chef> {

  protected final int id;
  protected final StationUIController uiController;
  protected final StationActionUI.ActionAlignment actionAlignment;
  protected TextureRegion stationImage;

  protected boolean inUse = false;

  protected List<Subject<Chef>> chefSubjects = new LinkedList<>();
  protected Chef nearbyChef = null;
  private float imageRotation = 0.0f;

  public Station(int id, TextureRegion image, StationUIController uiController,
      StationActionUI.ActionAlignment alignment) {
    this.id = id;
    stationImage = image; // Texture of the object
    actionAlignment = alignment;
    this.uiController = uiController;
  }

  public void setImageRotation(float rotation) {
    this.imageRotation = rotation;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(stationImage, getX(), getY(), 0.5f, 0.5f, getWidth(), getHeight(), 1f, 1f,
        imageRotation);
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

    // Check for any station colliders
    if (chefSubjects.isEmpty()) {
      shapes.setColor(oldColor);
      return;
    }

    // Draw lines to linked station colliders
    shapes.setColor(Color.BLUE);
    for (Subject<Chef> chefSubject : chefSubjects) {
      if (chefSubject instanceof Actor) {
        Actor collider = (Actor) chefSubject;
        Vector2 start = new Vector2(getX() + getWidth() / 2f, getY() + getHeight() / 2f);
        Vector2 end = new Vector2(collider.getX() + collider.getWidth() / 2f,
            collider.getY() + collider.getHeight() / 2f);
        shapes.line(start, end);
      }
    }

    // Reset colour
    shapes.setColor(oldColor);
  }

  @Override
  public void update(Chef chef) {
    if (chef != null) {
      this.nearbyChef = chef;
      uiController.showActions(this, getActionTypes());
    } else if (this.nearbyChef != null) {
      Chef remainingChef = null;
      for (Subject<Chef> chefSubject : chefSubjects) {
        remainingChef = chefSubject.getLastNotification();
        if (remainingChef != null) {
          break;
        }
      }
      if (remainingChef == null) {
        this.nearbyChef = null;
        uiController.hideActions(this);
      } else if (remainingChef != nearbyChef) {
        this.nearbyChef = remainingChef;
        uiController.showActions(this, getActionTypes());
      } else {
        uiController.showActions(this, getActionTypes());
      }
    }
  }

  @Override
  public void addSubject(Subject<Chef> chefSubject) {
    this.chefSubjects.add(chefSubject);
  }

  @Override
  public void removeSubject(Subject<Chef> chefSubject) {
    this.chefSubjects.remove(chefSubject);
  }

  @Override
  public void deregisterFromAllSubjects() {
    for (Subject<Chef> chefSubject : this.chefSubjects) {
      chefSubject.deregister(this);
    }
    this.chefSubjects.clear();
  }

  @Override
  public List<Subject<Chef>> getSubjects() {
    return this.chefSubjects;
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
  public StationActionUI.ActionAlignment getActionAlignment() {
    return actionAlignment;
  }

  public int getId() {
    return id;
  }
}
