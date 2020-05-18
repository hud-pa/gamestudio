package sk.tuke.gamestudio.game.reversi.core;

public class Field {
    private final int rowCount;
    private final int columnCount;
    private int blackDisksCount;
    private int whiteDisksCount;
    private GameState state = GameState.PLAYING;
    private PlayerState playerState = PlayerState.PLAYING_WHITE;
    private final Tile[][] tiles;

    private TitleState myDisk;
    private TitleState opponentDisk;
    private int myScore;
    private int opponentScore;
    public static final String RED = "\033[0;31m";     // RED
    public static final String RESET = "\033[0m";

    public Field() {
        rowCount = 8;
        columnCount = 8;
        this.tiles = new Tile[rowCount][columnCount];
        whiteDisksCount = 2;
        blackDisksCount = 2;
        createField();

        myDisk = TitleState.WHITE_DISK;
        opponentDisk = TitleState.BLACK_DISK;
        myScore = whiteDisksCount;
        opponentScore = blackDisksCount;

    }

    public void createField() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (row == 4 - 1 && column == 4 - 1) tiles[row][column] = new Tile(TitleState.WHITE_DISK);
                else if (row == 5 - 1 && column == 5 - 1) tiles[row][column] = new Tile(TitleState.WHITE_DISK);
                else if (row == 4 - 1 && column == 5 - 1) tiles[row][column] = new Tile(TitleState.BLACK_DISK);
                else if (row == 5 - 1 && column == 4 - 1) tiles[row][column] = new Tile(TitleState.BLACK_DISK);
                else tiles[row][column] = new Tile(TitleState.EMPTY);
            }
        }
    }

    public void putDisk(int row, int column){
        if(state == GameState.PLAYING){
            final Tile tile = tiles[row][column];
            if (tile.getState() == TitleState.MARKED){

                if(playerState == PlayerState.PLAYING_BLACK){
                    tiles[row][column] = new Tile(TitleState.BLACK_DISK);
                    blackDisksCount++;
                    myScore = blackDisksCount;
                } else {
                    tiles[row][column] = new Tile(TitleState.WHITE_DISK);
                    whiteDisksCount++;
                    myScore = whiteDisksCount;
                }
            } else {
                System.out.println( RED+ "wrong field selected, try another one" + RESET);
                changePlayer();
            }
            unMarkFields();
            turnDisks(row,column);
        }
        if(isFinished()) whoWon();
    }

    public void turnDisks(int row, int column){
        Tile actualPutDisk = tiles[row][column];

        int helpRow = row -1;
        int helpColumn = column -1;
        // left up
        if(helpRow > -1 && helpColumn > -1 && tiles[helpRow][helpColumn].getState() == opponentDisk ){ //mozno ovreni null
            while(helpRow-1 > -1 && helpColumn-1 > -1 )  {

                if(tiles[helpRow-1][helpColumn-1].getState() == myDisk ) {
                    //otočim predosle............code here
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpRow++;
                        helpColumn++;
                    }
                    break;
                }
                else if (tiles[helpRow-1][helpColumn-1].getState() == TitleState.EMPTY  ) break;
                else {
                    helpRow--;
                    helpColumn--;
                }
            }
        }

        helpRow = row-1;
        helpColumn = column;
        //up
        if(helpRow > -1 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow-1 > -1 )  {
                if(tiles[helpRow-1][helpColumn].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpRow++;
                    }
                    break;
                }
                else if (tiles[helpRow-1][helpColumn].getState() == TitleState.EMPTY) break;
                else {
                    helpRow--;
                }
            }
        }


        helpRow = row-1;
        helpColumn = column+1;
        //right up
        if(helpRow > -1 && helpColumn < 8 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow-1 > -1 && helpColumn+1 < 8 )  {
                if(tiles[helpRow-1][helpColumn+1].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpRow++;
                        helpColumn--;
                    }
                    break;
                }
                else if (tiles[helpRow-1][helpColumn+1].getState() == TitleState.EMPTY ) break;
                else {
                    helpRow--;
                    helpColumn++;
                }
            }
        }


        helpRow = row;
        helpColumn = column-1;
        //left
        if( helpColumn > -1 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpColumn-1 > -1 )  {
                if(tiles[helpRow][helpColumn-1].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk( helpRow,helpColumn);
                        helpColumn++;
                    }
                    break;
                }
                else if (tiles[helpRow][helpColumn-1].getState() == TitleState.EMPTY ) break;
                else {
                    helpColumn--;
                }
            }
        }


        helpRow = row;
        helpColumn = column+1;
        //right
        if(helpColumn < 8 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while( helpColumn+1 < 8 )  {
                if(tiles[helpRow][helpColumn+1].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpColumn--;
                    }
                    break;
                }
                else if (tiles[helpRow][helpColumn+1].getState() == TitleState.EMPTY ) break;
                else {
                    helpColumn++;
                }
            }
        }


        helpRow = row+1;
        helpColumn = column-1;
        //left down
        if(helpRow < 8 && helpColumn > -1 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow+1 < 8 && helpColumn-1 > -1 )  {
                if(tiles[helpRow+1][helpColumn-1].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpRow--;
                        helpColumn++;
                    }
                    break;
                }
                else if (tiles[helpRow+1][helpColumn-1].getState() == TitleState.EMPTY ) break;
                else {
                    helpRow++;
                    helpColumn--;
                }
            }
        }


        helpRow = row+1;
        helpColumn = column;
        //down
        if(helpRow < 8 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow+1 < 8  )  {
                if(tiles[helpRow+1][helpColumn].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpRow--;
                    }
                    break;
                }
                else if (tiles[helpRow+1][helpColumn].getState() == TitleState.EMPTY ) break;
                else {
                    helpRow++;

                }
            }
        }


        helpRow = row+1;
        helpColumn = column+1;
        //right down
        if(helpRow <8 && helpColumn <8 && tiles[helpRow][helpColumn] != null && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow+1 < 8 && helpColumn+1 < 8 )  {
                if(tiles[helpRow+1][helpColumn+1].getState() == myDisk ) {
                    while(tiles[helpRow][helpColumn] != actualPutDisk){
                        turnDisk(helpRow,helpColumn);
                        helpRow--;
                        helpColumn--;
                    }
                    break;
                }
                else if (tiles[helpRow+1][helpColumn+1].getState() == TitleState.EMPTY ) break;
                else {
                    helpRow++;
                    helpColumn++;
                }
            }
        }
    }

    public void turnDisk(int row, int column){

        if(tiles[row][column].getState() == opponentDisk){
            tiles[row][column].setState(myDisk);
            myScore++;
            opponentScore--;
            if (playerState == PlayerState.PLAYING_BLACK){
                blackDisksCount = myScore;
                whiteDisksCount = opponentScore;
            } else {
                whiteDisksCount = myScore;
                blackDisksCount = opponentScore;
            }
        }
    }

    public void unMarkFields(){
        for (int row = 0; row < getRowCount(); row++) {                         //... prechadzam riadky
            for (int column = 0; column < getColumnCount(); column++) {         //... hladam čiernu...(bielu)
                if(tiles[row][column].getState() == TitleState.MARKED){ tiles[row][column].setState(TitleState.EMPTY);}
            }
        }
    }

    public void markFields() {
        for (int row = 0; row < getRowCount(); row++) {                         //... prechadzam riadky
            for (int column = 0; column < getColumnCount(); column++) {         //... hladam čiernu...(bielu)
                if(tiles[row][column].getState() == myDisk){                    //...čekujem susedov
                    checkNeighbors(row,column);
                }
            }
        }
    }

    public void checkNeighbors(int row, int column){

        int helpRow = row -1;
        int helpColumn = column -1;
        // left up
        if(helpRow > -1 && helpColumn > -1 && tiles[helpRow][helpColumn].getState() == opponentDisk ){ //mozno ovreni null
            //if(tiles[helpRow-1][helpColumn-1] != null){
            while(helpRow-1 > -1 && helpColumn-1 > -1 )  {
                if(tiles[helpRow-1][helpColumn-1].getState() == TitleState.EMPTY ) {
                    tiles[helpRow-1][helpColumn-1].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow-1][helpColumn-1].getState() == myDisk ) break;
                else {
                    helpRow--;
                    helpColumn--;
                }
            }
        }

        helpRow = row-1;
        helpColumn = column;
        //up
        if(helpRow > -1 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow-1 > -1 )  {
                if(tiles[helpRow-1][helpColumn].getState() == TitleState.EMPTY ) {
                    tiles[helpRow-1][helpColumn].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow-1][helpColumn].getState() == myDisk ) break;
                else {
                    helpRow--;
                }
            }
        }


        helpRow = row-1;
        helpColumn = column+1;
        //right up
        if(helpRow > -1 && helpColumn < 8 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow-1 > -1 && helpColumn+1 < 8 )  {
                if(tiles[helpRow-1][helpColumn+1].getState() == TitleState.EMPTY ) {
                    tiles[helpRow-1][helpColumn+1].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow-1][helpColumn+1].getState() == myDisk ) break;
                else {
                    helpRow--;
                    helpColumn++;
                }
            }
        }


        helpRow = row;
        helpColumn = column-1;
        //left
        if(helpColumn > -1 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpColumn-1 > -1 )  {
                if(tiles[helpRow][helpColumn-1].getState() == TitleState.EMPTY ) {
                    tiles[helpRow][helpColumn-1].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow][helpColumn-1].getState() == myDisk ) break;
                else {
                    helpColumn--;
                }
            }
        }


        helpRow = row;
        helpColumn = column+1;
        //right
        if(helpColumn < 8 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while( helpColumn+1 < 8 )  {
                if(tiles[helpRow][helpColumn+1].getState() == TitleState.EMPTY ) {
                    tiles[helpRow][helpColumn+1].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow][helpColumn+1].getState() == myDisk ) break;
                else {
                    helpColumn++;
                }
            }
        }


        helpRow = row+1;
        helpColumn = column-1;
        //left down
        if(helpRow < 8 && helpColumn > -1 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow+1 < 8 && helpColumn-1 > -1 )  {
                if(tiles[helpRow+1][helpColumn-1].getState() == TitleState.EMPTY ) {
                    tiles[helpRow+1][helpColumn-1].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow+1][helpColumn-1].getState() == myDisk ) break;
                else {
                    helpRow++;
                    helpColumn--;
                }
            }
        }


        helpRow = row+1;
        helpColumn = column;
        //down
        if(helpRow < 8 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow+1 < 8  )  {
                if(tiles[helpRow+1][helpColumn].getState() == TitleState.EMPTY ) {
                    tiles[helpRow+1][helpColumn].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow+1][helpColumn].getState() == myDisk ) break;
                else {
                    helpRow++;

                }
            }
        }


        helpRow = row+1;
        helpColumn = column+1;
        //right down
        if(helpRow <8 && helpColumn <8 && tiles[helpRow][helpColumn].getState() == opponentDisk ){
            while(helpRow+1 < 8 && helpColumn+1 < 8 )  {
                if(tiles[helpRow+1][helpColumn+1].getState() == TitleState.EMPTY ) {
                    tiles[helpRow+1][helpColumn+1].setState(TitleState.MARKED);
                    break;
                }
                else if (tiles[helpRow+1][helpColumn+1].getState() == myDisk || tiles[helpRow+1][helpColumn].getState()==TitleState.MARKED) break;
                else {
                    helpRow++;
                    helpColumn++;
                }
            }
        }
    }


    public void changePlayer(){
        if(playerState == PlayerState.PLAYING_BLACK) {
            playerState = PlayerState.PLAYING_WHITE;
            myDisk = TitleState.WHITE_DISK;
            opponentDisk = TitleState.BLACK_DISK;
            myScore = whiteDisksCount;
            opponentScore = blackDisksCount;
        }
        else {
            playerState = PlayerState.PLAYING_BLACK;
            myDisk = TitleState.BLACK_DISK;
            opponentDisk = TitleState.WHITE_DISK;
            myScore = blackDisksCount;
            opponentScore = whiteDisksCount;
        }
    }

    public void whoWon(){
        if (whiteDisksCount > blackDisksCount) {state = GameState.WIN_WHITE;}
        else state = GameState.WIN_BLACK;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getBlackDisksCount() {
        return blackDisksCount;
    }

    public int getWhiteDisksCount() {
        return whiteDisksCount;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public boolean isFinished(){
        return blackDisksCount + whiteDisksCount == 64;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    /*public void putniDiscek(int row, int column){
        if(getPlayerState() == PlayerState.PLAYING_WHITE) {
            tiles[row][column] = new Tile(TitleState.WHITE_DISK);
            whiteDisksCount++;
        }
        else{
            tiles[row][column] = new Tile(TitleState.BLACK_DISK);
            blackDisksCount++;
        }
    }*/

    public void setPlayerState(PlayerState state){
        playerState = state;
    }

}
