package progark.mygdx.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.viewstates.LeaderboardViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.LocalLeaderBoardView;

public class LocalLeaderBoardController {
    private LocalLeaderBoardView localLeaderBoardView;
    private LeaderboardViewState state;
    private GameContext context;

    public LocalLeaderBoardController(LeaderboardViewState leaderboardState, GameContext context) {
        this.state = leaderboardState;
        this.context = context;
        this.localLeaderBoardView = new LocalLeaderBoardView(state.getCamera());
        Gdx.input.setInputProcessor(localLeaderBoardView.getStage());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPosition);

            if (localLeaderBoardView.getExitButtonBounds().contains(touchPosition.x, touchPosition.y)) {
                localLeaderBoardView.setExitButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state.getStateManager().setState(new StartMenuViewState(context));
                        localLeaderBoardView.setExitButtonPressed(false);
                    }
                }, 0.1f);
                System.out.println("Exited to home page");
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        localLeaderBoardView.render(sb);
    }

    public void enter() {
    }

    public void dispose() {
        localLeaderBoardView.dispose();
    }

    public void exit() {
        dispose();
    }
}
