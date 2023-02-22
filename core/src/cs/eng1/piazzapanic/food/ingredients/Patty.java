package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Cookable;
import cs.eng1.piazzapanic.food.interfaces.Holdable;

public class Patty extends Ingredient implements Cookable {

  private boolean halfCooked = false;
  private boolean flipped = false;

  private float cookingAccumulator = 0f;
  private float cookingStepTime = 2f;

  public Patty(FoodTextureManager textureManager) {
    super("patty", textureManager);
  }

  public void setHalfCooked() {
    halfCooked = true;
  }

  public boolean getIsHalfCooked() {
    return halfCooked;
  }

  /**
   * Get the texture based on whether the patty has been cooked.
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

  @Override
  public boolean cookingTick(float delta) {
    cookingAccumulator += delta;

    if (cookingAccumulator >= cookingStepTime) {
      if (!getHalfCooked()) {
        halfCooked = true;
      } else if (flipped) {
        isCooked = true;
      }
      return true;
    }
    return false;
  }

  @Override
  public boolean getHalfCooked() {
    return halfCooked;
  }

  @Override
  public boolean getCooked() {
    return isCooked;
  }

  @Override
  public Holdable getCookingResult() {
    return this;
  }

  @Override
  public boolean cookingStepComplete() {
    return (cookingAccumulator >= cookingStepTime);
  }

  @Override
  public float getCookingProgress() {
    return (cookingAccumulator / cookingStepTime) * 100f;
  }

  @Override
  public void flip() {
    flipped = true;
    cookingAccumulator = 0;
  }
}
