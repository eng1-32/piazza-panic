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

    FoodTextureManager textureManager = new FoodTextureManager();

    @Test
    public void testFromString() {
        Ingredient ingredient = Ingredient.fromString("patty",  textureManager);
        assertNotNull(ingredient);
        assertEquals(Patty.class, ingredient.getClass());
    }
    @Test
    public void testIsCooked() {
        Ingredient ingredient = Ingredient.fromString("patty", textureManager);
        assertFalse(ingredient.getCooked());

        ingredient.setIsCooked(true);
        assertTrue(ingredient.getCooked());

        ingredient.setIsCooked(false);
        assertFalse(ingredient.getCooked());
    }
    @Test
    public void testIsChopped() {
        Ingredient ingredient = Ingredient.fromString("lettuce", textureManager);
        assertFalse(ingredient.getChopped());

        ingredient.setChopped(true);
        assertTrue(ingredient.getChopped());

        ingredient.setChopped(false);
        assertFalse(ingredient.getChopped());
    }
    @Test
    public void testIsGrilled() {
        Ingredient ingredient = Ingredient.fromString("patty", textureManager);
        assertFalse(ingredient.getGrilled());

        ingredient.setIsGrilled(true);
        assertTrue(ingredient.getGrilled());

        ingredient.setIsGrilled(false);
        assertFalse(ingredient.getGrilled());
    }
    @Test
    public void testToString() {
        Ingredient ingredient = Ingredient.fromString("patty", textureManager);
        assertEquals("patty_raw", ingredient.toString());
    }


}

