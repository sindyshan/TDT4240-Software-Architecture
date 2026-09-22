package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.graphics.Color;


import progark.mygdx.game.main;

public class CreateGameView {
    private OrthographicCamera camera;
    private Stage stage;
    private TextField nicknameField;
    private String nickname;
    private String selectedMode = "easy";
    private String selectedPlayer = "single";


    // Textures
    private Texture backgroundTexture, backBtnTexture, settingsBtnTexture, titleTexture, nicknameTexture;
    private Texture plainBtnTexture, easyBtnTexture, mediumBtnTexture, hardBtnTexture;
    private Texture timeModeTexture, classicModeTexture, singleBtnTexture, multiBtnTexture, createBtnTexture;
    private Texture modeTexture;

    // Bounds
    private Rectangle backBtn, nicknameFieldBound, timeBtnBound, classicBtnBound;
    private Rectangle easyBtnBound, mediumBtnBound, hardBtnBound, singleBtnBound, multiBtnBound, createBtnBound;

    // Positions and sizes
    private float titleX, titleY, titleWidth, titleHeight;
    private float nicknameX, nicknameY, nicknameWidth, nicknameHeight;
    private float plainBtnX, plainBtnY, plainBtnWidth, plainBtnHeight;
    private float modeTextX, modeTextY, modeTextWidth, modeTextHeight;
    private float buttonWidth, buttonHeight, buttonSpacing, startXMode, modeY;
    private float startXDiff, difficultyY;
    private float easyBtnX, mediumBtnX, hardBtnX;
    private float playerY, singleBtnX, multiBtnX;
    private float createBtnX, createBtnY, createBtnWidth, createBtnHeight;
    private float backBtnX, backBtnY, backBtnWidth, backBtnHeight;
    private float settingsBtnX, settingsBtnY, settingsBtnWidth, settingsBtnHeight;

    // colors for the buttons
    private final Color buttonNormalColor = new Color(1, 1, 1, 1);
    private final Color buttonSelectedColor = new Color(0.6f, 0.6f, 0.6f, 1);
    private boolean isCreateGamePressed = false;
    private boolean isBackButtonPressed = false;
    private boolean isSettingsButtonPressed = false;

    private boolean showNicknameWarning = false;
    private float nicknameWarningTimer = 0;
    private final BitmapFont warningFont;


