package sk.tuke.gamestudio.game.reversi.core;

public  class Tile {
    private TitleState state;

    public Tile(TitleState state) {
        this.state = state;
    }

    public TitleState getState() {
        return state;
    }

    public void setState(TitleState state) {
        this.state = state;
    }
}
