package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;
import com.badlogic.gdx.utils.Queue;

import cs.eng1.piazzapanic.food.recipes.BakedPotato;
import cs.eng1.piazzapanic.food.recipes.Burger;
import cs.eng1.piazzapanic.food.recipes.Pizza;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.recipes.Salad;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.screens.HomeScreen;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.ui.UIOverlay;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomerManager {

  private final Queue<Recipe> customerOrders;
  private Recipe currentOrder;

  private final List<RecipeStation> recipeStations;
  private final UIOverlay overlay;
  public static ArrayList<Integer> recipeIndices = new ArrayList<Integer>();

  public CustomerManager(UIOverlay overlay) {
    this.overlay = overlay;
    this.recipeStations = new LinkedList<>();
    customerOrders = new Queue<>();
  }

  /**
   * Reset the scenario to the default scenario.
   *
   * @param textureManager The manager of food textures that can be passed to the
   *                       recipes
   */
  public void init(FoodTextureManager textureManager) {
    Recipe[] possibleRecipes = new Recipe[] { new Burger(textureManager), new Salad(textureManager),
        new BakedPotato(textureManager), new Pizza(textureManager) };

    customerOrders.clear();
    recipeIndices.clear();
    if (HomeScreen.mode == 0) {
      for (int i = 0; i < 5; i++) {
        recipeIndices.add((int) (Math.random() * ((4))));
      }
      for (int recipeIndex : recipeIndices) {
        customerOrders.addLast(possibleRecipes[recipeIndex]);
      }
    }
    if (HomeScreen.mode == 1) {
      for (int i = 0; i < 1; i++) {
        recipeIndices.add((int) (Math.random() * ((4))));
      }
      for (int recipeIndex : recipeIndices) {
        customerOrders.addLast(possibleRecipes[recipeIndex]);
      }
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
   * Complete the current order nad move on to the next one. Then update the UI.
   * If all the recipes
   * are completed, then show the winning UI.
   */
  public void nextRecipe(FoodTextureManager textureManager) {

    if (customerOrders.isEmpty()) {
      if (HomeScreen.mode == 1) {

        recipeIndices.clear();
        customerOrders.clear();
        Recipe[] possibleRecipes1 = new Recipe[] { new Burger(textureManager), new Salad(textureManager),
            new BakedPotato(textureManager), new Pizza(textureManager) };
        for (int j = 0; j < 1; j++) {
          recipeIndices.add((int) (Math.random() * ((4))));
        }
        for (int recipeIndex1 : recipeIndices) {
          customerOrders.addLast(possibleRecipes1[recipeIndex1]);
        }
        currentOrder = customerOrders.removeFirst();

      } else {

        currentOrder = null;

        overlay.updateRecipeCounter(0);
      }
    } else {
      if (GameScreen.customerTime > 60f) {
        UIOverlay.lives.takeLives();
        if (UIOverlay.lives.getLives() == 0) {
          overlay.loseGameUI();

        }
      }
      GameScreen.customerTime = 0;
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
   * If one recipe station has been updated, let all the other ones know that
   * there is a new recipe
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
