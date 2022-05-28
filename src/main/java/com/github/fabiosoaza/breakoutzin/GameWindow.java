package com.github.fabiosoaza.breakoutzin;

import com.github.fabiosoaza.breakoutzin.base.ControlCommandsRegister;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GameWindow extends JFrame {

    private JPanel panel;

    private Graphics2D g2d;

    private BufferedImage buffer;

    public static final int WINDOW_HEIGHT = 480;

    public static final int WINDOW_WIDTH = 640;

    private ControlCommandsRegister register;

    public GameWindow(ControlCommandsRegister register){
        this.register = register;


        this.addKeyListener(createKeyListener());

        buffer = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

        g2d = buffer.createGraphics();

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(buffer, 0, 0, null);
            }
        };

        getContentPane().add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        setVisible(true);
        panel.repaint();

    }

    private KeyListener createKeyListener() {
        return new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                register.register(e.getKeyCode(), false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                register.register(e.getKeyCode(), true);
            }
        };
    }

    public JPanel getPanel() {
        return panel;
    }

    public Graphics2D getG2d() {
        return g2d;
    }

    public void fillBlack(){
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, GameWindow.WINDOW_WIDTH, GameWindow.WINDOW_HEIGHT);
    }

    public void repaintPanel(){
        panel.repaint();
    }

}
