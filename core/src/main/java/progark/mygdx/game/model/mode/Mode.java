package progark.mygdx.game.model.mode;

import java.util.Objects;

public abstract class Mode {
    private static final int MAX_NUM_PEGS = 6;
    private static final int MAX_NUM_COLORS = 7;

    protected final int numColors;
    protected final int numPegs;
    protected final String mode;

    /**
     * Constructor for Mode.
     * @param mode The name of the game mode.
     * @param numColors The number of colors available (1-7).
     * @param numPegs The number of pegs in this mode (1-6).
     */
    public Mode(String mode, int numColors, int numPegs) {
        this.mode = Objects.requireNonNull(mode, "Mode name cannot be null");
        this.numColors = validateNumColors(numColors);
        this.numPegs = validateNumPegs(numPegs);
    }

    /**
     * Validates the number of colors (must be between 1 and MAX_NUM_COLORS).
     * @param numColors The number of colors to validate.
     * @return The validated number of colors.
     * @throws IllegalArgumentException if the value is out of bounds.
     */
    private int validateNumColors(int numColors) {
        if (numColors < 1 || numColors > MAX_NUM_COLORS) {
            throw new IllegalArgumentException("Number of colors must be between 1 and " + MAX_NUM_COLORS);
        }
        return numColors;
    }

    /**
     * Validates the number of pegs (must be between 1 and MAX_NUM_PEGS).
     * @param numPegs The number of pegs to validate.
     * @return The validated number of pegs.
     * @throws IllegalArgumentException if the value is out of bounds.
     */
    private int validateNumPegs(int numPegs) {
        if (numPegs < 1 || numPegs > MAX_NUM_PEGS) {
            throw new IllegalArgumentException("Number of pegs must be between 1 and " + MAX_NUM_PEGS);
        }
        return numPegs;
    }

    /**
     * @return the mode name as a string.
     */
    public String getMode() {
        return mode;
    }

    /**
     * @return the number of pegs in this mode.
     */
    public int getNumPegs() {
        return numPegs;
    }

    /**
     * @return the number of colors available for this mode.
     */
    public int getNumColors() {
        return numColors;
    }

    /**
     * Override equals() to compare Mode objects properly.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mode mode1 = (Mode) o;
        return numColors == mode1.numColors &&
            numPegs == mode1.numPegs &&
            mode.equals(mode1.mode);
    }

    /**
     * Override hashCode() to ensure Mode objects work correctly in collections.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numColors, numPegs, mode);
    }

    /**
     * Override toString() for better debugging and logging.
     */
    @Override
    public String toString() {
        return "Mode{" +
            "mode='" + mode + '\'' +
            ", numColors=" + numColors +
            ", numPegs=" + numPegs +
            '}';
    }
}
