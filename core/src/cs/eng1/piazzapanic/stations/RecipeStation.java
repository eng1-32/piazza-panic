package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.food.recipes.Burger;
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

public class RecipeStation extends Station {

  private final FoodTextureManager textureManager;
  private final CustomerManager customerManager;
  protected int bunCount = 0;
  protected int pattyCount = 0;
  protected int lettuceCount = 0;
  protected int tomatoCount = 0;
  private Recipe completedRecipe = null;

  public RecipeStation(int id, TextureRegion textureRegion, StationUIController stationUIController,
      ActionAlignment alignment, FoodTextureManager textureManager,
      CustomerManager customerManager) {
    super(id, textureRegion, stationUIController, alignment);
    this.textureManager = textureManager;
    this.customerManager = customerManager;
  }

  @Override
  public List<ActionType> getActionTypes() {
    LinkedList<ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef != null) {
      if (!nearbyChef.getStack().isEmpty()) {
        Ingredient checkItem = nearbyChef.getStack().peek();
        if (checkItem.getIsChopped() || checkItem.getIsCooked() || checkItem.getType() == "bun") {
          actionTypes.add(ActionType.PLACE_INGREDIENT);
        }
      }
      if (pattyCount >= 1 && bunCount >= 1 && nearbyChef.getStack().hasSpace()) {
        actionTypes.add(ActionType.MAKE_BURGER);
      }
      if (tomatoCount >= 1 && lettuceCount >= 1 && nearbyChef.getStack().hasSpace()) {
        actionTypes.add(ActionType.MAKE_SALAD);
      }
      if (completedRecipe != null && customerManager.checkRecipe(completedRecipe)) {
        actionTypes.add(ActionType.SUBMIT_ORDER);
      }
    }
    return actionTypes;
  }

  @Override
  public void doStationAction(ActionType action) {
    switch (action) {
      case PLACE_INGREDIENT:
        Ingredient topItem = nearbyChef.getStack().peek();
        switch (topItem.getType()) {
          case "patty":
            nearbyChef.getStack().pop();
            pattyCount += 1;
            break;
          case "tomato":
            nearbyChef.getStack().pop();
            tomatoCount += 1;
            break;
          case "lettuce":
            nearbyChef.getStack().pop();
            lettuceCount += 1;
            break;
          case "bun":
            nearbyChef.getStack().pop();
            bunCount += 1;
            break;
        }

        break;
      case MAKE_BURGER:
        completedRecipe = new Burger(textureManager);
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
}
