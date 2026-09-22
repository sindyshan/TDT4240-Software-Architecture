package progark.mygdx.game.android;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import progark.mygdx.game.DataHolderClass;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.model.Game;
import progark.mygdx.game.model.Player;
import progark.mygdx.game.model.pegcombination.peg.PegInterface;
import progark.mygdx.game.model.pegcombination.peg.CodePeg;


public class FirebaseInterfaceImpl implements FirebaseInterface {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public FirebaseInterfaceImpl()
    {
        database = FirebaseDatabase.getInstance("https://colormystery-ac885-default-rtdb.europe-west1.firebasedatabase.app");
        myRef = database.getReference("message");
    }

    @Override
    public void createGame(String pin, Game game) {
        DatabaseReference gameRef = database.getReference("games").child(pin);
        Map<String, Object> gameData = new HashMap<>();
        gameData.put("pin", pin);
        gameData.put("status", game.getStatus());
        gameData.put("mode", game.getMode().getMode());
        gameData.put("numColors", game.getMode().getNumColors());
        gameData.put("numPegs", game.getMode().getNumPegs());
        gameData.put("availableColors", game.getAvailableColors());
        if (!game.getPlayers().isEmpty()) {
            Player host = game.getPlayers().get(0);

            // Set host object
            Map<String, Object> hostData = new HashMap<>();
            hostData.put("name", host.getName());
            hostData.put("score", host.getScore());
            hostData.put("isColorblind", host.getIsColorblind());
            gameData.put("host", hostData);

            // Set players/{hostName}
            Map<String, Object> players = new HashMap<>();
            Map<String, Object> playerData = new HashMap<>();
            playerData.put("score", host.getScore());
            playerData.put("finished", false);
            playerData.put("name", host.getName());
            playerData.put("isColorblind", host.getIsColorblind());

            players.put(host.getName(), playerData);
            gameData.put("players", players);

            List<String> secretCodeColors = new ArrayList<>();
            for (PegInterface peg : game.getSecretCode().getPegCombination()) {
                if (peg != null && peg instanceof CodePeg) {
                    secretCodeColors.add(((CodePeg) peg).getColor().getColorName());
                }
            }
            gameData.put("secretCode", secretCodeColors);

        }
        // Store secret code as list of peg colors (if needed)
        //gameData.put("secretCode", game.getSecretCode());

        gameRef.setValue(gameData)
            .addOnSuccessListener(aVoid -> {
                Log.d("FIREBASE", "Game model saved successfully.");
            })
            .addOnFailureListener(e -> {
                Log.e("FIREBASE", "Failed to save game model", e);
            });
    }

    @Override
    public void joinLobby(String pin, Player player, Runnable onSuccess, Runnable onFail) {
        DatabaseReference gameRef = database.getReference("games").child(pin);

        gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.w("FIREBASE", "Attempted to join non-existing lobby with PIN: " + pin);
                    onFail.run(); // PIN doesn't exist
                    return;
                }

                DataSnapshot playersSnapshot = snapshot.child("players");
                if (playersSnapshot.getChildrenCount() >= 6) {
                    Log.w("FIREBASE", "Lobby is full for PIN: " + pin);
                    onFail.run();
                    return;
                }

                DatabaseReference playerRef = gameRef.child("players").child(player.getName());

                Map<String, Object> playerData = new HashMap<>();
                playerData.put("score", 0);
                playerData.put("finished", false);
                playerData.put("name", player.getName());
                playerData.put("isColorblind", player.getIsColorblind());

                playerRef.setValue(playerData)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("FIREBASE", player.getName() + " joined the lobby with PIN: " + pin);
                        onSuccess.run();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FIREBASE", "Failed to join lobby", e);
                        onFail.run();
                    });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE", "Database error while joining lobby", error.toException());
                onFail.run();
            }
        });
    }

    @Override
    public void SetOnValueChangedListener(String pin, final DataHolderClass dataholder) {
        DatabaseReference statusRef = database.getReference("games").child(pin).child("status");

        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Game status changed: " + value);
                dataholder.someValue = value;
                dataholder.PrintSomeValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to listen for game status.", error.toException());
            }
        });
    }


    @Override
    public void SetValueInDb(String target, String value) {
        myRef = database.getReference(target);
        myRef.setValue(value);
    }

    @Override
    public void updateGameState(String pin, String status) {
        DatabaseReference gameRef = database.getReference("games").child(pin);
        gameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.w("FIREBASE", "Attempted to update game state of a non-existing lobby with PIN: " + pin);
                    //onFail.run();
                    return;
                }
                // Update the status field
                gameRef.child("status").setValue(status)
                        .addOnSuccessListener(aVoid -> {
                            Log.d("FIREBASE", "Game state updated to: " + status);
                            //onSuccess.run();
                        })
                        .addOnFailureListener(e -> {
                            Log.e("FIREBASE", "Failed to update game state", e);
                            //onFail.run();
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE", "Database error while updating game state", error.toException());
                //onFail.run();
            }
        });


    }

    @Override
    public void listenForPlayers(String pin, PlayerUpdateListener listener) {
        myRef = database.getReference("games").child(pin).child("players");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> playerNames = new ArrayList<>();
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    String playerName = playerSnapshot.child("name").getValue(String.class);
                    if (playerName != null) {
                        playerNames.add(playerName);
                    }
                }
                listener.onPlayersUpdated(playerNames);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FIREBASE", "Failed to listen for players", databaseError.toException());
            }
        });
    }

    @Override
    public void fetchSecretCode(String pin, SecretCodeCallback callback) {
        DatabaseReference codeRef = database.getReference("games").child(pin).child("secretCode");

        codeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> codeColors = new ArrayList<>();
                for (DataSnapshot colorSnap : snapshot.getChildren()) {
                    String color = colorSnap.getValue(String.class);
                    if (color != null) {
                        codeColors.add(color);
                    }
                }
                callback.onCodeFetched(codeColors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    @Override
    public void fetchGameMode(String pin, ModeCallback callback) {
        database.getReference("games").child(pin).child("mode")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String mode = snapshot.getValue(String.class);
                    callback.onModeFetched(mode);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    callback.onError(error.getMessage());
                }
            });
    }

    @Override
    public void fetchAvailableColors(String pin, ColorsCallback callback) {
        DatabaseReference ref = database.getReference("games").child(pin).child("availableColors");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> colors = new ArrayList<>();
                for (DataSnapshot colorSnap : snapshot.getChildren()) {
                    colors.add(colorSnap.getValue(String.class));
                }
                callback.onColorsFetched(colors);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }



}
