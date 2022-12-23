package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.StationActionButtons;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class ChoppingStation extends Station {

  protected Ingredient[] validIngredients;
  protected Ingredient currentIngredient = null;

  public ChoppingStation(int id, TextureRegion image, StationUIController uiController,
      StationActionButtons.ActionAlignment alignment, Ingredient[] ingredients) {
    super(id, image, uiController, alignment);
    validIngredients = ingredients; //A list of the ingredients that can be used by this station.
  }

  @Override
  public List<StationAction.ActionType> getActionTypes() {
    LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef == null) {
      return actionTypes;
    }
    if (currentIngredient == null) {
      actionTypes.add(StationAction.ActionType.PLACE_INGREDIENT);
    } else {
      actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
      actionTypes.add(StationAction.ActionType.CHOP_ACTION);
    }
    return actionTypes;
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    switch (action) {
      case CHOP_ACTION:
        // TODO: implement
        break;
      case PLACE_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null) {
          currentIngredient = nearbyChef.placeIngredient();
        }
        break;
      case GRAB_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.canGrabIngredient()
            && currentIngredient != null) {
          nearbyChef.grabIngredient(currentIngredient);
          currentIngredient = null;
        }
        break;
    }
  }
}
