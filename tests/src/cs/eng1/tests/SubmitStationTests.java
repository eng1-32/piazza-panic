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
import cs.eng1.piazzapanic.food.recipes.Pizza;
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
    CustomerManager customerManager = new CustomerManager(overlay, 0);
    // pizza, jacket, burger, pizza

    @Test
    /**
     * Tests that getActionTypes returns nothing when the user is unable to use the
     * station
     * because there is no nearby chef, the chef isn't holding anything or they
     * aren't
     * holding a recipe
     */
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
        assertTrue("Nothing is added to action types if the chef has an item that is not a Recipe.",
                actionTypes.isEmpty());
    }

    @Test
    /**
     * Tests that getActionTypes returns SUBMIT_ORDER when the chef has the next
     * recipe
     * that needs to be submited
     */
    public void testCorrectRecipe() {
        customerManager = new CustomerManager(overlay, 0, 0);
        customerManager.init(textureManager);
        // pizza, jacket, burger, pizza
        SubmitStation station = new SubmitStation(1, null, null, null, customerManager);
        station.nearbyChef = chef;

        chef.getStack().clear();
        chef.grabItem(new Pizza(textureManager));

        List<StationAction.ActionType> actionTypes = station.getActionTypes();

        boolean test = actionTypes.contains(ActionType.SUBMIT_ORDER);
        assertTrue("submit order is added to action types if the chef has a correct recipe", test);
    }

    @Test
    /**
     * Tests that doStationAction(SUBMIT_ORDER) does nothing when the wrong recipe
     * is
     * submitted, and that it moves onto the next order when the correct recipe is
     * submitted
     */
    public void testDoStationAction() {
        customerManager = new CustomerManager(overlay, 0, 0);
        customerManager.init(textureManager);
        // pizza, jacket, burger, pizza
        SubmitStation station = new SubmitStation(1, null, uiController, null, customerManager);

        station.nearbyChef = chef;

        chef.getStack().clear();
        chef.getStack().add(new JacketPotato(textureManager));

        station.doStationAction(ActionType.SUBMIT_ORDER);

        assertTrue("Chef loses wrong item and order remains to be Pizza (instead of next JacketPotato)",
                chef.getStack().size() == 0 && customerManager.checkRecipe(new Pizza(textureManager)));

        chef.getStack().clear();
        chef.grabItem(new Pizza(textureManager));

        station.doStationAction(ActionType.SUBMIT_ORDER);
        assertTrue("Chef loses correct item and order switches to JacketPotato",
                chef.getStack().size() == 0 && customerManager.checkRecipe(new JacketPotato(textureManager)));
    }
}