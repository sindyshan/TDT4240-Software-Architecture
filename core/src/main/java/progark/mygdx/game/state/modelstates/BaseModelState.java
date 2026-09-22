package progark.mygdx.game.state.modelstates;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.manager.ModelStateManager;
import progark.mygdx.game.networking.FirebaseInterface;

public abstract class BaseModelState implements GameModelState {
    protected GameContext context;

    public BaseModelState(GameContext context) {
        this.context = context;
    }
    @Override public void exit() {}
}
