package com.github.fabiosoaza.breakoutzin.base;

import java.awt.Font;
import java.awt.Graphics2D;

public class Text extends Entity {

    public static final int TAMANHO_FONTE_DEFAULT = 16;
    public static final int FONT_STYLE_DEFAULT = Font.PLAIN;
    public static final String FONT_NAME_DEFAULT = "Tahoma";
    private Font font;
    private String text;

    public Text() {
        this(new Font(FONT_NAME_DEFAULT, FONT_STYLE_DEFAULT, TAMANHO_FONTE_DEFAULT));
    }

    public Text(String text) {
        this(new Font(FONT_NAME_DEFAULT, FONT_STYLE_DEFAULT, TAMANHO_FONTE_DEFAULT), text);
    }

    public Text(Font font) {
        this(font, null);
    }

    public Text(Font font, String text) {
        this.font = font;
        this.text = text;
    }

    @Override
    public void render(Graphics2D g) {
        this.render(g, getText());
    }

    public void render(Graphics2D g, String text) {
        this.render(g, text, getPx(), getPy());
    }

    public void render(Graphics2D g, String text, int px, int py) {
        if (getColor() != null)
            g.setColor(getColor());

        this.text = text;
        g.setFont(font);
        g.drawString(text== null ? "" : text, px, py);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}