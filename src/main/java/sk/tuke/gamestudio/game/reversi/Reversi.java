package sk.tuke.gamestudio.game.reversi;

import sk.tuke.gamestudio.game.reversi.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.reversi.core.Field;

public class Reversi {
    public static void main(String[] args) {
        Field field = new Field();
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
    }
}
