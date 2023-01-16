package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;

import java.util.HashMap;
import java.util.List;

public class StationUIController {

  private final Stage uiStage;
  private final HashMap<Integer, StationActionButtons> stationActionButtons;

  public StationUIController(Stage uiStage) {
    this.uiStage = uiStage;
    stationActionButtons = new HashMap<>();
  }

  public void addStation(Station station) {
    StationActionButtons buttons = new StationActionButtons(station);
    buttons.setActionAlignment(station.getActionAlignment());
    uiStage.addActor(buttons);
    stationActionButtons.put(station.getId(), buttons);
  }

  public void showActions(Station station, List<StationAction.ActionType> actions) {
    StationActionButtons buttons = stationActionButtons.get(station.getId());
    if (buttons == null) {
      addStation(station);
      buttons = stationActionButtons.get(station.getId());
    }

    buttons.showActions(actions);
  }

  public void hideActions(Station station) {
    StationActionButtons buttons = stationActionButtons.get(station.getId());
    if (buttons != null) {
      buttons.hideActions();
    }
  }
}
