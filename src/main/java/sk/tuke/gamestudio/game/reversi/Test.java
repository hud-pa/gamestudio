package sk.tuke.gamestudio.game.reversi;

import sk.tuke.gamestudio.game.reversi.core.Field;
import sk.tuke.gamestudio.game.reversi.core.Tile;

public class Test {
    public static void main(String[] args) {
        Field field = new Field();
        final Tile[][] tiles;

        field.changePlayer();
        field.markFields();
        field.putDisk(2,3);
        field.changePlayer();


        System.out.println("black "+ field.getBlackDisksCount());
        System.out.println("white "+field.getWhiteDisksCount());

        System.out.println(field.getTile(6, 5).getState());


       /*field.changePlayer();System.out.println(field.getPlayerState());
       field.markFields();
       field.putniDiscek(1,2);
       field.unMarkFields();
       field.turnDisks(1,2);*/

        //field.putDisk(1,2);
        //field.putDisk(7,7);




        /*
        field.putniDiscek(1,2);
        field.putniDiscek(1,3);
        field.putniDiscek(1,4);
        field.putniDiscek(1,5);
        field.putniDiscek(1,6);

        field.putniDiscek(2,6);
        field.putniDiscek(3,6);
        field.putniDiscek(4,6);
        field.putniDiscek(5,6);

        field.putniDiscek(5,5);
        field.putniDiscek(5,4);
        field.putniDiscek(5,3);
        field.putniDiscek(5,2);

        field.putniDiscek(4,2);
        field.putniDiscek(3,2);
        field.putniDiscek(2,2);

        field.turnDisks(3,4);
        field.turnDisks(3,4);/*/



        System.out.println(field.getPlayerState());
        for (int row = 0; row < field.getRowCount(); row++) {
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile tile = field.getTile(row, column);
                System.out.print(" ");
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
            System.out.println();
        }
    }

}

