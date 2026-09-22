package progark.mygdx.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class AvailableColorsGenerator {

    private Game game;
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

    public AvailableColorsGenerator(Game game) {
        this.game = Objects.requireNonNull(game, "Game cannot be null");
    }
    /**
     * @return list of colors as string possible to choose as code peg in the game
     */
    public List<String> selectGameColors() {
        int numColors = game.getMode().getNumColors();
        List<String> availableColors = new ArrayList<>(VALID_COLORS);
        Collections.shuffle(availableColors, rand);
        return availableColors.subList(0, numColors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvailableColorsGenerator that = (AvailableColorsGenerator) o;
        return Objects.equals(game, that.game);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(game);
    }

    @Override
    public String toString() {
        return "AvailableColorsGenerator{" +
            "game=" + game +
            '}';
    }
}
