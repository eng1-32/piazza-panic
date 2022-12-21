package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.StationCollider;

public class Chef extends Actor {
  private final Sprite image;
  private StationCollider currentCollider = null;
  FixedStack<Ingredient> ingredientStack = new FixedStack<>(5);

  //interactions between chef and stations are implemented
  public Chef(Sprite image) {
    this.image = image;

    initializeInputListener();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(image, getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public void act(float delta) {
    Actor actorHit = getStage().hit(getX() + getWidth() / 2f, getY() + getHeight() / 2f, false);
    if (actorHit instanceof StationCollider) {
      if (currentCollider != actorHit) {
        if (currentCollider != null) {
          currentCollider.notifyObservers(null);
        }
        currentCollider = (StationCollider) actorHit;
        currentCollider.notifyObservers(this);
      }
    } else if (currentCollider != null) {
      currentCollider.notifyObservers(null);
      currentCollider = null;
    }

    super.act(delta);
  }

  public boolean hasIngredient() {
    return !ingredientStack.empty();
  }

  public boolean canGrabIngredient() {
    return ingredientStack.hasSpace();
  }

  public void grabIngredient(Ingredient ingredient) {
    ingredientStack.push(ingredient);
  }

  public Ingredient placeIngredient() {
    return ingredientStack.pop();
  }

  private void initializeInputListener() {
    addListener(new InputListener() {
      public boolean keyDown(int keycode) {
        switch (keycode) {
          case Input.Buttons.LEFT:
            break;
        }
        return false;
      }

    });
  }
}
