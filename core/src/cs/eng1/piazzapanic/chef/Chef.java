package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.interfaces.Holdable;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.stations.Station;

/**
 * The Chef class is an actor representing a chef in the kitchen. It can pick up
 * and put down
 * ingredients and interact with stations.
 */
public class Chef extends Actor implements Disposable {

  /**
   * image, imageBounds and imageRotation are all used to display the chef to the
   * user and show the
   * user where the chef is and what direction it is moving without changing any
   * collision details.
   */
  private final Texture image;
  private final Vector2 imageBounds;
  private float imageRotation = 0f;

  private final ChefManager chefManager;
  private final FixedStack<Holdable> ingredientStack = new FixedStack<>(5);

  private final Vector2 inputVector;
  private final float speed = 3f;
  private Body body;

  private boolean inputEnabled = true;
  private boolean paused = false;

  /**
   * @param image       the texture to display to the user
   * @param imageBounds the bounds of the texture independent of the chef's own
   *                    bounds to use for
   *                    drawing the image to scale.
   * @param chefManager the controller from which we can get information about all
   *                    the chefs and
   *                    their surrounding environment
   */
  public Chef(Texture image, Vector2 imageBounds, ChefManager chefManager) {
    this.image = image;
    this.imageBounds = imageBounds;
    this.chefManager = chefManager;
    inputVector = new Vector2();
  }

  public void createBody() {
    CircleShape circle = new CircleShape();
    circle.setRadius(0.5f);
    FixtureDef fDef = new FixtureDef();
    fDef.shape = circle;
    fDef.density = 20f;
    fDef.friction = 0.4f;

    BodyDef bDef = new BodyDef();
    bDef.position.set(getX(), getY() + 0.2f);
    bDef.type = BodyType.DynamicBody;
    bDef.linearDamping = 20f;
    bDef.fixedRotation = true;

    body = chefManager.world.createBody(bDef);
    body.createFixture(fDef);

  }

  public void init(float x, float y) {
    setX(x);
    setY(y);
    createBody();
    getStack().clear();
    imageRotation = 0;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(image, getX() + (1 - imageBounds.x) / 2f, getY() + (1 - imageBounds.y) / 2f,
        imageBounds.x / 2f, imageBounds.y / 2f, imageBounds.x,
        imageBounds.y, 1f, 1f, imageRotation, 0, 0, image.getWidth(), image.getHeight(), false,
        false);
    for (Holdable item : ingredientStack) {
      Texture texture = item.getTexture();
      batch.draw(texture, getX() + 0.5f, getY() + 0.2f, 0f, 0.3f, 0.6f, 0.6f, 1f, 1f,
          imageRotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
  }

  @Override
  public void act(float delta) {
    getInput();

    Vector2 movement = calculateMovement(delta);

    Vector2 bodyVector2 = body.getPosition();

    if (!movement.isZero(0.1f)) {
      body.applyLinearImpulse(movement.scl(4.5f), bodyVector2, true);
    }

    bodyVector2 = body.getPosition();

    setPosition(bodyVector2.x - 0.5f, bodyVector2.y - 0.5f);

    super.act(delta);
  }

  /**
   * Set the input vector based on the input keys for movement
   */
  private void getInput() {
    inputVector.x = 0;
    inputVector.y = 0;
    if (!isInputEnabled() || isPaused()) {
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
    if (inputVector.len() > 0.01f) {
      imageRotation = inputVector.angleDeg(Vector2.X);
    }
  }

  /**
   * Calculate how far the chef should move based on the input vector
   *
   * @param delta the time that has passed since the last frame
   * @return the vector representing how far the chef should move
   */
  private Vector2 calculateMovement(float delta) {
    Vector2 movement = new Vector2(inputVector.x * speed, inputVector.y * speed);

    return movement;
  }

  public boolean hasIngredient() {
    return !ingredientStack.empty();
  }

  public boolean canGrabIngredient() {
    return ingredientStack.hasSpace();
  }

  public void grabItem(Holdable ingredient) {
    ingredientStack.push(ingredient);
    notifyAboutUpdatedStack();
  }

  /**
   * Pops the top ingredient from the stack to place on the station
   *
   * @return the ingredient that was popped from the stack.
   */
  public Ingredient popIngredient() {
    Holdable item = ingredientStack.peek();
    if (item instanceof Ingredient) {
      ingredientStack.pop();
      notifyAboutUpdatedStack();
      return (Ingredient) item;
    }
    return null;
  }

  public Recipe placeRecipe() {
    Holdable item = ingredientStack.peek();
    if (item instanceof Recipe) {
      ingredientStack.pop();
      notifyAboutUpdatedStack();
      return (Recipe) item;
    }
    return null;
  }

  public FixedStack<Holdable> getStack() {
    return ingredientStack;
  }

  /**
   * Sets the input vector based on x and y, but ensuring that the vector is never
   * greater than a
   * length of 1
   *
   * @param x the x input value
   * @param y the y input value
   */
  public void setInputVector(float x, float y) {
    inputVector.x = x;
    inputVector.y = y;
    inputVector.nor();
  }

  public boolean isInputEnabled() {
    return inputEnabled;
  }

  public void setInputEnabled(boolean inputEnabled) {
    this.inputEnabled = inputEnabled;
  }

  public boolean isPaused() {
    return paused;
  }

  public void setPaused(boolean pauseValue) {
    this.paused = pauseValue;
  }

  public Texture getTexture() {
    return image;
  }

  /**
   * Whenever the stack has items added or removed from it, notify the chef
   * manager about the new
   * stack.
   */
  public void notifyAboutUpdatedStack() {
    if (chefManager.getCurrentChef() == this) {
      chefManager.currentChefStackUpdated();
    }
  }

  @Override
  public void dispose() {
    image.dispose();
  }
}
