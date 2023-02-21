package cs.eng1.piazzapanic.food.interfaces;

public interface Cookable extends Holdable {
    public boolean cookingTick(float delta);

    public boolean getHalfCooked();

    public boolean getCooked();

    public Holdable getCookingResult();

    public boolean cookingStepComplete();

    public float getCookingProgress();

    public void flip();
}