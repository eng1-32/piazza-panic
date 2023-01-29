package cs.eng1.piazzapanic.food.recipes;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Recipe {

  private final FoodTextureManager textureManager;
  private final String type;

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

  public List<String> getRecipeIngredients() {
    LinkedList<String> ingredientTypes = new LinkedList<>();
    switch (getType()) {
      case "burger":
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
