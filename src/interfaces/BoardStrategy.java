package interfaces;

import model.Board;

/**
 * An interface to connect a chess board and a
 * user interface for different color schemes
 * @author Logan Beaver(100%)
 */
public interface BoardStrategy{

    /**
     * draw a board to the console
     * @param board - the chess board
     */
    public void draw(Board board);


}