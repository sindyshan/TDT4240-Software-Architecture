package progark.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.viewstates.SettingsViewState;
import progark.mygdx.game.view.SettingsView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.utils.Timer;

import progark.mygdx.game.manager.MusicManager;

public class SettingsController {
    private SettingsView settingsView;
    private SettingsViewState state;

    public SettingsController(SettingsViewState state) {
        this.state = state;
        this.settingsView = new SettingsView(state.getCamera());
        Gdx.input.setInputProcessor(settingsView.getStage());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPosition);

            if (settingsView.getToggleMusicBounds().contains(touchPosition.x, touchPosition.y)) {
                settingsView.toggleMusic();
                MusicManager.getInstance().toggleMusic();
                System.out.println("Music toggled.");
            }

            if (settingsView.getToggleColorBlindBounds().contains(touchPosition.x, touchPosition.y)) {
                settingsView.toggleColorBlind();
                System.out.println("Colorblind mode changed."); // placeholder for colorblind mode handling
            }

            if (settingsView.getCrossButtonBounds().contains(touchPosition.x, touchPosition.y)) {
                System.out.println("Closing settings..");
                settingsView.setXbuttonpressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state.getStateManager().returnToPreviousState();
                    }
                }, 0.2f);
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        settingsView.render(sb);
    }

    public void enter() {
    }

    public void dispose() {
        settingsView.dispose();
    }

    public void exit() {
        dispose();
    }
}
