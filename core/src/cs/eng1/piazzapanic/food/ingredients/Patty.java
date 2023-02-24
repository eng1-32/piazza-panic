package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Holdable;

public class Patty extends BasicGrillable {

  public Patty(FoodTextureManager textureManager) {
    super("patty", textureManager);
  }

  /**
   * Get the texture based on whether the patty has been cooked.
   *
   * @return the texture to display.
   */
  @Override
  public Texture getTexture() {
    String name = getType() + "_";
    if (grilled) {
      name += "grilled";
    } else {
      name += "raw";
    }
    return textureManager.getTexture(name);
  }

  @Override
  public Holdable getGrillResult() {
    return this;
  }
}
