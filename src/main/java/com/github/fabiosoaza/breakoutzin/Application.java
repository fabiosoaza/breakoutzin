package com.github.fabiosoaza.breakoutzin;

import com.github.fabiosoaza.breakoutzin.base.ControlCommandsRegister;

public class Application {


    public static final int FRAMES = 20;

    private static final int FPS = Math.round(1000 / (FRAMES + 1.5f));


    public static int delay = 0;
    public static int fpsSum = 0;


    private static GameWindow gameWindow;

    private static Game game;

    private boolean debug = false;


    public Application() {
        ControlCommandsRegister register = new ControlCommandsRegister();
        gameWindow = new GameWindow(register);
        game = new Game(register, gameWindow.getPanel().getWidth(), gameWindow.getPanel().getHeight(), gameWindow.getG2d(), new SoundPlayer());
    }


    public void run() {
        long nextUpdate = 0;

        int fpsCounter = 0;
        long nextSecond = System.currentTimeMillis() + 1000;

        game.onStart();

        while (true) {
            long now = System.currentTimeMillis();
            if (now >= nextUpdate) {
                gameWindow.fillBlack();
                game.onFrameUpdate();
                gameWindow.repaintPanel();
                fpsCounter++;
                delay = (int) (System.currentTimeMillis() - now);
                nextUpdate = System.currentTimeMillis() + FPS;
            }
            if (debug && (now >= nextSecond)) {
                System.out.println("Atraso = " + delay);
                System.out.println("SOMA FPS = " + fpsSum);
                fpsSum = fpsCounter;
                fpsCounter = 0;
                nextSecond = System.currentTimeMillis() + 1000;
            }
        }
    }


    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

}