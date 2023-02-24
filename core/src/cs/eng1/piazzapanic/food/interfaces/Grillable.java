package cs.eng1.piazzapanic.food.interfaces;

public interface Grillable {
    public boolean grillingTick(float delta);

    public boolean getHalfGrilled();

    public boolean getGrilled();

    public Holdable getGrillResult();

    public boolean grillStepComplete();

    public float getGrillProgress();

    public void flip();
}
