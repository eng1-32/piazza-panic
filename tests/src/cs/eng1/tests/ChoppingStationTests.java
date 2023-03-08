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
import cs.eng1.piazzapanic.stations.ChoppingStation;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.ingredients.Lettuce;
import cs.eng1.piazzapanic.food.ingredients.Patty;

@RunWith(GdxTestRunner.class)
public class ChoppingStationTests {

    ChefManager chefManager = new ChefManager(0, null, null);
    Chef chef = new Chef(null, null, chefManager);
    FoodTextureManager textureManager = new FoodTextureManager();
    Lettuce lettuce = new Lettuce(textureManager);
    StationUIController uiController = mock(StationUIController.class);

    @Test
    public void testGetActionTypesNothing() {
        ChoppingStation station = new ChoppingStation(1, null, null, null);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if no chef is nearby",
        actionTypes.isEmpty());
        station.nearbyChef = chef;
        actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef and station have no ingredients",
        actionTypes.isEmpty());
        chef.grabItem(new Patty(textureManager));
        actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef has an ingredient that can't be cooked",
        actionTypes.isEmpty());
    }

    @Test
    public void testGetActionPlaceIngredient(){
        ChoppingStation station = new ChoppingStation(1, null, null, null);
        chef.grabItem(lettuce);
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds PLACE_INGREDIENT to actionTypes when a chef is nearby with a choppable ingredient" ,actionTypes.contains(StationAction.ActionType.PLACE_INGREDIENT));
    }

    @Test
    public void testChopping() {
        ChoppingStation station = new ChoppingStation(1, null, uiController, null);
        station.nearbyChef = chef;
        station.currentIngredient = lettuce;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds CHOP_ACTION to actionTypes when a chef is nearby and an ingredient is in the station" ,actionTypes.contains(StationAction.ActionType.CHOP_ACTION));
        station.doStationAction(StationAction.ActionType.CHOP_ACTION);
        station.act(1);
        station.act(1);
        actionTypes = station.getActionTypes();
        assertTrue("adds GRAB_INGREDIENT to action types when the ingredient is chopped", actionTypes.contains(StationAction.ActionType.GRAB_INGREDIENT));
        station.doStationAction(ActionType.GRAB_INGREDIENT);
        assertTrue("the chef collects a cooked ingredient and the station is empty", lettuce.getChopped() && station.currentIngredient == null && chef.getStack().peek() == lettuce);
    } 


    @Test
    public void testPlaceIngredient() {
        ChoppingStation station = new ChoppingStation(1, null, uiController, null);
        station.nearbyChef = chef;
        chef.grabItem(lettuce);
        station.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertEquals("an ingredient can be placed on the station",lettuce, station.currentIngredient);
    }    

     @Test
    public void testReset() {
        ChoppingStation station = new ChoppingStation(1, null, uiController, null);
        station.currentIngredient = lettuce;
        station.progressVisible = true;
        station.reset();
        assertTrue("Tests the reset method",station.currentIngredient == null && station.progressVisible == false);
    }

}