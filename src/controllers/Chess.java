package controllers;

import enums.File;
import enums.Rank;
import interfaces.BoardIF;
import interfaces.PieceIF;
import interfaces.UserInterfaceIF;
import interfaces.guiIF;
import model.GameStateEditor;
import model.Settings;
import model.Player;
import model.Square;

import java.util.List;

/**
 * A class for modeling the game of chess
 *
 * @author Daniel Aoulou 50% Josiah Cherbonay 45% Michael Imerman 5%
 * @version 3/26/23
 */

public class Chess{

    private boolean undoOn = true;

    /** the user interface to use */
    UserInterfaceIF ui;

    guiIF gui;

    Settings settings;

    /** Holds the current state of the board, and any moves the player have made */
    public GameStateEditor stateEditor;

    static Chess game;

    /**
     * A constructor for a default Chess game
     */
//    public Chess(UserInterfaceIF UI){
//        ui = UI;
//        ui.setGame(this);
//        stateEditor = new GameStateEditor();
//    }

    /**
     * Constructor for if the game is to be on a gui
     */
    private Chess() {
        // gui = GUI;
        // gui.setGame(this);
        stateEditor = new GameStateEditor();
        settings = Settings.getOptions();

    }

    public static Chess getInstanceOfGame() {
        if (game == null) {
            game = new Chess();
        }
        return game;
    }

    /**
     * Start a demo game to demonstrate newly developed features
     */
    public void go(){
        // if(!gui.intro()) return;
        //ui.startGame(this);
        // gui.setGame(this);

    }

    /**
     * Helper function for finding a piece on the board
     *
     * @param an the algebraic notation for the piece's possible location
     * @return a piece, or null if there is no piece at the possible location
     */
    public PieceIF getPiece(String an) {
        File file = File.getFileByIndex(an.substring(0, 1));
        Rank rank = Rank.getRankByIndex(an.substring(1));

        GameStateEditor stateEditor = this.stateEditor;
        BoardIF board = stateEditor.getBoard();

        Square square = board.getSquare(rank, file);
        if (square != null && square.isOccupied()) {
            return square.getPiece();
        } else return null;
    }

    /**
     * Helper function for finding a square on the board
     * @param an the algebraic notation for the square's possible location
     * @return a square, or null if there is no piece at the possible location
     */
    public Square getSquare(String an) {
        File file = File.getFileByIndex(an.substring(0, 1));
        Rank rank = Rank.getRankByIndex(an.substring(1));

        return stateEditor.getBoard().getSquare(rank, file);
    }


    /**
     * Returns the current player of the current state of the chess game.
     *
     * @return the current player of the chess game.
     */
    public Player getCurrentPlayer() {
        return stateEditor.getCurrentPlayer();
    }


    /**
     * method to set chess to game loaded in from file by user.
     * @param board - the new board
     * @param players - list of players

     * @param stateEditor - to keep track of game history for saving
    */
    public void setChess(BoardIF board, List<Player> players, GameStateEditor stateEditor){
        this.stateEditor = stateEditor;
        this.stateEditor.setBoard(board);
        this.stateEditor.setPlayers(players);

    }

    /**
     * Sets the Game's User Interface.
     * @param ui the User Interface being used
     */
    public void setUi(UserInterfaceIF ui) {
        this.ui = ui;
    }

    /**
     * Return's the Game's User Interface.
     * @return the user interface
     */
    public UserInterfaceIF getUi() {
        return ui;
    }

    /**
     * Returns the state editor for this Chess game.
     * @return stateEditor
     */
    public GameStateEditor getStateEditor() {
        return stateEditor;
    }

    /**
     * Get the game's board
     * @return the board
     */
    public BoardIF getBoard() {
        return this.stateEditor.getBoard();
    }

    /**
     * Check if undo is enabled or disabled
     * @return true if undo is turned on
     */
    public boolean isUndoOn() {
        return undoOn;
    }

    /**
     * Enable or disable undo
     * @param undoOn true/false
     */
    public void setUndoOn(boolean undoOn) {
        this.undoOn = undoOn;
    }

    public Settings getSettings(){
        return settings;
    }

}
