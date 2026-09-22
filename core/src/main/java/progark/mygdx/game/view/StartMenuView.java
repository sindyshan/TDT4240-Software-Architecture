package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import progark.mygdx.game.main;

public class StartMenuView {

    private Texture backgroundTexture;
    private Texture buttonTexture;
    private Texture newGameButtonTexture;
    private Texture tutorialButtonTexture;
    private Texture settingsButtonTexture;
    private OrthographicCamera camera;
    private Rectangle settingsButtonBounds;
    private Rectangle joinGameButtonBounds;
    private Rectangle newGameButtonBounds;
    private Rectangle tutorialButtonBounds;
    private Stage stage;
    private boolean isNewGamePressed = false;
    private boolean isJoinGamePressed = false;
    private boolean isTutorialBtnPressed = false;
    private boolean isSettingsBtnPressed = false;

    public StartMenuView(OrthographicCamera camera) {
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);

        // Load background and button images
        backgroundTexture = new Texture("gui/Backgrounds/HomePage.png");
        buttonTexture = new Texture("gui/Buttons/JoinGameButton.png");
        newGameButtonTexture = new Texture("gui/Buttons/NewGameButton.png");
        tutorialButtonTexture = new Texture("gui/Buttons/TutorialButton.png");
        settingsButtonTexture = new Texture("gui/Buttons/SettingsButton.png");

        // Define button dimensions
        float buttonWidth = main.WIDTH / 2;
        float buttonHeight = buttonTexture.getHeight() * (buttonWidth / buttonTexture.getWidth());

        // Calculate button positions
        float buttonX = (main.WIDTH - buttonWidth) / 2;
        float buttonY = (main.HEIGHT - buttonHeight * 3) / 2 + 100;

        float newGameButtonY = buttonY - buttonHeight - 20; // 20 pixels spacing
        float tutorialButtonY = newGameButtonY - buttonHeight - 20;

        float settingsBtnWidth = main.WIDTH * 0.09f;
        float settingsBtnHeight = settingsButtonTexture.getHeight() * (settingsBtnWidth / settingsButtonTexture.getWidth()) - 10;
        float settingsButtonX = main.WIDTH - settingsBtnWidth - 10;
        float settingsButtonY = main.HEIGHT - settingsBtnHeight - 5;

        // Initialize button bounds
        settingsButtonBounds = new Rectangle(settingsButtonX, settingsButtonY, settingsBtnWidth, settingsBtnHeight);
        joinGameButtonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        newGameButtonBounds = new Rectangle(buttonX, newGameButtonY, buttonWidth, buttonHeight);
        tutorialButtonBounds = new Rectangle(buttonX, tutorialButtonY, buttonWidth, buttonHeight);

    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);


        if (isJoinGamePressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {
            batch.setColor(1, 1, 1, 1);
        }
        batch.draw(buttonTexture, joinGameButtonBounds.x, joinGameButtonBounds.y, joinGameButtonBounds.width, joinGameButtonBounds.height);

        if (isNewGamePressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {
            batch.setColor(1, 1, 1, 1);
        }
        batch.draw(newGameButtonTexture, newGameButtonBounds.x, newGameButtonBounds.y, newGameButtonBounds.width, newGameButtonBounds.height);

        if (isTutorialBtnPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {
            batch.setColor(1, 1, 1, 1);
        }
        batch.draw(tutorialButtonTexture, tutorialButtonBounds.x, tutorialButtonBounds.y, tutorialButtonBounds.width, tutorialButtonBounds.height);

        if (isSettingsBtnPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {
            batch.setColor(1, 1, 1, 1);
        }
        batch.draw(settingsButtonTexture, settingsButtonBounds.x, settingsButtonBounds.y, settingsButtonBounds.width, settingsButtonBounds.height);

        batch.setColor(1, 1, 1, 1);
        batch.end();
    }

    public Rectangle getSettingsButtonBounds() {
        return settingsButtonBounds;
    }

    public Rectangle getJoinGameButtonBounds() {
        return joinGameButtonBounds;
    }

    public Rectangle getNewGameButtonBounds() {
        return newGameButtonBounds;
    }

    public Rectangle getTutorialButtonBounds() {
        return tutorialButtonBounds;
    }

    public void setNewGamePressed(boolean pressed){
        this.isNewGamePressed = pressed;
    }
    public void setJoinGamePressed(boolean pressed){
        this.isJoinGamePressed = pressed;
    }

    public void setTutorialBtnPressed(boolean pressed) {
        this.isTutorialBtnPressed = pressed;
    }

    public void setSettingsBtnPressed(boolean pressed) {
        this.isSettingsBtnPressed = pressed;
    }

    public void dispose() {
        backgroundTexture.dispose();
        buttonTexture.dispose();
        newGameButtonTexture.dispose();
        tutorialButtonTexture.dispose();
        settingsButtonTexture.dispose();
        stage.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
