package com.github.fabiosoaza.breakoutzin.base;

public enum Command {
        UP("^"),
        DOWN("V"),
        LEFT("<"),
        RIGHT(">"),
        BUTTON_A("A"),
        BUTTON_B("B"),
        SELECT("SPACE"),
        START("ENTER"),
        RESET("ESC")
        ;

        private Command(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
}
