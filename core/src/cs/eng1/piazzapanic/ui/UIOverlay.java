package cs.eng1.piazzapanic.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
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
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.screens.GameScreen;
import cs.eng1.piazzapanic.screens.HomeScreen;
import cs.eng1.piazzapanic.stations.BakingStation;
import cs.eng1.piazzapanic.stations.ChoppingStation;
import cs.eng1.piazzapanic.stations.CookingStation;
import cs.eng1.piazzapanic.ui.ButtonManager.ButtonColour;

public class UIOverlay {

  private final Image pointer;
  private final Stack chefDisplay;
  private final Image chefImage;
  private final Image ingredientImagesBG;
  private final VerticalGroup ingredientImages;
  private final TextureRegionDrawable removeBtnDrawable;
  private final Image recipeImagesBG;

  private final VerticalGroup recipeImages;

  private final Timer timer;
  public static Money money;
  public static Lives lives;
  int gameResolutionX;
  int gameResolutionY;
  private final Label recipeCountLabel;
  private final Label resultLabel;
  private final Label loseLabel;

  private final Timer resultTimer;
  private final PiazzaPanicGame game;
  boolean buyClicked = false;
  boolean lifeClicked = false;
  boolean speedClicked = false;
  boolean cookClicked = false;
  boolean chopClicked = false;
  boolean burnClicked = false;
  public static ArrayList<Float> chefXsave = new ArrayList<Float>(Arrays.asList(3f, 11f, 12f, 3f));
  public static ArrayList<Float> chefYsave = new ArrayList<Float>(Arrays.asList(3f, 3f, 6f, 7f));

  Preferences prefs = Gdx.app.getPreferences("gameSave");

