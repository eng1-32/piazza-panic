package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import cs.eng1.piazzapanic.ingredients.Ingredient;

public class Chef extends Actor {
  private Sprite image;
  FixedStack<Ingredient> ingredientStack = new FixedStack<>(5);

  //interactions between chef and stations are implemented
  public Chef(Sprite image) {
    this.image = image;

    initializeInputListener();
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
