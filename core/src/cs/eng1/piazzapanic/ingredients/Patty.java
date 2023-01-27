package cs.eng1.piazzapanic.ingredients;

public class Patty extends Ingredient {

  protected boolean halfCooked = false;

  public Patty(IngredientTextureManager textureManager) {
    super("patty", textureManager);
  }

  public void setHalfCooked() {
    halfCooked = true;
  }

  public boolean getHalfCooked() {
    return halfCooked;
  }
}
