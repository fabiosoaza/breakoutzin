package com.github.fabiosoaza.breakoutzin;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    public enum Sfx {
        BRICK_BLUE,
        BRICK_GREEN,
        BRICK_ORANGE,
        RED_BRICK,
        YELLOW_BRICK,
        BALL_ORIGIN,
        WALL,
        PADDLE;


    }

    private static final String SOUND_FILES_PATH = "sound/";

    // Som
    private static AudioInputStream as;
    private static Clip clipBrickBlue;
    private static Clip clipBrickGreen;
    private static Clip clipBrickOrange;
    private static Clip clipBrickRed;
    private static Clip clipBrickYellow;
    private static Clip clipBallOrigin;
    private static Clip clipWall;
    private static Clip clipPaddle;

    public SoundPlayer(){
        load();
    }

    private void load() {

        try {
            clipBrickBlue = createClip(SOUND_FILES_PATH + "blue-beep.wav");
            clipBrickBlue.open(as);

            clipBrickGreen = createClip(SOUND_FILES_PATH + "green-beep.wav");
            clipBrickGreen.open(as);

            clipBrickOrange = createClip(SOUND_FILES_PATH + "orange-beep.wav");
            clipBrickOrange.open(as);

            clipBrickRed = createClip(SOUND_FILES_PATH + "red-beep.wav");
            clipBrickRed.open(as);

            clipBrickYellow = createClip(SOUND_FILES_PATH + "yellow-beep.wav");
            clipBrickYellow.open(as);

            clipBallOrigin = createClip(SOUND_FILES_PATH + "ball-origin-beep.wav");
            clipBallOrigin.open(as);

            clipWall = createClip(SOUND_FILES_PATH + "wall-beep.wav");
            clipWall.open(as);

            clipPaddle = createClip(SOUND_FILES_PATH + "orange-beep.wav");
            clipPaddle.open(as);

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void playSound(Sfx sound) {
        switch (sound) {
            case BRICK_BLUE:
                playClip(clipBrickBlue);
                break;
            case BRICK_GREEN:
                playClip(clipBrickGreen);
                break;
            case BRICK_ORANGE:
                playClip(clipBrickOrange);
                break;
            case RED_BRICK:
                playClip(clipBrickRed);
                break;
            case YELLOW_BRICK:
                playClip(clipBrickYellow);
                break;
            case BALL_ORIGIN:
                playClip(clipBallOrigin);
                break;
            case WALL:
                playClip(clipWall);
                break;
            case PADDLE:
                playClip(clipPaddle);
                break;
        }
    }

    private void playClip(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(0);
        }
    }

    public void unload() {
        stopClip(clipBrickBlue);
        stopClip(clipBrickGreen);
        stopClip(clipBrickOrange);
        stopClip(clipBrickRed);
        stopClip(clipBrickYellow);
        stopClip(clipBallOrigin);
        stopClip(clipWall);
        stopClip(clipPaddle);
    }

    private Clip createClip(String pathname) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        as = AudioSystem.getAudioInputStream(new File(pathname));
        return AudioSystem.getClip();
    }

    private void stopClip(Clip clip) {
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


}
