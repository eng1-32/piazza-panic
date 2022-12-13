package cs.eng1.piazzapanic.ingredients;

public class Ingredient {
  private final String type;

  public Ingredient(String type) {
    this.type = type;
  }

  public static Ingredient fromString(String ingredientName) {
    switch (ingredientName) {
      case "patty":
        return new Patty();
      case "tomato":
        return new Tomato();
      case "lettuce":
        return new Lettuce();
      case "bun":
        return new Bun();
      default:
        return null;
    }
  }
  public static Ingredient[] arrayFromString(String csvIngredientNames) {
    String[] ingredientNames = csvIngredientNames.split(",");
    Ingredient[] ingredients = new Ingredient[ingredientNames.length];
    for (int i = 0; i < ingredientNames.length; i++) {
      ingredients[i] = new Ingredient(ingredientNames[i]);
    }
    return ingredients;
  }

  public String getType() {
    return type;
  }

  public boolean doAction(IngredientAction action) {
    // TODO: work out how to integrate this with time
    return false;
  }
}
