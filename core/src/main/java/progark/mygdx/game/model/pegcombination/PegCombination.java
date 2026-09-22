package progark.mygdx.game.model.pegcombination;

import java.sql.Array;
import java.util.*;

import progark.mygdx.game.model.pegcombination.peg.PegInterface;

public class PegCombination {
    private Map<Integer, PegInterface> combination;
    private final int numPegs;

    /**
     * @param numPegs number of pegs the combination allows
     */
    public PegCombination(int numPegs) {
        this.numPegs = numPegs;
        this.combination = new HashMap<>();
    }

    /**
     * @return the map that includes both peg and its position
     */
    public Map<Integer, PegInterface> getCombination() {
        return new HashMap<>(combination);
    }

    /**
     * @return a string array of the combination for color blind version
     */
    public String[] getColorBlindCombination() {
        String[] map = new String[numPegs];
        for (int i = 0; i < numPegs; i++) {
            PegInterface peg = combination.get(i);
            if (peg != null) {
                map[i] = peg.getFigure();
            } else {
                map[i] = "empty"; // Or any placeholder for empty pegs
            }
        }
        return map;
    }


    /**
     * @return a list of the pegs in an ordered manner (null for empty slots)
     */
    public List<PegInterface> getPegCombination() {
        List<PegInterface> list = new ArrayList<>(Collections.nCopies(numPegs, null));
        for (Map.Entry<Integer, PegInterface> entry : combination.entrySet()) {
            list.set(entry.getKey(), entry.getValue());
        }
        return list;
    }

    /**
     * @return a list of the pegs' colors as strings (empty slots defined as "empty")
     */
    public List<String> getPegColorCombination() {
        List<String> list = new ArrayList<>(Collections.nCopies(numPegs, "empty"));
        for (Map.Entry<Integer, PegInterface> entry : combination.entrySet()) {
            list.set(entry.getKey(), entry.getValue().getColor().getColorName());
        }
        return list;
    }



    /**
     * @param peg PegInterface instance
     * @return the position of the given peg in the combination, or null if not found
     */
    public Integer getPegPosition(PegInterface peg) {
        return combination.entrySet().stream()
            .filter(entry -> Objects.equals(entry.getValue(), peg))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    /**
     * @param position Position in the combination
     * @return true if the position is valid
     */
    private boolean isValidPosition(int position) {
        return position >= 0 && position < numPegs;
    }

    /**
     * @param position Position in the combination
     * @return true if the position is empty
     */
    private boolean isEmptyPosition(int position) {
        return isValidPosition(position) && !combination.containsKey(position);
    }

    /**
     * Inserts a code peg into a specific position.
     * @param peg The peg to insert (whether it is code peg or key peg)
     * @param position The position to insert the peg
     */
    public void setPegPosition(PegInterface peg, int position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position for peg: " + position);
        }
        combination.put(position, peg);
    }

    /**
     * Deletes a code peg from a given position.
     * @param position The position of the CodePeg to delete
     */
    public void deleteCodePeg(int position) {
        if (!isValidPosition(position)) {
            throw new IllegalArgumentException("Invalid position for deleting CodePeg: " + position);
        }
        combination.remove(position);
    }

    /**
     * Override equals() and hashCode() to compare PegCombinations properly.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PegCombination that = (PegCombination) o;
        return numPegs == that.numPegs && Objects.equals(combination, that.combination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(combination, numPegs);
    }

    /**
     * Override toString() for meaningful output.
     */
    @Override
    public String toString() {
        return "PegCombination{" +
            "combination=" + combination +
            ", numPegs=" + numPegs +
            '}';
    }
}
