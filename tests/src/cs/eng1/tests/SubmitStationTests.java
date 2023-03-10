package cs.eng1.tests;

import cs.eng1.piazzapanic.stations.StationAction;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UIOverlay;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.List;

import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.recipes.JacketPotato;
import cs.eng1.piazzapanic.stations.SubmitStation;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.ingredients.Patty;

@RunWith(GdxTestRunner.class)
public class SubmitStationTests {
    ChefManager chefManager = new ChefManager(0, null, null);
    Chef chef = new Chef(null, null, chefManager);
    FoodTextureManager textureManager = new FoodTextureManager();
    StationUIController uiController = mock(StationUIController.class);
    UIOverlay overlay = mock(UIOverlay.class);
    CustomerManager customerManager = new CustomerManager(overlay);

    @Test
    public void testGetActionTypesNothing() {
        SubmitStation station = new SubmitStation(1, null, null, null, null);
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
    public void testCorrectRecipe() {
        customerManager.init(textureManager);
        SubmitStation station = new SubmitStation(1, null, null, null, customerManager);
        station.nearbyChef = chef;
        chef.grabItem(new JacketPotato(textureManager));
        customerManager.nextRecipe();
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        boolean a = actionTypes.contains(ActionType.SUBMIT_ORDER);
        assertTrue("submit order is added to action types if the chef has a correct recipe", a);

    }

}