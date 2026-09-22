package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.viewstates.CreateGameViewState;
import progark.mygdx.game.state.viewstates.HowToPlayViewState;
import progark.mygdx.game.state.viewstates.JoinGameViewState;
import progark.mygdx.game.state.viewstates.SettingsViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.StartMenuView;

public class StartMenuController{
    private StartMenuView startMenuView;
    private StartMenuViewState state;
    private GameContext context;

    public StartMenuController(StartMenuViewState state, GameContext context) {
        this.state = state;
        this.context = context;
        this.startMenuView = new StartMenuView(state.getCamera());
        Gdx.input.setInputProcessor(startMenuView.getStage());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPos);

            if (startMenuView.getNewGameButtonBounds().contains(touchPos.x, touchPos.y)) {
                startMenuView.setNewGamePressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        startMenuView.setNewGamePressed(false);
                        state.getStateManager().setState(new CreateGameViewState(context));
                    }
                }, 0.5f);
            }

            if (startMenuView.getJoinGameButtonBounds().contains(touchPos.x,touchPos.y)){
                startMenuView.setJoinGamePressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        startMenuView.setJoinGamePressed(false);
                        state.getStateManager().setState(new JoinGameViewState(context));
                    }
                }, 0.5f);
            }

            if (startMenuView.getTutorialButtonBounds().contains(touchPos.x, touchPos.y)) {
                startMenuView.setTutorialBtnPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        startMenuView.setTutorialBtnPressed(false);
                        state.getStateManager().setState(new HowToPlayViewState(context));
                    }
                }, 0.5f);
            }

            if (startMenuView.getSettingsButtonBounds().contains(touchPos.x, touchPos.y)) {
                startMenuView.setSettingsBtnPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        startMenuView.setSettingsBtnPressed(false);
                        state.getStateManager().setState(new SettingsViewState(context));
                    }
                }, 0.5f);
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        startMenuView.render(sb);
    }

    public void enter() {
    }

    public void dispose() {
        startMenuView.dispose();
    }

    public void exit() {
        dispose();
    }

}
