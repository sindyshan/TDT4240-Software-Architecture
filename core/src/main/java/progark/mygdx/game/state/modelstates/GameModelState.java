package progark.mygdx.game.state.modelstates;

public interface GameModelState {
    void enter();         // When entering the state
    void update(float dt); // Game loop update
    void exit();          // When leaving the state

}
