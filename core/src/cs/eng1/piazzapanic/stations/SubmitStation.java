package cs.eng1.piazzapanic.stations;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.interfaces.Holdable;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.stations.StationAction.ActionType;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.StationActionUI.ActionAlignment;

/**
 * AssembleStation
 */
public class SubmitStation extends Station {

    private CustomerManager customerManager;

    public SubmitStation(int id, TextureRegion image, StationUIController uiController, ActionAlignment alignment,
            CustomerManager customerManager) {
        super(id, image, uiController, alignment);
        this.customerManager = customerManager;
    }

    @Override
    public List<ActionType> getActionTypes() {
        LinkedList<ActionType> actionTypes = new LinkedList<>();
        if (nearbyChef == null || nearbyChef.getStack().isEmpty()) {
            return actionTypes;
        }
        Holdable topItem = nearbyChef.getStack().peek();
        if (checkCorrectRecipe(topItem)) {
            actionTypes.add(ActionType.SUBMIT_ORDER);
        }
        return actionTypes;
    }

    private boolean checkCorrectRecipe(Holdable item) {
        if (item instanceof Recipe) {
            if (customerManager.checkRecipe((Recipe) item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doStationAction(ActionType action) {
        switch (action) {
            case SUBMIT_ORDER:
                Holdable topItem = nearbyChef.getStack().pop();
                if (!checkCorrectRecipe(topItem)) {
                    return;
                }

                customerManager.nextRecipe();
                break;

            default:
                break;
        }

        uiController.showActions(this, getActionTypes());
    }

    /**
     * Updates the current available actions based on the new customer's order
     */
    public void updateOrderActions() {
        uiController.showActions(this, getActionTypes());
    }

}