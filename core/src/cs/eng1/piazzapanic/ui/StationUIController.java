package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import cs.eng1.piazzapanic.stations.Station;

public class StationUIController {
  private final Stage uiStage;

  public StationUIController(Stage uiStage) {
    this.uiStage = uiStage;
  }

  public void showActions(Station station, String[] actions) {
    StationActionButtons buttons = new StationActionButtons(station, actions);
    buttons.setActionAlignment(StationActionButtons.ActionAlignment.TOP);
    uiStage.addActor(buttons);
  }
}
