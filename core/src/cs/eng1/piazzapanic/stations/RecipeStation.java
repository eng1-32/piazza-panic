package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;
import cs.eng1.piazzapanic.ui.StationUIController;

public class RecipeStation extends Station {

  public RecipeStation(int id, TextureRegion textureRegion, StationUIController stationUIController,
      ActionAlignment alignment) {
    super(id, textureRegion, stationUIController, alignment);
  }
}
