package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.StationActionButtons;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class IngredientStation extends Station {

  protected Ingredient ingredientDispensed;

  public IngredientStation(int id, TextureRegion image, StationUIController uiController,
      StationActionButtons.ActionAlignment alignment, Ingredient ingredient) {
    super(id, image, uiController, alignment);
    ingredientDispensed = ingredient; //What ingredient the station will give to the player.
  }

  public Ingredient dispenseNewIngredient() {
    return Ingredient.fromString(ingredientDispensed.getType());
  }

  @Override
  public List<StationAction.ActionType> getActionTypes() {
    LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef == null) {
      return actionTypes;
    }
    actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
    return actionTypes;
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    if (action == StationAction.ActionType.GRAB_INGREDIENT) {
      if (this.nearbyChef != null && nearbyChef.canGrabIngredient()) {
        nearbyChef.grabIngredient(Ingredient.fromString(ingredientDispensed.getType()));
      }
    }
  }
}
