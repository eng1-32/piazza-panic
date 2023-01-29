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
  private final int numCustomers;
  private Recipe currentOrder;
  private final List<RecipeStation> recipeStations;
  private final UIOverlay overlay;

  public CustomerManager(int numCustomers, UIOverlay overlay) {
    this.overlay = overlay;
    this.recipeStations = new LinkedList<>();
    this.numCustomers = numCustomers;
    customerOrders = new Queue<>(numCustomers);
  }

  public void init(FoodTextureManager textureManager) {
    Recipe[] possibleRecipes = new Recipe[]{new Burger(textureManager), new Salad(textureManager)};
    for (int i = 0; i < numCustomers; i++) {
      Random rnd = new Random();
      customerOrders.addLast(possibleRecipes[rnd.nextInt(possibleRecipes.length)]);
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
