package progark.mygdx.game.model;

import java.util.Objects;
import com.google.gson.Gson;

public class Player {
    private String name;
    private int score;
    private boolean isFinished;
    private boolean isColorblind;

    public Player() {
        this.name = "";
        this.score = 0;
        this.isFinished = false;
        this.isColorblind = false;
    }


    /**
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * updates the player's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the score of the player (in a game)
     */
    public int getScore() {
        return score;
    }

    public void incrementScore(){
        this.score ++;
    }


    /**
     * @return whether the player has finished the game
     */
    public boolean getIsFinished() {
        return isFinished;
    }

    /**
     * @param isFinished
     * updates whether the player has finished the game
     */
    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * @return whether the player wants to play colorblind mode
     */
    public boolean getIsColorblind(){ return isColorblind; }

    /**
     * @param isColorblind
     * updates colorblind mode for this player
     */
    public void setIsColorblind(boolean isColorblind){ this.isColorblind = isColorblind; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score == player.score &&
            isFinished == player.isFinished &&
            isColorblind == player.isColorblind &&
            Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, isFinished, isColorblind);
    }

    @Override
    public String toString() {
        return "Player{" +
            "name='" + name + '\'' +
            ", score=" + score +
            ", isFinished=" + isFinished +
            ", isColorblind=" + isColorblind +
            '}';
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        Player player = new Player();
        player.setName("Natha");
        player.setFinished(true);
        player.setIsColorblind(true);

        // Generating emp object from emp json
        Player playerGenerated = gson.fromJson(gson.toJson(player), Player.class);

        // Print and display the employee been generated
        System.out.println(
            "Generated employee from json is "
                + playerGenerated);
    }
}
