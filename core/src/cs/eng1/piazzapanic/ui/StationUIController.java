package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import cs.eng1.piazzapanic.stations.Station;

public class StationUIController {
  private final Stage uiStage;
  private final VerticalGroup textGroup;

  public StationUIController(Stage uiStage) {
    this.uiStage = uiStage;
    textGroup = new VerticalGroup();
    textGroup.center();
    this.uiStage.addActor(textGroup);
  }

  public void showActions(Station station, String[] actions) {
    Label stationLabel = new Label(station.getName(), new Skin());
    textGroup.addActor(stationLabel);
  }
}
