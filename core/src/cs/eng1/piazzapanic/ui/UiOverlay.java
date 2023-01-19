package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;

public class UiOverlay {

    Label ingredientLabel;
    public UiOverlay(Stage uiStage) {
        Table table = new Table();
        table.setFillParent(true);
        uiStage.addActor(table);

        TextureRegionDrawable home = new TextureRegionDrawable(new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/1x/home.png")));
        table.add(PiazzaPanicGame.getButtonManager().createImageButton(home, ButtonManager.ButtonColour.BLUE, -1.5f));
        table.right().top();


        TextureRegionDrawable burger = new TextureRegionDrawable(new Texture(Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/1x/arrowDown.png")));
        table.add(PiazzaPanicGame.getButtonManager().createImageButton(burger, ButtonManager.ButtonColour.BLUE, -1.5f));


         this.ingredientLabel = new Label("",
                new Label.LabelStyle(PiazzaPanicGame.getFontManager().getLabelFont(), null));
        table.add(ingredientLabel);

    }

    public void updateChefUI(ChefManager chefManager){
        Chef chef = chefManager.getCurrentChef();
        if (chef!=null) {
            ingredientLabel.setText(chef.getIngredientStack().toString());
        }
        else{
            ingredientLabel.setText("");
        }
    }

}
