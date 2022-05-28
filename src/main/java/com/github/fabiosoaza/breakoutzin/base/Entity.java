package com.github.fabiosoaza.breakoutzin.base;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Entity {

    public static AffineTransform afAnterior;
    protected static final AffineTransform af = new AffineTransform();

    private float px;
    private float py;
    private int width;
    private int height;

    private int vel;
    private boolean enabled;
    private Color color;

    private ImageIcon image;

    private float oldPx;
    private float oldPy;

    public Entity() {
    }


    public Entity(Entity element) {
        this.px = element.px;
        this.py = element.py;
        this.width = element.width;
        this.height = element.height;
        this.vel = element.vel;
        this.enabled = element.enabled;
        this.color = element.color;
        this.image = element.image;
        this.oldPx = element.oldPx;
        this.oldPy = element.oldPy;
    }

    public Entity(int px, int py, int width, int height) {
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;
        this.oldPx = px;
        this.oldPy = py;
    }

    public void atualiza() {
    }

    public void render(Graphics2D g) {
        if (image == null) {
            g.setColor(color);
            g.fillRect((int) px, (int) py, width, height);
        } else {
            g.drawImage(image.getImage(), (int) px, (int) py, null);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPx() {
        return (int) px;
    }

    public void setPx(float px) {
        this.oldPx = this.px;
        this.px = px;
    }

    public int getPy() {
        return (int) py;
    }

    public void setPy(float py) {
        this.oldPy = this.py;
        this.py = py;
    }

    public int getOldPx() {
        return (int) oldPx;
    }

    public int getOldPy() {
        return (int) oldPy;
    }


    public int getLeft() {
        return getPx();
    }

    public int getRight() {
        return getLeft() + getWidth();
    }

    public int getTop() {
        return getPy();
    }

    public int getBottom() {
        return getTop() + getHeight();
    }

    public int getOldLeft() {
        return getOldPx();
    }

    public int getOldRight() {
        return getOldLeft() + getWidth();
    }

    public int getOldTop() {
        return getOldPy();
    }

    public int getOldBottom() {
        return getOldTop() + getHeight();
    }


    public float getMovPx() {
        return px;
    }

    public float getMovPy() {
        return py;
    }

    public int getVel() {
        return vel;
    }

    public void setVel(int vel) {
        this.vel = vel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public void incPx(int x) {
        setPx(px + x);
    }

    public void incPy(int y) {
        setPy(py + y);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "px=" + px +
                ", py=" + py +
                ", oldPx=" + oldPx +
                ", oldPy=" + oldPy +
                ", Top=" + getTop() +
                ", Right=" + getRight() +
                ", Bottom=" + getBottom() +
                ", Left=" + getTop() +
                ", OldTop=" + getOldTop() +
                ", OldRight=" + getOldRight() +
                ", OldBottom=" + getOldBottom() +
                ", OldLeft=" + getOldLeft() +
                ", width=" + width +
                ", height=" + height +
                ", vel=" + vel +
                ", enabled=" + enabled +
                ", color= RGBA[R:"+ color.getRed()+", G:"+ color.getGreen()+", B:"+ color.getBlue()+", A:"+ color.getAlpha()+"]" +
                '}';
    }
}
