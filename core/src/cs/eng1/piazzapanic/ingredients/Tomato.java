package cs.eng1.piazzapanic.ingredients;

import com.badlogic.gdx.graphics.Texture;

public class Tomato extends Ingredient {

  public Tomato(IngredientTextureManager textureManager) {
    super("tomato", textureManager);
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
