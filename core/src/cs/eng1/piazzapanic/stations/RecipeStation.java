package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.chef.FixedStack;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.ingredients.UncookedPizza;
import cs.eng1.piazzapanic.food.interfaces.Holdable;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * The RecipeStation class is a station representing the place in the kitchen
 * where you combine ingredients to create food. The food can then be served to
 * the customer via the station.
 */
public class RecipeStation extends Station {
  private final FoodTextureManager textureManager;

  private static final int MAX_ITEMS_PER_GROUP = 3;

  private Holdable completedRecipe = null;

  private Stack<Ingredient> displayIngredient = new Stack<>();

  /**
   * A hashmap of items grouped by type that does not allow empty lists to exist.
   * Yes, I'm using hashmaps again.
   */
  private Map<String, FixedStack<Ingredient>> heldItems = new HashMap<String, FixedStack<Ingredient>>() {
    /**
     * <p>
     * {@inheritDoc}
     * <p>
     * An empty list will always have no mapping, and will result in a {@code null}
     * return.
     */
    @Override
    public FixedStack<Ingredient> put(String key, FixedStack<Ingredient> value) {
      if (value.size() != 0) {
        return super.put(key, value);
      }
      return null;
    };

    /**
     * <p>
     * {@inheritDoc}
     * <p>
     * If replaced by an empty list, removes the mapping altogether.
     */
    public FixedStack<Ingredient> replace(String key, FixedStack<Ingredient> value) {
      FixedStack<Ingredient> returnVal = this.get(key);
      if (returnVal != null) {
        if (this.put(key, value) == null) {
          this.remove(key);
        }
        ;
      }
      return returnVal;
    };
  };

  HashMap<ActionType, String[]> makeActions = new HashMap<ActionType, String[]>() {
    {
      put(ActionType.MAKE_BURGER, new String[] { "bun", "patty" });
      put(ActionType.MAKE_SALAD, new String[] { "tomato", "lettuce" });
      put(ActionType.ASSEMBLE_PIZZA, new String[] { "dough", "cheese", "tomato" });
      put(ActionType.MAKE_JACKET, new String[] { "potato", "cheese" });
    }
  };

  private void placeIngredient(Ingredient input) {
    if (heldItems.containsKey(input.getType())) {
      FixedStack<Ingredient> ingredients = heldItems.get(input.getType());
      ingredients.add(input);
    } else {
      FixedStack<Ingredient> ingredients = new FixedStack<Ingredient>(MAX_ITEMS_PER_GROUP);
      ingredients.add(input);
      heldItems.put(input.getType(), ingredients);
    }
    displayIngredient.add(input);
  }

  private void removeIngredient(String type) {
    FixedStack<Ingredient> ingredients = heldItems.get(type);
    ingredients.pop();
    heldItems.replace(type, ingredients);
    displayIngredient.pop();
  }

  /**
   * The constructor method for the class
   *
   * @param id                  The unique identifier of the station
   * @param textureRegion       The rectangular area of the texture
   * @param stationUIController The controller from which we can get show and hide
   *                            the action
   *                            buttons belonging to the station
   * @param alignment           Dictates where the action buttons are shown
   * @param textureManager      The controller from which we can get information
   *                            on what texture
   *                            each ingredient should have
   * @param customerManager     The controller from which we can get information
   *                            on what food needs to be served
   */
  public RecipeStation(int id, TextureRegion textureRegion, StationUIController stationUIController,
      ActionAlignment alignment, FoodTextureManager textureManager) {
    super(id, textureRegion, stationUIController, alignment);
    this.textureManager = textureManager;
  }

  @Override
  public void reset() {
    heldItems.clear();
    displayIngredient.clear();

    completedRecipe = null;
    super.reset();
  }

  /**
   * Obtains the actions that can be currently performed depending on the states
   * of the station
   * itself and the selected chef
   *
   * @return actionTypes - the list of actions the station can currently perform.
   */
  @Override
  public List<ActionType> getActionTypes() {
    LinkedList<ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef != null) {
      if (!nearbyChef.getStack().isEmpty()) {
        Holdable item = nearbyChef.getStack().peek();
        if (item instanceof Ingredient) {
          Ingredient checkItem = (Ingredient) item;
          if (checkItem.getChopped() || checkItem.getCooked() || checkItem.getGrilled() || checkItem.getType() == "bun"
              || checkItem.getType() == "dough") {
            // If a chef is nearby and is carrying at least one ingredient
            // and the top ingredient is cooked, chopped or a bun then display the action
            actionTypes.add(ActionType.PLACE_INGREDIENT);
          }
        }
      }
      if (completedRecipe == null) {
        for (ActionType makeAction : makeActions.keySet()) {
          boolean canMake = true;
          for (String ingredientType : makeActions.get(makeAction)) {
            canMake = canMake && heldItems.containsKey(ingredientType);
          }
          if (canMake) {
            actionTypes.add(makeAction);
          }
        }
      } else {
        actionTypes.add(ActionType.GRAB_INGREDIENT);
      }
    }
    return actionTypes;
  }

  /**
   * Given an action, the station should attempt to do that action based on the
   * chef that is nearby
   * or what ingredient(s) are currently on the station.
   *
   * @param action the action that needs to be done by this station if it can.
   */
  @Override
  public void doStationAction(ActionType action) {
    switch (action) {
      case PLACE_INGREDIENT:
        if (!(nearbyChef.getStack().peek() instanceof Ingredient)) {
          return;
        }
        this.placeIngredient(nearbyChef.popIngredient());

        break;
      case MAKE_BURGER:
      case MAKE_SALAD:
      case MAKE_JACKET:
        for (String foodType : makeActions.get(action)) {
          this.removeIngredient(foodType);
        }
        completedRecipe = Recipe.fromString(action.toString(), textureManager);
        break;

      case ASSEMBLE_PIZZA:
        for (String foodType : makeActions.get(action)) {
          this.removeIngredient(foodType);
        }
        completedRecipe = new UncookedPizza(textureManager);
        break;
      case GRAB_INGREDIENT:
        if (nearbyChef.canGrabIngredient()) {
          nearbyChef.grabItem(completedRecipe);
          completedRecipe = null;
        }
        break;
    }
    uiController.showActions(this, getActionTypes());
  }

  /**
   * Displays ingredients that have been placed on the station
   *
   * @param batch       Used to display a 2D texture
   * @param parentAlpha The parent alpha, to be multiplied with this actor's
   *                    alpha, allowing the parent's alpha to affect all
   *                    children.
   */
  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (displayIngredient.size() != 0) {
      drawFoodTexture(batch, displayIngredient.peek().getTexture());
    }
    if (completedRecipe != null) {
      drawFoodTexture(batch, completedRecipe.getTexture());
    }
  }

}
