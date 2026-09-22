package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import progark.mygdx.game.main;

public class HowToPlayView {
    private Texture backgroundTexture;
    private Texture tutorialTitleTexture;
    private Texture settingsButtonTexture;
    private Texture backButtonTexture;

    private Rectangle settingsButtonBounds;
    private Rectangle backButtonBounds;

    private boolean isSettingsButtonPressed = false;
    private boolean isBackButtonPressed = false;

    private OrthographicCamera camera;

    private Stage stage;

    private final Color buttonNormalColor = new Color(1, 1, 1, 1);
    private final Color buttonSelectedColor = new Color(0.6f, 0.6f, 0.6f, 1);

    public HowToPlayView(OrthographicCamera camera) {
        // Load the textures (images)
        backgroundTexture = new Texture("gui/Backgrounds/Background3.png");
        tutorialTitleTexture = new Texture("gui/Titles/TutorialTitle.png");
        settingsButtonTexture = new Texture("gui/Buttons/SettingsButton.png");
        backButtonTexture = new Texture("gui/Buttons/BackButton.png");

        // Setting up camera
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);

        // Size and placing of title
        float titleWidth = main.WIDTH;
        float titleHeight = tutorialTitleTexture.getHeight() * (titleWidth / tutorialTitleTexture.getWidth()) - 20;
        float titleX = 0;
        float titleY = main.HEIGHT - titleHeight - 45;
        batch.draw(tutorialTitleTexture, titleX, titleY, titleWidth, titleHeight);

        // Size and placing of backbutton
        float backButtonWidth = main.WIDTH * 0.18f;
        float backBtnHeight = backButtonTexture.getHeight() * (backButtonWidth / backButtonTexture.getWidth());

        if (isBackButtonPressed) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(backButtonTexture, 15, main.HEIGHT - backBtnHeight - 10, backButtonWidth, backBtnHeight);
        batch.setColor(buttonNormalColor);

        // Size and placing of settingsbutton
        float settingsButtonWidth = main.WIDTH * 0.09f;
        float settingsBtnHeight = settingsButtonTexture.getHeight() * (settingsButtonWidth / settingsButtonTexture.getWidth()) -10;
        if (isSettingsButtonPressed) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(settingsButtonTexture, main.WIDTH - settingsButtonWidth - 10, main.HEIGHT - settingsBtnHeight - 5, settingsButtonWidth, settingsBtnHeight);
        batch.setColor(buttonNormalColor);


        // Initialize button bounds
        settingsButtonBounds = new Rectangle(main.WIDTH - settingsButtonWidth - 10, main.HEIGHT - settingsBtnHeight - 5, settingsButtonWidth, settingsBtnHeight);
        backButtonBounds = new Rectangle(15, main.HEIGHT - backBtnHeight - 10, backButtonWidth, backBtnHeight);
        batch.end();
    }

    public Rectangle getBackButtonBounds() {
        return backButtonBounds;
    }

    public void setBackButtonPressed(boolean pressed){
        this.isBackButtonPressed = pressed;
    }

    public Rectangle getSettingsButtonBounds() {
        return settingsButtonBounds;
    }

    public void setSettingsButtonPressed(boolean pressed){
        this.isSettingsButtonPressed = pressed;
    }

    public void dispose() {
        backgroundTexture.dispose();
        settingsButtonTexture.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }
}
