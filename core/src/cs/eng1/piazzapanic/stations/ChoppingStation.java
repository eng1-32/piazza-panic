package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class ChoppingStation extends Station {

  protected Ingredient[] validIngredients;
  protected Ingredient currentIngredient = null;
  protected long chopTime;
  protected float waitTime = 5;

  public ChoppingStation(int id, TextureRegion image, StationUIController uiController,
      StationActionUI.ActionAlignment alignment, Ingredient[] ingredients) {
    super(id, image, uiController, alignment);
    validIngredients = ingredients; //A list of the ingredients that can be used by this station.
  }

  private boolean isCorrectIngredient(Ingredient ingredientToCheck) {
    if (!ingredientToCheck.getCooked()) {
      for (Ingredient item : this.validIngredients) {
        if (ingredientToCheck.getType() == item.getType()) {
          return true;
        }
      }
    }
    return false;
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
      if (!inUse) {
        actionTypes.add(StationAction.ActionType.CHOP_ACTION);
      }
      actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);

    }
    return actionTypes;
  }

  @Override
  public void act(float delta) {
    //TODO: add time related things here!
    if (inUse) {
      waitTime -= delta;
      if (waitTime <= 0) {
        currentIngredient.setChopped(true);
        nearbyChef.setPaused(false);
        waitTime = 5;
      }
    }
    super.act(delta);
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    switch (action) {
      case CHOP_ACTION:
        // TODO: implement
        chopTime = TimeUtils.millis();
        inUse = true;
        uiController.showActions(this, getActionTypes());
        nearbyChef.setPaused(true);
        break;
      case PLACE_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null) {
          if ((this.isCorrectIngredient(nearbyChef.getStack().peek()))) {
            currentIngredient = nearbyChef.placeIngredient();
          }
        }
        break;
      case GRAB_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.canGrabIngredient()
            && currentIngredient != null) {
          nearbyChef.grabIngredient(currentIngredient);
          currentIngredient = null;
          inUse = false;
        }
        break;
    }
    uiController.showActions(this, getActionTypes());
  }
}
