package interfaces;
import model.Piece;

/** An interface to define each square on a board.
 * Used by BoardIF and PieceIF
 * @author Logan Beaver(100%)
 * */
public interface SquareIF{

    /**
     * Clear a square's occupying piece
     */
    public void clear();

    /**
     * Set a square's occupying piece
     * @param p the piece to set
     */
    public void setPiece(PieceIF p);

    /**
     * Get a square's occupying piece
     * @return the piece
     */
    public PieceIF getPiece();
}