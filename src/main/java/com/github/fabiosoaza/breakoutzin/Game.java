package com.github.fabiosoaza.breakoutzin;

import com.github.fabiosoaza.breakoutzin.base.Scene;
import com.github.fabiosoaza.breakoutzin.base.Command;
import com.github.fabiosoaza.breakoutzin.base.ControlCommandsRegister;
import com.github.fabiosoaza.breakoutzin.base.Text;
import com.github.fabiosoaza.breakoutzin.screen.StartScene;
import com.github.fabiosoaza.breakoutzin.screen.LevelScene;

import java.awt.*;

public class Game {


    private Scene scene;

    private final ControlCommandsRegister register;

    private final Graphics2D g2d;

    private final int width;
    private final int height;

    private final Text loadingText;

    private int levelToStart = 1;
    private GameState status = GameState.PLAYING;

    private final SoundPlayer soundPlayer;

    public Game(ControlCommandsRegister register, int width, int height, Graphics2D g2d, SoundPlayer soundPlayer) {
        this.register = register;
        this.width = width;
        this.height = height;
        this.g2d = g2d;
        this.loadingText = new Text("Carregando...");
        this.loadingText.setPx(20);
        this.loadingText.setPy(20);
        this.loadingText.setColor(Color.WHITE);
        this.loadingText.setEnabled(true);
        this.soundPlayer = soundPlayer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevelToStart() {
        return levelToStart;
    }

    public void setLevelToStart(int levelToStart) {
        this.levelToStart = levelToStart;
    }

    public GameState getStatus() {
        return status;
    }

    public void setStatus(GameState status) {
        this.status = status;
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void onStart() {
        loadStartScene();
    }


    public boolean notPlaying() {
        return status == GameState.PAUSED
                || status == GameState.GAME_OVER
                || status == GameState.WIN
                || status == GameState.LOST
                ;
    }

    public void pause() {
        if (status == GameState.PLAYING
                || status == GameState.PAUSED) {
            status = status == GameState.PAUSED ? GameState.PLAYING : GameState.PAUSED;

        }
    }

    public void onFrameUpdate() {
        readEvents();
        loadScene();
    }

    private void loadScene() {
        if (scene == null) {
            loadingText.render(g2d);

        } else {
            scene.update();
            scene.render(g2d);
        }
    }

    private void readEvents() {
        // Pressionou espaço ou enter
        if (register.pressed(Command.BUTTON_A)) {
            if (scene instanceof StartScene) {
                loadLevelScene();

            } else if (scene instanceof LevelScene) {
                pause();
            }

            register.clearBuffer();

        }// Pressionou ESQ
        else if (register.pressed(Command.BUTTON_B)) {

            if (scene instanceof LevelScene) {
                loadStartScene();
            }

            register.clearBuffer();
        }
    }

    private void loadLevelScene() {
        if (scene != null) {
            scene.unload();
        }
        scene = new LevelScene(this);
        scene.load();
    }

    private void loadStartScene() {
        if (scene != null) {
            scene.unload();
        }
        scene = new StartScene(this);
        reset();
        scene.load();
    }

    public void playSound(SoundPlayer.Sfx sound){
        soundPlayer.playSound(sound);
    }

    public void reset() {
        status = GameState.PLAYING;
        levelToStart = 1;
    }

}
