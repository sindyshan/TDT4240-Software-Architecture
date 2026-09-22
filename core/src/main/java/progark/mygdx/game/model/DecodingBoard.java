package progark.mygdx.game.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import progark.mygdx.game.model.pegcombination.peg.PegInterface;
import progark.mygdx.game.model.pegcombination.PegCombination;

public class DecodingBoard {
    private Game game;
    private Player player;
    private List<PegCombination> keyPegs;
    private List<String[]> colorBlindKeyPegs;
    private List<PegCombination> codePegs;
    private List<String[]> colorBlindCodePegs;
    private boolean isSolved;
    private boolean isColorBlind;
    private final static int MAX_SCORE = 12;

    public DecodingBoard(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.keyPegs = new ArrayList<>();
        this.codePegs = new ArrayList<>();
        this.colorBlindKeyPegs = new ArrayList<String[]>();
        this.colorBlindCodePegs = new ArrayList<String[]>();
        this.isSolved = false;
        this.isColorBlind = false;
    }

    /**
     * @return the game object of the decoding board
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game
     *             sets the game which the decoding board is related to
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the player of this decoding board
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player of the player
     *               sets the playerId of the player of the decoding board
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return a list of all of the feedback (key pegs) related to the guesses (code
     *         pegs)
     */
    public List<PegCombination> getKeyPegs() {
        return keyPegs;
    }

    /**
     * @param keyPegCombination
     *                          add a combination of key pegs from guessEvaluator to
     *                          the decoding board
     */
    public void addKeyPegCombination(PegCombination keyPegCombination) {
        keyPegs.add(keyPegCombination);
    }

    /**
     * @return all the guesses (code pegs) related to this game by this.player
     */
    public List<PegCombination> getCodePegs() {
        return codePegs;
    }

    /**
     * @return the last peg combination that was added to the decoding board
     */
    public PegCombination getLastGuess(){
        if (this.getCodePegs().isEmpty()) {
            throw new IllegalStateException("PegCombination.getLastGuess: No guesses have been made yet.");
        }
        return this.getCodePegs().get(this.getCodePegs().size() - 1);
    }

    /**
     * @return whether the peg combination doesn't contain any empty slots
     */
    public boolean isValidPegCombination(PegCombination pegCombination) {
        return pegCombination.getPegCombination().stream().noneMatch(Objects::isNull);
    }


    /**
     * @param codePegCombination
     * add a guess (combination of code pegs) related to this game by this.player
     * update the decoding board whether it is solved or not
     * update whether the player is finished with the game
     */
    public void addCodePegCombination(PegCombination codePegCombination) {
        if (player == null) {
            throw new IllegalStateException("Player must be set before adding code peg combinations.");
        }
        if(player.getScore() >= MAX_SCORE){
            throw new IllegalStateException("You have reached maximum number of guesses.");
        }
        if(!isValidPegCombination(codePegCombination)){
            throw new IllegalArgumentException("The inserted code peg combination shouldn't contain any empty slots.");
        }
        codePegs.add(codePegCombination);
        colorBlindCodePegs.add(codePegCombination.getColorBlindCombination());
        PegCombination keypegs = getGame().getCodeEvaluator().evaluate(this);
        addKeyPegCombination(keypegs);
        colorBlindKeyPegs.add(keypegs.getColorBlindCombination());
        incrementScore();

        if (!keyPegs.isEmpty()) {
            PegCombination lastKeyPegCombination = keyPegs.get(keyPegs.size() - 1);

            //checks whether the decoding board is now solved or not (for all code pegs are equal to key pegs)
            boolean solved = true;
            for (PegInterface peg : lastKeyPegCombination.getCombination().values()) {
                if (peg == null || peg.getColor() == null) {
                    continue; // Skip null pegs to prevent NullPointerException
                }
                if (!peg.getColor().getColorName().equals("black")) {
                    solved = false; // Not all pegs are black, so the game is not solved
                }
            }

            this.isSolved = solved; // Update game state if fully solved
        }
    }

    /**
     * @return whether the decoding board is solved or not
     */
    public boolean isSolved() {
        return isSolved;
    }

    /**
     * @param solved
     *               update whether the decoding board is solved or not
     */
    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    /**
     * @return the score of the decoding
     */
    public int getScore() {
        return player.getScore();
    }


    /**
     * Increments the score of this decoding board
     */
    private void incrementScore(){
        this.player.incrementScore();
    }

    /**
     * @param isColorBlind
     * sets the decoding board as color blind version or not
     */
    public void setColorBlind(boolean isColorBlind){
        this.isColorBlind = isColorBlind;
    }

    /**
     * @return whether the decoding board is color blind version or not
     */
    public boolean getIsColorBlind(){
        return isColorBlind;
    }


    /**
     * @return a list of string arrays of the key pegs for color blind version
     */
    public List<String[]> getColorBlindKeyPegs(){
        return colorBlindKeyPegs;
    }

    /**
     * @return a list of string arrays of the code pegs for color blind version
     */
    public List<String[]> getColorBlindCodePegs(){
        return colorBlindCodePegs;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DecodingBoard)) {
            return false;
        }
        DecodingBoard other = (DecodingBoard) obj;
        return Objects.equals(game, other.game) &&
            Objects.equals(player, other.player) && // Fix: Compare players properly
            Objects.equals(keyPegs, other.keyPegs) &&
            Objects.equals(codePegs, other.codePegs) &&
            isSolved == other.isSolved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, player, keyPegs, codePegs, isSolved);
    }

    @Override
    public String toString() {
        return "DecodingBoard {" +
            "player=" + player +
            ", keyPegs=" + keyPegs +
            ", codePegs=" + codePegs +
            ", isSolved=" + isSolved +
            '}';
    }
}
