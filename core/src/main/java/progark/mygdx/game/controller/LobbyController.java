package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.List;

import progark.mygdx.game.DataHolderClass;
import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.state.viewstates.GamePlayViewState;
import progark.mygdx.game.state.viewstates.LobbyViewState;
import progark.mygdx.game.state.viewstates.SettingsViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.LobbyView;

public class LobbyController {
    private LobbyViewState state;
    private LobbyView lobbyView;
    private GameContext context;

    public LobbyController(LobbyViewState state, GameContext context) {
        this.state = state;
        this.context = context;
        this.lobbyView = new LobbyView(state.getCamera(), context.getGame());

        Player hostPlayer = context.getGame().getPlayers().get(0);

        context.getFirebaseInterface().createGame(context.getGame().getPin(), context.getGame());

        context.getFirebaseInterface().joinLobby(context.getGame().getPin(), hostPlayer,
            () -> Gdx.app.log("FIREBASE", "Host added as player"),
            () -> Gdx.app.log("FIREBASE", "Host failed to add self")
        );

        listenForPlayers();

        context.getFirebaseInterface().SetOnValueChangedListener(
            context.getGame().getPin(), new DataHolderClass() {
                @Override
                public void PrintSomeValue() {
                    if (someValue != null && someValue.equals("in_progress")) {
                        Gdx.app.postRunnable(() -> {
                            context.getViewStateManager().setState(
                                new GamePlayViewState(context)
                            );
                        });
                    }
                }
            });
    }

    private void listenForPlayers(){
        context.getFirebaseInterface().listenForPlayers(context.getGame().getPin(), new FirebaseInterface.PlayerUpdateListener() {
            @Override
            public void onPlayersUpdated(List<String> playerNames) {
                Gdx.app.postRunnable(() -> {
                    lobbyView.updatePlayers(playerNames);
                    Gdx.app.log("FIREBASE", "Updated players: " + playerNames.toString());
                });
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPos);

            if (lobbyView.getSettingsButtonBounds().contains(touchPos.x, touchPos.y)) {
                lobbyView.setSettingsButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        lobbyView.setSettingsButtonPressed(false);
                        state.getStateManager().setState(new SettingsViewState(context));
                    }
                }, 0.5f);
            }

            if (lobbyView.getExitButtonBounds().contains(touchPos.x, touchPos.y)) {
                lobbyView.setExitButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        lobbyView.setExitButtonPressed(false);
                        state.getStateManager().setState(new StartMenuViewState(context));
                    }
                }, 0.5f);
            }

            if (lobbyView.getStartGameButtonBounds().contains(touchPos.x, touchPos.y)) {
                lobbyView.setStartGameButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        lobbyView.setStartGameButtonPressed(false);
                        Game game = context.getGame();
                        context.getFirebaseInterface().updateGameState(game.getPin(), "in_progress");
                    }
                }, 0.5f);
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }
    public void render(SpriteBatch sb) {
        lobbyView.render(sb);
    }

    public void dispose() {
        lobbyView.dispose();
    }

    public void enter() {

    }

    public void exit() {
        dispose();
    }

}

