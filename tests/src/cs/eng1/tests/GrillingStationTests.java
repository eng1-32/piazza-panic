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
import cs.eng1.piazzapanic.stations.GrillingStation;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.ingredients.Patty;
import cs.eng1.piazzapanic.food.recipes.Salad;

@RunWith(GdxTestRunner.class)
public class GrillingStationTests{

    ChefManager chefManager = new ChefManager(0, null, null);
    FoodTextureManager textureManager = new FoodTextureManager();
    Patty patty = new Patty(textureManager);
    Chef chef = new Chef(null, null, chefManager);
    StationUIController uiController = mock(StationUIController.class);

    @Test
    public void testGetActionTypesNothing() {
        GrillingStation station = new GrillingStation(1,null,null,null);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if no chef is nearby" ,actionTypes.isEmpty());
        station.nearbyChef = chef;
        actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef and station have no ingredients" ,actionTypes.isEmpty());
        chef.grabItem(new Salad(textureManager));
        actionTypes = station.getActionTypes();
        assertTrue("nothing is added to action types if the chef has an ingredient that can't be grilled" ,actionTypes.isEmpty());
    }

    @Test
    public void testGetActionPlaceIngredient(){
        GrillingStation station = new GrillingStation(1, null, null, null);
        chef.grabItem(patty);
        station.nearbyChef = chef;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds PLACE_INGREDIENT to actionTypes when a chef is nearby with a grillable ingredient" ,actionTypes.contains(StationAction.ActionType.PLACE_INGREDIENT));
    }

    @Test
    public void testCooking() {
        GrillingStation station = new GrillingStation(1, null, uiController, null);
        station.nearbyChef = chef;
        station.currentIngredient = patty;
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue("adds COOK_ACTION to actionTypes when a chef is nearby and an ingredient is in the station" ,actionTypes.contains(StationAction.ActionType.COOK_ACTION));
        station.doStationAction(StationAction.ActionType.COOK_ACTION);
        station.act(1);
        station.act(1);
        actionTypes = station.getActionTypes();
        assertTrue("adds FLIP_ACTION to action types when the ingredient is ready to be flipped", actionTypes.contains(StationAction.ActionType.FLIP_ACTION));
        station.doStationAction(StationAction.ActionType.FLIP_ACTION);
        station.act(3);
        actionTypes = station.getActionTypes();
        assertTrue("adds GRAB_INGREDIENT to action types when the ingredient is fully cooked", actionTypes.contains(StationAction.ActionType.GRAB_INGREDIENT));
        station.doStationAction(ActionType.GRAB_INGREDIENT);
        assertTrue("the chef collects a cooked ingredient and the station is empty", patty.getGrilled() && station.currentIngredient == null && chef.getStack().peek() == patty);
    } 
 
    @Test
    public void testPlaceIngredient() {
        GrillingStation station = new GrillingStation(1, null, uiController, null);
        station.nearbyChef = chef;
        chef.grabItem(patty);
        station.doStationAction(StationAction.ActionType.PLACE_INGREDIENT);
        assertEquals("an ingredient can be placed on the station",patty, station.currentIngredient);
    }    

    @Test
    public void testReset() {
        GrillingStation station = new GrillingStation(1, null, uiController, null);
        station.currentIngredient = patty;
        station.progressVisible = true;
        station.reset();
        assertTrue("Tests the reset method",station.currentIngredient == null && station.progressVisible == false);
    }

    
}