package progark.mygdx.game.state.viewstates;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.HowToPlayController;
import progark.mygdx.game.manager.ViewStateManager;


public class HowToPlayViewState extends BaseViewState {
    private HowToPlayController howToPlayControllerController;

    public HowToPlayViewState(GameContext context) {
        super(context);
        this.howToPlayControllerController = new HowToPlayController(this, context);

    }

    @Override
    public void enter() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        howToPlayControllerController.update(dt);
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        howToPlayControllerController.render(sb);
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {
        howToPlayControllerController.dispose();
    }

    public ViewStateManager getStateManager() {
        return context.getViewStateManager();
    }

}

