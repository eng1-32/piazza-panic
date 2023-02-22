package cs.eng1.piazzapanic.food.interfaces;

public interface Choppable extends Holdable {
    public boolean choppingTick(float delta);

    public boolean getChopped();

    public Holdable getChoppingResult();

    public float getChoppingProgress();
}
