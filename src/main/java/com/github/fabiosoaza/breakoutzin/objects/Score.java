package com.github.fabiosoaza.breakoutzin.objects;

import com.github.fabiosoaza.breakoutzin.base.Text;

import java.awt.*;


public class Score extends Text {

    public static final int FONT_SIZE = 36;
    public static final Font DEFAULT_FONT = new Font("Consolas", Font.PLAIN, FONT_SIZE);

    private short ponto;

    public Score() {
        super.setFont(DEFAULT_FONT);
        setColor(new Color(142,142,142));
    }

    public short getValue() {
        return ponto;
    }

    public void setValue(short ponto) {
        this.ponto = ponto;
    }

    public void add() {
        ponto++;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g, String.format("%03d", ponto), getPx(), getPy());
    }

}
