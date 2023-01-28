package cs.eng1.piazzapanic.ingredients;

import com.badlogic.gdx.graphics.Texture;

public class Lettuce extends Ingredient {

  public Lettuce(IngredientTextureManager textureManager) {
    super("lettuce", textureManager);
  }

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
