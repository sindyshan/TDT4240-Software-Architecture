package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.viewstates.HowToPlayViewState;
import progark.mygdx.game.state.viewstates.SettingsViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.HowToPlayView;

public class HowToPlayController {
    private HowToPlayViewState state;
    private HowToPlayView howToPlayView;
    private GameContext context;

    public HowToPlayController(HowToPlayViewState state, GameContext context) {
        this.state = state;
        this.howToPlayView = new HowToPlayView(state.getCamera());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPos);

            if (howToPlayView.getBackButtonBounds().contains(touchPos.x,touchPos.y)){
                howToPlayView.setBackButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state.getStateManager().setState(new StartMenuViewState(context));
                    }
                }, 0.2f);
            }

            if (howToPlayView.getSettingsButtonBounds().contains(touchPos.x,touchPos.y)){
                howToPlayView.setSettingsButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state.getStateManager().setState(new SettingsViewState(context));
                        howToPlayView.setSettingsButtonPressed(false);
                    }
                }, 0.2f);
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }
    public void render(SpriteBatch sb) {
        howToPlayView.render(sb);
    }

    public void dispose() {
        howToPlayView.dispose();
    }

    public void enter() {

    }

    public void exit() {
        dispose();
    }

}

