package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Station extends Actor {
    protected TextureRegion stationImage;
    protected Boolean inUse = false;
    public Station(TextureRegion image){
        stationImage = image; //Texture of the object
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(stationImage, getX(), getY(), getWidth(), getHeight());
    }
}
