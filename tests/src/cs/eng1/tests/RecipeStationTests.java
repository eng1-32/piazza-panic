package cs.eng1.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;

import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.ingredients.Lettuce;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;

@RunWith(GdxTestRunner.class)
public class RecipeStationTests {
    public ChefManager chefManager = new ChefManager(0, null, null);

    @Test
    public void testGetActionTypesNoChef() {
        RecipeStation station = new RecipeStation(1, null, null, null, null);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("Nothing is added to action types if no chef is nearby.", actionTypes.isEmpty());
    }

    @Test
    public void testGetActionTypesWithNoIngredient() {
        RecipeStation station = new RecipeStation(1, null, null, null, null);
        Chef chef = new Chef(null, null, null);
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("Nothing is added to action types if the chef has nothing in their inventory.",
                actionTypes.isEmpty());
    }

    @Test
    public void testGetActionTypesWithCorrectIngredient() {
        RecipeStation station = new RecipeStation(1, null, null, null, null);
        Chef chef = new Chef(null, null, chefManager);
        Lettuce lettuce = new Lettuce(null);
        station.nearbyChef = chef;
        lettuce.setChopped(true);
        chef.grabItem(lettuce);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("Returns PLACE_INGREDIENT if the chef has a processed ingredient.",
                actionTypes.contains(ActionType.PLACE_INGREDIENT));
    }
}
