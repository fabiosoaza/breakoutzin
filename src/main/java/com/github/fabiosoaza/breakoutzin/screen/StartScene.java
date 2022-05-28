package com.github.fabiosoaza.breakoutzin.screen;

import com.github.fabiosoaza.breakoutzin.base.ControlCommandsRegister;
import com.github.fabiosoaza.breakoutzin.Game;
import com.github.fabiosoaza.breakoutzin.Application;
import com.github.fabiosoaza.breakoutzin.Levels;
import com.github.fabiosoaza.breakoutzin.base.Menu;
import com.github.fabiosoaza.breakoutzin.base.*;
import com.github.fabiosoaza.breakoutzin.objects.Ball;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StartScene extends Scene {

    private static final Font fonte = new Font("Consolas", Font.BOLD, 36);

    private Ball ball;
    private Menu menuLevel;
    private Text title;
    private Text pushStartText;
    private int fpsTimer = 0;
    private boolean blink = false;
    private Game game;

    public StartScene(Game game) {
        super(game.getWidth(), game.getHeight());
        this.game = game;
    }
    private ControlCommandsRegister register;


    @Override
    public void load() {

        register = new ControlCommandsRegister();

        ball = new Ball();
        title = new Text(fonte, "Breakout");
        title.setColor(Color.WHITE);
        pushStartText = new Text("Pressione <" + Command.BUTTON_A.getName() + "> para iniciar.");
        pushStartText.setColor(Color.WHITE);

        menuLevel = new Menu("Level");
        menuLevel.addOptions(IntStream.rangeClosed(1, Levels.getTotalLevels()).mapToObj(v -> String.format("%02d", v)).collect(Collectors.toList()).toArray(new String[]{}));

        Util.center(ball, width, height);
        Util.center(title, width, height);
        Util.center(menuLevel, width, height);
        Util.center(pushStartText, width, height);

        menuLevel.setPy(pushStartText.getPy() + pushStartText.getFont().getSize() + 30);
        menuLevel.setPx(pushStartText.getPx());

        title.setPx(pushStartText.getPx() - 10);
        pushStartText.setPy(title.getPy() + title.getFont().getSize());


        ball.setPx(title.getTop() + ball.getHeight());
        ball.setEnabled(true);
        title.setEnabled(true);
        menuLevel.setSelected(true);
        menuLevel.setEnabled(true);
        pushStartText.setEnabled(true);
    }

    @Override
    public void unload() {

    }

    @Override
    public void update() {
        if (menuLevel.isEnabled()) {
            menuLevel();
        }
        // Controle da bola
        ball.incPx();
        ball.incPy();

        if (Util.collided(menuLevel, ball) || Util.collided(title, ball)) {
            ball.invertX();
            ball.invertY();
        }

        if (ball.getPx() < 0 || ball.getPx() + ball.getWidth() > width) {
            // Colisao nas laterais da tela
            ball.invertX();

        } else if (ball.getPy() <= 0 || ball.getPy() + ball.getHeight() >= height) {
            // Colisao no topo ou base da tela
            ball.invertY();
        }

        if (ball.getPy() < 0)
            ball.setPy(0);
        else if (ball.getPy() + ball.getHeight() > height)
            ball.setPy(height - ball.getHeight());
    }

    private void menuLevel() {
        if (register.pressed(Command.UP) || register.pressed(Command.DOWN)) {
            if (menuLevel.isSelected()) {
                menuLevel.setSelected(false);

            } else {
                menuLevel.setSelected(true);
            }

        } else if (register.pressed(Command.LEFT) || register.pressed(Command.RIGHT)) {
            boolean leftPressed = register.pressed(Command.LEFT);
            menuLevel.changeOption(leftPressed);

            int levelToStart = menuLevel.getOptionId() + 1;
            ball.setVel(Levels.getLevelByNumber(levelToStart).getBallSpeed());
            game.setLevelToStart(levelToStart);
        }

        register.clearBuffer();
    }

    @Override
    public void render(Graphics2D g) {
        ball.render(g);
        if (menuLevel.isEnabled()) {
            menuLevel.render(g);
        }
        title.render(g);

        //Atualiza a cada 3 segundos
        if (fpsTimer == Math.round(Application.FRAMES / 1.2)) {
            fpsTimer = 0;
            blink = !blink;
        } else {
            fpsTimer++;
        }

        if (blink) {
            pushStartText.render(g);

        }

    }

}