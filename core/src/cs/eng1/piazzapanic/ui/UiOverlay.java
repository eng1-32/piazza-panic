package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;

public class UiOverlay {

    Label ingredientLabel;
    public UiOverlay(Stage uiStage, final PiazzaPanicGame game) {
        Table table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);

        Timer timer = new Timer(game);
        table.add(timer);
        table.right().top();

        ImageButton homeButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/1x/home.png"))), ButtonManager.ButtonColour.BLUE, -1.5f);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.loadHomeScreen();
            }
        });

        table.add(homeButton);


        this.ingredientLabel = new Label("",
                new Label.LabelStyle(game.getFontManager().getLabelFont(), null));
        VerticalGroup vg = new VerticalGroup();
        vg.setFillParent(true);
        vg.addActor(ingredientLabel);
        uiStage.addActor(vg);


    }

    public void updateChefUI(ChefManager chefManager){
        Chef chef = chefManager.getCurrentChef();
        if (chef!=null) {
            ingredientLabel.setText(chef.getStack().toString());
        }
        else{
            ingredientLabel.setText("Empty");
        }
    }

}
