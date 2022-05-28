package com.github.fabiosoaza.breakoutzin.objects;

import com.github.fabiosoaza.breakoutzin.base.Entity;

import java.awt.*;


public class Ball extends Entity {

    public static final int VEL_MAXIMA = 40;
    public static final int VEL_INICIAL = 3;
    public static final Color COR_DEFAULT = new Color(213, 85, 70);
    private static final int RAIO = 10;

    private int dirX = -1;

    private int dirY = -1;

    private float velX;

    private float velY;

    public Ball() {
        velX = velY = VEL_INICIAL;
        setHeight(RAIO);
        setWidth(RAIO);
        setColor(COR_DEFAULT);
    }

    @Override
    public void render(Graphics2D g) {
        if (!isEnabled())
            return;

        g.setColor(getColor());
        g.fillOval(getPx(), getPy(), getWidth(), getHeight());
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public void incVel(float vx, float vy) {
        if (vx < VEL_MAXIMA) {
            velX = vx;
        } else {
            velX = VEL_MAXIMA;
        }
        if (vy < VEL_MAXIMA) {
            velY = vy;
        } else {
            velY = VEL_MAXIMA;
        }
    }

    @Override
    public void setVel(int vel) {
        if (vel < VEL_MAXIMA) {
            velX = velY = vel;
        } else {
            velX = velY = VEL_MAXIMA;
        }
    }

    @Override
    public int getVel() {
        return (int) velX;
    }

    public void incPx() {
        incPx((int) velX * dirX);
    }

    public void incPy() {
        incPy((int) velY * dirY);
    }

    public int nextIncPx() {
        return ((int) velX * dirX) + getPx();
    }

    public int nextTop() {
        return nextIncPy();
    }

    public int nextBottom() {
        return nextIncPy() + getHeight();
    }

    public int nextLeft() {
        return nextIncPx();
    }

    public int nextRight() {
        return nextIncPx() + getWidth();
    }

    public int nextIncPy() {
        return ((int) velY * dirY) + getPy();
    }

    public void invertX() {
        dirX *= -1;
    }

    public void invertY() {
        dirY *= -1;
    }

}