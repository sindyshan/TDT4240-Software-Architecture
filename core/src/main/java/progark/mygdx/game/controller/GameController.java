package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.DecodingBoard;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.model.mode.Mode;
import progark.mygdx.game.model.pegcombination.peg.CodePeg;
import progark.mygdx.game.model.pegcombination.peg.KeyPeg;
import progark.mygdx.game.model.pegcombination.peg.PegInterface;
import progark.mygdx.game.model.pegcombination.PegCombination;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.state.viewstates.GamePlayViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.GameView;

public class GameController extends InputAdapter {
    private final GamePlayViewState gameState;
    private GameContext context;
    private final Game game;
    private final Player player;
    private final DecodingBoard board;
    private final GameView gameView;

    private final int totalRows = 12;
    private float codePegHoleSize;
    private float gapX;
    private float gapY;
    private float startX;
    private float startY;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private int activeGuessRow = 0;

    public GameController(GamePlayViewState gameState, GameContext context) {
        this.gameState = gameState;
        this.context = context;
        this.game = context.getGame();
        this.player = context.getCurrentPlayer();
        if (context.getCurrentPlayer() == null) {
            throw new IllegalStateException("Current player is not set in context. Cannot create DecodingBoard.");
        }
        this.board = new DecodingBoard(game, player);
        this.game.addDecodingBoard(board);
        this.gameView = new GameView(gameState.getCamera(), game);
        Mode mode = game.getMode();

        Gdx.input.setInputProcessor(this);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        codePegHoleSize = screenHeight / 25f;
        gapX = codePegHoleSize / 3;
        gapY = codePegHoleSize / 2;
        float boardWidth = mode.getNumPegs() * codePegHoleSize + (mode.getNumPegs() - 1) * gapX;
        startX = (screenWidth - boardWidth - 150) / 2;
        startY = screenHeight / 7f;

        selectedRow = 0;
        selectedCol = 0;
        activeGuessRow = 0;
        gameView.setSelectedHole(0, 0);
        gameView.setActiveGuessRow(activeGuessRow);

        // For debugging
        List<PegInterface> secret = game.getSecretCode().getPegCombination();
        StringBuilder secretLog = new StringBuilder("Secret Code: ");
        for (PegInterface peg : secret) {
            if (peg == null) {
                secretLog.append("[empty] ");
            } else {
                secretLog.append(peg.getColor().getColorName()).append(" ");
            }
        }
        Gdx.app.log("GuessDebug - GameStart", secretLog.toString());
    }

    public void update(float dt) {
        gameView.setPegRows(board.getCodePegs());
        gameView.setKeyPegs(board.getKeyPegs());
        gameView.getStage().act(dt);
    }

    public void render(SpriteBatch batch) {
        gameView.render(batch);
        gameView.getStage().draw();
    }

    public void resize(int width, int height) {
        gameView.resize(width, height);
    }

