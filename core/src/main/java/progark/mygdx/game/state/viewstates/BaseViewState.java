package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.main;
import progark.mygdx.game.manager.ModelStateManager;
import progark.mygdx.game.manager.ViewStateManager;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.networking.FirebaseInterface;

public abstract class BaseViewState implements GameViewState {
    protected GameContext context;
    OrthographicCamera camera;
    Viewport viewport;

    public BaseViewState(GameContext context) {
        this.context = context;
        camera = new OrthographicCamera();
        viewport = new FitViewport(main.WIDTH, main.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    protected ViewStateManager getStateManager() {
        return context.getViewStateManager();
    }

    protected FirebaseInterface getFirebaseInterface() {
        return context.getFirebaseInterface();
    }

    protected ModelStateManager getModelStateManager() {
        return context.getModelStateManager();
    }

    protected Game getGame() {
        return context.getGame();
    }
}
