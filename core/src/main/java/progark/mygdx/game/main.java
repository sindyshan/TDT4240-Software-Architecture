package progark.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import progark.mygdx.game.manager.MusicManager;
import progark.mygdx.game.networking.FirebaseInterface;
import progark.mygdx.game.state.viewstates.StartMenuViewState;
import progark.mygdx.game.manager.ViewStateManager;

public class main extends ApplicationAdapter {
    public static float WIDTH;
    public static float HEIGHT;
    private SpriteBatch batch;
    private ViewStateManager viewStateManager;
    private FirebaseInterface _FBIC;

    public main(FirebaseInterface _FBIC) {
        this._FBIC = _FBIC;
    }

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        MusicManager.getInstance().playMusic();
        viewStateManager = new ViewStateManager();
        GameContext context = new GameContext(_FBIC, viewStateManager, null);
        viewStateManager.setState(new StartMenuViewState(context));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        viewStateManager.update(dt);
        viewStateManager.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        viewStateManager.dispose();
    }
}
