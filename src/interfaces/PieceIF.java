package interfaces;

import enums.ChessPieceType;
import enums.GameColor;
import model.Position;

import java.util.List;

/** An interface for a piece in chess, for the user interface,
 * and for the BoardIF to retrieve pieces.
 * @author Logan Beaver(100%)
 */
public interface PieceIF{

    /**
     * get a chess piece's type
     * @return the type of chess piece
     */
    public ChessPieceType getChessPieceType();

    /**
     * Set a piece's chess type
     * @param t the chess piece
     */
    public void setChessPieceType(ChessPieceType t);

    /**
     * Get the position of a piece
     * @return a position
     */
    Position getPosition();

    /**
     * Get the color of a piece
     * @return a color
     */
    GameColor getColor();

    /**
     * increment the number of moves a piee has made
     */
    void incrementMoves();

    /**
     * get the moves a piece could make
     * @return a list of moves
     */
    List<MoveIF> getMoves();

    /**
     * Set a piece's position
     * @param position the position to be set
     */
    void setPosition(Position position);

    /**
     * Check if a piece is white
     * @return true/false
     */
    boolean isWhite();

    /**
     * Check if a piece is black
     * @return true/false
     */
    boolean isBlack();

    /**
     * Copy a piece
     * @return a copy of a pieceIF object
     */
    public PieceIF clone();
}