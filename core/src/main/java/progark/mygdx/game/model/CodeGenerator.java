package progark.mygdx.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import progark.mygdx.game.model.pegcombination.peg.CodePeg;
import progark.mygdx.game.model.pegcombination.PegCombination;

public class CodeGenerator {
    private final Game game;
    private static final List<String> VALID_COLORS = Collections.unmodifiableList(
        Arrays.asList(new String[] {
            "red",
            "blue",
            "green",
            "yellow",
            "orange",
            "purple",
            "pink"}));
    private static final Random rand = new Random();
    private AvailableColorsGenerator availableColorsGenerator;

    public CodeGenerator(Game game) {
        this.game = Objects.requireNonNull(game, "Game cannot be null");
        this.availableColorsGenerator = new AvailableColorsGenerator(game);
    }

    /**
     * @return game which this code generator generates code for
     */
    public Game getGame(){
        return game;
    }

    /**
     * @return the game's available colors generator
     */
    public AvailableColorsGenerator getAvailableColorsGenerator(){
        return this.availableColorsGenerator;
    }

    /**
     * @return peg combination for secret code
     */
    public PegCombination generateCode() {
        int numColors = game.getMode().getNumColors();
        int numPegs = game.getMode().getNumPegs();

        List<String> gameColors = availableColorsGenerator.selectGameColors();
        List<String> secretCodeColors = generateSecretCodeColors(gameColors);
        this.game.setAvailableColors(gameColors);


        PegCombination pegCombination = new PegCombination(numPegs);
        for (int i = 0; i < numPegs; i++) {
            pegCombination.setPegPosition(new CodePeg(secretCodeColors.get(i)), i);
        }
        return pegCombination;
    }

    /**
     * @return list of color for each peg in the secret code (may contain multiple of the same color)
     */
    private List<String> generateSecretCodeColors(List<String> gameColors) {
        int numPegs = game.getMode().getNumPegs();
        List<String> secretCodeColors = new ArrayList<>();
        for (int i = 0; i < numPegs; i++) {
            secretCodeColors.add(gameColors.get(rand.nextInt(gameColors.size())));
        }
        return secretCodeColors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeGenerator that = (CodeGenerator) o;
        return Objects.equals(game, that.game) && Objects.equals(availableColorsGenerator, that.availableColorsGenerator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, availableColorsGenerator);
    }

    @Override
    public String toString() {
        return "CodeGenerator{game=" + game.getMode().getClass().getSimpleName() + "}";
    }
}
