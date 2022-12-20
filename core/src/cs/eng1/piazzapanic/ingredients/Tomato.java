package cs.eng1.piazzapanic.ingredients;

public class Tomato extends Ingredient implements Choppable {
  final float timeToChop = 5.0f;
  float timeChopped = 0.0f;
  public Tomato() {
    super("tomato");
  }

  @Override
  public void chop(float deltaTime) {
    timeChopped += deltaTime;
  }

  @Override
  public boolean isChopped() {
    return timeChopped > timeToChop;
  }

  @Override
  public boolean isChopping() {
    return timeChopped > 0.001f;
  }

  @Override
  public float getChoppedAmount() {
    return timeChopped / timeToChop;
  }
}
