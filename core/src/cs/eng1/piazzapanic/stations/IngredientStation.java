package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.Ingredient;

public class IngredientStation extends Station {

    protected Ingredient ingredientDispensed;
    public IngredientStation(Texture image, float[] position, Ingredient ingredient){
        super(image, position);
        ingredientDispensed = ingredient; //What ingredient the station will give to the player.
    }

}
