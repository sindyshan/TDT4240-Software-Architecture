package progark.mygdx.game.networking;

import java.util.List;

import progark.mygdx.game.DataHolderClass;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.model.Player;

public interface FirebaseInterface {
    public void createGame(String pin, Game game);
    public void joinLobby(String pin, Player player, Runnable onSuccess, Runnable onFail);

    void SetOnValueChangedListener(String pin, DataHolderClass dataholder);

    public void SetValueInDb(String target, String value);
    public void updateGameState(String pin, String status);

    public void listenForPlayers(String pin, PlayerUpdateListener listener);
    interface PlayerUpdateListener {
         void onPlayersUpdated(List<String> playerNames);

        void onError(String error);
    }

    void fetchSecretCode(String pin, SecretCodeCallback callback);

    interface SecretCodeCallback {
        void onCodeFetched(List<String> colorNames);
        void onError(String error);
    }

    void fetchGameMode(String pin, ModeCallback callback);

    interface ModeCallback {
        void onModeFetched(String modeName);
        void onError(String error);
    }

    void fetchAvailableColors(String pin, ColorsCallback callback);

    interface ColorsCallback {
        void onColorsFetched(List<String> colors);
        void onError(String error);
    }

}
