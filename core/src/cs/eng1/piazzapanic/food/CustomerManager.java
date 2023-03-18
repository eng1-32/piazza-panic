package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.utils.Queue;
import cs.eng1.piazzapanic.food.recipes.Humborge;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.ui.UIOverlay;
import java.util.LinkedList;
import java.util.List;

public class CustomerManager {

  private final Queue<Recipe> customerOrders;
  private Recipe currentOrder;
  private final List<RecipeStation> recipeStations;
  private final UIOverlay overlay;

  public CustomerManager(UIOverlay overlay) {
    this.overlay = overlay;
    this.recipeStations = new LinkedList<>();
    customerOrders = new Queue<>();
  }

  /**
   * Reset the scenario to the default scenario.
   *
   * @param textureManager The manager of food textures that can be passed to the recipes
   */
  public void init(FoodTextureManager textureManager) {
    Recipe[] possibleRecipes = new Recipe[]{new Humborge(textureManager), new Salad(textureManager)};

    // Salad, Burger, Burger, Salad, Burger. This can be replaced by randomly selecting from
    // possibleRecipes or by using another scenario
    customerOrders.clear();
    int[] recipeIndices = new int[]{1, 0, 0, 1, 0};
    for (int recipeIndex : recipeIndices) {
      customerOrders.addLast(possibleRecipes[recipeIndex]);
    }
  }

  /**
   * Check to see if the recipe matches the currently requested order.
   *
   * @param recipe The recipe to check against the current order.
   * @return a boolean signifying if the recipe is correct.
   */
  public boolean checkRecipe(Recipe recipe) {
    if (currentOrder == null) {
      return false;
    }
    return recipe.getType().equals(currentOrder.getType());
  }

  /**
   * Complete the current order nad move on to the next one. Then update the UI. If all the recipes
   * are completed, then show the winning UI.
   */
  public void nextRecipe() {
    if (customerOrders.isEmpty()) {
      currentOrder = null;
      overlay.updateRecipeCounter(0);
    } else {
      overlay.updateRecipeCounter(customerOrders.size);
      currentOrder = customerOrders.removeFirst();
    }
    notifyRecipeStations();
    overlay.updateRecipeUI(currentOrder);
    if (currentOrder == null) {
      overlay.finishGameUI();
    }
  }

  /**
   * If one recipe station has been updated, let all the other ones know that there is a new recipe
   * to be built.
   */
  private void notifyRecipeStations() {
    for (RecipeStation recipeStation : recipeStations) {
      recipeStation.updateOrderActions();
    }
  }

  public void addRecipeStation(RecipeStation station) {
    recipeStations.add(station);
  }
}
