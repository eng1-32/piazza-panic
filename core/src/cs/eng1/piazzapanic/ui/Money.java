package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Money extends Label {

    private float totalMoney = 0;
    private boolean isRunning = true;

    public Money(Label.LabelStyle labelStyle) {
        super("£0", labelStyle);
    }

    public void reset() {
        totalMoney = 0;
        setText("£0");
    }

    public void setMoney(float money) {
        totalMoney = money;
    }

    public void addMoney(float money) {
        totalMoney += money;
    }

    public void takeMoney(float money) {
        totalMoney -= money;
    }

    public float getMoney() {
        return totalMoney;
    }

    public void act(float delta) {
        if (isRunning) {
            updateMoney();
        }
    }

    public void updateMoney() {
        this.setText("£" + (int) totalMoney);
    }

}
