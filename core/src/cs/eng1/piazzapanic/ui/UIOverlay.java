package cs.eng1.piazzapanic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.chef.FixedStack;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.ui.ButtonManager.ButtonColour;

public class UIOverlay {

  Stack chefDisplay;
  Image chefImage;
  Image ingredientImagesBG;
  VerticalGroup ingredientImages;
  PiazzaPanicGame game;

  TextureRegionDrawable removeBtnDrawable;

  public UIOverlay(Stage uiStage, final PiazzaPanicGame game) {
    this.game = game;

    Table table = new Table();
    table.setFillParent(true);
    table.center().top().pad(15f);
    uiStage.addActor(table);

    chefDisplay = new Stack();
    chefDisplay.add(new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png")));
    chefImage = new Image();
    chefImage.setScaling(Scaling.fit);
    chefDisplay.add(chefImage);

    Stack stackDisplay = new Stack();
    ingredientImagesBG = new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png"));
    ingredientImagesBG.setVisible(false);
    stackDisplay.add(ingredientImagesBG);
    ingredientImages = new VerticalGroup();
    ingredientImages.padBottom(10f);
    stackDisplay.add(ingredientImages);

    LabelStyle style = new Label.LabelStyle(game.getFontManager().getTitleFont(), null);
    style.background = new TextureRegionDrawable(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    Timer timer = new Timer(style);
    timer.setAlignment(Align.center);

    ImageButton homeButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
            new Texture(
                Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/White/1x/home.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    homeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        game.loadHomeScreen();
      }
    });
    removeBtnDrawable = new TextureRegionDrawable(
        new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_crossWhite.png"));

    Value scale = Value.percentWidth(0.04f, table);
    Value timerWidth = Value.percentWidth(0.2f, table);
    table.add(chefDisplay).left().width(scale).height(scale);
    table.add(timer).expandX().width(timerWidth).height(scale);
    table.add(homeButton).right().width(scale).height(scale);
    table.row().padTop(10f);
    table.add(stackDisplay).left().width(scale);
  }

  public void show() {

  }

  public void updateChefUI(ChefManager chefManager) {
    final Chef chef = chefManager.getCurrentChef();

    if (chef != null) {
      Texture texture = chef.getTexture();
      chefImage.setDrawable(new TextureRegionDrawable(texture));

      ingredientImages.clearChildren();
      for (Ingredient ingredient : chef.getStack()) {
        Image image = new Image(ingredient.getTexture());
        image.getDrawable().setMinHeight(chefDisplay.getHeight());
        image.getDrawable().setMinWidth(chefDisplay.getWidth());
        ingredientImages.addActor(image);
      }
      if (!chef.getStack().isEmpty()) {
        ImageButton btn = game.getButtonManager().createImageButton(removeBtnDrawable,
            ButtonColour.RED, -1.5f);
        btn.addListener(new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            chef.placeIngredient();
          }
        });
        ingredientImages.addActor(btn);
      }
      ingredientImagesBG.setVisible(!chef.getStack().isEmpty());
    } else {
      chefImage.setDrawable(null);
      ingredientImages.clearChildren();
      ingredientImagesBG.setVisible(false);
    }
  }

}
