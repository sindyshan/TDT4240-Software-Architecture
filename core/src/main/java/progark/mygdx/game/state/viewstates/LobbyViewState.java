package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.LobbyController;
import progark.mygdx.game.manager.ViewStateManager;
import progark.mygdx.game.model.Game;

public class LobbyViewState extends BaseViewState {
    private LobbyController lobbyController;

    public LobbyViewState(GameContext context) {
        super(context);
        Gdx.app.log("DEBUG", "StartMenuViewState created!");
        this.lobbyController = new LobbyController(this, context);
    }

    @Override
    public void enter() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        lobbyController.update(dt);
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        lobbyController.render(sb);
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {
        lobbyController.dispose();
    }

    public ViewStateManager getStateManager() {
        return context.getViewStateManager();
    }

}

