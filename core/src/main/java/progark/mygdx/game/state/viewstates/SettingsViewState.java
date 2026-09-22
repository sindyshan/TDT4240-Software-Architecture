package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.SettingsController;
import progark.mygdx.game.main;
import progark.mygdx.game.manager.ViewStateManager;

public class SettingsViewState extends BaseViewState {
    private SettingsController controller;

    public SettingsViewState(GameContext context) {
        super(context);
        this.controller = new SettingsController(this);
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
    }
    @Override
    public void enter() {
        System.out.println("Entered SettingsViewState");
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
