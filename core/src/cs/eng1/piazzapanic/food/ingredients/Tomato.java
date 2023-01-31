package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;

public class Tomato extends Ingredient {

  public Tomato(FoodTextureManager textureManager) {
    super("tomato", textureManager);
  }

  /**
   * Get the texture based on whether the tomato has been chopped.
   *
   * @return the texture to display.
   */
  @Override
  public Texture getTexture() {
    String name = getType() + "_";
    if (isChopped) {
      name += "chopped";
    } else {
      name += "raw";
    }
    return textureManager.getTexture(name);
  }
}
