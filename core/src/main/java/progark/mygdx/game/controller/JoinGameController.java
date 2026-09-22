package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import java.util.List;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.model.mode.EasyMode;
import progark.mygdx.game.model.mode.HardMode;
import progark.mygdx.game.model.mode.MediumMode;
import progark.mygdx.game.model.mode.Mode;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.viewstates.JoinGameViewState;
import progark.mygdx.game.state.viewstates.JoinLobbyState;
import progark.mygdx.game.state.viewstates.SettingsViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.JoinGameView;

public class JoinGameController{
    private JoinGameView joinGameView;
    private JoinGameViewState state;
    private GameContext context;

    public JoinGameController(JoinGameViewState state, GameContext context) {
        this.state = state;
        this.context = context;
        this.joinGameView = new JoinGameView(state.getCamera());
        Gdx.input.setInputProcessor(joinGameView.getStage());
    }

    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPos);

            if (joinGameView.getBackButtonBounds().contains(touchPos.x, touchPos.y)) {
                joinGameView.setBackButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        joinGameView.setBackButtonPressed(false);
                        state.getStateManager().setState(new StartMenuViewState(context));
                    }
                }, 0.2f);
            }

            if (joinGameView.getSettingsButtonBounds().contains(touchPos.x,touchPos.y)){
                joinGameView.setSettingsButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        joinGameView.setSettingsButtonPressed(false);
                        state.getStateManager().setState(new SettingsViewState(context));
                    }
                }, 0.2f);
            }

            if (joinGameView.getJoinGameBounds().contains(touchPos.x, touchPos.y)){
                if (joinGameView.isNicknameEntered() && joinGameView.isPinEntered()){
                    joinGameView.setJoinButtonPressed(true);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            joinGameView.setJoinButtonPressed(false);
                            //state.getStateManager().setState(new LobbyState(state.getStateManager(), _FBIC));
                        }
                    }, 0.2f);
                    String nickname = joinGameView.getNickname();
                    String pin = joinGameView.getPin();
                    Player player = new Player();
                    player.setName(nickname);

                    // Store in context for later use
                    context.setCurrentPlayer(player);

                    checkPlayerCount(pin, (isFull) -> {
                        if (isFull) {
                            Gdx.app.postRunnable(() -> joinGameView.setErrorMessage("Game is full"));
                        } else {
                            context.getFirebaseInterface().fetchGameMode(pin, new FirebaseInterface.ModeCallback() {
                                @Override
                                public void onModeFetched(String modeStr) {
                                    Mode mode;
                                    switch (modeStr) {
                                        case "Easy":
                                            mode = new EasyMode();
                                            break;
                                        case "Medium":
                                            mode = new MediumMode();
                                            break;
                                        case "Hard":
                                            mode = new HardMode();
                                            break;
                                        default:
                                            mode = new EasyMode();
                                            break;
                                    }

                                    // Set the game first
                                    context.setGame(new Game(mode, false));

                                    context.getFirebaseInterface().fetchAvailableColors(pin, new FirebaseInterface.ColorsCallback() {
                                        @Override
                                        public void onColorsFetched(List<String> colors) {
                                            context.getGame().setAvailableColors(colors);
                                            Gdx.app.log("FIREBASE", "Colors loaded: " + colors);

                                            // Then join the lobby
                                            context.getFirebaseInterface().joinLobby(pin, player, () -> {
                                                context.getViewStateManager().setState(new JoinLobbyState(context, pin));
                                            }, () -> {
                                                Gdx.app.log("FIREBASE", "Failed to join lobby");
                                            });
                                        }

                                        @Override
                                        public void onError(String error) {
                                            Gdx.app.log("FIREBASE", "Failed to fetch colors: " + error);
                                        }
                                    });
                                }

                                @Override
                                public void onError(String error) {
                                    Gdx.app.log("FIREBASE", "Failed to fetch game mode: " + error);
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public void checkPlayerCount(String pin, java.util.function.Consumer<Boolean> callback) {
        context.getFirebaseInterface().listenForPlayers(pin, new FirebaseInterface.PlayerUpdateListener() {
            @Override
            public void onPlayersUpdated(List<String> playerNames) {
                boolean isFull = playerNames.size() >= 6;
                callback.accept(isFull);
            }

            @Override
            public void onError(String error) {
                Gdx.app.log("FIREBASE", "Error while checking player count: " + error);
                callback.accept(false);
            }
        });
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        joinGameView.render(sb);
    }

    public void enter() {
    }

    public void dispose() {
        joinGameView.dispose();
    }

    public void exit() {
        dispose();
    }

}

