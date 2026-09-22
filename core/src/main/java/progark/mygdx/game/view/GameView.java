package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import progark.mygdx.game.main;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.model.pegcombination.peg.CodePeg;
import progark.mygdx.game.model.pegcombination.peg.PegInterface;
import progark.mygdx.game.model.pegcombination.PegCombination;

import java.util.List;
import java.util.Objects;

public class GameView {
    private SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Stage stage;

    private final Texture colorMysteryLogo;
    private final Texture backgroundTexture;

    private final Texture crossBtnTexture;
    private final Texture holeTexture;
    private final Texture ringAroundHoleTexture;
    private final Texture boardTexture;
    private final Texture chooseColorTexture;
    private final Texture[] colorPegs;
    private final Texture blackKeyPegTexture;
    private final Texture whiteKeyPegTexture;
    private final Texture guessBtnTexture;
    private Texture winPopupTexture;
    private Texture losePopupTexture;

    private final Texture homeBtnTexture;
    private final Texture nextBtnTexture;

    private Rectangle homeBtnBounds;
    private Rectangle nextBtnBounds;

    private Rectangle crossBtnBounds;

    private final BitmapFont font;

    private List<PegCombination> keyPegs;

    private final int numHolesPerGuess;
    private float codePegHoleSize;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private List<PegCombination> pegRows;
    private List<String> availableColorNames;

    private float guessButtonX, guessButtonY, guessButtonWidth, guessButtonHeight;
    private int activeGuessRow = -1;

    private boolean showWinPopup = false;
    private boolean showLosePopup = false;

    public GameView(OrthographicCamera camera, Game game) {
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);

        this.numHolesPerGuess = game.getMode().getNumPegs();
        this.availableColorNames = game.getAvailableColors();

        loadEndGameTextures();

        colorMysteryLogo = new Texture("gui/Titles/ColorMysteryLogo.png");
        backgroundTexture = new Texture("gui/Backgrounds/Background1.png");
        crossBtnTexture = new Texture("gui/Buttons/CrossButton.png");
        holeTexture = new Texture("gui/Pegs/PegHole.png");
        ringAroundHoleTexture = new Texture("gui/Pegs/BlackRing.png");
        boardTexture = new Texture("gui/Pegs/Board.png");
        chooseColorTexture = new Texture("gui/Pegs/SmallBoard.png");
        guessBtnTexture = new Texture("gui/Buttons/GuessBtn.png");
        blackKeyPegTexture = new Texture("gui/Pegs/BlackPeg.png");
        whiteKeyPegTexture = new Texture("gui/Pegs/WhitePeg.png");
        homeBtnTexture = new Texture("gui/PopUps/HomeBtn.png");
        nextBtnTexture = new Texture("gui/PopUps/NextBtn.png");

        colorPegs = new Texture[]{
            new Texture("gui/Pegs/RedPeg.png"),
            new Texture("gui/Pegs/GreenPeg.png"),
            new Texture("gui/Pegs/BluePeg.png"),
            new Texture("gui/Pegs/YellowPeg.png"),
            new Texture("gui/Pegs/OrangePeg.png"),
            new Texture("gui/Pegs/PurplePeg.png"),
            new Texture("gui/Pegs/PinkPeg.png")
        };