  public UIOverlay(Stage uiStage, final PiazzaPanicGame game) {
    this.game = game;
    // Initialize the money button
    LabelStyle moneyStyle = new Label.LabelStyle(game.getFontManager().getTitleFont(), null);
    moneyStyle.background = new TextureRegionDrawable(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    money = new Money(moneyStyle);
    money.setAlignment(Align.bottom);

    // Initialize the money button
    LabelStyle livesStyle = new Label.LabelStyle(game.getFontManager().getTitleFont(), null);
    livesStyle.background = new TextureRegionDrawable(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    lives = new Lives(livesStyle);
    lives.setAlignment(Align.bottom);

    // Initialize table
    Table table = new Table();
    table.setFillParent(true);
    table.center().top().pad(15f);
    uiStage.addActor(table);

    // Initialise pointer image
    pointer = new Image(
        new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/blue_sliderDown.png"));
    pointer.setScaling(Scaling.none);

    // Initialize UI for showing current chef
    chefDisplay = new Stack();
    chefDisplay.add(new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png")));
    chefImage = new Image();
    chefImage.setScaling(Scaling.fit);
    chefDisplay.add(chefImage);

    // Initialize UI for showing current chef's ingredient stack
    Stack ingredientStackDisplay = new Stack();
    ingredientImagesBG = new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png"));
    ingredientImagesBG.setVisible(false);
    ingredientStackDisplay.add(ingredientImagesBG);
    ingredientImages = new VerticalGroup();
    ingredientImages.padBottom(10f);
    ingredientStackDisplay.add(ingredientImages);

    // Initialize the timer
    LabelStyle timerStyle = new Label.LabelStyle(game.getFontManager().getTitleFont(), null);
    timerStyle.background = new TextureRegionDrawable(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/green_button_gradient_down.png"));
    timer = new Timer(timerStyle);
    timer.setAlignment(Align.center);

    // Initialize the home button
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

    final ImageButton buyButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/shoppingBasket.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);

    buyButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (money.getMoney() > 0 && buyClicked == false) {
          money.takeMoney(1);
          ChefManager.chefs.get(3).isLocked = false;
          buyClicked = true;

        }

      }
    });
    buyButton.setPosition(200, 490);
    buyButton.setSize(40, 40);

    final ImageButton speedButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/arrowUp.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    speedButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (money.getMoney() > 0 && speedClicked == false) {
          money.takeMoney(1);
          GameScreen.speedClick = true;
          speedClicked = true;
          for (int i = 0; i < ChefManager.chefs.size(); i++) {
            ChefManager.chefs.get(i).speed = 6f;
          }

        }
      }
    });
    speedButton.setPosition(75, 100);
    speedButton.setSize(40, 40);

    final ImageButton lifeButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/heart.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    lifeButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (money.getMoney() > 0 && lifeClicked == false) {
          money.takeMoney(1);
          lives.addLives();
          lifeClicked = true;
        }
      }
    });
    lifeButton.setPosition(75, 50);
    lifeButton.setSize(40, 40);

    final ImageButton cookButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/wrench.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    cookButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (money.getMoney() > 0 && cookClicked == false) {
          money.takeMoney(1);
          cookClicked = true;
          GameScreen.cookClick = true;

          BakingStation.totalTimeToCook = 5f;
          CookingStation.totalTimeToCook = 5f;
        }
      }
    });
    cookButton.setPosition(30, 50);
    cookButton.setSize(40, 40);

    final ImageButton chopButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/knife.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    chopButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (money.getMoney() > 0 && chopClicked == false) {
          money.takeMoney(1);
          chopClicked = true;
          GameScreen.chopClick = true;

          ChoppingStation.totalTimeToChop = 2f;
        }
      }
    });
    chopButton.setPosition(30, 100);
    chopButton.setSize(40, 40);

    final ImageButton burnButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/warning.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    burnButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        if (money.getMoney() > 0 && burnClicked == false) {
          money.takeMoney(1);
          burnClicked = true;
          GameScreen.burnClick = true;
          CookingStation.totalTimeToBurn = 45f;
          BakingStation.totalTimeToBurn = 25f;

        }
      }
    });
    burnButton.setPosition(30, 150);
    burnButton.setSize(40, 40);

    final ImageButton saveButton = game.getButtonManager().createImageButton(new TextureRegionDrawable(
        new Texture(
            Gdx.files.internal("Kenney-Game-Assets-1/2D assets/Game Icons/PNG/Black/2x/import.png"))),
        ButtonManager.ButtonColour.BLUE, -1.5f);
    saveButton.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {

        prefs.putFloat("chef1x", ChefManager.chefs.get(0).getX());
        prefs.putFloat("chef2x", ChefManager.chefs.get(1).getX());
        prefs.putFloat("chef3x", ChefManager.chefs.get(2).getX());
        prefs.putFloat("chef4x", ChefManager.chefs.get(3).getX());
        prefs.putFloat("chef1y", ChefManager.chefs.get(0).getY());
        prefs.putFloat("chef2y", ChefManager.chefs.get(1).getY());
        prefs.putFloat("chef3y", ChefManager.chefs.get(2).getY());
        prefs.putFloat("chef4y", ChefManager.chefs.get(3).getY());
        prefs.putInteger("difficulty", HomeScreen.difficulty);
        prefs.putInteger("mode", HomeScreen.mode);
        prefs.putInteger("lives", (int) UIOverlay.lives.getLives());
        prefs.putInteger("money", (int) UIOverlay.money.getMoney());
        prefs.putFloat("timer", timer.getTime());
        prefs.putBoolean("buyClicked", buyClicked);
        prefs.putBoolean("lifeClicked", lifeClicked);
        prefs.putBoolean("speedClicked", speedClicked);
        prefs.putBoolean("cookClicked", cookClicked);
        prefs.putBoolean("chopClicked", chopClicked);
        prefs.putFloat("speedTime", GameScreen.speedTime);
        prefs.flush();
      }
    });
    saveButton.setPosition(900, 200);
    saveButton.setSize(40, 40);

    removeBtnDrawable = new TextureRegionDrawable(
        new Texture("Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_crossWhite.png"));

    // Initialize the UI to display the currently requested recipe
    Stack recipeDisplay = new Stack();
    recipeImagesBG = new Image(new Texture(
        "Kenney-Game-Assets-1/2D assets/UI Base Pack/PNG/grey_button_square_gradient_down.png"));
    recipeImagesBG.setVisible(false);
    recipeDisplay.add(recipeImagesBG);
    recipeImages = new VerticalGroup();
    recipeDisplay.add(recipeImages);

    // Initialize counter for showing remaining recipes
    LabelStyle counterStyle = new LabelStyle(game.getFontManager().getHeaderFont(), Color.BLACK);
    recipeCountLabel = new Label("0", counterStyle);

    // Initialize winning label
    LabelStyle labelStyle = new Label.LabelStyle(game.getFontManager().getTitleFont(), null);
    resultLabel = new Label("Congratulations! Your final time was:", labelStyle);
    resultLabel.setVisible(false);
    resultTimer = new Timer(labelStyle);
    resultTimer.setVisible(false);
    loseLabel = new Label("Fail! You've Lost all your reputation!:", labelStyle);
    loseLabel.setVisible(false);

    // Add everything
    uiStage.addActor(buyButton);
    uiStage.addActor(speedButton);
    uiStage.addActor(lifeButton);
    uiStage.addActor(cookButton);
    uiStage.addActor(chopButton);
    uiStage.addActor(burnButton);
    uiStage.addActor(saveButton);

    Value scale = Value.percentWidth(0.04f, table);
    Value timerWidth = Value.percentWidth(0.2f, table);

    table.add(chefDisplay).left().width(scale).height(scale);

    table.add(money).left().width(scale).height(scale);

    table.add(timer).center().width(timerWidth).height(scale);
    table.add(homeButton).right().width(scale).height(scale);
    table.row().padTop(10f);
    table.add(ingredientStackDisplay).left().top().width(scale);
    table.add().expandX().width(timerWidth);
    table.add(lives).right().width(scale).height(scale);

    table.add(recipeDisplay).right().top().width(scale);
    table.row();
    table.add(resultLabel).colspan(3);
    table.row();
    table.add(loseLabel).colspan(3);
    table.row();

    table.add(resultTimer).colspan(3);
  }

  /**
   * Reset values and UI to be in their default state.
   */
  public void init() {
    if (HomeScreen.load == false) {
      timer.reset();
      timer.start();
      money.reset();
      loseLabel.setVisible(false);
      resultLabel.setVisible(false);
      resultTimer.setVisible(false);
      updateChefUI(null);
      buyClicked = false;
      lifeClicked = false;
      speedClicked = false;
      cookClicked = false;
      chopClicked = false;
      ChoppingStation.totalTimeToChop = 5f;
      BakingStation.totalTimeToCook = 10f;
      CookingStation.totalTimeToCook = 10f;
      for (int i = 0; i < ChefManager.chefs.size(); i++) {
        ChefManager.chefs.get(i).speed = 3f;
      }
      GameScreen.speedClick = false;
      GameScreen.speedTime = 0f;
      GameScreen.chopClick = false;
      GameScreen.chopTime = 0f;
      GameScreen.cookClick = false;
      GameScreen.cookTime = 0f;
      GameScreen.burnClick = false;
      GameScreen.burnTime = 0f;
      CookingStation.totalTimeToBurn = 30f;
      BakingStation.totalTimeToBurn = 15f;
      lives.reset();
      chefXsave.set(0, 3f);
      chefXsave.set(1, 11f);
      chefXsave.set(2, 12f);
      chefXsave.set(3, 3f);
      chefYsave.set(0, 3f);
      chefYsave.set(1, 3f);
      chefYsave.set(2, 6f);
      chefYsave.set(3, 7f);
    } else {

      lives.setLives(prefs.getInteger("lives", 3));
      money.setMoney(prefs.getInteger("money", 0));
      HomeScreen.difficulty = prefs.getInteger("difficulty", HomeScreen.difficulty);
      HomeScreen.mode = prefs.getInteger("mode", 0);
      chefXsave.set(0, prefs.getFloat("chef1x"));
      chefXsave.set(1, prefs.getFloat("chef2x"));
      chefXsave.set(2, prefs.getFloat("chef3x"));
      chefXsave.set(3, prefs.getFloat("chef4x"));
      chefYsave.set(0, prefs.getFloat("chef1y"));
      chefYsave.set(1, prefs.getFloat("chef2y"));
      chefYsave.set(2, prefs.getFloat("chef3y"));
      chefYsave.set(3, prefs.getFloat("chef4y"));
      timer.setTime(prefs.getFloat("timer"));
      buyClicked = prefs.getBoolean("buyClicked");
      lifeClicked = prefs.getBoolean("lifeClicked");
      speedClicked = prefs.getBoolean("speedClicked");
      cookClicked = prefs.getBoolean("cookClicked");
      chopClicked = prefs.getBoolean("chopClicked");
      GameScreen.speedTime = prefs.getFloat("speedTime");
    }
  }

  /**
   * Show the image of the currently selected chef as well as have the stack of
   * ingredients
   * currently held by the chef.
   *
   * @param chef The chef that is currently selected for which to show the UI.
   */
  public void updateChefUI(final Chef chef) {
    if (chef == null) {
      chefImage.setDrawable(null);
      ingredientImages.clearChildren();
      ingredientImagesBG.setVisible(false);
      return;
    }
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

  }

  /**
   * Show the label displaying that the game has finished along with the time it
   * took to complete.
   */
  public void finishGameUI() {
    resultLabel.setVisible(true);
    resultTimer.setTime(timer.getTime());
    resultTimer.setVisible(true);
    timer.stop();
  }

  public void loseGameUI() {
    loseLabel.setVisible(true);
    resultTimer.setTime(timer.getTime());
    resultTimer.setVisible(true);
    timer.stop();
  }

  /**
   * Show the current requested recipe that the player needs to make, the
   * ingredients for that, and
   * the number of remaining recipes.
   *
   * @param recipe The recipe to display the ingredients for.
   */
  public void updateRecipeUI(Recipe recipe) {
    // recipe will be null when we reach the end of the scenario
    if (recipe == null) {
      recipeImages.clearChildren();
      recipeImagesBG.setVisible(false);
      return;
    }
    recipeImages.clearChildren();
    recipeImages.addActor(recipeCountLabel);

    for (String recipeIngredient : recipe.getRecipeIngredients()) {
      Image image = new Image(recipe.getTextureManager().getTexture(recipeIngredient));
      image.getDrawable().setMinHeight(chefDisplay.getHeight());
      image.getDrawable().setMinWidth(chefDisplay.getWidth());
      recipeImages.addActor(image);
    }
    recipeImages.addActor(pointer);
    Image recipeImage = new Image(recipe.getTexture());
    recipeImage.getDrawable().setMinHeight(chefDisplay.getHeight());
    recipeImage.getDrawable().setMinWidth(chefDisplay.getWidth());
    recipeImages.addActor(recipeImage);
    recipeImagesBG.setVisible(true);
  }

  /**
   * Update the number of remaining recipes to be displayed.
   *
   * @param remainingRecipes The number of remaining recipes.
   */
  public void updateRecipeCounter(int remainingRecipes) {
    recipeCountLabel.setText(remainingRecipes);
  }
}
