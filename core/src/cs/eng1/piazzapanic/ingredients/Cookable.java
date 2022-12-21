package cs.eng1.piazzapanic.ingredients;

public interface Cookable {
  void cook(float deltaTime);
  boolean isCooked();
  boolean isCooking();
  float getCookedAmount();
}
