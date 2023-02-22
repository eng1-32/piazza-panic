package cs.eng1.piazzapanic.food.ingredients;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Holdable;

public class Potato extends BasicCookable {

    public Potato(FoodTextureManager textureManager) {
        super("potato", textureManager);
    }


    public Holdable getCookingResult() {
        return this;
    }
}