    public CreateGameView(OrthographicCamera camera) {
        this.camera = camera;
        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("gui/Backgrounds/Background1.png");
        backBtnTexture = new Texture("gui/Buttons/BackButton.png");
        settingsBtnTexture = new Texture("gui/Buttons/SettingsButton.png");
        titleTexture = new Texture("gui/Titles/CreateGameTitle.png");
        plainBtnTexture = new Texture("gui/Buttons/PlainButton.png");
        easyBtnTexture = new Texture("gui/Buttons/EasyButton.png");
        mediumBtnTexture = new Texture("gui/Buttons/MediumButton.png");
        hardBtnTexture = new Texture("gui/Buttons/HardButton.png");
        timeModeTexture = new Texture("gui/Buttons/TimeModeButton.png");
        classicModeTexture = new Texture("gui/Buttons/ClassicModeButton.png");
        singleBtnTexture = new Texture("gui/Buttons/SinglePlayerButton.png");
        multiBtnTexture = new Texture("gui/Buttons/MultiplayerButton.png");
        createBtnTexture = new Texture("gui/Buttons/CreateGameButton.png");
        nicknameTexture = new Texture("gui/Texts/NicknameText.png");
        modeTexture = new Texture("gui/Texts/ModeText.png");
        warningFont = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
        warningFont.getData().setScale(2f);

        BitmapFont font = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
        font.getData().setScale(1.5f);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.messageFontColor = Color.PINK;
        textFieldStyle.fontColor = Color.PINK;

        nicknameField = new TextField("", textFieldStyle);
        nicknameField.setMessageText("Enter nickname");
        nicknameField.setMaxLength(20);
        nicknameField.setAlignment(1);


        // Layout calculations
        titleWidth = main.WIDTH;
        titleHeight = titleTexture.getHeight() * (titleWidth / titleTexture.getWidth()) - 20;
        titleX = 0;
        titleY = main.HEIGHT - titleHeight - 150;

        // Nickname
        nicknameWidth = main.WIDTH * 0.3f;
        nicknameHeight = nicknameTexture.getHeight() * (nicknameWidth / nicknameTexture.getWidth());
        nicknameX = (main.WIDTH - nicknameWidth) / 2;
        nicknameY = main.HEIGHT - titleHeight - 250;

        plainBtnWidth = main.WIDTH * 0.7f;
        plainBtnHeight = plainBtnTexture.getHeight() * (plainBtnWidth / plainBtnTexture.getWidth()) + 10;
        plainBtnX = (main.WIDTH - plainBtnWidth) / 2;
        plainBtnY = nicknameY - plainBtnHeight - 10;

        nicknameField.setPosition(plainBtnX, plainBtnY);
        nicknameField.setSize(plainBtnWidth, plainBtnHeight);
        stage.addActor(nicknameField);

        // Mode
        modeTextWidth = main.WIDTH * 0.18f;
        modeTextHeight = modeTexture.getHeight() * (modeTextWidth / modeTexture.getWidth());
        modeTextX = (main.WIDTH - modeTextWidth) / 2;
        modeTextY = plainBtnY - modeTextHeight - 70;

        // Button dimensions
        buttonWidth = main.WIDTH * 0.31f;
        buttonHeight = timeModeTexture.getHeight() * (buttonWidth / timeModeTexture.getWidth()) + 25;
        buttonSpacing = 25f;
        startXMode = (main.WIDTH - (2 * buttonWidth + buttonSpacing)) / 2;
        modeY = modeTextY - buttonHeight - 10;


        difficultyY = modeTextY - buttonHeight - 10;
        startXDiff = (main.WIDTH - (3 * buttonWidth + 2 * buttonSpacing)) / 2;
        easyBtnX = startXDiff;
        mediumBtnX = startXDiff + buttonWidth + buttonSpacing;
        hardBtnX = startXDiff + 2 * (buttonWidth + buttonSpacing);

        playerY = difficultyY - buttonHeight - 120;
        singleBtnX = startXMode;
        multiBtnX = startXMode + buttonWidth + buttonSpacing;

        createBtnWidth = main.WIDTH * 0.50f;
        createBtnHeight = createBtnTexture.getHeight() * (createBtnWidth / createBtnTexture.getWidth()) + 20;
        createBtnX = (main.WIDTH - createBtnWidth) / 2;
        createBtnY = playerY - createBtnHeight - 140;

        backBtnWidth = main.WIDTH * 0.18f;
        backBtnHeight = backBtnTexture.getHeight() * (backBtnWidth / backBtnTexture.getWidth());
        backBtnX = 15;
        backBtnY = main.HEIGHT - backBtnHeight - 10;

        settingsBtnWidth = main.WIDTH * 0.09f;
        settingsBtnHeight = settingsBtnTexture.getHeight() * (settingsBtnWidth / settingsBtnTexture.getWidth()) - 10;
        settingsBtnX = main.WIDTH - settingsBtnWidth - 10;
        settingsBtnY = main.HEIGHT - settingsBtnHeight - 5;

        // Rectangle bounds
        createBtnBound = new Rectangle(createBtnX, createBtnY, createBtnWidth, createBtnHeight);
        backBtn = new Rectangle(backBtnX, backBtnY, backBtnWidth, backBtnHeight);
        easyBtnBound = new Rectangle(easyBtnX, difficultyY, buttonWidth, buttonHeight);
        mediumBtnBound = new Rectangle(mediumBtnX, difficultyY, buttonWidth, buttonHeight);
        hardBtnBound = new Rectangle(hardBtnX, difficultyY, buttonWidth, buttonHeight);
        singleBtnBound = new Rectangle(singleBtnX, playerY, buttonWidth, buttonHeight);
        multiBtnBound = new Rectangle(multiBtnX, playerY, buttonWidth, buttonHeight);
        timeBtnBound = new Rectangle(startXMode, modeY, buttonWidth, buttonHeight);
        classicBtnBound = new Rectangle(startXMode + buttonWidth + buttonSpacing, modeY, buttonWidth, buttonHeight);
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Textures
        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);
        batch.draw(titleTexture, titleX, titleY, titleWidth, titleHeight);
        batch.draw(nicknameTexture, nicknameX, nicknameY, nicknameWidth, nicknameHeight);
        batch.draw(plainBtnTexture, plainBtnX, plainBtnY, plainBtnWidth, plainBtnHeight);
        batch.draw(modeTexture, modeTextX, modeTextY, modeTextWidth, modeTextHeight);

