package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;

import java.util.LinkedList;
import java.util.List;

public class IngredientStation extends Station {

  protected final Ingredient ingredientDispensed;

  public IngredientStation(int id, TextureRegion image, StationUIController uiController,
      StationActionUI.ActionAlignment alignment, Ingredient ingredient) {
    super(id, image, uiController, alignment);
    ingredientDispensed = ingredient; //What ingredient the station will give to the player.
  }

  @Override
  public List<StationAction.ActionType> getActionTypes() {
    LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
    if (nearbyChef == null) {
      return actionTypes;
    }
    if (nearbyChef.canGrabIngredient()) {
      actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
    }
    return actionTypes;
  }

  @Override
  public void doStationAction(StationAction.ActionType action) {
    if (action == StationAction.ActionType.GRAB_INGREDIENT) {
      if (this.nearbyChef != null && nearbyChef.canGrabIngredient()) {
        nearbyChef.grabIngredient(Ingredient.fromString(ingredientDispensed.getType(),
            ingredientDispensed.getTextureManager()));
      }
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    if (ingredientDispensed != null) {
      drawFoodTexture(batch, ingredientDispensed.getTexture());
    }
  }
}
