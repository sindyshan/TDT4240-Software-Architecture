package progark.mygdx.game.state.modelstates;

import com.badlogic.gdx.Gdx;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.manager.ModelStateManager;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.networking.FirebaseInterface;

public class FinishedGameState extends BaseModelState{
    public FinishedGameState(GameContext context) {
        super(context);
    }

    @Override public void enter() {
        Gdx.app.log("MODEL_STATE", "Entering FinishedGameState");
        context.getGame().setStatus("finished");
        context.getFirebaseInterface().updateGameState(context.getGame().getPin(), "finished");
    }

    @Override
    public void update(float dt) {
        // Maybe update scores, show winners, etc.
    }
}
