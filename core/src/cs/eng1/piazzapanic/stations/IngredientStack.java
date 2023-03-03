package cs.eng1.piazzapanic.stations;

import java.util.HashMap;
import java.util.Map;

import cs.eng1.piazzapanic.chef.FixedStack;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;

public class IngredientStack {

    private int maxStackSize;

    private Map<String, FixedStack<Ingredient>> map = new HashMap<String, FixedStack<Ingredient>>();

    public IngredientStack(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public Ingredient removeIngredient(String type) {
        if (map.containsKey(type)) {
            FixedStack<Ingredient> stack = map.get(type);
            if (stack.size() > 0) {
                Ingredient ingredient = stack.pop();
                if (stack.size() == 0) {
                    map.remove(type);
                }
                return ingredient;
            }
        }
        return null;
    }

    public void addIngredient(String type, Ingredient ingredient) {
        if (!map.containsKey(type)) {
            FixedStack<Ingredient> newStack = new FixedStack<Ingredient>(maxStackSize);
            newStack.add(ingredient);
            map.put(type, newStack);
        } else {
            map.get(type).add(ingredient);
        }
    }

    public boolean contains(String type) {
        if (!map.containsKey(type)) {
            return false;
        }

        return map.get(type).size() > 0;
    }

    public void reset() {
        map.clear();
    }

}
