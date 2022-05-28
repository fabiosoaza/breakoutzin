package com.github.fabiosoaza.breakoutzin;

import com.github.fabiosoaza.breakoutzin.objects.Brick;

import java.util.Arrays;

public class Level {

    private int number;
    private int ballSpeed;
    private int paddleSpeed;
    private int[][] bitmapBricks;

    public Level() {

    }

    public Level(int number, int ballSpeed, int paddleSpeed, int[][] bitmapBricks) {
        this.number = number;
        this.ballSpeed = ballSpeed;
        this.paddleSpeed = paddleSpeed;
        this.bitmapBricks = bitmapBricks;
    }

    public int getNumber() {
        return number;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public int[][] getBitmapBricks() {
        return bitmapBricks;
    }

    public int getTotalBreakableBricks() {
        int total = (int) Arrays.stream(bitmapBricks)
                .flatMapToInt(Arrays::stream)
                .filter(valor -> Brick.BlockType.fromCode(valor).isDestructible()).
                count();
        return total;
    }
}
