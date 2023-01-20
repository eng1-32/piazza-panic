package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ingredients.Patty;
import cs.eng1.piazzapanic.ui.StationActionButtons;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class CookingStation extends Station {

  protected Ingredient[] validIngredients;
  protected Ingredient currentIngredient;
  protected long timeCooked;
  protected Patty statusPatty = new Patty();
//TODO: Create doc strings for functions
  public CookingStation(int id, TextureRegion image, StationUIController uiController,
                        StationActionButtons.ActionAlignment alignment, Ingredient[] ingredients) {
    super(id, image, uiController, alignment);
    validIngredients = ingredients; //A list of the ingredients that can be used by this station.
  }

  private boolean isCorrectIngredient(Ingredient ingredientToCheck) {
    if(!ingredientToCheck.getCooked()){
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
      switch(currentIngredient.getType()){
        case "patty":
          //check to see if 10 seconds has passed to progress the state of the patty.
          if(TimeUtils.timeSinceMillis(timeCooked) >= 10000 && inUse && !statusPatty.getHalfCooked()){
            actionTypes.add(StationAction.ActionType.FLIP_ACTION);
          } else if (TimeUtils.timeSinceMillis(timeCooked) >= 10000 && inUse && statusPatty.getHalfCooked()) {
            actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
          }
      }
      if(!inUse){
        actionTypes.add(StationAction.ActionType.COOK_ACTION);
      }
    }
    return actionTypes;
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    switch (action) {
      case COOK_ACTION:
        //timeCooked is used to track how long the
        //ingredient has been cooking for.
        timeCooked = TimeUtils.millis();
        inUse = true;
        break;

      case FLIP_ACTION:
        statusPatty.setHalfCooked();
        currentIngredient = statusPatty;
        timeCooked = TimeUtils.millis();
        break;

      case PLACE_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null ) {
          if(this.isCorrectIngredient(nearbyChef.getStack().peek())){
            currentIngredient = nearbyChef.placeIngredient();
          }
          //TODO: Display a warning when an incorrect ingredient is presented
        }
        break;

      case GRAB_INGREDIENT:
        if (nearbyChef.canGrabIngredient()) {
          switch (currentIngredient.getType()){
            case "patty":
              statusPatty.setCooked(true);
              currentIngredient = statusPatty;
          }
          nearbyChef.grabIngredient(currentIngredient);
          statusPatty = new Patty();
          currentIngredient = null;
          inUse = false;
        }
        break;
    }
    uiController.showActions(this, getActionTypes());
  }
}
