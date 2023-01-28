package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.utils.Queue;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.RecipeStation;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CustomerManager {

  private final Queue<Recipe> customerOrders;
  private Recipe currentOrder;
  private final List<RecipeStation> recipeStations;

  public CustomerManager(int numCustomers, FoodTextureManager textureManager) {
    this.recipeStations = new LinkedList<>();
    Recipe[] possibleRecipes = new Recipe[]{new Burger(textureManager), new Salad(textureManager)};
    customerOrders = new Queue<>(numCustomers);
    for (int i = 0; i < numCustomers; i++) {
      Random rnd = new Random();
      customerOrders.addLast(possibleRecipes[rnd.nextInt(possibleRecipes.length)]);
    }
    currentOrder = customerOrders.removeFirst();
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
