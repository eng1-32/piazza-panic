package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.utils.Queue;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.JacketPotato;
import cs.eng1.piazzapanic.food.recipes.Pizza;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.stations.SubmitStation;
import cs.eng1.piazzapanic.ui.UIOverlay;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class CustomerManager {

  private final Queue<Recipe> customerOrders;
  private final List<SubmitStation> recipeStations;
  private final UIOverlay overlay;
  private int totalCustomers;
  private int completedOrders = 0;
  private Recipe[] possibleRecipes;

  private Random random;

  public CustomerManager(UIOverlay overlay, int customers) {
    this.overlay = overlay;
    this.recipeStations = new LinkedList<>();
    customerOrders = new Queue<>();
    totalCustomers = customers;
    random = new Random();
  }

  public CustomerManager(UIOverlay overlay, int customers, long seed) {
    this(overlay, customers);
    random.setSeed(seed);
  }

  /**
   * Reset the scenario to the default scenario.
   *
   * @param textureManager The manager of food textures that can be passed to the
   *                       recipes
   */
  public void init(FoodTextureManager textureManager) {
    // Salad, Burger, Burger, Salad, Burger. This can be replaced by randomly
    // selecting from possibleRecipes or by using another scenario
    customerOrders.clear();

    possibleRecipes = new Recipe[] { new Burger(textureManager), new Salad(textureManager), new Pizza(textureManager),
        new JacketPotato(textureManager) };

    generateOrders();
  }

  /**
   * Check to see if the recipe matches the currently requested order.
   *
   * @param recipe The recipe to check against the current order.
   * @return a boolean signifying if the recipe is correct.
   */
  public boolean checkRecipe(Recipe recipe) {
    if (customerOrders.isEmpty()) {
      return false;
    }

    // could be changed to allow entering in any order, allowing you to do later
    // recipes by checking with .contains and then getting first index.
    return recipe.getType().equals(customerOrders.first().getType());
  }

  /**
   * Complete the current order nad move on to the next one. Then update the UI.
   * If all the recipes are completed, then show the winning UI.
   * 
   * With the current implementation, it is possible to have endless mode use the
   * totalCustomers value of 0 without requiring changes
   */
  public void nextRecipe() {
    completedOrders++;
    overlay.updateRecipeCounter(completedOrders);
    if (completedOrders != totalCustomers) {
      customerOrders.removeFirst();
      generateOrders();
    }

    notifySubmitStations();
    // requires updating overlay to allow for multiple orders being displayed at
    // once
    overlay.updateRecipeUI(customerOrders.first());
    if (completedOrders == totalCustomers) {
      overlay.updateRecipeUI(null);
      overlay.finishGameUI();
    }
  }

  /**
   * If one recipe station has been updated, let all the other ones know that
   * there is a new recipe to be built.
   */
  private void notifySubmitStations() {
    for (SubmitStation recipeStation : recipeStations) {
      recipeStation.updateOrderActions();
    }
  }

  public void addStation(SubmitStation station) {
    recipeStations.add(station);
  }

  public void generateOrders() {

    // implement random generation of two or three customers at once here

    customerOrders.addLast(possibleRecipes[random.nextInt(4)]);

  }

  public Recipe getFirstOrder() {
    return customerOrders.first();
  }
}
