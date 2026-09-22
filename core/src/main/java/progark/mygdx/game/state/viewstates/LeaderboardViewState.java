package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.controller.LocalLeaderBoardController;
import progark.mygdx.game.main;
import progark.mygdx.game.manager.ViewStateManager;

public class LeaderboardViewState extends BaseViewState {
    private LocalLeaderBoardController controller;

    public LeaderboardViewState(GameContext context) {
        super(context);
        this.controller = new LocalLeaderBoardController(this, context);
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
    }


    @Override
    public void enter() {
        System.out.println("Entered LeaderboardViewState");
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
