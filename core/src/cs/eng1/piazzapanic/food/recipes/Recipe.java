package cs.eng1.piazzapanic.food.recipes;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;

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
}
