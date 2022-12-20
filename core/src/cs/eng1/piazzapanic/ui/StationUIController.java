package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import cs.eng1.piazzapanic.stations.Station;

public class StationUIController {
  private final Stage uiStage;
  private final FontManager fontManager;
  private final VerticalGroup textGroup;

  public StationUIController(Stage uiStage, FontManager fontManager) {
    this.uiStage = uiStage;
    this.fontManager = fontManager;

    textGroup = new VerticalGroup();
    textGroup.setPosition(uiStage.getWidth() / 2f, uiStage.getHeight() / 2f);
    this.uiStage.addActor(textGroup);
  }

  public void showActions(Station station, String[] actions) {
    Label.LabelStyle stationLabelStyle = new Label.LabelStyle(fontManager.getLabelFont(), null);
    Label stationLabel = new Label(station.getName(), stationLabelStyle);
    textGroup.addActor(stationLabel);
  }
}
