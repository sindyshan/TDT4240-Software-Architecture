package progark.mygdx.game.manager;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.modelstates.GameModelState;
import progark.mygdx.game.state.modelstates.WaitingState;

public class ModelStateManager {
    private GameModelState currentState;
    private GameContext context;

    public ModelStateManager(GameContext context) {
        this.context = context;
        this.currentState = new WaitingState(context);
        currentState.enter();
    }

    public void update(float dt) {
        currentState.update(dt);
    }

    public void setState(GameModelState newState) {
        currentState.exit();
        currentState = newState;
        currentState.enter();
    }

    public Game getGame() {
        return context.getGame();
    }

    public FirebaseInterface getFirebaseInterface() {
        return context.getFirebaseInterface();
    }

}
