package progark.mygdx.game.model;
import java.util.*;

public class Scoreboard {
    protected List<Player> players;

    public Scoreboard() {
        players = new ArrayList<>();
    }

    /**
     * @param player
     * Add a player and their score to the scoreboard
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     *  Sort players based on their scores (e.g., by highest score first)
     */
    public void sortPlayersByScore() {
        players.sort(Comparator.comparingInt(p -> -p.getScore()));
    }


    /**
     * @return a list of player names sorted by score
     */
    public List<String> getSortedPlayerNames() {
        List<String> sortedNames = new ArrayList<>();
        sortPlayersByScore();
        for (Player player : players) {
            sortedNames.add(player.getName());
        }
        return sortedNames;
    }

    /**
     * @return all players in the scoreboard
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns a list of player name-score pairs (as Map.Entry).
     */
    public List<Map.Entry<String, Integer>> getNameScoreTuples() {
        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        for (Player player : players) {
            result.add(new AbstractMap.SimpleEntry<>(player.getName(), player.getScore()));
        }
        return result;
    }

    @Override
    public String toString() {
        return "Scoreboard{" +
            "players=" + players +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scoreboard that = (Scoreboard) o;
        return Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(players);
    }
}
