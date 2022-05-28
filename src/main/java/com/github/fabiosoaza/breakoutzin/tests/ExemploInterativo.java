package com.github.fabiosoaza.breakoutzin.tests;

import com.github.fabiosoaza.breakoutzin.objects.Ball;
import com.github.fabiosoaza.breakoutzin.base.Direction;
import com.github.fabiosoaza.breakoutzin.base.Entity;
import com.github.fabiosoaza.breakoutzin.base.Text;
import com.github.fabiosoaza.breakoutzin.base.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class ExemploInterativo extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final int LARGURA = 980;
    public static final int ALTURA = 480;
    public static final Font FONTE = new Font("Consolas", Font.BOLD, 10);

    private JPanel tela;

    private int px;
    private int py;
    private boolean jogando = true;

    private int velocidadeX = 5;
    private int velocidadeY = 5;

    private Entity boxParado;
    private Ball ball;
    private Text textBoxParado;
    private Text textBoxMovel;
    private Text textColidiu;


    private Graphics2D g2d;

    private BufferedImage buffer;

    private final int FPS = 1000 / 20; // 50

    public void inicia() {
        long prxAtualizacao = 0;

        while (jogando) {
            if (System.currentTimeMillis() >= prxAtualizacao) {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, tela.getWidth(), tela.getHeight());
                atualizar();
                desenhar(g2d);
                tela.repaint();
                prxAtualizacao = System.currentTimeMillis() + FPS;
            }
        }

    }


    public ExemploInterativo() {


        KeyListener l = getKeyListener();
        super.addKeyListener(l);

        buffer = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);
        g2d = buffer.createGraphics();

        tela = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(buffer, 0, 0, null);
            }
        };

        carregar();

        getContentPane().add(tela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(LARGURA, ALTURA);
        setVisible(true);
        setResizable(false);

        tela.repaint();
    }

    private void carregar() {


        boxParado = new Entity((LARGURA / 2) / 2 - 50 , (ALTURA / 2) - 50, 70, 70);

        this.px = boxParado.getPx()+boxParado.getWidth();
        this.py = boxParado.getPy()+boxParado.getHeight();


        ball = new Ball();
        ball.setPx(this.px);
        ball.setPy(this.py);

        textBoxParado = new Text();
        textBoxMovel = new Text();
        textColidiu = new Text();

        textBoxParado.setPx(5);
        textBoxParado.setPy(40);
        textBoxParado.setColor(Color.BLACK);
        textBoxParado.setFont(FONTE);

        textBoxMovel.setPx(5);
        textBoxMovel.setPy(textBoxParado.getPy() + textBoxParado.getFont().getSize() + 5);
        textBoxMovel.setColor(Color.BLACK);
        textBoxMovel.setFont(FONTE);

        textColidiu.setPx(5);
        textColidiu.setPy(textBoxMovel.getPy() + textBoxMovel.getFont().getSize() + 5);
        textColidiu.setColor(Color.BLACK);
        textColidiu.setFont(FONTE);

        boxParado.setColor(Color.YELLOW);
        boxParado.setEnabled(true);

        ball.setColor(Color.BLUE);
        ball.setEnabled(true);



    }

    private void atualizar() {

    }


    private void desenhar(Graphics2D g) {

        if (Util.collided(ball, boxParado)) {
            boxParado.setColor(Color.ORANGE);
        } else {
            boxParado.setColor(Color.YELLOW);
        }
        Direction directionColisao = Util.getCollisionDirection(ball, boxParado);

        textBoxParado.render(g, "Parado = " + boxParado);
        textBoxMovel.render(g, "Movel = " + ball);
        textColidiu.render(g, "Velocidade X= " + velocidadeX + " Velocidade Y= " + velocidadeY + " Direcao Colisao = " + directionColisao);

        ball.render(g);
        boxParado.render(g);


    }

    private KeyListener getKeyListener() {
        KeyListener l = new KeyListener() {


            @Override
            // Evento para tecla apertada
            public void keyTyped(KeyEvent e) {
            }

            @Override
            // Evento para tecla liberada
            public void keyReleased(KeyEvent e) {
            }

            @Override
            // Evento para tecla pressionada
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();
                switch (tecla) {
                    case KeyEvent.VK_ESCAPE:
                        // Tecla ESC
                        jogando = false;
                        // Chame o método dispose() para fechar a janela.
                        dispose();
                        break;
                    case KeyEvent.VK_UP:
                        // Seta para cima
                        moverAcima();
                        break;
                    case KeyEvent.VK_DOWN:
                        // Seta para baixo
                        moverBaixo();
                        break;
                    case KeyEvent.VK_LEFT:
                        // Seta para esquerda
                        moverEsquerda();
                        break;
                    case KeyEvent.VK_RIGHT:
                        // Seta para direita
                        moverDireita();
                        break;
                }
            }
        };
        return l;
    }

    private void atualizarBox(){
        ball.setPx(px);
        ball.setPy(py);
    }
    private void moverAcima() {
        py -= velocidadeY;
        atualizarBox();
    }

    private void moverBaixo() {
        py += velocidadeY;
        atualizarBox();
    }

    private void moverEsquerda() {
        px -= velocidadeX;
        atualizarBox();
    }

    private void moverDireita() {
        px += velocidadeX;
        atualizarBox();
    }

    public static void main(String[] args) {
        ExemploInterativo jogo = new ExemploInterativo();
        jogo.inicia();
    }

}