        // Choose mode
        batch.draw(modeTexture, modeTextX, modeTextY, modeTextWidth, modeTextHeight);
        if (selectedMode.equals("easy")) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(easyBtnTexture, easyBtnX, difficultyY, buttonWidth, buttonHeight);
        if (selectedMode.equals("medium")) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(mediumBtnTexture, mediumBtnX, difficultyY, buttonWidth, buttonHeight);
        if (selectedMode.equals("hard")) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(hardBtnTexture, hardBtnX, difficultyY, buttonWidth, buttonHeight);
        batch.setColor(buttonNormalColor);

        // Single or multiplayer
        if (selectedPlayer.equals("single")) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(singleBtnTexture, singleBtnX, playerY, buttonWidth, buttonHeight);
        if (selectedPlayer.equals("multi")) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(multiBtnTexture, multiBtnX, playerY, buttonWidth, buttonHeight);
        batch.setColor(buttonNormalColor);

        // Create game
        if (isCreateGamePressed) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(createBtnTexture, createBtnX, createBtnY, createBtnWidth, createBtnHeight);
        batch.setColor(buttonNormalColor);

        // Back and settings-buttons
        if (isBackButtonPressed) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(backBtnTexture, backBtnX, backBtnY, backBtnWidth, backBtnHeight);
        if (isSettingsButtonPressed) {
            batch.setColor(buttonSelectedColor);
        } else {
            batch.setColor(buttonNormalColor);
        }
        batch.draw(settingsBtnTexture, settingsBtnX, settingsBtnY, settingsBtnWidth, settingsBtnHeight);
        batch.setColor(buttonNormalColor);

        if (showNicknameWarning) {
            nicknameWarningTimer -= Gdx.graphics.getDeltaTime();
            if (nicknameWarningTimer <= 0) {
                showNicknameWarning = false;
            } else {
                String warning = "YOU NEED A NICKNAME";
                GlyphLayout layout = new GlyphLayout(warningFont, warning);
                float x = (main.WIDTH - layout.width) / 2f;
                float y = createBtnY + createBtnHeight + 60;
                warningFont.draw(batch, layout, x, y);
            }
        }

        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void triggerNicknameWarning() {
        showNicknameWarning = true;
        nicknameWarningTimer = 2f;
    }


    public void dispose() {
        backgroundTexture.dispose();
        titleTexture.dispose();
        backBtnTexture.dispose();
        settingsBtnTexture.dispose();
        plainBtnTexture.dispose();
        easyBtnTexture.dispose();
        mediumBtnTexture.dispose();
        hardBtnTexture.dispose();
        timeModeTexture.dispose();
        classicModeTexture.dispose();
        singleBtnTexture.dispose();
        multiBtnTexture.dispose();
        createBtnTexture.dispose();
        nicknameTexture.dispose();
        modeTexture.dispose();
        stage.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Stage getStage() {
        return stage;
    }

    public String generatePin() {
        return "1235";
    }

    // Add getters for button bounds so the controller can use them
    public Rectangle getBackBtnBound() { return backBtn; }
    public String getNickname() { return nicknameField.getText(); }
    public Rectangle getTimeBtnBound() { return timeBtnBound; }
    public Rectangle getClassicBtnBound() { return classicBtnBound; }
    public Rectangle getEasyBtnBound() { return easyBtnBound; }
    public Rectangle getMediumBtnBound() { return mediumBtnBound; }
    public Rectangle getHardBtnBound() { return hardBtnBound; }
    public Rectangle getSingleBtnBound() { return singleBtnBound; }
    public Rectangle getMultiBtnBound() { return multiBtnBound; }
    public Rectangle getCreateBtnBound() { return createBtnBound; }

    public String getPin() {
      return generatePin();
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public String getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedMode(String mode) {
        this.selectedMode = mode;
    }

    public void setSelectedPlayer(String player) {
        this.selectedPlayer = player;
    }

    public void setCreateGamePressed(boolean pressed){
        this.isCreateGamePressed = pressed;
    }

    public void setBackButtonPressed(boolean pressed){
        this.isBackButtonPressed = pressed;
    }

    public void setSettingsButtonPressed(boolean pressed){
        this.isSettingsButtonPressed = pressed;
    }




}
