package cs.eng1.piazzapanic.ingredients;

public class Patty extends Ingredient implements Cookable {
  final float timeToCook = 5.0f;
  float timeCooked = 0.0f;
  public Patty() {
    super("patty");
  }

  @Override
  public void cook(float deltaTime) {
    timeCooked += deltaTime;
  }

  @Override
  public boolean isCooked() {
    return timeCooked > timeToCook;
  }

  @Override
  public boolean isCooking() {
    return timeCooked > 0.001f;
  }

  @Override
  public float getCookedAmount() {
    return timeCooked / timeToCook;
  }
}
