package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import cs.eng1.piazzapanic.PiazzaPanicGame;
public class Timer extends Label {
    private int totalTime = 0;
    public Timer(PiazzaPanicGame game){
        super("0:00", new Label.LabelStyle(game.getFontManager().getLabelFont(), null));
    }

    @Override
    public void act(float delta){
        totalTime += delta;
        updateTimer();
    }

    public void updateTimer(){
        if (totalTime>=60){
            int seconds = totalTime % 60;
            int minutes = totalTime / 60;
            this.setText(minutes + ":" + seconds);
        }
        else{
            this.setText("0:" + totalTime);
        }
    }
}
