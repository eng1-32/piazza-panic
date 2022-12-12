package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.Texture;

public class Station {
    protected Texture stationImage;
    protected float[] stationPosition;
    protected Boolean inUse = false;
    public Station(Texture image, float[] position){
        stationImage = image; //Texture of the object
        stationPosition = position; //Position of the object
    }

}
