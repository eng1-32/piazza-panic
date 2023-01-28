package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ingredients.Burger;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.ingredients.Salad;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class RecipeStation extends Station {

  protected  int bunCount = 0;
  protected  int pattyCount = 0;
  protected  int lettuceCount = 0;
  protected  int tomatoCount = 0;
  public RecipeStation(int id, TextureRegion textureRegion, StationUIController stationUIController,
      ActionAlignment alignment) {
    super(id, textureRegion, stationUIController, alignment);
  }

  @Override
  public List<StationAction.ActionType> getActionTypes() {
    LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef != null){
      if(!nearbyChef.getStack().isEmpty()){
        Ingredient checkItem = nearbyChef.getStack().peek();
        if(checkItem.getChopped() || checkItem.getCooked() || checkItem.getType() == "bun"){
          actionTypes.add(StationAction.ActionType.PLACE_INGREDIENT);
        }
      }
      if(pattyCount >= 1 && bunCount >= 1 && nearbyChef.getStack().hasSpace()){
        actionTypes.add(StationAction.ActionType.MAKE_BURGER);
      }
      if(tomatoCount >= 1 && lettuceCount >= 1 && nearbyChef.getStack().hasSpace()){
        actionTypes.add(StationAction.ActionType.MAKE_SALAD);
      }
    }
    return actionTypes;
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    switch (action){
      case PLACE_INGREDIENT:
        Ingredient topItem = nearbyChef.getStack().peek();
        switch (topItem.getType()){
          case "patty":
            nearbyChef.getStack().pop();
            pattyCount += 1;
            break;
          case "tomato":
            nearbyChef.getStack().pop();
            tomatoCount += 1;
            break;
          case "lettuce":
            nearbyChef.getStack().pop();
            lettuceCount += 1;
            break;
          case "bun":
            nearbyChef.getStack().pop();
            bunCount += 1;
            break;
        }

        break;
      case MAKE_BURGER:
        Ingredient newBurger = new Burger();
        nearbyChef.grabIngredient(newBurger);
        pattyCount -= 1;
        bunCount -= 2;
        break;

      case MAKE_SALAD:
        Ingredient newSalad = new Salad();
        nearbyChef.grabIngredient(newSalad);
        tomatoCount -= 1;
        lettuceCount -= 1;
        break;

    }
  }
}
