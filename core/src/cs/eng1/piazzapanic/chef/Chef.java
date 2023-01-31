package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.Station;

/**
 * The Chef class is an actor representing a chef in the kitchen. It can pick up and put down
 * ingredients and interact with stations.
 */
public class Chef extends Actor implements Disposable {

  /**
   * image, imageBounds and imageRotation are all used to display the chef to the user and show the
   * user where the chef is and what direction it is moving without changing any collision details.
   */
  private final Texture image;
  private final Vector2 imageBounds;
  private float imageRotation = 0f;

  private final ChefManager chefManager;
  private final FixedStack<Ingredient> ingredientStack = new FixedStack<>(5);

  private final Vector2 inputVector;
  private final float speed = 3f;

  /**
   * a parameter which adds a small amount of distance between the chef's boundaries and any other
   * objects it can collide with. This helps avoid boundary errors in collision calculations
   */
  private final float collisionSkin = 0.01f;
  private boolean inputEnabled = true;
  private boolean paused = false;

  /**
   * @param image       the texture to display to the user
   * @param imageBounds the bounds of the texture independent of the chef's own bounds to use for
   *                    drawing the image to scale.
   * @param chefManager the controller from which we can get information about all the chefs and
   *                    their surrounding environment
   */
  public Chef(Texture image, Vector2 imageBounds, ChefManager chefManager) {
    this.image = image;
    this.imageBounds = imageBounds;
    this.chefManager = chefManager;
    inputVector = new Vector2();
  }

