package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ingredients.Patty;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;


public class CookingStation extends Station {

  protected Ingredient[] validIngredients;
  protected Ingredient currentIngredient;
  protected float timeCooked;
  protected float totalTimeToCook = 10f;
  private boolean progressVisible = false;

  //TODO: Create doc strings for functions
  public CookingStation(int id, TextureRegion image, StationUIController uiController,
      StationActionUI.ActionAlignment alignment, Ingredient[] ingredients) {
    super(id, image, uiController, alignment);
    validIngredients = ingredients; //A list of the ingredients that can be used by this station.
  }

  @Override
  public void act(float delta) {
    if (inUse) {
      timeCooked += delta;
      uiController.updateProgressValue(this, (timeCooked / totalTimeToCook) * 100f);
      if (timeCooked >= totalTimeToCook && progressVisible) {
        uiController.hideProgressBar(this);
        uiController.showActions(this, getActionTypes());
        progressVisible = false;
        if (currentIngredient instanceof Patty && !((Patty) currentIngredient).getHalfCooked()) {
          ((Patty) currentIngredient).setHalfCooked();
        } else if (currentIngredient instanceof Patty
            && ((Patty) currentIngredient).getHalfCooked() && !currentIngredient.getIsCooked()) {
          currentIngredient.setIsCooked(true);
        }
      }
    }
  }

  private boolean isCorrectIngredient(Ingredient ingredientToCheck) {
    if (!ingredientToCheck.getIsCooked()) {
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
      if (currentIngredient instanceof Patty) {
        //check to see if total number of seconds has passed to progress the state of the patty.
        if (timeCooked >= totalTimeToCook && inUse
            && !((Patty) currentIngredient).getHalfCooked()) {
          actionTypes.add(StationAction.ActionType.FLIP_ACTION);
        } else if (timeCooked >= totalTimeToCook && inUse
            && ((Patty) currentIngredient).getHalfCooked()) {
          actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
        }
      }
      if (!inUse) {
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
        timeCooked = 0;
        inUse = true;
        uiController.hideActions(this);
        uiController.showProgressBar(this);
        progressVisible = true;
        break;

      case FLIP_ACTION:
        timeCooked = 0;
        uiController.hideActions(this);
        uiController.showProgressBar(this);
        progressVisible = true;
        break;

      case PLACE_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null) {
          if (this.isCorrectIngredient(nearbyChef.getStack().peek())) {
            currentIngredient = nearbyChef.placeIngredient();
          }
          //TODO: Display a warning when an incorrect ingredient is presented
        }
        uiController.showActions(this, getActionTypes());
        break;

      case GRAB_INGREDIENT:
        if (nearbyChef.canGrabIngredient()) {
          nearbyChef.grabIngredient(currentIngredient);
          currentIngredient = null;
          inUse = false;
        }
        uiController.showActions(this, getActionTypes());
        break;
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (currentIngredient != null) {
      batch.draw(currentIngredient.getTexture(), getX() + .2f, getY() + .2f, .6f, .6f);
    }
  }
}
