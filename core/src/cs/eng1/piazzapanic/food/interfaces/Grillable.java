package cs.eng1.piazzapanic.food.interfaces;

public interface Grillable extends Holdable {
    public boolean grillTick(float delta);

    public boolean getHalfGrilled();

    public boolean getGrilled();

    public Holdable getGrillResult();

    public boolean grillStepComplete();

    public float getGrillProgress();

    public void flip();
}
