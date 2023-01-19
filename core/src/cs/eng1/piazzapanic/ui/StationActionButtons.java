package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;

import java.util.List;


public class StationActionButtons extends Table {

  private ActionAlignment actionAlignment = ActionAlignment.TOP;

  public enum ActionAlignment {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM
  }

  Station station;
  PiazzaPanicGame game;


  public StationActionButtons(final Station station, final PiazzaPanicGame game) {
    this.station = station;
    this.game = game;
    setVisible(false);
    center();
    bottom();
  }

  public void showActions(List<StationAction.ActionType> actions) {
    clearChildren();
    for (final StationAction.ActionType action : actions) {
      String actionDescription = StationAction.getActionDescription(action);
      TextButton actionButton = game.getButtonManager()
          .createTextButton(actionDescription, ButtonManager.ButtonColour.BLUE);
      actionButton.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          station.doStationAction(action);
          super.clicked(event, x, y);
        }
      });
      add(actionButton).width(100).height(30).pad(2f);
      row();
    }
    setVisible(true);
  }

  public void hideActions() {
    setVisible(false);
    clearChildren();
  }

  public void setActionAlignment(ActionAlignment actionAlignment) {
    this.actionAlignment = actionAlignment;
    center();
    switch (actionAlignment) {
      case TOP:
        bottom();
        break;
      case BOTTOM:
        top();
        break;
      case LEFT:
        right();
        break;
      case RIGHT:
        left();
        break;
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    calculatePositionFromAlignment();
    super.draw(batch, parentAlpha);
  }

  private void calculatePositionFromAlignment() {
    Vector3 worldPosition = new Vector3();
    switch (actionAlignment) {
      case LEFT:
        // Get left center of station in screen coordinates
        worldPosition.x = station.getX();
        worldPosition.y = station.getY() + station.getHeight() / 2f;
        break;
      case TOP:
        // Get upper middle of station in screen coordinates
        worldPosition.x = station.getX() + station.getWidth() / 2f;
        worldPosition.y = station.getY() + station.getHeight();
        break;
      case RIGHT:
        // Get right center of station in screen coordinates
        worldPosition.x = station.getX() + station.getWidth();
        worldPosition.y = station.getY() + station.getHeight() / 2f;
        break;
      case BOTTOM:
        // Get lower middle of station in screen coordinates
        worldPosition.x = station.getX() + station.getWidth() / 2f;
        worldPosition.y = station.getY();
        break;
    }
    Vector3 screenPosition = station.getStage().getCamera().project(worldPosition);
    setPosition(screenPosition.x, screenPosition.y);
  }
}
