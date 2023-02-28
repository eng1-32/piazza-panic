package cs.eng1.tests;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Patty;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
@RunWith(GdxTestRunner.class)
public class IngredientTests {

    @Test
    public void testFromString() {
        Ingredient ingredient = Ingredient.fromString("patty",  new FoodTextureManager());
        assertNotNull(ingredient);
        assertEquals(Patty.class, ingredient.getClass());
    }
    @Test
    public void testIsCooked() {
        Ingredient ingredient = Ingredient.fromString("patty", new FoodTextureManager());
        assertFalse(ingredient.getCooked());

        ingredient.setIsCooked(true);
        assertTrue(ingredient.getCooked());

        ingredient.setIsCooked(false);
        assertFalse(ingredient.getCooked());
    }
    @Test
    public void testIsChopped() {
        Ingredient ingredient = Ingredient.fromString("lettuce", new FoodTextureManager());
        assertFalse(ingredient.getChopped());

        ingredient.setIsChopped(true);
        assertTrue(ingredient.getChopped());

        ingredient.setIsChopped(false);
        assertFalse(ingredient.getChopped());
    }
    @Test
    public void testIsGrilled() {
        Ingredient ingredient = Ingredient.fromString("patty", new FoodTextureManager());
        assertFalse(ingredient.getGrilled());

        ingredient.setIsGrilled(true);
        assertTrue(ingredient.getGrilled());

        ingredient.setIsGrilled(false);
        assertFalse(ingredient.getGrilled());
    }
    @Test
    public void testToString() {
        Ingredient ingredient = Ingredient.fromString("bun", new FoodTextureManager());
        assertEquals("bun_raw", ingredient.toString());

        ingredient.setIsCooked(true);
        assertEquals("bun_cooked", ingredient.toString());

        ingredient.setIsCooked(false);
        ingredient.setIsChopped(true);
        assertEquals("bun_chopped", ingredient.toString());

        ingredient.setIsChopped(false);
        ingredient.setIsGrilled(true);
        assertEquals("bun_grilled", ingredient.toString());
    }


}

