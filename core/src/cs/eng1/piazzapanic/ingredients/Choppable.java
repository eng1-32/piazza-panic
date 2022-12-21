package cs.eng1.piazzapanic.ingredients;

public interface Choppable {
  void chop(float deltaTime);
  boolean isChopped();
  boolean isChopping();
  float getChoppedAmount();
}