  public void init(float x, float y) {
    setX(x);
    setY(y);
    getStack().clear();
    imageRotation = 0;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.draw(image, getX() + (1 - imageBounds.x) / 2f, getY() + (1 - imageBounds.y) / 2f,
        imageBounds.x / 2f, imageBounds.y / 2f, imageBounds.x,
        imageBounds.y, 1f, 1f, imageRotation, 0, 0, image.getWidth(), image.getHeight(), false,
        false);
    for (Ingredient ingredient : ingredientStack) {
      Texture texture = ingredient.getTexture();
      batch.draw(texture, getX() + 0.5f, getY() + 0.2f, 0f, 0.3f, 0.6f, 0.6f, 1f, 1f,
          imageRotation, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
  }

  @Override
  public void act(float delta) {
    getInput();

    Vector2 movement = calculateMovement(delta);
    moveBy(movement.x, movement.y);

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
   * Calculate how far the chef should move based on the input vector while avoiding collisions
   *
   * @param delta the time that has passed since the last frame
   * @return the vector representing how far the chef should move
   */
  private Vector2 calculateMovement(float delta) {
    Vector2 movement = new Vector2(inputVector.x * speed * delta, inputVector.y * speed * delta);

    // Adjust movement for collision
    movement.x = adjustHorizontalMovementForCollision(movement.x);
    movement.y = adjustVerticalMovementForCollision(movement.y);

    return movement;
  }

  /**
   * Check to see if a point lies within a tile in the collision layer, or if the point is in a chef
   * or station
   *
   * @param x the x-coordinate to check for a collision
   * @param y the y-coordinate to check for a collision
   * @return the bounding box of the object that the point lies within. It will be null if the point
   * does not lie in any object
   */
  private Rectangle getCollisionObjectBoundaries(float x, float y) {
    Actor actorHit = getStage().hit(x, y, false);
    Cell tileHit = chefManager.getCellAtPosition((int) Math.floor(x), (int) Math.floor(y));

    if (tileHit != null) {
      // Return the bounds of the foreground tile that the selected point overlaps
      return new Rectangle((float) Math.floor(x), (float) Math.floor(y), 1, 1);
    } else if (actorHit instanceof Station || actorHit instanceof Chef) {
      // Return the bounds of the station or chef that the point lies within.
      return new Rectangle(actorHit.getX(), actorHit.getY(), actorHit.getWidth(),
          actorHit.getHeight());
    } else {
      return null;
    }
  }

  /**
   * @param xMovement the amount to move the chef along the x-axis before collision
   * @return the new change in the x-axis that ensures that the chef does not collide with any
   * objects
   */
  private float adjustHorizontalMovementForCollision(float xMovement) {
    if (xMovement > 0.0001f) {
      // Check right side of chef at top, middle and bottom
      float rightBorder = getX() + getWidth() + xMovement;
      Rectangle hitBoundsBottom = getCollisionObjectBoundaries(rightBorder, getY());
      Rectangle hitBoundsMiddle = getCollisionObjectBoundaries(rightBorder,
          getY() + getHeight() / 2f);
      Rectangle hitBoundsTop = getCollisionObjectBoundaries(rightBorder, getY() + getHeight());

      // Calculate new change in x relative to the collision object boundaries
      float adjustment = -getWidth() - collisionSkin - getX();
      if (hitBoundsBottom != null) {
        xMovement = hitBoundsBottom.x + adjustment;
      }
      if (hitBoundsMiddle != null) {
        xMovement = Math.min(xMovement, hitBoundsMiddle.x + adjustment);
      }
      if (hitBoundsTop != null) {
        xMovement = Math.min(xMovement, hitBoundsTop.x + adjustment);
      }
    } else if (xMovement < -0.0001f) {
      // Check left side of chef at top, middle and bottom
      float leftBorder = getX() + xMovement;
      Rectangle hitBoundsBottom = getCollisionObjectBoundaries(leftBorder, getY());
      Rectangle hitBoundsMiddle = getCollisionObjectBoundaries(leftBorder,
          getY() + getHeight() / 2f);
      Rectangle hitBoundsTop = getCollisionObjectBoundaries(leftBorder, getY() + getHeight());

      // Calculate new change in x relative to the collision object boundaries
      float adjustment = collisionSkin - getX();
      if (hitBoundsBottom != null) {
        xMovement = hitBoundsBottom.x + hitBoundsBottom.width + adjustment;
      }
      if (hitBoundsMiddle != null) {
        xMovement = Math.max(xMovement, hitBoundsMiddle.x + hitBoundsMiddle.width + adjustment);
      }
      if (hitBoundsTop != null) {
        xMovement = Math.max(xMovement, hitBoundsTop.x + hitBoundsTop.width + adjustment);
      }
    }
    return xMovement;
  }

  /**
   * @param yMovement the amount to move the chef along the y-axis before collision
   * @return the new change in the y-axis that ensures that the chef does not collide with any
   * objects
   */
  private float adjustVerticalMovementForCollision(float yMovement) {
    if (yMovement > 0.0001f) {
      // Check top side of chef at left, middle and right
      float topBorder = getY() + getHeight() + yMovement;
      Rectangle hitBoundsLeft = getCollisionObjectBoundaries(getX(), topBorder);
      Rectangle hitBoundsMiddle = getCollisionObjectBoundaries(getX() + getWidth() / 2f, topBorder);
      Rectangle hitBoundsRight = getCollisionObjectBoundaries(getX() + getWidth(), topBorder);

      // Calculate new change in y relative to the collision object boundaries
      float adjustment = -getHeight() - collisionSkin - getY();
      if (hitBoundsLeft != null) {
        yMovement = Math.min(yMovement, hitBoundsLeft.y + adjustment);
      }
      if (hitBoundsMiddle != null) {
        yMovement = Math.min(yMovement, hitBoundsMiddle.y + adjustment);
      }
      if (hitBoundsRight != null) {
        yMovement = Math.min(yMovement, hitBoundsRight.y + adjustment);
      }
    } else if (yMovement < -0.0001f) {
      // Check bottom side of chef at left, middle and right
      float bottomBorder = getY() + yMovement;
      Rectangle hitBoundsLeft = getCollisionObjectBoundaries(getX(), bottomBorder);
      Rectangle hitBoundsMiddle = getCollisionObjectBoundaries(getX() + getWidth() / 2f,
          bottomBorder);
      Rectangle hitBoundsRight = getCollisionObjectBoundaries(getX() + getWidth(), bottomBorder);

      // Calculate new change in y relative to the collision object boundaries
      float adjustment = collisionSkin - getY();
      if (hitBoundsLeft != null) {
        yMovement = Math.max(yMovement, hitBoundsLeft.y + hitBoundsLeft.height + adjustment);
      }
      if (hitBoundsMiddle != null) {
        yMovement = Math.max(yMovement, hitBoundsMiddle.y + hitBoundsMiddle.height + adjustment);
      }
      if (hitBoundsRight != null) {
        yMovement = Math.max(yMovement, hitBoundsRight.y + hitBoundsRight.height + adjustment);
      }
    }
    return yMovement;
  }

  public boolean hasIngredient() {
    return !ingredientStack.empty();
  }

  public boolean canGrabIngredient() {
    return ingredientStack.hasSpace();
  }

  public void grabIngredient(Ingredient ingredient) {
    ingredientStack.push(ingredient);
    notifyAboutUpdatedStack();
  }

  /**
   * Pops the top ingredient from the stack to place on the station
   *
   * @return the ingredient that was popped from the stack.
   */
  public Ingredient placeIngredient() {
    Ingredient ingredient = ingredientStack.pop();
    notifyAboutUpdatedStack();
    return ingredient;
  }

  public FixedStack<Ingredient> getStack() {
    return ingredientStack;
  }

  /**
   * Sets the input vector based on x and y, but ensuring that the vector is never greater than a
   * length of 1
   *
   * @param x the x input value
   * @param y the y input value
   */
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
   * Whenever the stack has items added or removed from it, notify the chef manager about the new
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
