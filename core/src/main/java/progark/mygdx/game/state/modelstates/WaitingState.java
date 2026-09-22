package progark.mygdx.game.state.modelstates;

import com.badlogic.gdx.Gdx;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.manager.ModelStateManager;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.networking.FirebaseInterface;

public class WaitingState extends BaseModelState{
    public WaitingState(GameContext context) {
        super(context);
    }

    @Override public void enter() {
        Gdx.app.log("MODEL_STATE", "Entering WaitingState");
        context.getGame().setStatus("waiting");
        context.getFirebaseInterface().updateGameState(context.getGame().getPin(), "waiting");
    }

    @Override
    public void update(float dt) {
        context.getModelStateManager().setState(new PlayGameState(context));

    }
}
