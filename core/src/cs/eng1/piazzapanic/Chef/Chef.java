package cs.eng1.piazzapanic.Chef;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import cs.eng1.piazzapanic.ingredients.Ingredient;

import java.awt.*;
import java.util.Stack;

public class Chef extends Sprite implements InputProcessor {
    Stack<Ingredient> ingredientStack = new FixedStack<Ingredient>(5);
    private List listOfStations; //list of station positions (Might be removed depending on how
    //interactions between chef and stations are implemented
    public Chef(Sprite image, List stations){
        super(image);
        listOfStations = stations;

    }

    //TODO: Create interactive functionality between chef and stations.

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Buttons.LEFT:
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
