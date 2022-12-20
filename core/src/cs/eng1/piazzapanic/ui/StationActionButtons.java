package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.stations.Station;


public class StationActionButtons extends VerticalGroup {
  Station station;
  public StationActionButtons(Station station, String[] actions) {
    this.station = station;
    Label.LabelStyle stationLabelStyle = new Label.LabelStyle(PiazzaPanicGame.getFontManager().getLabelFont(), null);
    for (String action : actions) {
      Label stationLabel = new Label(action, stationLabelStyle);
      this.addActor(stationLabel);
    }
    bottom();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Vector3 worldPosition = new Vector3(station.getX() + station.getWidth() / 2f,
        station.getY() + station.getHeight(), 0f);
    Vector3 position = station.getStage().getCamera().project(worldPosition);
    setPosition(position.x, position.y);
    super.draw(batch, parentAlpha);
  }
}
