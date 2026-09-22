package progark.mygdx.game.state.modelstates;

import com.badlogic.gdx.Gdx;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.manager.ModelStateManager;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.networking.FirebaseInterface;

public class PlayGameState extends BaseModelState {
    public PlayGameState(GameContext context) {
        super(context);
    }

    @Override public void enter() {
        Gdx.app.log("MODEL_STATE", "Entering PlayGameState");
        context.getGame().setStatus("in_progress");
        context.getFirebaseInterface().updateGameState(context.getGame().getPin(), "in_progress");
    }

    @Override
    public void update(float dt) {
        // Game logic, checking turns, guesses, etc.
        if (context.getModelStateManager().getGame().isGameOver()) {
            context.getModelStateManager().setState(new FinishedGameState(context));
        }
    }
}
