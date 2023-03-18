package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;

public class PizzaBase extends Ingredient {

  public PizzaBase(FoodTextureManager textureManager) {
    super("pizza_base", textureManager);
  }

  /**
   * Get the texture based on whether the pizza base has been cooked.
   *
   * @return the texture to display.
   */
  @Override
  public Texture getTexture() {
    String name = getType() + "_";
    if (isCooked) {
      name += "cooked";
    } else {
      name += "raw";
    }
    return textureManager.getTexture(name);
  }
}
