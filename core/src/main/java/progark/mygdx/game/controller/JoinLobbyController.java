package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.List;

import progark.mygdx.game.DataHolderClass;
import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.DecodingBoard;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.state.viewstates.GamePlayViewState;
import progark.mygdx.game.state.viewstates.JoinLobbyState;
import progark.mygdx.game.state.viewstates.SettingsViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.JoinLobbyView;

public class JoinLobbyController {
    private JoinLobbyState state;
    private JoinLobbyView joinLobbyView;
    private GameContext context;
    private String pin;

    public JoinLobbyController(JoinLobbyState state, GameContext context, String pin) {
        this.state = state;
        this.context = context;
        this.pin = pin;

        Gdx.app.postRunnable(() -> {
            this.joinLobbyView = new JoinLobbyView(state.getCamera(), pin);
            listenForPlayers();
            fetchSecretCode();
        });

        context.getFirebaseInterface().SetOnValueChangedListener(pin, new DataHolderClass() {
            @Override
            public void PrintSomeValue() {
                if (someValue != null && someValue.equals("in_progress")) {
                    Gdx.app.postRunnable(() -> {
                        Game game = context.getGame();
                        Player currentPlayer = context.getCurrentPlayer();

                        // Only add decoding board if it's missing
                        boolean alreadyExists = game.getPlayers().stream()
                            .anyMatch(p -> p.getName().equals(currentPlayer.getName()));
                        if (!alreadyExists) {
                            game.addDecodingBoard(new DecodingBoard(game, currentPlayer));
                        }

                        context.getViewStateManager().setState(
                            new GamePlayViewState(context)
                        );
                    });
                }
            }


        });

    }

    private void listenForPlayers(){
        context.getFirebaseInterface().listenForPlayers(pin, new FirebaseInterface.PlayerUpdateListener() {
            @Override
            public void onPlayersUpdated(List<String> playerNames) {
                Gdx.app.postRunnable(() -> {
                    joinLobbyView.updatePlayers(playerNames);
                    Gdx.app.log("FIREBASE", "Players updated: " + playerNames);

                });
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void fetchSecretCode() {
        context.getFirebaseInterface().fetchSecretCode(pin, new FirebaseInterface.SecretCodeCallback() {
            @Override
            public void onCodeFetched(List<String> colorNames) {
                Game game = context.getGame();
                if (game != null) {
                    game.setSecretCodeFromColors(colorNames);
                    Gdx.app.log("FIREBASE", "JoinLobbyController: Secret code loaded: " + colorNames);
                } else {
                    Gdx.app.log("FIREBASE", "Game is null, cannot set secret code.");
                }
            }

            @Override
            public void onError(String error) {
                Gdx.app.log("FIREBASE", "Failed to load secret code: " + error);
            }
        });
    }


    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPos);

            if (joinLobbyView.getSettingsButtonBounds().contains(touchPos.x, touchPos.y)) {
                joinLobbyView.setSettingsButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        joinLobbyView.setSettingsButtonPressed(false);
                        state.getStateManager().setState(new SettingsViewState(context));
                    }
                }, 0.5f);
            }

            if (joinLobbyView.getExitButtonBounds().contains(touchPos.x, touchPos.y)) {
                joinLobbyView.setExitButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        joinLobbyView.setExitButtonPressed(false);
                        state.getStateManager().setState(new StartMenuViewState(context));
                    }
                }, 0.5f);
            }

        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        if (joinLobbyView != null) {
            joinLobbyView.render(sb);
        }
    }


    public void dispose() {
        joinLobbyView.dispose();
    }

    public void enter() {

    }

    public void exit() {
        dispose();
    }
}
