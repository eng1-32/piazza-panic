package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;

import java.util.List;

public class StationUIController {
  private final Stage uiStage;

  public StationUIController(Stage uiStage) {
    this.uiStage = uiStage;
  }

  public void showActions(Station station, List<StationAction.ActionType> actions) {
    StationActionButtons buttons = new StationActionButtons(station, actions);
    buttons.setActionAlignment(StationActionButtons.ActionAlignment.TOP);
    uiStage.addActor(buttons);
  }
}
