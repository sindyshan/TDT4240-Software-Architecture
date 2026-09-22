package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

import progark.mygdx.game.main;
import progark.mygdx.game.model.Game;

public class LobbyView {
    private Texture backgroundTexture;
    private Texture lobbyTitleTexture;
    private Texture pinTextTexture;
    private Texture playersTextTexture;
    private Texture startGameButtonTexture;
    private Texture exitButtonTexture;
    private Texture settingsButtonTexture;

    private Rectangle startGameButtonBounds;
    private Rectangle exitButtonBounds;
    private Rectangle settingsButtonBounds;

    private OrthographicCamera camera;
    private String gamepin;
    private List<String> players = new ArrayList<>();
    private String hostNickname;

    private BitmapFont font;
    private Stage stage;
    private boolean isSettingsButtonPressed = false;
    private boolean isStartGameButtonPressed = false;
    private boolean isExitButtonPressed = false;
    private Game game;


    public LobbyView(OrthographicCamera camera, Game game) {
        // Load the textures (images)
        backgroundTexture = new Texture("gui/Backgrounds/Background2.png");
        lobbyTitleTexture = new Texture("gui/Titles/LobbyTitle.png");
        pinTextTexture = new Texture("gui/Texts/PinText.png");
        playersTextTexture = new Texture("gui/Texts/PlayersText.png");
        startGameButtonTexture = new Texture("gui/Buttons/StartGameButton.png");
        exitButtonTexture = new Texture("gui/Buttons/ExitButton.png");
        settingsButtonTexture = new Texture("gui/Buttons/SettingsButton.png");

        this.game = game;
        this.gamepin = game.getPin();
        // Setting up camera
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);

        // Define selfmade font
        font = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
        font.getData();
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);

        // Size and placing of title
        float titleWidth = main.WIDTH;
        float titleHeight = lobbyTitleTexture.getHeight() * (titleWidth / lobbyTitleTexture.getWidth()) - 20;
        float titleX = 0;
        float titleY = main.HEIGHT - titleHeight - 45;
        batch.draw(lobbyTitleTexture, titleX, titleY, titleWidth, titleHeight);

        // Size and placing of settingsbutton
        float settingsButtonWidth = main.WIDTH * 0.09f;
        float settingsBtnHeight = settingsButtonTexture.getHeight() * (settingsButtonWidth / settingsButtonTexture.getWidth()) -10;
        if (isSettingsButtonPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {batch.setColor(1, 1, 1, 1);}
        //batch.draw(settingsButtonTexture, main.WIDTH - settingsButtonWidth - 10, main.HEIGHT - settingsBtnHeight - 5, settingsButtonWidth, settingsBtnHeight);
        batch.setColor(1, 1, 1, 1);

        // Size of other buttons
        float buttonWidth = main.WIDTH / 2;
        float buttonHeight = startGameButtonTexture.getHeight() * (buttonWidth / startGameButtonTexture.getWidth());
        float buttonX = (main.WIDTH - buttonWidth) / 2; // All placed in center of screen

        // Placing of startGameButton
        float startButtonY = main.HEIGHT / 4;
        if (isStartGameButtonPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {batch.setColor(1, 1, 1, 1);}
        batch.draw(startGameButtonTexture, buttonX, startButtonY, buttonWidth, buttonHeight);
        batch.setColor(1, 1, 1, 1);

        // Placing of exitGameButton
        float exitButtonY = startButtonY - buttonHeight - main.HEIGHT / 100;
        if (isExitButtonPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1);
        } else {batch.setColor(1, 1, 1, 1);}
        batch.draw(exitButtonTexture, buttonX, exitButtonY, buttonWidth, buttonHeight);
        batch.setColor(1, 1, 1, 1);

        // Size and placing of "Pin"-text
        float textWidth = main.WIDTH / 2;
        float textHeight = pinTextTexture.getHeight() * (textWidth / pinTextTexture.getWidth());
        float textX = (main.WIDTH - textWidth) / 2;
        float textY = (main.HEIGHT - titleHeight * 2);
        batch.draw(pinTextTexture, textX, textY, textWidth, textHeight);

        // Placing of the actual pincode
        font.getData().setScale(3.5f);
        float pinTextX = main.WIDTH / 2;
        float pinTextY = textY + textHeight;
        font.draw(batch, gamepin, pinTextX, pinTextY);

        // Size and placing of "Players"-text
        float playersTextY = textY - textHeight * 2;
        batch.draw(playersTextTexture, textX, playersTextY, textWidth, textHeight);

        // Placing of the names of players
        float playerY = playersTextY - main.HEIGHT / 100;
        for (String player : players) {
            font.draw(batch, player, textX, playerY);
            playerY -= main.HEIGHT / 25;
        }

        // Initialize button bounds
        startGameButtonBounds = new Rectangle(buttonX, startButtonY, buttonWidth, buttonHeight);
        exitButtonBounds = new Rectangle(buttonX, exitButtonY, buttonWidth, buttonHeight);
        settingsButtonBounds = new Rectangle(main.WIDTH - settingsButtonWidth - 10, main.HEIGHT - settingsBtnHeight - 5, settingsButtonWidth, settingsBtnHeight);
        batch.end();
    }

    public Rectangle getStartGameButtonBounds() {
        return startGameButtonBounds;
    }

    public Rectangle getExitButtonBounds() {
        return exitButtonBounds;
    }

    public Rectangle getSettingsButtonBounds() {
        return settingsButtonBounds;
    }

    public void setSettingsButtonPressed(boolean pressed){
        this.isSettingsButtonPressed = pressed;
    }

    public void setStartGameButtonPressed(boolean pressed){
        this.isStartGameButtonPressed = pressed;
    }

    public void setExitButtonPressed(boolean pressed){
        this.isExitButtonPressed = pressed;
    }

    public void dispose() {
        backgroundTexture.dispose();
        startGameButtonTexture.dispose();
        exitButtonTexture.dispose();
        settingsButtonTexture.dispose();
        pinTextTexture.dispose();
        playersTextTexture.dispose();
        font.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }

    public void updatePlayers(List<String> players){
        this.players.clear();
        this.players.addAll(players);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
