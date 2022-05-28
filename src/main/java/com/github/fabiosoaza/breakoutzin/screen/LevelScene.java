package com.github.fabiosoaza.breakoutzin.screen;

import com.github.fabiosoaza.breakoutzin.*;
import com.github.fabiosoaza.breakoutzin.base.*;
import com.github.fabiosoaza.breakoutzin.objects.*;

import java.awt.*;
import java.util.Arrays;

public class LevelScene extends Scene {

    private static final short SCORE_SPACING = 10;
    private static final short TOP_BRICK_SPACING = 50;
    private static final short MAX_TRIES = 5;

    private final Score score, tries, level;
    private final Paddle paddle;

    private final Wall ceiling;
    private final Wall leftWall;
    private final Wall rightWall;

    private final Text textPause = new Text(Score.DEFAULT_FONT);
    private final Text textGameOver = new Text(Score.DEFAULT_FONT);
    private final Text textWinner = new Text(Score.DEFAULT_FONT);

    private Ball ball;
    private Brick[][] bricks;

    private int remainingBlocksLevel = 0;
    private boolean restartBallPosition = false;
    private float inc = 0.5f;


    private ControlCommandsRegister register;
    private Game game;


    public LevelScene(Game game) {
        super(game.getWidth(), game.getHeight());

        this.game = game;
        register = new ControlCommandsRegister();

        ball = new Ball();
        ball.setDirY(1);

        paddle = new Paddle();
        ceiling = new Wall();
        leftWall = new Wall();
        rightWall = new Wall();

        level = new Score();
        this.level.setValue((short) game.getLevelToStart());

        score = new Score();
        tries = new Score();

        tries.setValue(MAX_TRIES);
    }

    private Brick[][] createBrickWall(int[][] bitmapBricks) {
        return Arrays.stream(bitmapBricks).map(
                r -> Arrays.stream(r).mapToObj(
                        b -> new Brick(
                                Brick.BlockType.fromCode(b)
                        )
                ).toArray(Brick[]::new)
        ).toArray(Brick[][]::new);
    }

    @Override
    public void load() {
        Level level = getCurrentLevel();

        score.setPx(width / 2 - 120);
        score.setPy(Score.FONT_SIZE);

        tries.setPx(width / 2 + 120 - Score.FONT_SIZE / 2);
        tries.setPy(Score.FONT_SIZE);

        this.level.setPx((width / 2 + 240 - Score.FONT_SIZE / 2));
        this.level.setPy(Score.FONT_SIZE);



        textGameOver.setColor(score.getColor());
        textWinner.setColor(score.getColor());

        ceiling.setHeight(15);
        ceiling.setWidth(width);
        ceiling.setPx(0);
        ceiling.setPy(score.getPy() + SCORE_SPACING);

        leftWall.setHeight(height - score.getHeight());
        leftWall.setWidth(15);
        leftWall.setPx(0);
        leftWall.setPy(ceiling.getBottom());

        rightWall.setHeight(height - score.getHeight());
        rightWall.setWidth(15);
        rightWall.setPx(width - rightWall.getWidth());
        rightWall.setPy(ceiling.getBottom());

        paddle.setVel(level.getPaddleSpeed());
        paddle.setPy(height - (paddle.getHeight() + ball.getHeight()));

        bricks = createBrickWall(level.getBitmapBricks());

        int firstBrickXPosition = firstBrickXPosition();
        int firstBrickYPosition = firstBrickYPosition();

        for (Brick[] line : bricks) {
            for (Brick brick : line) {
                brick.setPy(firstBrickYPosition);
                brick.setPx(firstBrickXPosition);
                firstBrickXPosition += Brick.DEFAULT_WIDTH + Brick.BRICK_SPACING;
            }
            firstBrickXPosition = firstBrickXPosition();
            firstBrickYPosition += Brick.DEFAULT_HEIGHT + Brick.BRICK_SPACING;
        }

        remainingBlocksLevel = level.getTotalBreakableBricks();
        ball.setVel(level.getBallSpeed());

        resetBallPosition();
        Util.center(paddle, width, 0);

        ball.setEnabled(true);
        paddle.setEnabled(true);
        ceiling.setEnabled(true);
        leftWall.setEnabled(true);
        rightWall.setEnabled(true);


    }

    private void resetBallPosition() {
        Util.center(ball, width, height);
    }

