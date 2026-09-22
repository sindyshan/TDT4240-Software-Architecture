package progark.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static MusicManager instance;
    private Music backgroundMusic;
    private boolean isMusicOn = true;

    private MusicManager() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        backgroundMusic.setLooping(true);
    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public void playMusic() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying() && isMusicOn) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    public void toggleMusic() {
        if (isMusicOn) {
            isMusicOn = false;
            stopMusic();
        } else {
            isMusicOn = true;
            playMusic();
        }
    }

    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }

}