    public void dispose() {
        gameView.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        gameView.getCamera().unproject(touchPos);

        if (checkHoleSelection(touchPos.x, touchPos.y)) return true;
        if (checkColorSelection(touchPos.x, touchPos.y)) return true;

        if (gameView.getHomeBtnBounds() != null && gameView.getHomeBtnBounds().contains(touchPos.x, touchPos.y)) {
            gameState.getStateManager().setState(new StartMenuViewState(context));
            return true;
        }

        if (gameView.getNextBtnBounds() != null && gameView.getNextBtnBounds().contains(touchPos.x, touchPos.y)) {
            Gdx.app.log("Popup", "Next button clicked");
            // TODO: Redirect to leaderboard
            return true;
        }

        if (gameView.getCrossBtnBounds().contains(touchPos.x, touchPos.y)) {
            Gdx.app.log("GAME_VIEW", "Cross button clicked. Returning to StartMenuViewState.");
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    gameState.getStateManager().setState(new StartMenuViewState(context));
                }
            }, 0.2f);

        }


        return checkGuessButtonPress(touchPos.x, touchPos.y);
    }

    private boolean checkGuessButtonPress(float x, float y) {
        if (activeGuessRow == -1 || board.getCodePegs().size() <= activeGuessRow) return false;

        PegCombination currentRow = board.getCodePegs().get(activeGuessRow);
        boolean isRowFilled = currentRow.getPegCombination().stream().allMatch(Objects::nonNull);
        if (!isRowFilled) return false;

        float buttonX = gameView.getGuessButtonX();
        float buttonY = gameView.getGuessButtonY();
        float buttonWidth = gameView.getGuessButtonWidth();
        float buttonHeight = gameView.getGuessButtonHeight();

        if (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight) {
            PegCombination completedRow = new PegCombination(game.getMode().getNumPegs());
            for (int i = 0; i < game.getMode().getNumPegs(); i++) {
                CodePeg originalPeg = (CodePeg) currentRow.getPegCombination().get(i);
                if (originalPeg != null) {
                    CodePeg newPeg = new CodePeg(originalPeg.getColor().getColorName());
                    completedRow.setPegPosition(newPeg, i);
                }
            }

            board.addCodePegCombination(completedRow);

            // TODO: Remove later, only for debugging
            PegCombination lastKeyPegs = board.getKeyPegs().get(board.getKeyPegs().size() - 1);
            List<PegInterface> feedbackPegs = lastKeyPegs.getPegCombination();
            StringBuilder feedbackDebug = new StringBuilder("Feedback: ");
            for (PegInterface peg : feedbackPegs) {
                if (peg == null) {
                    feedbackDebug.append("[empty] ");
                } else {
                    feedbackDebug.append(peg.getColor().getColorName()).append(" ");
                }
            }
            Gdx.app.log("GuessDebug", feedbackDebug.toString());

            board.getCodePegs().remove(activeGuessRow);
            board.getCodePegs().add(activeGuessRow, completedRow);

            activeGuessRow++;
            selectedRow = activeGuessRow;
            selectedCol = 0;

            if (activeGuessRow < totalRows) {
                while (board.getCodePegs().size() <= activeGuessRow) {
                    board.getCodePegs().add(new PegCombination(game.getMode().getNumPegs()));
                }
                PegCombination emptyRow = new PegCombination(game.getMode().getNumPegs());
                board.getCodePegs().remove(activeGuessRow);
                board.getCodePegs().add(activeGuessRow, emptyRow);
            }

            gameView.setSelectedHole(selectedRow, selectedCol);
            gameView.setPegRows(board.getCodePegs());
            gameView.setKeyPegs(board.getKeyPegs());
            gameView.setActiveGuessRow(activeGuessRow);

            List<PegInterface> keyPegList = lastKeyPegs.getPegCombination();
            boolean solvedThisTurn = keyPegList.stream()
                .allMatch(peg ->
                    peg instanceof KeyPeg &&
                        ((KeyPeg) peg).getColor() != null &&
                        ((KeyPeg) peg).getColor().getColorName().equals("black")
                );

            boolean lastGuessWasWrong = !solvedThisTurn && activeGuessRow >= totalRows;

            if (solvedThisTurn || lastGuessWasWrong) {
                Gdx.app.log("Game", board.isSolved() ? "Solved!" : "Game Over");

                player.setFinished(true);

                if (game.getSingleplayer()) {
                    if (solvedThisTurn) {
                        gameView.setShowWinPopup(true);
                    } else {
                        gameView.setShowLosePopup(true);
                    }
                } else {
                    // Multiplayer - wait for all players
                    boolean allFinished = game.getPlayers().stream().allMatch(Player::getIsFinished);

                    if (allFinished) {
                        List<DecodingBoard> boards = game.getDecodingBoards();
                        int minScore = boards.stream()
                            .mapToInt(DecodingBoard::getScore)
                            .min().orElse(Integer.MAX_VALUE);

                        List<Player> winners = boards.stream()
                            .filter(b -> b.getScore() == minScore)
                            .map(DecodingBoard::getPlayer)
                            .collect(Collectors.toList());

                        if (winners.contains(player)) {
                            gameView.setShowWinPopup(true);
                        } else {
                            gameView.setShowLosePopup(true);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    private boolean checkHoleSelection(float x, float y) {
        if (selectedRow < 0 || selectedRow >= totalRows) return false;

        for (int col = 0; col < game.getMode().getNumPegs(); col++) {
            float holeX = startX + col * (codePegHoleSize + gapX);
            float holeY = startY + (totalRows - 1 - selectedRow) * (codePegHoleSize + gapY);

            if (x >= holeX && x <= holeX + codePegHoleSize && y >= holeY && y <= holeY + codePegHoleSize) {
                selectedCol = col;
                gameView.setSelectedHole(selectedRow, col);
                return true;
            }
        }
        return false;
    }

    private boolean checkColorSelection(float x, float y) {
        if (selectedRow == -1 || selectedCol == -1) return false;

        float screenWidth = Gdx.graphics.getWidth();
        int spacing = 40;
        int colorCount = game.getAvailableColors().size();
        float totalWidth = colorCount * (codePegHoleSize) + (colorCount - 1) * spacing;
        float startX = (screenWidth - totalWidth) / 2f;
        float yPos = 80;

        for (int i = 0; i < colorCount; i++) {
            float xPeg = startX + i * (codePegHoleSize + spacing);
            if (x >= xPeg && x <= xPeg + codePegHoleSize && y >= yPos && y <= yPos + codePegHoleSize) {
                String selectedColor = game.getAvailableColors().get(i);
                PegCombination currentRow;

                while (board.getCodePegs().size() <= selectedRow) {
                    board.getCodePegs().add(new PegCombination(game.getMode().getNumPegs()));
                }
                currentRow = board.getCodePegs().get(selectedRow);

                currentRow.setPegPosition(new CodePeg(selectedColor), selectedCol);

                if (selectedCol + 1 < game.getMode().getNumPegs()) {
                    selectedCol++;
                    gameView.setSelectedHole(selectedRow, selectedCol);
                } else {
                    selectedCol = -1;
                    gameView.setSelectedHole(-1, -1);
                }

                gameView.setPegRows(board.getCodePegs());
                gameView.setKeyPegs(board.getKeyPegs());
                return true;
            }
        }
        return false;
    }
}
