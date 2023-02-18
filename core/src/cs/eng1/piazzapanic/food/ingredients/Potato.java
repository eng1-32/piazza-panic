package cs.eng1.piazzapanic.food.ingredients;

import cs.eng1.piazzapanic.food.FoodTextureManager;

public class Potato extends Ingredient {

    private boolean halfCooked = false;

    public Potato(FoodTextureManager textureManager) {
        super("potato", textureManager);
    }

    public void setHalfCooked() {
        halfCooked = true;
    }

    public boolean getIsHalfCooked() {
        return halfCooked;
    }

}
