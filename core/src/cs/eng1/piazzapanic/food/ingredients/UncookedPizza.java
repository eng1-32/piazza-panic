package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Cookable;
import cs.eng1.piazzapanic.food.interfaces.Holdable;
import cs.eng1.piazzapanic.food.recipes.Pizza;

public class UncookedPizza extends Ingredient implements Cookable {

    private boolean halfCooked = false;
    private boolean cooked = false;
    private boolean flipped = false;

    private float cookingAccumulator = 0f;
    private float cookingStepTime = 2f;

    public UncookedPizza(FoodTextureManager textureManager) {
        super("uncooked_pizza", textureManager);
    }

    public boolean getHalfCooked() {
        return halfCooked;
    }

    public boolean getCooked() {
        return cooked;
    }

    public boolean cookingTick(float delta) {
        cookingAccumulator += delta;

        if (cookingAccumulator >= cookingStepTime) {
            if (!getHalfCooked()) {
                halfCooked = true;
            } else if (flipped) {
                cooked = true;
            }
            return true;
        }
        return false;

    }

    public void flip() {
        cookingAccumulator = 0;
        flipped = true;
    }

    public boolean cookingStepComplete() {
        return (cookingAccumulator >= cookingStepTime);
    }

    public float getCookingProgress() {
        return (cookingAccumulator / cookingStepTime) * 100f;
    }

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
