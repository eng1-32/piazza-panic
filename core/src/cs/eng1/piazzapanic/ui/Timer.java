package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Timer extends Label {

  private float totalTime = 0;
  private boolean isRunning = false;

  public Timer(Label.LabelStyle labelStyle) {
    super("0:00", labelStyle);
  }

  public void reset() {
    stop();
    totalTime = 0;
    setText("0:00");
  }

  public void start() {
    isRunning = true;
  }

  public void stop() {
    isRunning = false;
  }

  public void setTime(float time) {
    totalTime = time;
    updateTimer();
  }

  public float getTime() {
    return totalTime;
  }

  @Override
  public void act(float delta) {
    if (isRunning) {
      totalTime += delta;
      updateTimer();
    }
  }

  /**
   * Show the minutes and seconds correctly based on the total number of seconds that have passed.
   */
  public void updateTimer() {
    DecimalFormat df = new DecimalFormat("#");
    df.setRoundingMode((RoundingMode.FLOOR));
    if (totalTime >= 60) {
      int seconds = (int) (totalTime % 60);
      int minutes = (int) (totalTime / 60);
      if (seconds < 10) {
        this.setText(minutes + ":0" + df.format(seconds));
      } else {
        this.setText(minutes + ":" + df.format(seconds));
      }
    } else {
      if (totalTime < 10) {
        this.setText("0:0" + df.format(totalTime));
      } else {
        this.setText("0:" + df.format(totalTime));
      }
    }
  }
}
