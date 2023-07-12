package interfaces;

import controllers.Chess;
import menus.Screen_Set;
import model.Board;
import model.Piece;

/**
 * An interface for different types of user interface implementations
 */
public interface UserInterfaceIF {

    /**
     * A set of screens for the user interface to interact with the user
     */
    public Screen_Set screens = new Screen_Set();

    /**
     *
     * @param board the board to be drawn
     */
    void draw(BoardIF board);

    /**
     * Allows the user to preview moves of a piece
     * @param board the board to be drawn
     * @param p the piece to be previewed
     */
    void previewMoves(BoardIF board, PieceIF p);

}
