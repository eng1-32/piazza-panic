package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.StationActionButtons;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;


public class CookingStation extends Station {
  protected Ingredient[] validIngredients;
  protected Ingredient currentIngredient;
  protected long timeCooked;

  public CookingStation(int id, TextureRegion image, StationUIController uiController,
                        StationActionButtons.ActionAlignment alignment, Ingredient[] ingredients) {
    super(id, image, uiController, alignment);
    validIngredients = ingredients; //A list of the ingredients that can be used by this station.
  }

  public void stationInteract() {
    //TODO: Change if statement to check for a flipped patty or a
    // uncooked patty once ingredients class is implemented
    if (this.currentIngredient == currentIngredient) {
      collectPatty();
    } else if (this.currentIngredient == currentIngredient) {
      flipPatty();
    } else {
      //cookFood(top ingredient in food stack);
    }
  }

  public void cookFood(Ingredient foodItem) {
    if (this.inUse || !(isCorrectIngredient(foodItem))) {
      return; //If the station is in use or the wrong ingredient is used
      //then nothing happens.
    }
    this.inUse = true;
    timeCooked = TimeUtils.millis();
    currentIngredient = foodItem;

  }

  public void flipPatty() {
    if (TimeUtils.timeSinceMillis(timeCooked) >= 10) {
      //ingredientCooking = new Ingredient(flippedPatty);
    }
  }

  public void collectPatty() {
    if (TimeUtils.timeSinceMillis(timeCooked) >= 10) {
      //ingredientCooking = new Ingredient(flippedPatty);
      //TODO: Add cooked patty back to chef's ingredient stack
      this.inUse = false;
      this.currentIngredient = null;
    }
  }

  private boolean isCorrectIngredient(Ingredient ingredientToCheck) {
    for (Ingredient item : this.validIngredients) {
      if (item.getType().equals(ingredientToCheck.getType())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<StationAction.ActionType> getActionTypes() {
    LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef == null) return actionTypes;
    if (currentIngredient == null) {
      actionTypes.add(StationAction.ActionType.PLACE_INGREDIENT);
    } else {
      actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
      actionTypes.add(StationAction.ActionType.COOK_ACTION);
    }
    return actionTypes;
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    switch (action) {
      case COOK_ACTION:
        // TODO: implement
        break;
      case PLACE_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null) {
          currentIngredient = nearbyChef.placeIngredient();
        }
        break;
      case GRAB_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.canGrabIngredient() && currentIngredient != null) {
          nearbyChef.grabIngredient(currentIngredient);
          currentIngredient = null;
        }
        break;
    }
  }
}
