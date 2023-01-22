package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;

import java.util.HashMap;
import java.util.List;

/**
 * The controller of all UI relating to all stations. It enables showing and hiding action buttons
 * and displaying progress bars (WIP).
 */
public class StationUIController {

  private final Stage uiStage;
  private final PiazzaPanicGame game;
  private final HashMap<Integer, StationActionUI> stationActionUI;

  public StationUIController(Stage uiStage, PiazzaPanicGame game) {
    this.uiStage = uiStage;
    this.game = game;
    stationActionUI = new HashMap<>();
  }

  /**
   * Keep track of another station and initialize UI for that station.
   *
   * @param station the station to keep track of.
   */
  public void addStation(Station station) {
    StationActionUI buttons = new StationActionUI(station, game);
    buttons.setActionAlignment(station.getActionAlignment());
    uiStage.addActor(buttons);
    stationActionUI.put(station.getId(), buttons);
  }

  /**
   * Display a set of actions for a given station. If the station is not one that this class knows,
   * then it will initialize the UI for it and then show the actions.
   *
   * @param station The station for which the UI should be displayed.
   * @param actions The list of actions to show.
   */
  public void showActions(Station station, List<StationAction.ActionType> actions) {
    StationActionUI buttons = stationActionUI.get(station.getId());
    if (buttons == null) {
      addStation(station);
      buttons = stationActionUI.get(station.getId());
    }

    buttons.showActions(actions);
  }

  /**
   * Hide any actions that may be visible for a given station.
   *
   * @param station The station for which action should be hidden.
   */
  public void hideActions(Station station) {
    StationActionUI buttons = stationActionUI.get(station.getId());
    if (buttons != null) {
      buttons.hideActions();
    }
  }


  /**
   * Display a progress bar for a station. If the station is not one that this class knows, then it
   * will initialize the UI for it and then show the actions.
   *
   * @param station The station for which the UI should be displayed.
   */
  public void showProgressBar(Station station) {
    StationActionUI buttons = stationActionUI.get(station.getId());
    if (buttons == null) {
      addStation(station);
      buttons = stationActionUI.get(station.getId());
    }

    buttons.showProgressBar();
  }

  /**
   * Update progress bar for a station. If the station is not one that this class knows, then it
   * will initialize the UI for it and then show the actions.
   *
   * @param station The station for which the progress should be updated.
   * @param value   The value to be shown on the progress bar.
   */
  public void updateProgressValue(Station station, float value) {
    StationActionUI buttons = stationActionUI.get(station.getId());
    if (buttons == null) {
      addStation(station);
      buttons = stationActionUI.get(station.getId());
    }

    buttons.updateProgress(value);
  }

  /**
   * Hide any actions that may be visible for a given station.
   *
   * @param station The station for which action should be hidden.
   */
  public void hideProgressBar(Station station) {
    StationActionUI buttons = stationActionUI.get(station.getId());
    if (buttons != null) {
      buttons.hideProgressBar();
    }
  }
}
