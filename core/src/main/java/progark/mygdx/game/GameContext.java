package progark.mygdx.game;

import progark.mygdx.game.manager.ModelStateManager;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.manager.ViewStateManager;

public class GameContext {
    private FirebaseInterface firebaseInterface;
    private ViewStateManager viewStateManager;
    private Game game;
    private ModelStateManager modelStateManager;
    private Player currentPlayer;

    public GameContext(FirebaseInterface firebaseInterface, ViewStateManager viewStateManager, Game game) {
        this.firebaseInterface = firebaseInterface;
        this.viewStateManager = viewStateManager;
        this.game = game;

        if (game != null) {
            this.modelStateManager = new ModelStateManager(this);
        }
    }

    public void setGame(Game game) {
        this.game = game;
        this.modelStateManager = new ModelStateManager(this);
    }

    public FirebaseInterface getFirebaseInterface() {
        return firebaseInterface;
    }

    public ViewStateManager getViewStateManager() {
        return viewStateManager;
    }

    public Game getGame() {
        return game;
    }

    public ModelStateManager getModelStateManager() {
        return modelStateManager;
    }


    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
}
