package progark.mygdx.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import progark.mygdx.game.model.pegcombination.peg.KeyPeg;
import progark.mygdx.game.model.pegcombination.PegCombination;

public class CodeEvaluator {
    private final Game game;
    private final PegCombination secretCode;

    public CodeEvaluator(Game game) {
        this.game = Objects.requireNonNull(game, "Game cannot be null");
        this.secretCode = this.game.getSecretCode();
    }

    /**
     * @param decodingBoard
     * @return PegCombination of key pegs related to the last guess of the decoding board
     */
    public PegCombination evaluate(DecodingBoard decodingBoard) {
        if (!game.getDecodingBoards().contains(decodingBoard)) {
            throw new IllegalArgumentException("Decoding board " + decodingBoard + " is not a part of this game.");
        }

        // Validation whether there are any guesses at all
        List<PegCombination> pastGuesses = decodingBoard.getCodePegs();
        if (pastGuesses.isEmpty()) {
            throw new IllegalStateException("No guesses found on the decoding board.");
        }

        // Initialization of secret code and guess
        PegCombination secretCode = this.game.getSecretCode();
        PegCombination guess = decodingBoard.getLastGuess();

        // Initialiser key pegs
        PegCombination keyPegCombination = new PegCombination(game.getMode().getNumPegs());

        boolean[] correctColor = new boolean[game.getMode().getNumPegs()];
        boolean[] correctColorAndPosition = new boolean[game.getMode().getNumPegs()];

        // Check 1 : Check for correct color and correct positions
        Map<Integer, String> restSecretCode = new HashMap<>();
        Map<Integer, String> restGuess = new HashMap<>();
        for (int i = 0; i < guess.getCombination().size(); i++) {
            boolean correct = guess.getCombination().get(i).getColor().getColorName()
                .equals(secretCode.getCombination().get(i).getColor().getColorName());
            if (correct) {
                correctColorAndPosition[i] = correct;
            } else {
                restSecretCode.put(i, secretCode.getCombination().get(i).getColor().getColorName());
                restGuess.put(i, guess.getCombination().get(i).getColor().getColorName());
            }
        }

        // Check 2 : Check for correct color but wrong positions
        List<String> remainingGuess = new ArrayList<>(restGuess.values());
        List<String> remainingSecret = new ArrayList<>(restSecretCode.values());

        for (String color : remainingGuess) {
            if (remainingSecret.contains(color)) {
                correctColor[restGuess.entrySet().stream()
                    .filter(e -> e.getValue().equals(color))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(-1)] = true;

                remainingSecret.remove(color); // Tp prevent duplicate matches
            }
        }

        // Insert in a pegcombination
        for (int i = 0; i < correctColor.length; i++) {
            if (correctColor[i]) {
                KeyPeg w = new KeyPeg("white");
                keyPegCombination.setPegPosition(w, i);
            } else if (correctColorAndPosition[i]) {
                KeyPeg b = new KeyPeg("black");
                keyPegCombination.setPegPosition(b, i);
            } else {
                keyPegCombination.setPegPosition(null, i);
            }

        }

        return keyPegCombination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeEvaluator that = (CodeEvaluator) o;
        return Objects.equals(game, that.game) && Objects.equals(secretCode, that.secretCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, secretCode);
    }

    @Override
    public String toString() {
        return "CodeEvaluator{" +
            "game=" + game +
            ", secretCode=" + secretCode +
            '}';
    }
}
