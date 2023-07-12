package model;

import interfaces.BoardIF;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for modeling a Chess game's history. Includes the current state
 * of the board, a list of moves that have been made, and the players.
 * @author Josiah Cherbony(100%)
 * @version 4/2/23
 */
public class GameState {

    /** The player whose turn it currently is FOR THIS STATE */
    Player currPlayer = null;

    /** Represents the state of the board, as the player's had left it */
    BoardIF boardState;

    /** The list of moves made by the players */
    List<PlayerMove> moveList;

    /**
     * A constructor for our GameHistory (only requires a list of Players
     * to be constructed).
     */
    public GameState(){
        this(new Board(), new ArrayList<>());
    }

    /**
     * A constructor for our GameHistory
     * @param boardState - Represents the board's current state.
     * @param moveList - The list of moves made by the players.
     */
    public GameState(BoardIF boardState, List<PlayerMove> moveList){
        this.boardState = boardState;
        this.moveList = moveList;
    }

    /**
     * A constructor for our GameHistory
     * @param boardState - Represents the board's current state.
     * @param moveList - The list of moves made by the players.
     */
    public GameState(BoardIF boardState, List<PlayerMove> moveList, Player currPlayer){
        this.boardState = boardState;
        this.moveList = moveList;
        this.currPlayer = currPlayer;
    }

    /**
     * Adds a player, the placed they moved from, and the place they moved to
     * in our history.
     * @param player - The player who made the move.
     * @param fromPosition - The position the piece came from.
     * @param toPosition - The position the piece moved to.
    */
    public void addMoveToHistory(Player player, Position fromPosition, Position toPosition) {
        // Adds a "PlayerMove" to our list of moves
        PlayerMove move = new PlayerMove(player, fromPosition, toPosition);
        moveList = getMoveList();
        moveList.add(move);
    }

    /**
     * Gets the list of moves made from this state.
     * @return moveList
     */
    public List<PlayerMove> getMoveList() {
        return moveList;
    }

    /**
     * A method that returns a snapshot of our current game state.
     * @return A state with a snapshot of our current game state.
     */
    public State takeSnapshot(){
        return new State(this.boardState.clone(), cloneMoveList(), currPlayer);
    }

    /**
     * Restores our game state to the version saved in the
     * input state.
     * @param state - The state we want to restore our game to.
     */
    public void restore(State state){
        this.boardState = state.getBoardState();
        this.moveList = state.getMoveList();
        this.currPlayer = state.getCurrentPlayer();
    }

    /**
     * Clones and returns a copy of our list of moves.
     * @return clonedList
     */
    public List<PlayerMove> cloneMoveList(){
        List<PlayerMove> clonedList = new ArrayList<>();
        for (PlayerMove playerMove : moveList) {
            clonedList.add(playerMove.clone());
        }
        return clonedList;
    }

    /**
     * Gets the current player.
     * @return currentPlayer
    */
    public Player getCurrPlayer(){
        return currPlayer;
    }


    /**
     * Sets the current player.
     * @param currPlayer - The player whose turn it is.
     */
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * Clones and returns a copy of the gameState.
     * @return A copy of the gameState.
     */
    public GameState clone(){
        return new GameState(this.boardState.clone(), this.moveList, this.currPlayer);
    }

    /**
     * A class for modeling a GameState's state. That way the memento pattern can
     * be used to shift through a list of game states.
     * @author Josiah Cherbony(100%)
     * @version 5/15/23
     */
    public static class State {

        /** Represents the state of the board, as the player's had left it */
        final private BoardIF boardState;

        /** The list of moves made by the players */
        final private List<PlayerMove> moveList;

        /** The player whose turn it is this state */
        final private Player currPlayer;

        /**
         * A constructor for a state of a game.
         * @param boardState - Represents the state of the board, as the player's had left it
         * @param moveList - The list of moves made by the players.
         */
        private State(BoardIF boardState, List<PlayerMove> moveList, Player currPlayer){
            this.boardState = boardState;
            this.moveList = moveList;
            this.currPlayer = currPlayer;
        }

        /**
         * Gets the board state from this state.
         * @return boardState
         */
        public BoardIF getBoardState() {
            return boardState;
        }

        /**
         * Gets the list of moves made from this state.
         * @return moveList
         */
        public List<PlayerMove> getMoveList() {
            return moveList;
        }

        /**
         * Gets the list of moves made from this state.
         * @return moveList
         */
        public Player getCurrentPlayer() {
            return currPlayer;
        }

    }
}
