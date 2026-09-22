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
import progark.mygdx.game.manager.MusicManager;

public class SettingsView {
    private OrthographicCamera camera;

    private Texture backgroundTexture;
    private Texture crossButtonTexture;
    private Texture settingsTitleTexture;
    private Texture colorBlindTextTexture;
    private Texture musicTextTexture;
    private Texture toggleBtnOffTexture;
    private Texture toggleBtnOnTexture;

    private Rectangle crossButtonBounds;
    private Rectangle toggleMusicBounds;
    private Rectangle toggleColorBlindBounds;
    private Stage stage;

    private boolean isMusicOn;
    private boolean isXbuttonpressed = false;
    private boolean isColorBlindModeEnabled = false;

    public SettingsView(OrthographicCamera camera) {
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);

        isMusicOn = MusicManager.getInstance().isMusicOn();

        backgroundTexture = new Texture("gui/Backgrounds/PlainBackground.png");
        crossButtonTexture = new Texture("gui/Buttons/CrossButton.png");
        settingsTitleTexture = new Texture("gui/Titles/SettingsTitle.png");
        colorBlindTextTexture = new Texture("gui/Texts/ColorBlindText.png");
        musicTextTexture = new Texture("gui/Texts/MusicText.png");
        toggleBtnOffTexture = new Texture("gui/Buttons/ToggleBtnOff.png");
        toggleBtnOnTexture = new Texture("gui/Buttons/ToggleBtnOn.png");

        float crossButtonWidth = main.WIDTH * 0.07f;
        float crossButtonHeight = crossButtonTexture.getHeight() * (crossButtonWidth / crossButtonTexture.getWidth());
        float crossButtonX = main.WIDTH - crossButtonWidth - 50;
        float crossButtonY = main.HEIGHT - crossButtonHeight - 50;
        crossButtonBounds = new Rectangle(crossButtonX, crossButtonY, crossButtonWidth, crossButtonHeight);

        float colorBlindToggleWidth = main.WIDTH * 0.2f;
        float colorBlindToggleHeight = toggleBtnOffTexture.getHeight() * (colorBlindToggleWidth / toggleBtnOffTexture.getWidth());
        float colorBlindToggleX = (Gdx.graphics.getWidth() - colorBlindToggleWidth) / 2;
        float colorBlindToggleY = main.HEIGHT - 1200;
        toggleColorBlindBounds = new Rectangle(colorBlindToggleX, colorBlindToggleY, colorBlindToggleWidth, colorBlindToggleHeight);

        float musicToggleY = main.HEIGHT - 1700;
        toggleMusicBounds = new Rectangle(colorBlindToggleX, musicToggleY, colorBlindToggleWidth, colorBlindToggleHeight);
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);

        float titleWidth = main.WIDTH;
        float titleHeight = settingsTitleTexture.getHeight() * (titleWidth / settingsTitleTexture.getWidth()) - 20;
        float titleX = 0;
        float titleY = main.HEIGHT - titleHeight - 300;
        batch.draw(settingsTitleTexture, titleX, titleY, titleWidth, titleHeight);

        if (isXbuttonpressed) {
            batch.setColor(new Color(0.6f, 0.6f, 0.6f, 1));
        } else {
            batch.setColor(1,1,1,1);
        }
        batch.draw(crossButtonTexture, crossButtonBounds.x, crossButtonBounds.y, crossButtonBounds.width, crossButtonBounds.height);
        batch.setColor(1,1,1,1);

        float colorBlindTextWidth = main.WIDTH * 0.8f;
        float colorBlindTextHeight = colorBlindTextTexture.getHeight() * (colorBlindTextWidth / colorBlindTextTexture.getWidth());
        float colorBlindTextX = (Gdx.graphics.getWidth() - colorBlindTextWidth) / 2;
        batch.draw(colorBlindTextTexture, colorBlindTextX, titleY - 400, colorBlindTextWidth, colorBlindTextHeight);

        float musicTextWidth = main.WIDTH * 0.3f;
        float musicTextX = (Gdx.graphics.getWidth() - musicTextWidth) / 2;
        batch.draw(musicTextTexture, musicTextX, titleY - 900, musicTextWidth, colorBlindTextHeight);

        Texture switchMusicTexture = isMusicOn ? toggleBtnOnTexture : toggleBtnOffTexture;
        batch.draw(switchMusicTexture, toggleMusicBounds.x, toggleMusicBounds.y, toggleMusicBounds.width, toggleMusicBounds.height);

        Texture switchColorBlindTexture = isColorBlindModeEnabled ? toggleBtnOnTexture : toggleBtnOffTexture;
        batch.draw(switchColorBlindTexture, toggleColorBlindBounds.x, toggleColorBlindBounds.y, toggleColorBlindBounds.width, toggleColorBlindBounds.height);

        batch.end();
    }

    public void toggleMusic() {
        isMusicOn = !isMusicOn;
    }

    public void toggleColorBlind() {
        isColorBlindModeEnabled = !isColorBlindModeEnabled;
    }

    public Rectangle getCrossButtonBounds() {
        return crossButtonBounds;
    }

    public Rectangle getToggleMusicBounds() {
        return toggleMusicBounds;
    }

    public Rectangle getToggleColorBlindBounds() {
        return toggleColorBlindBounds;
    }

    public Stage getStage(){
        return this.stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void setXbuttonpressed(boolean pressed) {
        this.isXbuttonpressed = pressed;
    }

    public void dispose() {
        backgroundTexture.dispose();
        crossButtonTexture.dispose();
        settingsTitleTexture.dispose();
        colorBlindTextTexture.dispose();
        musicTextTexture.dispose();
        toggleBtnOffTexture.dispose();
        toggleBtnOnTexture.dispose();
    }
}
