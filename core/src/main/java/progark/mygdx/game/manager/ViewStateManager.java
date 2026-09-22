package progark.mygdx.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.state.viewstates.GameViewState;

public class ViewStateManager {
    private static ViewStateManager instance;
    private GameViewState currentState;
    private GameViewState previousState;

    public ViewStateManager() {

    }

    public static ViewStateManager getInstance() {
        if(instance == null){
            instance = new ViewStateManager();
        }
        return instance;
    }

    public void setState(GameViewState newState) {
        if (currentState != null) {
            previousState = currentState;
            currentState.exit();
        }
        currentState = newState;
        newState.enter();
    }

    public void returnToPreviousState() {
        if (previousState != null) {
            setState(previousState);
        }
    }


    public void update(float dt) {
        if (currentState != null) currentState.update(dt);
    }

    public void render(SpriteBatch batch) {
        if (currentState != null) currentState.render(batch); // Modify this line
    }

    public void dispose() {
    }

}
