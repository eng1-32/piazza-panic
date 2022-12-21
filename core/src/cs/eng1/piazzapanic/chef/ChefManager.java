package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

public class ChefManager {
  private final ArrayList<Chef> chefs;

  public ChefManager(float chefScale) {
    String[] chefSprites = new String[]{
        "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Man Brown/manBrown_hold.png",
        "Kenney-Game-Assets-2/2D assets/Topdown Shooter (620 assets)/PNG/Woman Green/womanGreen_hold.png"
    };
    chefs = new ArrayList<>(chefSprites.length);

    for (int i = 0; i < chefSprites.length; i++) {
      String sprite = chefSprites[i];
      Sprite chefSprite = new Sprite(new Texture(Gdx.files.internal(sprite)));
      Chef chef = new Chef(chefSprite);
      chef.setBounds(2 + 2 * i, 3, chefSprite.getWidth() * chefScale, chefSprite.getHeight() * chefScale);
      chef.setInputEnabled(false);
      chefs.add(chef);
    }

    chefs.get(0).setInputEnabled(true);
  }

  public List<Chef> getChefs() {
    return chefs;
  }

  public void addChefsToStage(Stage stage) {
    for (Chef chef : chefs) {
      stage.addActor(chef);
    }
  }
}
