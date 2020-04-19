package sk.tuke.gamestudio.game.reversi.consoleui;

import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.GameState;
import sk.tuke.gamestudio.game.reversi.core.Tile;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private static final Pattern INPUT_PATTERN = Pattern.compile("([P])([A-H])([1-8])");
    private Field field;

    public ConsoleUI(Field field) {
        this.field = field;
    }


    public void play() {
        do {
            show();
            handleInput();
        } while(field.getState() == GameState.PLAYING);
        show();
        if (field.getState() == GameState.WIN_BLACK ) {
            System.out.println("BLACK WON!!!");
        } else
            System.out.println("WHITE WON!!!");
    }



    private void show() {
        field.changePlayer();
        field.markFields();
        printFieldHeader();
        printFieldBody();
        printActualScore();
    }

    private void printActualScore(){
        System.out.print("Score: ");
        System.out.print("black player: "+ field.getBlackDisksCount());
        System.out.println("  white player: "+field.getWhiteDisksCount());
    }
    private void printFieldHeader() {
        System.out.println(field.getPlayerState());
        System.out.print(' ');
        System.out.print(" ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(" " + (column + 1));
        }
        System.out.println();
    }

    private void printFieldBody() {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char)('A' + row));
            System.out.print(" ");
            for (int column = 0; column < field.getColumnCount(); column++) {
                System.out.print(" ");
                printTile(row,column);
            }
            System.out.println();
        }
    }

    private void printTile(int row, int column) {
        final Tile tile = field.getTile(row, column);
        switch (tile.getState()) {
            case EMPTY:
                System.out.print("_");
                break;
            case MARKED:
                System.out.print("*");
                break;
            case WHITE_DISK:
                System.out.print("W");
                break;
            case BLACK_DISK:
                System.out.print("B");
                break;

            default:
                throw new IllegalArgumentException("Unsupported tile state " + tile.getState());
        }
    }

    public void handleInput(){
        while (true){
            System.out.println("Enter input (eg. PA3, PB4, X(exit) ... positions where you want put disk");
            String input = new Scanner(System.in).nextLine().trim().toUpperCase();

            if("X".equals(input))
                System.exit(0);

            if("R".equals(input))
                play();

            Matcher matcher = INPUT_PATTERN.matcher(input);
            if (matcher.matches()) {
                try {
                    int row = matcher.group(2).charAt(0) - 'A';
                    int column = Integer.parseInt(matcher.group(3)) - 1;
                    if (row >= 0 && row < field.getRowCount() && column >= 0 && column < field.getColumnCount()) {
                        if ("P".equals(matcher.group(1))) {
                            field.putDisk(row,column);
                            return;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Bad input");
                }
            }
        }
    }

}
