package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import cs.eng1.piazzapanic.Ingredient;


public class CookingStation extends Station {
  protected Ingredient[] validIngredients;
  protected Ingredient ingredientCooking;
  protected long timeCooked;

  public CookingStation(TextureRegion image, Ingredient[] Ingredients) {
    super(image);
    validIngredients = Ingredients; //A list of the ingredients that can be used by this station.
  }

  public void stationInteract() {
    //TODO: Change if statement to check for a flipped patty or a
    // uncooked patty once ingredients class is implemented
    if (this.ingredientCooking == ingredientCooking) {
      collectPatty();
    } else if (this.ingredientCooking == ingredientCooking) {
      flipPatty();
    } else {
      //cookFood(top ingredient in food stack);
    }
  }

  public void cookFood(Ingredient foodItem) {
    if (this.inUse || !(correctIngredient(foodItem))) {
      return; //If the station is in use or the wrong ingredient is used
      //then nothing happens.
    }
    this.inUse = true;
    timeCooked = TimeUtils.millis();
    ingredientCooking = foodItem;

  }

  public void flipPatty() {
    if (TimeUtils.timeSinceMillis(timeCooked) >= 10) {
      //ingredientCooking = new Ingredient(flippedPatty);
    }
  }

  public void collectPatty() {
    if (TimeUtils.timeSinceMillis(timeCooked) >= 10) {
      //ingredientCooking = new Ingredient(flippedPatty);
      //TODO: Add cooked patty back to chef's ingredient stack
      this.inUse = false;
      this.ingredientCooking = null;
    }
  }

  private Boolean correctIngredient(Ingredient check) {
    for (Ingredient item : this.validIngredients) {
      if (item == check) {
        return true;
      }
    }
    return false;
  }
}
