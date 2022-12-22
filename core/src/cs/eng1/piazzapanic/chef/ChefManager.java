package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.List;

public class ChefManager {

  private final ArrayList<Chef> chefs;
  private Chef currentChef = null;
  private final TiledMapTileLayer collisionLayer;

  public ChefManager(float chefScale, TiledMapTileLayer collisionLayer) {
    this.collisionLayer = collisionLayer;
    String[] chefSprites = new String[]{
        "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Brown/manBrown_hold.png",
        "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Woman Green/womanGreen_hold.png"
    };
    chefs = new ArrayList<>(chefSprites.length);

    for (int i = 0; i < chefSprites.length; i++) {
      String sprite = chefSprites[i];
      Texture chefTexture = new Texture(Gdx.files.internal(sprite));
      Chef chef = new Chef(chefTexture, this);
      chef.setBounds(2 + 2 * i, 3, chefTexture.getWidth() * chefScale,
          chefTexture.getHeight() * chefScale);
      chef.setInputEnabled(false);
      chefs.add(chef);
    }
  }

  public Cell getCellAtPosition(float x, float y) {
    return collisionLayer.getCell((int) Math.floor(x), (int) Math.floor(y));
  }

  public List<Chef> getChefs() {
    return chefs;
  }

  public void addChefsToStage(final Stage stage) {
    for (Chef chef : chefs) {
      stage.addActor(chef);
    }
    final ChefManager manager = this;
    stage.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        Actor actorHit = stage.hit(x, y, false);
        if (actorHit instanceof Chef) {
          manager.setCurrentChef((Chef) actorHit);
        } else {
          manager.setCurrentChef(null);
        }
      }
    });
  }

  public void setCurrentChef(Chef chef) {
    if (chef == null && currentChef != null) {
      currentChef.setInputEnabled(false);
      currentChef = null;
    }
    if (currentChef != chef) {
      if (currentChef != null) {
        currentChef.setInputEnabled(false);
      }
      currentChef = chef;
      currentChef.setInputEnabled(true);
    }
  }
}
