package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ingredients.Ingredient;

public class ChoppingStation extends Station {
    protected Ingredient[] validIngredients;
    public ChoppingStation(TextureRegion image, Ingredient[] ingredients){
        super(image);
        validIngredients = ingredients; //A list of the ingredients that can be used by this station.
    }
}
