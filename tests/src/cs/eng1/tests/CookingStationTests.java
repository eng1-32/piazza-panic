package cs.eng1.tests;

import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;
import cs.eng1.piazzapanic.ui.StationUIController;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.ingredients.Potato;
import cs.eng1.piazzapanic.food.recipes.Salad;

@RunWith(GdxTestRunner.class)
public class CookingStationTests {

    ChefManager chefManager = new ChefManager(0, null, null);
    FoodTextureManager textureManager = new FoodTextureManager();
    Potato potato = new Potato(textureManager);
    Chef chef = new Chef(null, null, chefManager);
    StationUIController uiController = mock(StationUIController.class);

    @Test
    public void testGetActionTypesNothing() {
        CookingStation station = new CookingStation(1,null,null,null);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if no chef is nearby" ,actionTypes.isEmpty());
        station.nearbyChef = chef;
        actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef and station have no ingredients" ,actionTypes.isEmpty());
        chef.grabItem(new Salad(textureManager));
        actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef has an ingredient that can't be cooked" ,actionTypes.isEmpty());
    }

    @Test
    public void testGetActionPlaceIngredient(){
        CookingStation station = new CookingStation(1, null, null, null);
        chef.grabItem(potato);
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds PLACE_INGREDIENT to actionTypes when a chef is nearby with a cookable ingredient" ,actionTypes.contains(StationAction.ActionType.PLACE_INGREDIENT));
    }

    @Test
    public void testCooking() {
        CookingStation station = new CookingStation(1, null, uiController, null);
        station.nearbyChef = chef;
        station.currentIngredient = potato;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds COOK_ACTION to actionTypes when a chef is nearby and an ingredient is in the station" ,actionTypes.contains(StationAction.ActionType.COOK_ACTION));
        station.doStationAction(StationAction.ActionType.COOK_ACTION);
        station.act(1);
        station.act(1);
        actionTypes = station.getActionTypes();
        assertTrue("adds FLIP_ACTION to action types when the ingredient is ready to be flipped", actionTypes.contains(StationAction.ActionType.FLIP_ACTION));
        station.doStationAction(StationAction.ActionType.FLIP_ACTION);
        station.act(2);
        actionTypes = station.getActionTypes();
        assertTrue("adds GRAB_INGREDIENT to action types when the ingredient is fully cooked", actionTypes.contains(StationAction.ActionType.GRAB_INGREDIENT));
        station.doStationAction(ActionType.GRAB_INGREDIENT);
        assertTrue("the chef collects a cooked ingredient and the station is empty", potato.getCooked() && station.currentIngredient == null && chef.getStack().peek() == potato);
    } 

    @Test
    public void testPlaceIngredient() {
        CookingStation station = new CookingStation(1, null, uiController, null);
        station.nearbyChef = chef;
        chef.grabItem(potato);
        station.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertEquals("an ingredient can be placed on the station",potato, station.currentIngredient);
    }

    @Test
    public void testReset() {
        CookingStation station = new CookingStation(1, null, uiController, null);
        station.currentIngredient = potato;
        station.progressVisible = true;
        station.reset();
        assertTrue("Tests the reset method",station.currentIngredient == null && station.progressVisible == false);
    }
}
