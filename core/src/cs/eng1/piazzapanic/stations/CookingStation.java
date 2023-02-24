package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.interfaces.Cookable;
import cs.eng1.piazzapanic.food.interfaces.Holdable;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

/**
 * The CookingStation class is a station representing the place in the kitchen
 * where you cook
 * patties to be used in making burgers.
 */
public class CookingStation extends Station {

  protected Cookable currentIngredient;
  private boolean progressVisible = false;

  /**
   * The constructor method for the class
   *
   * @param id           The unique identifier of the station
   * @param image        The rectangular area of the texture
   * @param uiController The controller from which we can get show and hide the
   *                     action buttons
   *                     belonging to the station
   * @param alignment    Dictates where the action buttons are shown
   * @param ingredients  An array of ingredients used to define what ingredients
   *                     can be cooked
   */
  public CookingStation(int id, TextureRegion image, StationUIController uiController,
      StationActionUI.ActionAlignment alignment) {
    super(id, image, uiController, alignment);
  }

  @Override
  public void reset() {
    currentIngredient = null;
    progressVisible = false;
    super.reset();
  }

  /**
   * Called every frame. Used to update the progress bar and check if enough time
   * has passed for the
   * ingredient to be changed to its half cooked or cooked variant
   *
   * @param delta Time in seconds since the last frame.
   */
  @Override
  public void act(float delta) {
    if (inUse) {
      currentIngredient.cookingTick(delta);

      uiController.updateProgressValue(this, currentIngredient.getCookingProgress());

      if (currentIngredient.cookingStepComplete() && progressVisible) {
        uiController.hideProgressBar(this);
        progressVisible = false;
        uiController.showActions(this, getActionTypes());
      }
    }
    super.act(delta);
  }

  /**
   * Checks the presented ingredient with the list of valid ingredients to see if
   * it can be cooked
   *
   * @param ingredientToCheck The ingredient presented by the chef to be checked
   *                          if it can be used
   *                          by the station
   * @return true if the ingredient is in the validIngredients array; false
   *         otherwise
   */
  private boolean isCorrectIngredient(Holdable itemToCheck) {
    if (itemToCheck instanceof Ingredient) {
      if (itemToCheck instanceof Cookable) {
        return !((Cookable) itemToCheck).getCooked();
      }
    }
    return false;
  }

  /**
   * Obtains the actions that can be currently performed depending on the states
   * of the station
   * itself and the selected chef
   *
   * @return actionTypes - the list of actions the station can currently perform.
   */
  @Override
  public List<StationAction.ActionType> getActionTypes() {
    LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef == null) {
      return actionTypes;
    }
    if (currentIngredient == null) {

      if (nearbyChef.hasIngredient() && isCorrectIngredient(nearbyChef.getStack().peek())) {
        actionTypes.add(StationAction.ActionType.PLACE_INGREDIENT);
      }
    } else {
      // check to see if total number of seconds has passed to progress the state of
      // the patty.
      if (currentIngredient.cookingStepComplete() && !currentIngredient.getCooked()) {
        actionTypes.add(StationAction.ActionType.FLIP_ACTION);
      }

      if (currentIngredient.getCooked()) {
        actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
      }

      if (!inUse) {
        actionTypes.add(StationAction.ActionType.COOK_ACTION);
      }
    }
    return actionTypes;

  }

  /**
   * Given an action, the station should attempt to do that action based on the
   * chef that is nearby
   * or the state of the ingredient currently on the station.
   *
   * @param action the action that needs to be done by this station if it can.
   */
  @Override
  public void doStationAction(StationAction.ActionType action) {
    switch (action) {
      case COOK_ACTION:
        // timeCooked is used to track how long the
        // ingredient has been cooking for.
        inUse = true;
        uiController.hideActions(this);
        uiController.showProgressBar(this);
        progressVisible = true;
        break;

      case FLIP_ACTION:
        currentIngredient.flip();
        uiController.hideActions(this);
        uiController.showProgressBar(this);
        progressVisible = true;
        break;

      case PLACE_INGREDIENT:
        if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null) {
          if (this.isCorrectIngredient(nearbyChef.getStack().peek())) {
            currentIngredient = (Cookable) nearbyChef.popIngredient();
          }
        }
        uiController.showActions(this, getActionTypes());
        break;

      case GRAB_INGREDIENT:
        if (nearbyChef.canGrabIngredient()) {
          nearbyChef.grabItem(currentIngredient.getCookingResult());
          currentIngredient = null;
          inUse = false;
        }
        uiController.showActions(this, getActionTypes());
        break;

    }
  }

  /**
   * Displays ingredients that have been placed on the station
   *
   * @param batch       Used to display a 2D texture
   * @param parentAlpha The parent alpha, to be multiplied with this actor's
   *                    alpha, allowing the
   *                    parent's alpha to affect all children.
   */
  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (currentIngredient != null) {
      drawFoodTexture(batch, currentIngredient.getTexture());
    }
  }
}
