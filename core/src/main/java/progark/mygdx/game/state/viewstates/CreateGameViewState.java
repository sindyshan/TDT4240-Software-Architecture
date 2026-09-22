package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.CreateGameController;
import progark.mygdx.game.main;
import progark.mygdx.game.manager.ViewStateManager;

public class CreateGameViewState extends BaseViewState {
    private CreateGameController controller;

    public CreateGameViewState(GameContext context) {
        super(context);
        this.controller = new CreateGameController(this, context);
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
    }

    @Override
    public void enter() {
        System.out.println("Entered CreateGameViewState!");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        controller.update(dt);
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        controller.update(Gdx.graphics.getDeltaTime());
        controller.render(sb); // uses same view that controller uses
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {
        controller.dispose(); // cleans up view too
    }

    public ViewStateManager getStateManager() {
    return context.getViewStateManager();
    }
}
