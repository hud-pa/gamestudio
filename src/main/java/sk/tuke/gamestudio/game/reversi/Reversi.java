package sk.tuke.gamestudio.game.reversi;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.game.reversi.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.reversi.core.Field;

import java.time.LocalDate;
import java.util.Date;

public class Reversi {
    public static void main(String[] args) {
        Field field = new Field();
        ConsoleUI ui = new ConsoleUI(field);
        ui.play();
        Date date = new Date();
        Comment comment = new Comment("fesdro", "gamesdska","idzem ci", date);
    }
}
