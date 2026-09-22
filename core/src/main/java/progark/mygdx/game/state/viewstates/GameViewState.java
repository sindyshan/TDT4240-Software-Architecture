package progark.mygdx.game.state.viewstates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameViewState {
    void enter();
    void handleInput();
    void update(float dt);
    void render(SpriteBatch sb);
    void exit();
    void dispose();
    void resize(int width, int height);
}
