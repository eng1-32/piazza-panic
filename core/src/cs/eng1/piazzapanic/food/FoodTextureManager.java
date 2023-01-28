package cs.eng1.piazzapanic.food;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class FoodTextureManager {

  private final HashMap<String, Texture> ingredientTextures;
  private final Texture notFoundImage;

  public FoodTextureManager() {
    this.ingredientTextures = new HashMap<>();
    this.ingredientTextures.put("patty_cooked",
        new Texture(Gdx.files.internal("food/original/cooked_patty.png")));
    this.ingredientTextures.put("patty_raw",
        new Texture(Gdx.files.internal("food/original/uncooked_patty.png")));
    this.ingredientTextures.put("lettuce_raw",
        new Texture(Gdx.files.internal("food/glitch/vegetable/lettuce.png")));
    this.ingredientTextures.put("lettuce_chopped",
        new Texture(Gdx.files.internal("food/original/lettuce_chopped.png")));
    this.ingredientTextures.put("tomato_raw",
        new Texture(Gdx.files.internal("food/glitch/fruit/tomato.png")));
    this.ingredientTextures.put("tomato_chopped",
        new Texture(Gdx.files.internal("food/original/tomato_chopped.png")));
    this.ingredientTextures.put("bun",
        new Texture(Gdx.files.internal("food/glitch/misc/bun.png")));
    this.ingredientTextures.put("burger",
        new Texture(Gdx.files.internal("food/glitch/misc/sandwich_burger_04.png")));
    this.ingredientTextures.put("salad",
        new Texture(Gdx.files.internal("food/glitch/misc/salad.png")));
    notFoundImage = new Texture(Gdx.files.internal("badlogic.jpg"));
  }

  public Texture getTexture(String ingredientType) {
    Texture texture = ingredientTextures.get(ingredientType);
    if (texture != null) {
      return texture;
    } else {
      return notFoundImage;
    }
  }
}
