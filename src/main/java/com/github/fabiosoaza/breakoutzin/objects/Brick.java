package com.github.fabiosoaza.breakoutzin.objects;

import com.github.fabiosoaza.breakoutzin.base.Entity;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Brick extends Entity {


    public enum BlockType {
        INVISIVEL(0, 0, false),
        RED(1, 7, true),
        ORANGE(2, 7, true),
        DARK_YELLOW(3, 4, true),
        YELLOW_BRASILIS(4, 4, true),
        GREEN(5, 1, true),
        BLUE(6, 1, true),
        INQUEBRAVEL(7, 0, false);

        BlockType(int codigo, int pontuacao, boolean quebravel) {
            this.codigo = codigo;
            this.pontuacao = pontuacao;
            this.quebravel = quebravel;
        }

        private int codigo;
        private int pontuacao;
        private boolean quebravel;


        public int getCodigo() {
            return codigo;
        }

        public int getPontuacao() {
            return pontuacao;
        }

        public boolean isDestructible() {
            return quebravel;
        }

        public static BlockType fromCode(int codigo) {
            return Arrays.stream(BlockType.values()).filter(c -> c.getCodigo() == codigo).findFirst().orElse(null);
        }


    }

    public static final short BRICK_SPACING = 5;
    public static final short DEFAULT_HEIGHT = 10;
    public static final short DEFAULT_WIDTH = 35;


    private BlockType tipo;

    public Brick(BlockType tipo) {
        setTipo(tipo);
        setHeight(DEFAULT_HEIGHT);
        setWidth(DEFAULT_WIDTH);
        setColor(getCorPorTipo(tipo));
        setEnabled(tipo != BlockType.INVISIVEL);
    }

    public Color getCorPorTipo(BlockType tipo) {
        Map<BlockType, Color> cores = new HashMap<>();
        cores.put(BlockType.INVISIVEL, new Color(0, 0, 0, 100));
        cores.put(BlockType.RED, new Color(213, 85, 70));
        cores.put(BlockType.ORANGE, new Color(208, 114, 55));
        cores.put(BlockType.DARK_YELLOW, new Color(187, 122, 45));
        cores.put(BlockType.YELLOW_BRASILIS, new Color(167, 154, 39));
        cores.put(BlockType.GREEN, new Color(70, 145, 70));
        cores.put(BlockType.BLUE, new Color(73, 96, 208));
        cores.put(BlockType.INQUEBRAVEL, new Color(142, 142, 142));
        return cores.get(tipo);
    }

    public short calcScorePoint() {
        return (short)this.tipo.getPontuacao();
    }

    public BlockType getType() {
        return tipo;
    }

    public void setTipo(BlockType tipo) {
        this.tipo = tipo;
    }

    @Override
    public void render(Graphics2D g) {
        if (isEnabled()) {
            super.render(g);
        }
    }
}
