package cs.eng1.piazzapanic.chef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import cs.eng1.piazzapanic.ingredients.Ingredient;
import cs.eng1.piazzapanic.stations.Station;

public class Chef extends Actor {

  private final Texture image;
  private final Vector2 imageBounds;
  private float imageRotation = 0f;
  private final ChefManager chefManager;
  private final FixedStack<Ingredient> ingredientStack = new FixedStack<>(5);

  private final Vector2 inputVector;
  private final float speed = 3f;
  private final float collisionSkin = 0.01f;
  private boolean inputEnabled = true;
  private boolean paused = false;

  //interactions between chef and stations are implemented
  public Chef(Texture image, Vector2 imageBounds, ChefManager chefManager) {
    this.image = image;
    this.imageBounds = imageBounds;
    this.chefManager = chefManager;
    inputVector = new Vector2();
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
//    batch.draw(image, getX(), getY(), getWidth(), getHeight());
    batch.draw(image, getX(), getY(), getWidth() / 2f, getHeight() / 2f, imageBounds.x, imageBounds.y,
        1f, 1f, imageRotation, 0, 0, image.getWidth(), image.getHeight(), false, false);
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
    if (!isInputEnabled() || isPaused() ) {
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
    Cell tileHit = chefManager.getCellAtPosition(x, getY());

    if (tileHit != null) {
      return new Rectangle((float) Math.floor(x), (float) Math.floor(y), 1, 1);
    } else if (actorHit instanceof Station || actorHit instanceof Chef) {
      return new Rectangle(actorHit.getX(), actorHit.getY(),
          actorHit.getWidth(), actorHit.getHeight());
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
        yMovement = hitBoundsLeft.y + adjustment;
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
        yMovement = hitBoundsLeft.y + hitBoundsLeft.height + adjustment;
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
  }

  public Ingredient placeIngredient() {
    return ingredientStack.pop();
  }

  public FixedStack<Ingredient> getStack(){return ingredientStack;}

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

  public boolean isPaused(){
    return paused;
  }

  public void setPaused(boolean pauseValue){
    this.paused = pauseValue;
  }
}
