package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.Ingredient;

public class IngredientStation extends Station {

    protected Ingredient ingredientDispensed;
    public IngredientStation(TextureRegion image, Ingredient ingredient){
        super(image);
        ingredientDispensed = ingredient; //What ingredient the station will give to the player.
    }

}
