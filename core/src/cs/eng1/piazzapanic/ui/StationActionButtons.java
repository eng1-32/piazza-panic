package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.stations.Station;


public class StationActionButtons extends Table {
  private ActionAlignment actionAlignment = ActionAlignment.TOP;

  public static enum ActionAlignment {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM
  }
  Station station;
  public StationActionButtons(Station station, String[] actions) {
    this.station = station;
    for (String action : actions) {
      TextButton actionButton = PiazzaPanicGame.getButtonManager().createTextButton(action, ButtonManager.ButtonColour.BLUE);
      add(actionButton).width(100).height(30).pad(2f);
      row();
    }
    bottom();
  }

  public void setActionAlignment(ActionAlignment actionAlignment) {
    this.actionAlignment = actionAlignment;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    calculatePositionFromAlignment();
    super.draw(batch, parentAlpha);
  }

  private void calculatePositionFromAlignment() {
    Vector3 worldPosition;
    Vector3 screenPosition;
    switch (actionAlignment) {
      case LEFT:
        // Get left center of station in screen coordinates
        worldPosition = new Vector3(station.getX(),
            station.getY() + station.getHeight() / 2f, 0f);
        screenPosition = station.getStage().getCamera().project(worldPosition);
        // Adjust for lower left of table
        setPosition(screenPosition.x - getWidth(), screenPosition.y - getHeight() / 2f);
        break;
      case TOP:
        // Get upper middle of station in screen coordinates
        worldPosition = new Vector3(station.getX() + station.getWidth() / 2f,
            station.getY() + station.getHeight(), 0f);
        screenPosition = station.getStage().getCamera().project(worldPosition);
        // Adjust for lower left of table
        setPosition(screenPosition.x - getWidth() / 2f, screenPosition.y);
        break;
      case RIGHT:
        // Get right center of station in screen coordinates
        worldPosition = new Vector3(station.getX() + station.getWidth(),
            station.getY() + station.getHeight() / 2f, 0f);
        screenPosition = station.getStage().getCamera().project(worldPosition);
        // Adjust for lower left of table
        setPosition(screenPosition.x, screenPosition.y - getHeight() / 2f);
        break;
      case BOTTOM:
        // Get lower middle of station in screen coordinates
        worldPosition = new Vector3(station.getX() + station.getWidth() / 2f,
            station.getY(), 0f);
        screenPosition = station.getStage().getCamera().project(worldPosition);
        // Adjust for lower left of table
        setPosition(screenPosition.x - getWidth() / 2f, screenPosition.y);
        break;
    }
  }
}
