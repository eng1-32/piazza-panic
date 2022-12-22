package cs.eng1.piazzapanic.ingredients;

public class IngredientAction {

  public enum ActionType {
    COOK,
    FLIP,
    CHOP,
  }

  Ingredient newIngredientType;
  float timeToComplete;
  ActionType actionType;

  public IngredientAction(Ingredient newIngredientType, float timeToComplete,
      ActionType actionType) {
    this.newIngredientType = newIngredientType;
    this.timeToComplete = timeToComplete;
    this.actionType = actionType;
  }
}
