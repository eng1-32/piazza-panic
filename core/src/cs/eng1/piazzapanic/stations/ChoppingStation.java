package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.Texture;
import cs.eng1.piazzapanic.Ingredient;

public class ChoppingStation extends Station {
    protected Ingredient[] validIngredients;
    public ChoppingStation(Texture image, float[] position, Ingredient[] Ingredients){
        super(image, position);
        validIngredients = Ingredients; //A list of the ingredients that can be used by this station.
    }
}
