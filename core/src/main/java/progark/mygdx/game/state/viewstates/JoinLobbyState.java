package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.JoinLobbyController;
import progark.mygdx.game.manager.ViewStateManager;

public class JoinLobbyState extends BaseViewState {
    private JoinLobbyController joinLobbyController;

    public JoinLobbyState(GameContext context, String pin) {
        super(context);
        this.joinLobbyController = new JoinLobbyController(this, context, pin);
    }

    @Override
    public void enter() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        joinLobbyController.update(dt);
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        joinLobbyController.render(sb);

    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {

    }

    public ViewStateManager getStateManager() {
        return context.getViewStateManager();
    }
}
