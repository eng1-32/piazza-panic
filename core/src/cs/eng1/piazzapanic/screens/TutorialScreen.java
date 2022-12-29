package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.ui.ButtonManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class TutorialScreen implements Screen {

    private Stage stage;
    private Table table;
    private TextButton backButton;

    PiazzaPanicGame game;


    public TutorialScreen(PiazzaPanicGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.table = new Table();

    }

    @Override
    public void show() {
        System.out.println("Tutorial");
        Gdx.input.setInputProcessor(stage);

        initButtons();

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    public void initButtons() {
        backButton = PiazzaPanicGame.getButtonManager().createTextButton("Back", ButtonManager.ButtonColour.GREY);
        stage.addActor(backButton);
        table.add(backButton).expandX();
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });

    }
}
