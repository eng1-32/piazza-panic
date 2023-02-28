package cs.eng1.tests;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.stations.StationAction;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.stations.IngredientStation;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
//IMPORT INGREDIENT
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.UIOverlay;
import cs.eng1.piazzapanic.chef.FixedStack;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.food.FoodTextureManager;

@RunWith(GdxTestRunner.class)
public class IngredientStationTests {
    @Test
    public void testGetActionTypesNoChef() {
        IngredientStation station = new IngredientStation(1, null, null, null, null);
        List<StationAction.ActionType> actionTypes = station.getActionTypes();
        assertTrue(actionTypes.isEmpty());
    }

//    @Test
//    public void testGetActionTypesCanGrab() {
//        TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
//        int sizeX = map.getProperties().get("width", Integer.class);
//        int sizeY = map.getProperties().get("height", Integer.class);
//        float tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);
//
//        // Initialize stage and camera
//        OrthographicCamera camera = new OrthographicCamera();
//        ExtendViewport viewport = new ExtendViewport(sizeX, sizeY, camera); // Number of tiles
//        Stage stage= new Stage(viewport);
//
//        ScreenViewport uiViewport = new ScreenViewport();
//        Stage uiStage = new Stage(uiViewport);
//
////        this.stationUIController = new StationUIController(uiStage, game);
////
////        uiOverlay = new UIOverlay(uiStage, game);
//
//        StationUIController stationUIController = new StationUIController(uiStage, game);
//
//        // Create a chef with an empty inventory
//        ChefManager chefManager = new ChefManager(1, null, null);
//        Texture texture = new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_sliderDown.png"));
//        Chef chef = new Chef(texture,  new Vector2(texture.getWidth(),
//                texture.getHeight()), chefManager);
//
//        // Create an ingredient station with a tomato
//        Ingredient tomato = new Ingredient("tomato", new FoodTextureManager());
//        IngredientStation station = new IngredientStation(1, null, null, null,
//                tomato);
//
//        // Assert that the station action type list contains GRAB_INGREDIENT
//        chef.setPosition(station.getX(), station.getY());
//        chefManager.setCurrentChef(chef);
//        List<StationAction.ActionType> actionTypes = station.getActionTypes();
//        assertTrue(actionTypes.contains(StationAction.ActionType.GRAB_INGREDIENT));
//
//
//
//
//    }

}