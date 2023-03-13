package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager.ButtonColour;

public class ModeSelectOverlay {

    private final Table table;

    public ModeSelectOverlay(PiazzaPanicGame game) {
        table = new Table();
        table.setFillParent(true);
        table.setVisible(false);
        table.center();
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.LIGHT_GRAY);
        bgPixmap.fill();

        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new Texture(bgPixmap));
        table.setBackground(textureRegionDrawableBg);

        final TextField scenarioNumber = game.getButtonManager().createTextField("");

        TextButton backButton = game.getButtonManager().createTextButton("Back", ButtonManager.ButtonColour.GREY);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });

        final TextButton scenarioCheckbox = game.getButtonManager()
                .createTextButton("Scenario Mode", ButtonColour.BLUE);
        scenarioCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String text = scenarioNumber.getText();

                if (text.matches("[1-9]{1,}")) {
                    game.loadGameScreen(Integer.parseInt(text));
                } else {

                }
            }
        });

        final TextButton endlessCheckbox = game.getButtonManager()
                .createTextButton("Endless Mode", ButtonColour.BLUE);
        endlessCheckbox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.loadGameScreen(0);
            }
        });


        table.add(scenarioCheckbox);
        table.add(scenarioNumber);
        table.row();
        table.add(endlessCheckbox);
        table.row();
        table.add(backButton).padTop(20f);
    }

    public void addToStage(Stage uiStage) {
        uiStage.addActor(table);
    }

    public void show() {
        table.setVisible(true);
    }

    public void hide() {
        table.setVisible(false);
    }

}
