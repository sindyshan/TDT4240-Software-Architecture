package progark.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

import progark.mygdx.game.GameContext;
import progark.mygdx.game.model.DecodingBoard;
import progark.mygdx.game.model.mode.EasyMode;
import progark.mygdx.game.model.mode.HardMode;
import progark.mygdx.game.model.mode.MediumMode;
import progark.mygdx.game.model.mode.Mode;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.state.viewstates.CreateGameViewState;
import progark.mygdx.game.state.viewstates.GamePlayViewState;
import progark.mygdx.game.state.viewstates.LobbyViewState;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.view.CreateGameView;

public class CreateGameController {
    private CreateGameViewState state;
    private CreateGameView createGameView;
    private Game game;
    private Mode selectedMode;
    private Player player;
    private DecodingBoard board;
    private GameContext context;

    public CreateGameController(CreateGameViewState state, GameContext context) {
        this.state = state;
        this.context = context;
        this.createGameView = new CreateGameView(state.getCamera());
        Gdx.input.setInputProcessor(createGameView.getStage());
    }


    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            state.getCamera().unproject(touchPos);

            if (createGameView.getBackBtnBound().contains(touchPos.x,touchPos.y)){
                createGameView.setBackButtonPressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        state.getStateManager().setState(new StartMenuViewState(context));
                    }
                }, 0.2f);
            }

            if (createGameView.getTimeBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedMode("time");
            }
            if (createGameView.getClassicBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedMode("classic");
            }
            if (createGameView.getEasyBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedMode("easy");
            }
            if (createGameView.getMediumBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedMode("medium");
            }
            if (createGameView.getHardBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedMode("hard");
            }
            if (createGameView.getSingleBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedPlayer("single");
            }
            if (createGameView.getMultiBtnBound().contains(touchPos.x, touchPos.y)) {
                createGameView.setSelectedPlayer("multi");
            }
            if (createGameView.getCreateBtnBound().contains(touchPos.x, touchPos.y)) {
                String nickname = createGameView.getNickname().trim();
                if (nickname.isEmpty()) {
                    Gdx.app.log("CREATE_GAME", "Nickname is required to start the game.");
                    createGameView.triggerNicknameWarning();
                    return;
                }
                createGameView.setCreateGamePressed(true);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        //state.getStateManager().setState(new GamePlayState(state.getStateManager(), _FBIC));
                    }
                }, 0.2f);
                switch (createGameView.getSelectedMode()) {
                    case "easy":
                        selectedMode = new EasyMode();
                        break;
                    case "medium":
                        selectedMode = new MediumMode();
                        break;
                    case "hard":
                        selectedMode = new HardMode();
                        break;
                    default:
                        selectedMode = new EasyMode(); // fallback
                }

                System.out.println(createGameView.getNickname());
                System.out.println(createGameView.getPin());
                System.out.println(createGameView.getSelectedMode());
                System.out.println(createGameView.getSelectedMode());
                System.out.println(createGameView.getSelectedPlayer());

                // Set up game and multiplayer mode
                boolean isMultiplayer = createGameView.getSelectedPlayer().equals("multi");
                game = new Game(selectedMode, isMultiplayer);

                // Set up player and decoding board
                player = new Player();
                player.setName(createGameView.getNickname());
                context.setCurrentPlayer(player);
                board = new DecodingBoard(game, player);
                game.addDecodingBoard(board);

                if (createGameView.getSelectedPlayer().equals("multi")) {
                    game.setSingleplayer(false);
                    context.setGame(game);
                    state.getStateManager().setState(new LobbyViewState(context));
                } else if (createGameView.getSelectedPlayer().equals("single")) {
                    game.setSingleplayer(true);
                    context.setGame(game);
                    state.getStateManager().setState(new GamePlayViewState(context));
                }

            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        createGameView.render(sb);
    }

    public void enter() {
    }

    public void dispose() {
        createGameView.dispose();
    }

    public void exit() {
        dispose();
    }
}