    private void breakBrick(Brick brick) {
        playSound(brick);
        if (brick.getType().isDestructible()) {
            short scorePoint = (short) (score.getValue() + brick.calcScorePoint());
            score.setValue(scorePoint);
            brick.setEnabled(false);
            remainingBlocksLevel--;
            if (remainingBlocksLevel <= 0 && level.getValue() == Levels.getTotalLevels()) {
                game.setStatus(GameState.WIN);
            } else if (remainingBlocksLevel <= 0) {
                level.add();
                load();
            }
        }

    }

    private void playSound(Brick brick) {
        if (brick.getType().isDestructible()) {
            switch (brick.getType()) {
                case BLUE:
                    game.playSound(SoundPlayer.Sfx.BRICK_BLUE);
                    break;
                case ORANGE:
                    game.playSound(SoundPlayer.Sfx.BRICK_ORANGE);
                    break;
                case GREEN:
                    game.playSound(SoundPlayer.Sfx.BRICK_GREEN);
                    break;
                case RED:
                    game.playSound(SoundPlayer.Sfx.RED_BRICK);
                    break;
                case DARK_YELLOW:
                case YELLOW_BRASILIS:
                    game.playSound(SoundPlayer.Sfx.YELLOW_BRICK);
                    break;
            }
        } else {
            game.playSound(SoundPlayer.Sfx.WALL);
        }

    }


    @Override
    public void update() {
        if (game.notPlaying())
            return;


        ball.incPx();
        ball.incPy();

        checkPaddles();

        if (restartBallPosition) {
            restartBallPosition = false;
            ball.invertX();
            ball.setVel(getCurrentLevel().getBallSpeed());
            resetBallPosition();
        } else {
            boolean ballIsOutOfBounds = ballIsOutOfBounds(ball);
            this.restartBallPosition = ballIsOutOfBounds;
            if (ballIsOutOfBounds) {
                short scorePoint = (short) (tries.getValue() - 1);
                tries.setValue(scorePoint);
                if (scorePoint == 0) {
                    game.setStatus(GameState.GAME_OVER);
                    this.restartBallPosition = false;
                }
            } else {
                checkWallCollision();
                correctBallCollisionWalls();
                checkPaddleCollision();
                checkBricksCollision();
            }
        }

    }


    private void correctBallCollisionWalls() {
        if (ball.getTop() <= ceiling.getBottom()) {
            //trava posicao da bola para não ultrapassar limites
            ball.setPy(ceiling.getBottom());
        }
        if (ball.getRight() >= rightWall.getLeft()) {
            //trava posicao da bola para não ultrapassar limites
            ball.setPx(rightWall.getLeft() - ball.getWidth());
        }
        if (ball.getLeft() <= leftWall.getRight()) {
            //trava posicao da bola para não ultrapassar limites
            ball.setPx(leftWall.getRight());
        }
    }

    private void checkBricksCollision() {
        for (Brick[] lines : bricks) {
            for (Brick brick : lines) {
                Direction direction = Util.getCollisionDirection(ball, brick);
                if (direction == null) {
                    continue;
                }
                if (direction == Direction.TOP) {
                    //trava bola para não ultrapassar limites do tijolo. Alta velocidade
                    ball.setPy(brick.getTop() - ball.getHeight());
                    ball.invertY();
                    breakBrick(brick);
                    return;
                } else if (direction == Direction.BOTTOM) {
                    //trava bola para não ultrapassar limites do tijolo. Alta velocidade
                    ball.setPy(brick.getBottom());
                    ball.invertY();
                    breakBrick(brick);
                    return;
                } else if (direction == Direction.RIGHT) {
                    //trava bola para não ultrapassar limites do tijolo. Alta velocidade
                    ball.setPx(brick.getRight());
                    ball.invertX();
                    breakBrick(brick);
                    return;
                } else if (direction == Direction.LEFT) {
                    //trava bola para não ultrapassar limites do tijolo. Alta velocidade
                    ball.setPx(brick.getLeft() - ball.getWidth());
                    ball.invertX();
                    breakBrick(brick);
                    return;
                }

            }

        }
    }

    private void checkWallCollision() {
        boolean collided = false;
        if (ball.getTop() <= ceiling.getBottom()) {
            collided = true;
            ball.invertY();
        }
        if (ball.getRight() >= rightWall.getLeft() || ball.getLeft() <= leftWall.getRight()) {
            collided = true;
            ball.invertX();
        }
        if (collided) {
            game.playSound(SoundPlayer.Sfx.WALL);
        }

    }

    private void checkPaddleCollision() {
        if (Util.collided(paddle, ball)) {
            game.playSound(SoundPlayer.Sfx.PADDLE);
            hitBall(paddle, ball);
        }
    }


