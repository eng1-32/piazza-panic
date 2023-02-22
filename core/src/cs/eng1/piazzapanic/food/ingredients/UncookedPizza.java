package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Holdable;
import cs.eng1.piazzapanic.food.recipes.Pizza;

public class UncookedPizza extends BasicCookable {

    public UncookedPizza(FoodTextureManager textureManager) {
        super("uncooked_pizza", textureManager);
    }

    @Override
    public Holdable getCookingResult() {
        if (cooked) {
            return new Pizza(textureManager);
        } else {
            return null;
        }
    }

    @Override
    public Texture getTexture() {
        String name = "";
        if (!cooked) {
            name = "uncooked_pizza";
        } else {
            name = "pizza";
        }

        return textureManager.getTexture(name);
    }

}