        font = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
        font.getData().setScale(2f);
        font.setColor(Color.WHITE);
    }

    public void setPegRows(List<PegCombination> pegRows) {
        this.pegRows = pegRows;
    }

    public void render(SpriteBatch batch) {
        this.batch = batch;
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        drawInitialization();
        drawBoard();
        drawColorSelection();

        if (showWinPopup && winPopupTexture != null) {
            drawPopup(batch, winPopupTexture, "Attempts: " + activeGuessRow);
        } else if (showLosePopup && losePopupTexture != null) {
            drawPopup(batch, losePopupTexture, "Attempts: " + activeGuessRow);
        }

        batch.end();
    }

    private void drawInitialization() {
        float crossBtnWidth = main.WIDTH * 0.09f;
        float crossBtnHeight = crossBtnTexture.getHeight() * (crossBtnWidth / crossBtnTexture.getWidth()) - 10;
        float crossBtnX = main.WIDTH - crossBtnWidth - 10;
        float crossBtnY = main.HEIGHT - crossBtnHeight - 5;

        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);
        batch.draw(colorMysteryLogo, 30, main.HEIGHT * 0.87f, main.WIDTH - 50, main.HEIGHT / 14);
        batch.draw(crossBtnTexture, crossBtnX, crossBtnY, crossBtnWidth, crossBtnHeight);

        crossBtnBounds = new Rectangle(crossBtnX, crossBtnY, crossBtnWidth, crossBtnHeight);
    }

    private void drawBoard() {
        batch.draw(boardTexture, 50, main.HEIGHT / 8f, main.WIDTH - 100, (main.HEIGHT / 4f) * 3);
        batch.draw(chooseColorTexture, 50, main.WIDTH / 20f, main.WIDTH - 100, main.WIDTH / 7f);

        float screenWidth = main.WIDTH;
        float screenHeight = main.HEIGHT;

        codePegHoleSize = main.HEIGHT / 25f;
        float keyPegHoleSize = screenHeight / 65f;
        float gapX = codePegHoleSize / 3;
        float gapY = codePegHoleSize / 2;

        float boardWidth = numHolesPerGuess * codePegHoleSize + (numHolesPerGuess - 1) * gapX;
        float startX = (screenWidth - boardWidth - 150) / 2;
        float startY = screenHeight / 7f;

        int totalRows = 12;
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < numHolesPerGuess; col++) {
                float x = startX + col * (codePegHoleSize + gapX);
                float y = startY + (totalRows - 1 - row) * (codePegHoleSize + gapY);
                batch.draw(holeTexture, x, y, codePegHoleSize, codePegHoleSize);

                if (pegRows != null && row < pegRows.size()) {
                    PegCombination combination = pegRows.get(row);
                    List<PegInterface> pegs = combination.getPegCombination();

                    if (col < pegs.size()) {
                        PegInterface pegInterface = pegs.get(col);
                        if (pegInterface instanceof CodePeg) {
                            CodePeg peg = (CodePeg) pegInterface;
                            Texture pegTexture = getTextureForColor(peg.getColor().getColorName());
                            if (pegTexture != null) {
                                batch.draw(pegTexture, x, y, codePegHoleSize, codePegHoleSize);
                            }
                        }
                    }
                }

                if (row == selectedRow && col == selectedCol) {
                    float ringSize = codePegHoleSize + 10;
                    batch.draw(ringAroundHoleTexture, x - 5, y - 5, ringSize, ringSize);
                }
            }

            float y = startY + (totalRows - 1 - row) * (codePegHoleSize + gapY);
            drawKeyPegs(startX + boardWidth + 50, y, row, codePegHoleSize, keyPegHoleSize);
        }

        if (pegRows != null && activeGuessRow >= 0 && activeGuessRow < pegRows.size()) {
            PegCombination row = pegRows.get(activeGuessRow);
            boolean isFilled = row.getPegCombination().stream().allMatch(Objects::nonNull);

            if (isFilled) {
                guessButtonHeight = codePegHoleSize;
                float aspectRatio = (float) guessBtnTexture.getWidth() / guessBtnTexture.getHeight();
                guessButtonWidth = guessButtonHeight * aspectRatio;

                guessButtonX = startX + boardWidth + 50;
                guessButtonY = startY + (totalRows - 1 - activeGuessRow) * (codePegHoleSize + gapY);

                batch.draw(guessBtnTexture, guessButtonX, guessButtonY, guessButtonWidth, guessButtonHeight);
            }

        }

    }

    private void drawKeyPegs(float startX, float startY, int row, float codePegHoleSize, float keyPegHoleSize) {
        int pegsInRow = (numHolesPerGuess == 4) ? 2 : 3;
        float xBase = startX;
        float yBase = startY + (codePegHoleSize - keyPegHoleSize);

        // First: draw holes
        for (int i = 0; i < numHolesPerGuess; i++) {
            float x = xBase + (i % pegsInRow) * (keyPegHoleSize + 10);
            float y = yBase - (i / pegsInRow) * (keyPegHoleSize + 10);
            batch.draw(holeTexture, x, y, keyPegHoleSize, keyPegHoleSize);
        }

        // Second: draw key pegs if available for this row
        if (pegRows != null && row < pegRows.size()) {
            if (keyPegs == null || row >= keyPegs.size()) return;
            PegCombination combination = keyPegs.get(row);

            List<PegInterface> pegs = combination.getPegCombination();

            int keyPegIndex = 0;
            for (PegInterface peg : pegs) {
                if (peg instanceof progark.mygdx.game.model.pegcombination.peg.KeyPeg && peg.getColor() != null) {
                    Texture texture = null;
                    String color = peg.getColor().getColorName();
                    if (color.equals("black")) texture = blackKeyPegTexture;
                    else if (color.equals("white")) texture = whiteKeyPegTexture;

                    if (texture != null) {
                        float x = xBase + (keyPegIndex % pegsInRow) * (keyPegHoleSize + 10);
                        float y = yBase - (keyPegIndex / pegsInRow) * (keyPegHoleSize + 10);
                        batch.draw(texture, x, y, keyPegHoleSize, keyPegHoleSize);
                        keyPegIndex++;
                    }
                }
            }
        }
    }

    private void drawColorSelection() {
        float screenWidth = main.WIDTH;
        int spacing = 40;

        int colorCount = availableColorNames.size();
        float totalWidth = colorCount * codePegHoleSize + (colorCount - 1) * spacing;
        float startX = (screenWidth - totalWidth) / 2f;
        int y = 80;

        for (int i = 0; i < colorCount; i++) {
            String colorName = availableColorNames.get(i);
            Texture pegTexture = getTextureForColor(colorName);
            if (pegTexture != null) {
                float x = startX + i * (codePegHoleSize + spacing);
                batch.draw(pegTexture, x, y, codePegHoleSize, codePegHoleSize);
            }
        }
    }

    private Texture getTextureForColor(String colorName) {
        if (colorName == null) return null;

        switch (colorName.toLowerCase()) {
            case "red": return colorPegs[0];
            case "green": return colorPegs[1];
            case "blue": return colorPegs[2];
            case "yellow": return colorPegs[3];
            case "orange": return colorPegs[4];
            case "purple": return colorPegs[5];
            case "pink": return colorPegs[6];
            default: return null;
        }
    }

    public void loadEndGameTextures() {
        winPopupTexture = new Texture("gui/PopUps/YouWinPopUp.png");
        losePopupTexture = new Texture("gui/PopUps/GameOverPopUp.png");
    }

    private void drawPopup(SpriteBatch batch, Texture popupTexture, String attemptsText) {
        float popupWidth = main.WIDTH * 0.7f;
        float popupHeight = main.HEIGHT * 0.35f;
        float popupX = (main.WIDTH - popupWidth) / 2f;
        float popupY = (main.HEIGHT - popupHeight) / 2f - 160;

        batch.draw(popupTexture, popupX, popupY, popupWidth, popupHeight);

        GlyphLayout layout = new GlyphLayout(font, attemptsText);
        float textX = popupX + popupWidth / 2f - layout.width / 2f;
        float textY = popupY + popupHeight * 0.41f;
        font.draw(batch, layout, textX, textY);

        float buttonWidth = popupWidth * 0.3f;
        float buttonHeight = buttonWidth * (homeBtnTexture.getHeight() / (float) homeBtnTexture.getWidth());

        float homeBtnX = popupX + popupWidth * 0.32f - buttonWidth / 2f;
        float nextBtnX = popupX + popupWidth * 0.68f - buttonWidth / 2f;
        float buttonY = popupY + popupHeight * 0.15f;

        batch.draw(homeBtnTexture, homeBtnX, buttonY, buttonWidth, buttonHeight);
        batch.draw(nextBtnTexture, nextBtnX, buttonY, buttonWidth, buttonHeight);

        homeBtnBounds = new com.badlogic.gdx.math.Rectangle(homeBtnX, buttonY, buttonWidth, buttonHeight);
        nextBtnBounds = new com.badlogic.gdx.math.Rectangle(nextBtnX, buttonY, buttonWidth, buttonHeight);
    }


    public Stage getStage() { return stage; }
    public void resize(int width, int height) { stage.getViewport().update(width, height, true); }

    public OrthographicCamera getCamera() { return camera; }
    public void setSelectedHole(int row, int col) {
        this.selectedRow = row;
        this.selectedCol = col;
    }
    public void setKeyPegs(List<PegCombination> keyPegs) {
        this.keyPegs = keyPegs;
    }
    public void setActiveGuessRow(int row) {
        this.activeGuessRow = row;
    }
    public void setShowWinPopup(boolean show) { this.showWinPopup = show; }
    public void setShowLosePopup(boolean show) { this.showLosePopup = show; }

    public float getGuessButtonX() { return guessButtonX; }
    public float getGuessButtonY() { return guessButtonY; }
    public float getGuessButtonWidth() { return guessButtonWidth; }
    public float getGuessButtonHeight() { return guessButtonHeight; }
    public Rectangle getHomeBtnBounds() { return homeBtnBounds; }
    public Rectangle getNextBtnBounds() { return nextBtnBounds; }
    public Rectangle getCrossBtnBounds() {
        return crossBtnBounds;
    }



    public void dispose() {
        holeTexture.dispose();
        ringAroundHoleTexture.dispose();
        boardTexture.dispose();
        backgroundTexture.dispose();
        guessBtnTexture.dispose();
        for (Texture peg : colorPegs) peg.dispose();
        blackKeyPegTexture.dispose();
        whiteKeyPegTexture.dispose();
        font.dispose();
    }
}