    public void hitBall(Entity paddle, Ball ball) {
        float vx = ball.getVelX();
        float vy = ball.getVelY();

         /*
      Se a bola bater no terço da esquerda, ela é rebatida para esquerda
          (passamos -1 para dirX ), e adicionamos velocidade em ambos os
          eixos.
      * */
        if (ball.getPx() < paddle.getPx() + paddle.getWidth() / 3) {
            ball.setDirX(-1);

            vx += inc;
            vy += inc;

              /*
             Se o eixo X da bola for menor que o da paddle (quina),
incrementamos um pouco mais a velocidade no eixo X
             * */
            if (ball.getPx() < paddle.getPx()) {
                vx += inc;
            }

        }
              /*
        Se a bola bater no terço da direita, ela é rebatida para direita
            (passamos 1 para dirX ), e adicionamos velocidade em ambos os
            eixos.
        * */

        else if (ball.getPx() > paddle.getPx() + paddle.getWidth() - paddle.getWidth() / 3) {
            ball.setDirX(1);

            vx += inc;
            vy += inc;

                       /*
             Se o eixo x da bola for menor que o da paddle (quina),
incrementamos um pouco mais a velocidade no eixo x
             * */
            if (ball.getPx() + ball.getWidth() > paddle.getPx() + paddle.getWidth()) {
                vx += inc;
            }

        }

            /*
        Caso
        seja no terço central, incrementamos velY e deixamos velx
        com 1, fazendo a bola voltar quase em linha reta. Estamos usando
        um tipo flutuante para termos mais opções de velocidade.
        */
        else {
            vx = 1;
            vy += inc;
        }

        ball.invertY();
        ball.incVel(vx, vy);

    }

    private Level getCurrentLevel() {
        return Levels.getLevelByNumber((short) this.level.getValue());
    }

    private boolean ballIsOutOfBounds(Ball ball) {
        return ball.getBottom() > height;
    }

    private void checkPaddles() {
        //Movimenta Raquete
        if ( register.pressed(Command.LEFT)) {
            paddle.incPx(paddle.getVel() * -1);
        } else if (register.pressed(Command.RIGHT)) {
            paddle.incPx(paddle.getVel());
        }

        //trava raquete ultrapassar os limites das paredes
        if (Util.collidedX(paddle, leftWall)) {
            paddle.setPx(leftWall.getPx() + leftWall.getWidth());
        }
        if (Util.collidedX(paddle, rightWall)) {
            paddle.setPx(rightWall.getPx() - paddle.getWidth());
        }
    }

    private int firstBrickXPosition() {
        int larguraTodosTijolos = getBrickWallWidth();
        return (width / 2) - (larguraTodosTijolos / 2);
    }

    private int getBrickWallWidth() {
        return (Brick.DEFAULT_WIDTH + Brick.BRICK_SPACING) * bricks[0].length;
    }

    private int getBrickWallHeight() {
        return (Brick.DEFAULT_HEIGHT + Brick.BRICK_SPACING) * bricks.length;
    }

    private int firstBrickYPosition() {
        return ceiling.getPy() + ceiling.getHeight() + TOP_BRICK_SPACING;
    }


    @Override
    public void unload() {
    }


    @Override
    public void render(Graphics2D g) {

        score.render(g);
        tries.render(g, tries.getValue() + "");
        level.render(g, level.getValue() + "");
        ceiling.render(g);
        leftWall.render(g);
        rightWall.render(g);

        Arrays.stream(bricks).forEachOrdered(
                lst -> Arrays.stream(lst)
                        .forEachOrdered(t -> t.render(g))
        );

        ball.render(g);

        paddle.render(g);

        if (game.getStatus() == GameState.PAUSED) {
            Util.center(textPause, width, height + score.getHeight() + getBrickWallHeight());
            textPause.setPx(textPause.getPx() - ((textPause.getWidth() + leftWall.getWidth() + rightWall.getWidth()) * 2));
            textPause.render(g, "PAUSA");
        } else if (game.getStatus() == GameState.GAME_OVER ) {
            Util.center(textGameOver, width, height + score.getHeight() + getBrickWallHeight());
            textGameOver.setPx(score.getPx());
            textGameOver.render(g, "GAME OVER");
        } else if (game.getStatus() == GameState.WIN) {
            Util.center(textWinner, width, height + score.getHeight() + getBrickWallHeight());
            textWinner.setPx(textWinner.getPx() - ((textWinner.getWidth() + leftWall.getWidth() + rightWall.getWidth()) * 2));
            textWinner.render(g, "VENCEU");

        }

    }


}