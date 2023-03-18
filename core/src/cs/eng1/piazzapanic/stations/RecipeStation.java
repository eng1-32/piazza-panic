package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.food.recipes.Humborge;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The RecipeStation class is a station representing the place in the kitchen
 * where you combine ingredients to create food. The food can then be served to
 * the customer via the station.
 */
public class RecipeStation extends Station {
  private final FoodTextureManager textureManager;
  private final CustomerManager customerManager;
  protected int bunCount = 0;
  protected int pattyCount = 0;
  protected int lettuceCount = 0;
  protected int tomatoCount = 0;
  private Recipe completedRecipe = null;

  /**
   * The constructor method for the class
   *
   * @param id                    The unique identifier of the station
   * @param textureRegion         The rectangular area of the texture
   * @param stationUIController   The controller from which we can get show and hide the action
   *                              buttons belonging to the station
   * @param alignment             Dictates where the action buttons are shown
   * @param textureManager        The controller from which we can get information on what texture
   *                              each ingredient should have
   * @param customerManager       The controller from which we can get information on what food
   *                              needs to be served
   */
  public RecipeStation(int id, TextureRegion textureRegion, StationUIController stationUIController,
      ActionAlignment alignment, FoodTextureManager textureManager,
      CustomerManager customerManager) {
    super(id, textureRegion, stationUIController, alignment);
    this.textureManager = textureManager;
    this.customerManager = customerManager;
  }

  @Override
  public void reset() {
    bunCount = 0;
    pattyCount = 0;
    lettuceCount = 0;
    tomatoCount = 0;
    completedRecipe = null;
    super.reset();
  }

  /**
   * Obtains the actions that can be currently performed depending on the states of the station
   * itself and the selected chef
   *
   * @return actionTypes - the list of actions the station can currently perform.
   */
  @Override
  public List<ActionType> getActionTypes() {
    LinkedList<ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef != null) {
      if (!nearbyChef.getStack().isEmpty()) {
        Ingredient checkItem = nearbyChef.getStack().peek();
        if (checkItem.getIsChopped() || checkItem.getIsCooked() || Objects.equals(
            checkItem.getType(), "bun")) {
          //If a chef is nearby and is carrying at least one ingredient
          // and the top ingredient is cooked, chopped or a bun then display the action
          actionTypes.add(ActionType.PLACE_INGREDIENT);
        }
      }
      if (completedRecipe == null) {
        if (pattyCount >= 1 && bunCount >= 1 && nearbyChef.getStack().hasSpace()) {
          actionTypes.add(ActionType.MAKE_BURGER);
        }
        if (tomatoCount >= 1 && lettuceCount >= 1 && nearbyChef.getStack().hasSpace()) {
          actionTypes.add(ActionType.MAKE_SALAD);
        }
      } else if (customerManager.checkRecipe(completedRecipe)) {
        actionTypes.add(ActionType.SUBMIT_ORDER);
      }
    }
    return actionTypes;
  }

  /**
   * Given an action, the station should attempt to do that action based on the chef that is nearby
   * or what ingredient(s) are currently on the station.
   *
   * @param action the action that needs to be done by this station if it can.
   */
  @Override
  public void doStationAction(ActionType action) {
    switch (action) {
      case PLACE_INGREDIENT:
        Ingredient topItem = nearbyChef.getStack().peek();
        switch (topItem.getType()) {
          case "patty":
            nearbyChef.placeIngredient();
            pattyCount += 1;
            break;
          case "tomato":
            nearbyChef.placeIngredient();
            tomatoCount += 1;
            break;
          case "lettuce":
            nearbyChef.placeIngredient();
            lettuceCount += 1;
            break;
          case "bun":
            nearbyChef.placeIngredient();
            bunCount += 1;
            break;
        }

        break;
      case MAKE_BURGER:
        completedRecipe = new Humborge(textureManager);
        pattyCount -= 1;
        bunCount -= 1;
        break;

      case MAKE_SALAD:
        completedRecipe = new Salad(textureManager);
        tomatoCount -= 1;
        lettuceCount -= 1;
        break;

      case SUBMIT_ORDER:
        if (completedRecipe != null) {
          if (customerManager.checkRecipe(completedRecipe)) {
            customerManager.nextRecipe();
            completedRecipe = null;
          }
        }
        break;
    }
    uiController.showActions(this, getActionTypes());
  }

  /**
   * Displays ingredients that have been placed on the station
   *
   * @param batch       Used to display a 2D texture
   * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
   *           children.
   */
  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (bunCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("bun"));
    }
    if (pattyCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("patty_cooked"));
    }
    if (lettuceCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("lettuce_chopped"));
    }
    if (tomatoCount > 0) {
      drawFoodTexture(batch, textureManager.getTexture("tomato_chopped"));
    }
    if (completedRecipe != null) {
      drawFoodTexture(batch, completedRecipe.getTexture());
    }
  }

  /**
   * Updates the current available actions based on the new customer's order
   */
  public void updateOrderActions() {
    uiController.showActions(this, getActionTypes());
  }
}
