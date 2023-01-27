package cs.eng1.piazzapanic.ingredients;

import com.badlogic.gdx.graphics.Texture;

public class Ingredient {

  private final String type;
  protected IngredientTextureManager textureManager;
  protected boolean isCooked = false;
  protected  boolean isChopped = false;

  public Ingredient(String type, IngredientTextureManager textureManager) {
    this.type = type;
    this.textureManager = textureManager;
  }

  /**
   * Initialize an Ingredient based on a string name
   *
   * @param ingredientName the name of the ingredient which can be defined from Tiled
   * @return the Ingredient of the type defined by the input
   */
  public static Ingredient fromString(String ingredientName,
      IngredientTextureManager textureManager) {
    switch (ingredientName) {
      case "patty":
        return new Patty(textureManager);
      case "tomato":
        return new Tomato(textureManager);
      case "lettuce":
        return new Lettuce(textureManager);
      case "bun":
        return new Bun(textureManager);
      default:
        return null;
    }
  }

  /**
   * Initialize an array of ingredients based on the input string.
   *
   * @param csvIngredientNames A string containing a list of ingredient names seperated by commas
   *                           with no whitespace as defined in Tiled
   * @return An array of Ingredient based on the input string
   */
  public static Ingredient[] arrayFromString(String csvIngredientNames,
      IngredientTextureManager textureManager) {
    String[] ingredientNames = csvIngredientNames.split(",");
    Ingredient[] ingredients = new Ingredient[ingredientNames.length];
    for (int i = 0; i < ingredientNames.length; i++) {
      ingredients[i] = fromString(ingredientNames[i], textureManager);
    }
    return ingredients;
  }

  public String getType() {
    return type;
  }

  public Texture getTexture() {
    return textureManager.getTexture(getType(), isCooked, isChopped);
  }

  public void setIsCooked(boolean value) {
    isCooked = value;
  }

  public boolean getIsCooked() {
    return isCooked;
  }

  public void setIsChopped(boolean value) {
    isChopped = value;
  }

  public boolean getIsChopped() {
    return isChopped;
  }

  public IngredientTextureManager getTextureManager() {
    return textureManager;
  }
}
