package cs.eng1.piazzapanic.food.ingredients;

import cs.eng1.piazzapanic.food.FoodTextureManager;

public class UncookedPizza extends Ingredient {

    private boolean halfCooked = false;

    public UncookedPizza(FoodTextureManager textureManager) {
        super("uncooked pizza", textureManager);
    }

    public void setHalfCooked() {
        halfCooked = true;
    }

    public boolean getIsHalfCooked() {
        return halfCooked;
    }

}
