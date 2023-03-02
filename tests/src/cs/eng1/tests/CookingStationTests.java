package cs.eng1.tests;

import cs.eng1.piazzapanic.stations.StationAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.util.List;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.ingredients.BasicCookable;
import cs.eng1.piazzapanic.food.ingredients.Patty;
import cs.eng1.piazzapanic.food.ingredients.Potato;
import cs.eng1.piazzapanic.food.recipes.Salad;

@RunWith(GdxTestRunner.class)
public class CookingStationTests {

    ChefManager chefManager = new ChefManager(0, null, null);
    FoodTextureManager textureManager = new FoodTextureManager();

    @Test
    public void testGetActionTypesNoChef() {
        CookingStation station = new CookingStation(1,null,null,null);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if no chef is nearby" ,actionTypes.isEmpty());
    }

    @Test
    public void testGetActionTypeNoIngredient() {
        CookingStation station = new CookingStation(1, null, null, null);
        Chef chef = new Chef(null, null, null);
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef has no ingredients" ,actionTypes.isEmpty());
    }
    
    @Test
    public void testGetActionTypeWrongIngredient(){
        CookingStation station = new CookingStation(1, null, null, null);
        Chef chef = new Chef(null, null, chefManager);
        chef.grabItem(new Salad(textureManager));
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef has an ingredient that can't be cooked" ,actionTypes.isEmpty());
    }

    @Test
    public void testGetActionPlaceIngredient(){
        CookingStation station = new CookingStation(1, null, null, null);
        Chef chef = new Chef(null, null, chefManager);
        chef.grabItem(new Potato(textureManager));
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds PLACE_INGREDIENT to actionTypes when a chef is nearby with a cookable ingredient" ,actionTypes.contains(StationAction.ActionType.PLACE_INGREDIENT));
    }

    @Test
    public void testGetActionCookAction(){
        //do this next
    }

    @Test
    public void testDoCookAction() {
        CookingStation cookingStation = new CookingStation(1, null, null, null);
        cookingStation.doStationAction(StationAction.ActionType.COOK_ACTION);

        assertTrue(cookingStation.inUse);
        assertTrue(cookingStation.progressVisible);
    } 
}
