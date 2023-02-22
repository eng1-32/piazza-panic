package cs.eng1.piazzapanic.food.ingredients;

import com.badlogic.gdx.graphics.Texture;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.interfaces.Choppable;
import cs.eng1.piazzapanic.food.interfaces.Holdable;

public abstract class BasicChoppable extends Ingredient implements Choppable {

    private float accumulator = 0f;
    private float chopTime = 2f;

    public BasicChoppable(String type, FoodTextureManager textureManager) {
        super(type, textureManager);
    }

    @Override
    public boolean choppingTick(float delta) {
        accumulator += delta;

        if (accumulator >= chopTime) {
            chopped = true;
        }
        return chopped;
    }

    @Override
    public float getChoppingProgress() {
        return (accumulator / chopTime) * 100f;
    }

    @Override
    public Holdable getChoppingResult() {
        return this;
    }

    @Override
    public boolean getChopped() {
        return chopped;
    }

    /**
     * Get the texture based on whether the lettuce has been chopped.
     *
     * @return the texture to display.
     */
    @Override
    public Texture getTexture() {
        String name = getType() + "_";
        if (chopped) {
            name += "chopped";
        } else {
            name += "raw";
        }
        return textureManager.getTexture(name);
    }
}
