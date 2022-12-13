package cs.eng1.piazzapanic;

public class Ingredient {
  private final String type;

  public Ingredient(String type) {
    this.type = type;
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
}
