package cs.eng1.piazzapanic.ingredients;

import com.badlogic.gdx.graphics.Texture;

public class Patty extends Ingredient {

  protected boolean halfCooked = false;

  public Patty(IngredientTextureManager textureManager) {
    super("patty", textureManager);
  }

  public void setHalfCooked() {
    halfCooked = true;
  }

  public boolean getIsHalfCooked() {
    return halfCooked;
  }

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
