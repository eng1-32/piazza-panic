package cs.eng1.piazzapanic;

import java.util.HashMap;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

import cs.eng1.piazzapanic.box2d.MapBodyBuilder;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.ChoppingStation;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.stations.GrillingStation;
import cs.eng1.piazzapanic.stations.IngredientStation;
import cs.eng1.piazzapanic.stations.RecipeStation;
import cs.eng1.piazzapanic.stations.Station;
import cs.eng1.piazzapanic.stations.StationCollider;
import cs.eng1.piazzapanic.stations.SubmitStation;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;

public class MapLoader {
    private TiledMap map;

    public final int pixelsPerTile;
    public final float unitScale;

    public final Vector2 mapSize;

    public MapLoader(String path) {
        map = new TmxMapLoader().load(path);

        int sizeX = map.getProperties().get("width", Integer.class);
        int sizeY = map.getProperties().get("height", Integer.class);

        mapSize = new Vector2(sizeX, sizeY);

        pixelsPerTile = map.getProperties().get("tilewidth", Integer.class);
        unitScale = 1 / (float) pixelsPerTile;
    }

    public Map getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer createMapRenderer() {
        return new OrthogonalTiledMapRenderer(map, unitScale);
    }

    public Array<Body> createBox2DBodies(String mapLayerName, World world) {
        MapLayer b2dBodyLayer = map.getLayers().get(mapLayerName);
        return MapBodyBuilder.buildShapes(b2dBodyLayer, pixelsPerTile, world);
    }

    public void createStations(String mapLayerName, ChefManager chefManager, Stage stage,
            StationUIController stationUIController, FoodTextureManager foodTextureManager,
            CustomerManager customerManager) {
        MapLayer objectLayer = map.getLayers().get(mapLayerName);
        Array<TiledMapTileMapObject> tileObjects = objectLayer.getObjects()
                .getByType(TiledMapTileMapObject.class);

        Array<RectangleMapObject> colliderObjects = objectLayer.getObjects()
                .getByType(RectangleMapObject.class);

        HashMap<Integer, StationCollider> colliders = new HashMap<>();

        for (RectangleMapObject colliderObject : new Array.ArrayIterator<>(colliderObjects)) {
            Integer id = colliderObject.getProperties().get("id", Integer.class);
            StationCollider collider = new StationCollider(chefManager);
            Rectangle bounds = colliderObject.getRectangle();
            collider.setBounds(bounds.getX() * unitScale, bounds.getY() * unitScale,
                    bounds.getWidth() * unitScale, bounds.getHeight() * unitScale);
            stage.addActor(collider);
            colliders.put(id, collider);
        }

        for (TiledMapTileMapObject tileObject : new Array.ArrayIterator<>(tileObjects)) {
            // Check if it is actually a station
            if (!tileObject.getProperties().containsKey("stationType")) {
                continue;
            }

            // Get basic station properties
            Station station;
            int id = tileObject.getProperties().get("id", Integer.class);
            String ingredients = tileObject.getProperties().get("ingredients", String.class);
            StationActionUI.ActionAlignment alignment = StationActionUI.ActionAlignment.valueOf(
                    tileObject.getProperties().get("actionAlignment", "TOP", String.class));

            // Initialize specific station types
            switch (tileObject.getProperties().get("stationType", String.class)) {
                case "cookingStation":
                    station = new CookingStation(id, tileObject.getTextureRegion(), stationUIController,
                            alignment);
                    break;
                case "ingredientStation":
                    station = new IngredientStation(id, tileObject.getTextureRegion(), stationUIController,
                            alignment, Ingredient.fromString(ingredients, foodTextureManager));
                    break;
                case "choppingStation":
                    station = new ChoppingStation(id, tileObject.getTextureRegion(), stationUIController,
                            alignment);
                    break;
                case "recipeStation":
                    station = new RecipeStation(id, tileObject.getTextureRegion(), stationUIController,
                            alignment, foodTextureManager);
                    break;
                case "grillingStation":
                    station = new GrillingStation(id, tileObject.getTextureRegion(), stationUIController, alignment);
                    break;
                case "submitStation":
                    station = new SubmitStation(id, tileObject.getTextureRegion(), stationUIController, alignment,
                            customerManager);
                    customerManager.addStation((SubmitStation) station);
                    break;
                default:
                    station = new Station(id, tileObject.getTextureRegion(), stationUIController, alignment);
            }
            float tileX = tileObject.getX() * unitScale;
            float tileY = tileObject.getY() * unitScale;
            float rotation = tileObject.getRotation();

            // Adjust x and y positions based on Tiled quirks with rotation changing the
            // position of the tile
            if (rotation == 90) {
                tileY -= 1;
            } else if (rotation == 180) {
                tileX -= 1;
                tileY -= 1;
            } else if (rotation == -90 || rotation == 270) {
                tileX -= 1;
            }

            station.setBounds(tileX, tileY, 1, 1);
            station.setImageRotation(-tileObject.getRotation());
            stage.addActor(station);

            String colliderIDs = tileObject.getProperties().get("collisionObjectIDs", String.class);
            for (String idString : colliderIDs.split(",")) {
                try {
                    Integer colliderID = Integer.parseInt(idString);
                    StationCollider collider = colliders.get(colliderID);
                    if (collider != null) {
                        collider.register(station);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing collider ID: " + e.getMessage());
                }

            }
        }
    }

}
