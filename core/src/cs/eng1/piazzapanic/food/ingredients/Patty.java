package cs.eng1.piazzapanic.food.ingredients;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Holdable;

public class Patty extends BasicGrillable {

  public Patty(FoodTextureManager textureManager) {
    super("patty", textureManager);
  }

  @Override
  public Holdable getGrillResult() {
    return this;
  }
}
