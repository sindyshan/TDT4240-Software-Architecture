package progark.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import progark.mygdx.game.main;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class JoinGameView{
    private Texture backgroundTexture, joinGameTitle, joinButtonTexture;
    private OrthographicCamera camera;
    private TextField nicknameField, pinField;
    private Texture nicknameTexture, pinTexture, plainButtonTexture, settingsTexture, backButtonTexture;
    private Stage stage;
    private BitmapFont font;
    private BitmapFont errorFont;

    private Rectangle backButtonBounds, settingsButtonBounds, joinGameBounds;
    private float settingsBtnWidth, settingsBtnHeight, backBtnWidth, backBtnHeight, titleWidth, titleHeight, titleX, titleY;

    private float nicknameWidth, nicknameHeight, nicknameX, nicknameY, plainButtonWidth, plainButtonHeight, nicknameButtonX, nicknameButtonY;
    private float pinWidth, pinHeight, pinX, pinY, pinButtonY, joinButtonWidth, joinButtonHeight, joinButtonX, joinButtonY;
    private boolean isJoinBtnPressed = false;
    private boolean isSettingsButtonPressed = false;
    private boolean isBackButtonPressed = false;
    private String errorMessage = "";


    public JoinGameView(OrthographicCamera camera){
        this.camera = camera;
        camera.setToOrtho(false, main.WIDTH, main.HEIGHT);
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("gui/Backgrounds/Background3.png");
        joinButtonTexture = new Texture("gui/Buttons/JoinButton.png");
        joinGameTitle = new Texture("gui/Titles/JoinGameTitle.png");
        nicknameTexture = new Texture("gui/Texts/NicknameText.png");
        pinTexture = new Texture("gui/Texts/PinText.png");
        plainButtonTexture = new Texture("gui/Buttons/PlainButton.png");
        settingsTexture = new Texture("gui/Buttons/SettingsButton.png");
        backButtonTexture = new Texture("gui/Buttons/BackButton.png");

        settingsBtnWidth = main.WIDTH * 0.09f;
        settingsBtnHeight = settingsTexture.getHeight() * (settingsBtnWidth / settingsTexture.getWidth()) -10;
        settingsButtonBounds = new Rectangle(main.WIDTH - settingsBtnWidth - 10, main.HEIGHT - settingsBtnHeight - 5, settingsBtnWidth, settingsBtnHeight);

        backBtnWidth = main.WIDTH * 0.18f;
        backBtnHeight = backButtonTexture.getHeight() * (backBtnWidth / backButtonTexture.getWidth());
        backButtonBounds = new Rectangle(15, main.HEIGHT - backBtnHeight - 10, backBtnWidth, backBtnHeight);

        titleWidth = main.WIDTH * 0.7f;
        titleHeight = joinGameTitle.getHeight() * (titleWidth / joinGameTitle.getWidth());
        titleX = (main.WIDTH - titleWidth) / 2;
        titleY = (main.HEIGHT/6)*5;

        nicknameWidth = main.WIDTH * 0.5f;
        nicknameHeight = nicknameTexture.getHeight() * (nicknameWidth / nicknameTexture.getWidth());
        nicknameX = (main.WIDTH - nicknameWidth) / 2;
        nicknameY = titleY - nicknameHeight - 50;

        plainButtonWidth = main.WIDTH * 0.6f;
        plainButtonHeight = plainButtonTexture.getHeight() * (plainButtonWidth / plainButtonTexture.getWidth());
        nicknameButtonX = (main.WIDTH - plainButtonWidth) / 2;
        nicknameButtonY = nicknameY - plainButtonHeight - 20;

        pinWidth = main.WIDTH * 0.5f;
        pinHeight = pinTexture.getHeight() * (pinWidth / pinTexture.getWidth());
        pinX = (main.WIDTH - pinWidth) / 2;
        pinY = nicknameButtonY - pinHeight - 50;
        pinButtonY = pinY - plainButtonHeight - 20;

        joinButtonWidth = main.WIDTH * 0.6f;
        joinButtonHeight = joinButtonTexture.getHeight() * (joinButtonWidth / joinButtonTexture.getWidth());
        joinButtonX = (main.WIDTH - joinButtonWidth) / 2;
        joinButtonY = pinButtonY - joinButtonHeight - 300;
        joinGameBounds = new Rectangle(joinButtonX,joinButtonY,joinButtonWidth,joinButtonHeight);

        font = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
        font.getData().setScale(1.5f);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.messageFontColor = Color.PURPLE;
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.PINK;

        nicknameField = new TextField("",textFieldStyle);
        nicknameField.setMessageText("Enter nickname");
        nicknameField.setSize(plainButtonWidth, plainButtonHeight);
        nicknameField.setPosition((main.WIDTH - plainButtonWidth) / 2, nicknameY - plainButtonHeight - 20);
        nicknameField.setMaxLength(20);
        nicknameField.setAlignment(1);

        pinField = new TextField("",textFieldStyle);
        pinField.setMessageText("Enter pin");
        pinField.setSize(plainButtonWidth, plainButtonHeight);
        pinField.setPosition((main.WIDTH - plainButtonWidth) / 2, pinY - plainButtonHeight - 20);
        pinField.setMaxLength(4);
        pinField.setAlignment(1);

        stage.addActor(nicknameField);
        stage.addActor(pinField);
        setupInputListeners();
    }

    private void setupInputListeners() {
        nicknameField.setTextFieldListener((textField, c) -> {
            if (c == '\n') {
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        });

        pinField.setTextFieldListener((textField, c) -> {
            if (c == '\n') {
                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        });
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, main.WIDTH, main.HEIGHT);
        batch.draw(joinGameTitle, titleX, titleY, titleWidth, titleHeight);
        batch.draw(nicknameTexture, nicknameX, nicknameY, nicknameWidth, nicknameHeight);
        batch.draw(pinTexture, pinX, pinY, pinWidth, pinHeight);
        batch.setColor(1, 1, 1, 0.8f);
        batch.draw(plainButtonTexture, nicknameButtonX, nicknameButtonY, plainButtonWidth, plainButtonHeight);
        batch.draw(plainButtonTexture, nicknameButtonX, pinButtonY, plainButtonWidth, plainButtonHeight);
        batch.setColor(1, 1, 1, 1);

        if (isJoinBtnPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1); // Darker shade when pressed
        } else {
            batch.setColor(1, 1, 1, 1); // Normal color
        }
        batch.draw(joinButtonTexture, joinButtonX, joinButtonY, joinButtonWidth, joinButtonHeight);
        batch.setColor(1, 1, 1, 1);

        if (isSettingsButtonPressed) {
             batch.setColor(0.6f, 0.6f, 0.6f, 1); // Darker shade when pressed
         } else {
             batch.setColor(1, 1, 1, 1); // Normal color
         }
        //batch.draw(settingsTexture, main.WIDTH - settingsBtnWidth - 10, main.HEIGHT - settingsBtnHeight - 5, settingsBtnWidth, settingsBtnHeight);
        batch.setColor(1, 1, 1, 1);

        if (isBackButtonPressed) {
            batch.setColor(0.6f, 0.6f, 0.6f, 1); // Darker shade when pressed
        } else {
            batch.setColor(1, 1, 1, 1); // Normal color
        }
        batch.draw(backButtonTexture, 15, main.HEIGHT - backBtnHeight - 10, backBtnWidth, backBtnHeight);

        batch.setColor(1, 1, 1, 1);

        if (!errorMessage.isEmpty()) {
            errorFont = new BitmapFont(Gdx.files.internal("gui/Fonts/jhenghei.fnt"));
            errorFont.getData().setScale(2.2f);
            errorFont.setColor(Color.WHITE);

            float errorTextWidth = errorFont.getRegion().getRegionWidth();
            try {
                com.badlogic.gdx.graphics.g2d.GlyphLayout layout = new com.badlogic.gdx.graphics.g2d.GlyphLayout();
                layout.setText(errorFont, errorMessage);
                errorTextWidth = layout.width;
            } catch (Exception ignored) {}

            float errorX = (main.WIDTH - errorTextWidth) / 2f;
            float errorY = joinButtonY - 90;

            errorFont.draw(batch, errorMessage, errorX, errorY);
            errorFont.setColor(Color.WHITE);
        }


        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        backgroundTexture.dispose();
        stage.dispose();
    }

    public Stage getStage(){
        return this.stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public Rectangle getBackButtonBounds(){
        return this.backButtonBounds;
    }

    public Rectangle getSettingsButtonBounds(){
        return this.settingsButtonBounds;
    }

    public Rectangle getJoinGameBounds(){
        return this.joinGameBounds;
    }

    public String getNickname(){
        return this.nicknameField.getText();
    }
    public String getPin(){
        return this.pinField.getText();
    }

    public boolean isNicknameEntered(){
        return !nicknameField.getText().trim().isEmpty();
    }
    public boolean isPinEntered(){
        return !pinField.getText().trim().isEmpty();
    }
    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

    public void setJoinButtonPressed(boolean pressed) {
        isJoinBtnPressed = pressed;
    }

    public void setSettingsButtonPressed(boolean pressed) {
        isSettingsButtonPressed = pressed;
    }

    public void setBackButtonPressed(boolean pressed) {
        isBackButtonPressed = pressed;
    }

}
