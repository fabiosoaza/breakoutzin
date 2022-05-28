package com.github.fabiosoaza.breakoutzin.base;

import java.awt.event.KeyEvent;

public class ControlCommandsRegister {

    private static boolean[] commands = new boolean[Command.values().length];

    public void clearBuffer() {
        for (int i = 0; i < commands.length; i++) {
            commands[i] = false;
        }
    }

    public boolean pressed(Command command){
        return commands[command.ordinal()];
    }

    public void register(int keyCode, boolean pressed) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                commands[Command.UP.ordinal()] = pressed;
                break;
            case KeyEvent.VK_DOWN:
                commands[Command.DOWN.ordinal()] = pressed;
                break;
            case KeyEvent.VK_LEFT:
                commands[Command.LEFT.ordinal()] = pressed;
                break;
            case KeyEvent.VK_RIGHT:
                commands[Command.RIGHT.ordinal()] = pressed;
                break;

            case KeyEvent.VK_ESCAPE:
                commands[Command.BUTTON_B.ordinal()] = pressed;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                commands[Command.BUTTON_A.ordinal()] = pressed;
        }
    }


}
