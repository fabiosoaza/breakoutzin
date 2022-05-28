package com.github.fabiosoaza.breakoutzin.objects;

import com.github.fabiosoaza.breakoutzin.base.Entity;

import java.awt.*;

public class Paddle extends Entity {

    public static final int ALTURA = 10;
    public static final int LARGURA = 60;
    public static final Color COR = new Color(213, 85, 70);

    public Paddle(){
        setHeight(ALTURA);
        setWidth(LARGURA);
        setColor(COR);
    }
}
