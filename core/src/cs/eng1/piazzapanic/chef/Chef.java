package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import cs.eng1.piazzapanic.ingredients.Ingredient;

public class Chef extends Actor {

  private final Sprite image;
  private final FixedStack<Ingredient> ingredientStack = new FixedStack<>(5);

  private final Vector2 inputVector;
  private final float speed = 2f;
  private boolean inputEnabled = true;

  //interactions between chef and stations are implemented
  public Chef(Sprite image) {
    this.image = image;
    inputVector = new Vector2();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(image, getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public void act(float delta) {
    getInput();

    calculateMovement(delta);

    super.act(delta);
  }

  private void getInput() {
    inputVector.x = 0;
    inputVector.y = 0;
    if (!isInputEnabled()) {
      return;
    }
    float x = 0f;
    float y = 0f;
    if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
      y += 1f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      y -= 1f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      x += 1f;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      x -= 1f;
    }
    setInputVector(x, y);
  }

  private void calculateMovement(float delta) {
    addAction(Actions.moveBy(inputVector.x * speed * delta, inputVector.y * speed * delta));
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

  public void setInputVector(float x, float y) {
    inputVector.x = x;
    inputVector.y = y;
    if (inputVector.len() > 1f) {
      inputVector.nor();
    }
  }

  public boolean isInputEnabled() {
    return inputEnabled;
  }

  public void setInputEnabled(boolean inputEnabled) {
    this.inputEnabled = inputEnabled;
  }
}
