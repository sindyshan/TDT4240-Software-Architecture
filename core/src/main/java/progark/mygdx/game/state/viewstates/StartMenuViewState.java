package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.StartMenuController;
import progark.mygdx.game.manager.ViewStateManager;

public class StartMenuViewState extends BaseViewState {
    private StartMenuController controller;

    public StartMenuViewState(GameContext context) {
        super(context);
        Gdx.app.log("DEBUG", "StartMenuViewState created!");
        this.controller = new StartMenuController(this, context);

    }

    @Override
    public void enter() {

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
        controller.render(sb);
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {
        controller.dispose();
    }

    public ViewStateManager getStateManager() {
        return context.getViewStateManager();
    }

}
