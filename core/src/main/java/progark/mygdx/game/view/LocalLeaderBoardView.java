package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import progark.mygdx.game.main;

public class LocalLeaderBoardView {
    private OrthographicCamera camera;
    private Texture background;
    private Texture leaderboardFrame;
    private Texture exitButton;
    private Texture leaderboardTitle;
    private BitmapFont playerFont;
    private Map<String, Integer> playerScoresMap;
    private Rectangle exitButtonBounds;
    private Boolean isExitButtonPressed = false;
    private Stage stage;


    public LocalLeaderBoardView(OrthographicCamera camera) {
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("gui/Backgrounds/Background1.png");
        leaderboardFrame = new Texture("gui/Backgrounds/LeaderboardFrame.png");
        exitButton = new Texture("gui/Buttons/ExitButton.png");
        leaderboardTitle = new Texture("gui/Titles/LeaderboardTitle.png");

        playerFont = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
        playerFont.getData().setScale(2.5f);

        float buttonWidth = main.WIDTH / 2;
        float buttonHeight = exitButton.getHeight() * (buttonWidth / exitButton.getWidth());
        float buttonX = (main.WIDTH - buttonWidth) / 2;
        float buttonY = (main.HEIGHT - main.HEIGHT * 0.9f) - buttonHeight;

        exitButtonBounds = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, main.WIDTH, main.HEIGHT);

        float frameWidth = main.WIDTH * 0.8f;
        float frameHeight = main.HEIGHT * 0.7f;
        float frameX = (main.WIDTH - frameWidth) / 2;
        float frameY = (main.HEIGHT - frameHeight) / 2;
        batch.draw(leaderboardFrame, frameX, frameY, frameWidth, frameHeight);

        float originalTitleWidth = leaderboardTitle.getWidth();
        float originalTitleHeight = leaderboardTitle.getHeight();
        float titleWidth = frameWidth * 1.2f;
        float titleHeight = (originalTitleHeight / originalTitleWidth) * titleWidth;
        float titleX = frameX + (frameWidth - titleWidth) / 2;
        float titleY = frameY + frameHeight;
        batch.draw(leaderboardTitle, titleX, titleY, titleWidth, titleHeight);

        if (isExitButtonPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1); // Darker shade when pressed
        } else {
            batch.setColor(1, 1, 1, 1); // Normal color
        }
        batch.draw(exitButton, exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);
        batch.setColor(1, 1, 1, 1);

        if (playerScoresMap != null) {
            float yPos = titleY - exitButtonBounds.y;
            int place = 1;

            // Sort the player scores by score (high to low)
            List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(playerScoresMap.entrySet());
            sortedScores.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

            // Render each player's scores
            for (Map.Entry<String, Integer> entry : sortedScores) {
                String playerName = entry.getKey();
                int playerScore = entry.getValue();

                playerFont.draw(batch, String.valueOf(place) + ".", main.WIDTH/6, yPos);
                playerFont.draw(batch, playerName, main.WIDTH/3.5f, yPos);
                playerFont.draw(batch, String.valueOf(playerScore), main.WIDTH/1.35f, yPos);

                yPos -= titleHeight / 2;
                place++;
            }
        }

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void setMap(Map<String, Integer> playerScores) {
        this.playerScoresMap = playerScores;
    }

    public Rectangle getExitButtonBounds() {
        return exitButtonBounds;
    }

    public void setExitButtonPressed(boolean pressed) {
        isExitButtonPressed = pressed;
    }

    public void dispose() {
        background.dispose();
        leaderboardTitle.dispose();
        leaderboardFrame.dispose();
        exitButton.dispose();
        stage.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
