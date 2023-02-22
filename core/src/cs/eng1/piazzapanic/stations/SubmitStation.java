package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;

/**
 * AssembleStation
 */
public class SubmitStation extends Station {

    public SubmitStation(int id, TextureRegion image, StationUIController uiController, ActionAlignment alignment) {
        super(id, image, uiController, alignment);
    }

    /**
     * to put in getActiontypes:
     * if (customerManager.checkRecipe(completedRecipe)) {
        actionTypes.add(ActionType.SUBMIT_ORDER);
      }
     */
    
}