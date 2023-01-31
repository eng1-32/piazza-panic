package cs.eng1.piazzapanic.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;

public class SettingsScreen implements Screen {

    private Stage stage;
    private Table table;
    private TextButton backButton;
    private TextButton saveButton;

    // TextButton colorBlindSettings

    // TO DO: Color blind settings in own screen or either in this screen.
    // TO Do: More options needed
    PiazzaPanicGame game;



    public SettingsScreen(PiazzaPanicGame game){
        this.game = game;
        this.stage = new Stage(new ExtendViewport(1280, 720)); // temporary default screen size
        this.table = new Table();

    }


    @Override
    public void show() {
        System.out.println("Settings - Piazza Panic");
        Gdx.input.setInputProcessor(stage);

        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,1f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void initButtons(){
        backButton = game.getButtonManager().createTextButton("Back", ButtonManager.ButtonColour.GREY);
        stage.addActor(backButton);
        table.add(backButton).expandX();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });

        saveButton = game.getButtonManager().createTextButton("Save", ButtonManager.ButtonColour.GREY);
        // TO DO Add functionality to save button if needed
        stage.addActor(saveButton);
        table.add(saveButton);
        table.right().bottom().right();



    }
}
