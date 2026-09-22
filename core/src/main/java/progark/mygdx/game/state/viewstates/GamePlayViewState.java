package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.manager.ViewStateManager;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.GameController;
import progark.mygdx.game.model.mode.Mode;
import progark.mygdx.game.model.Player;

public class GamePlayViewState extends BaseViewState {
    private final GameController controller;

    public GamePlayViewState(GameContext context) {
        super(context);
        this.controller = new GameController(this, context);
    }

    @Override
    public void enter() {}

    @Override
    public void handleInput() {}

    @Override
    public void update(float dt) {
        controller.update(dt);
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        controller.render(sb);
    }

    @Override
    public void exit() {}

    @Override
    public void dispose() {
        controller.dispose();
    }

    public ViewStateManager getStateManager() {
        return context.getViewStateManager();
    }
}

