package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import cs.eng1.piazzapanic.ingredients.Ingredient;

import java.awt.*;
import java.util.Stack;

public class Chef extends Actor {
  private Sprite image;
  Stack<Ingredient> ingredientStack = new FixedStack<Ingredient>(5);
  private List listOfStations; //list of station positions (Might be removed depending on how

  //interactions between chef and stations are implemented
  public Chef(Sprite image, List stations) {
    this.image = image;
    listOfStations = stations;

    initializeInputListener();
  }

  //TODO: Create interactive functionality between chef and stations.

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
