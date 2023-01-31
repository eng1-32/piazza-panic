# Piazza Panic

## Game Map

To update the game map and the layout, we are using Tiled ([https://www.mapeditor.org/]())
The format of the map consists of 3 layers:

- Stations
- Foreground
- Background

The Background layer is purely for visual purposes and has no effect on gameplay.</br>
The Foreground layer is for collision that defines where the chef can or cannot go.</br>
The Stations layer is where all the Stations and StationColliders are defined. The formats are
defined as follows:

- Station
    1. Select the Insert Tile tool, select the tile that you want the station to look like and place
       it on the map.
    2. Set the "Class" property to "Station"
    3. Set "actionAlignment" to where you want the action buttons and progress bars to show up.
    4. Set "collisionObjectIDs" to a comma separated list of the ids of the station colliders.
    5. Select the ingredients that can be used on the station. Depending on the station there may
       not be any, or there may be a maximum of one.
    6. Finally select the type of station it should be from the enum.
- StationCollider
    1. Select the Insert Rectangle tool and place it where you want the chef to go to interact with
       a station.
    2. Add the ID to the "collisionObjectIDs" of the corresponding stations that you want it to
       interact with.

All of this setup allows the code to automatically instantiate the stations in the correct positions
with their corresponding textures and station colliders.</br>

To add a new type of station to the tile map, go to View->Custom Types Editor, select the
StationType enum, and add it there. You can also add possible ingredients here with the Ingredient
enum. The station properties can be edited here too.

