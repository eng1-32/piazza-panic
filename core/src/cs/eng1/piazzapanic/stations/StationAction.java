package cs.eng1.piazzapanic.stations;

public class StationAction {
  public enum ActionType {
    CHOP_ACTION,
    COOK_ACTION,
    PLACE_INGREDIENT,
    GRAB_INGREDIENT,
  }

  public static String getActionDescription(ActionType actionType) {
    switch (actionType) {
      case CHOP_ACTION:
        return "Chop";
      case COOK_ACTION:
        return "Cook";
      case GRAB_INGREDIENT:
        return "Grab Item";
      case PLACE_INGREDIENT:
        return "Place Item";
      default:
        return "Unknown Action";
    }
  }
}
