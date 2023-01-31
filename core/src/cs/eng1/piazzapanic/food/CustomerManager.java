package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.utils.Queue;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.ui.UIOverlay;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

  public void init(FoodTextureManager textureManager) {
    Recipe[] possibleRecipes = new Recipe[]{new Burger(textureManager), new Salad(textureManager)};

    // Salad, Burger, Burger, Salad, Burger. This can be replaced by randomly selecting from
    // possibleRecipes or by using another scenario
    int[] recipeIndices = new int[]{1, 0, 0, 1, 0};
    for (int recipeIndex : recipeIndices) {
      customerOrders.addLast(possibleRecipes[recipeIndex]);
    }
  }

  public boolean checkRecipe(Recipe recipe) {
    if (currentOrder == null) {
      return false;
    }
    return recipe.getType() == currentOrder.getType();
  }

  public void nextRecipe() {
    if (customerOrders.isEmpty()) {
      currentOrder = null;
    } else {
      currentOrder = customerOrders.removeFirst();
    }
    notifyRecipeStations();
    overlay.updateRecipeUI(currentOrder);
    if (currentOrder == null) {
      overlay.finishGameUI();
    }
  }

  private void notifyRecipeStations() {
    for (RecipeStation recipeStation : recipeStations) {
      recipeStation.updateOrderActions();
    }
  }

  public void addRecipeStation(RecipeStation station) {
    recipeStations.add(station);
  }
}
