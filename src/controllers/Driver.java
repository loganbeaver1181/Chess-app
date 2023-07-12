package controllers;

/**
 * A driver class file
 * Daniel Aoulou 100%
 */
public class Driver {
    /**
     * The game to control
     */
    private Chess game;

    /**
     * driver constructor
     * @param game - the chess game
     */
    public Driver(Chess game) {
        this.game = game;
    }

    /**
     * run the game
     */
    public void runGame() {
        if(this.game == null) return;
        game.go();
    }
}
