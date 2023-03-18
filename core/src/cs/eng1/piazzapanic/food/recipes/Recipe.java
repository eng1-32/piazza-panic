package cs.eng1.piazzapanic.food.recipes;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import java.util.LinkedList;
import java.util.List;

/**
 * The Recipe class is the parent class of the food classes that
 * dictates what ingredients are needed to make them
 */
public class Recipe {

  private final FoodTextureManager textureManager;
  private final String type;

  /**
   * The constructor method for the class
   *
   * @param type            The food object that inherits the class
   * @param textureManager  The controller from which we can get information on what texture
   *                        each food object should have
   */
  public Recipe(String type, FoodTextureManager textureManager) {
    this.type = type;
    this.textureManager = textureManager;
  }

  public Texture getTexture() {
    return textureManager.getTexture(type);
  }

  public String getType() {
    return type;
  }

  /**
   * Gets what ingredients are needed to make the food item
   *
   * @return The list of ingredients that are needed
   */
  public List<String> getRecipeIngredients() {
    LinkedList<String> ingredientTypes = new LinkedList<>();
    switch (getType()) {
      case "humborge":
        ingredientTypes.add("bun");
        ingredientTypes.add("patty_cooked");
        break;
      case "salad":
        ingredientTypes.add("tomato_chopped");
        ingredientTypes.add("lettuce_chopped");
        break;
    }
    return ingredientTypes;
  }

  public FoodTextureManager getTextureManager() {
    return textureManager;
  }
}
