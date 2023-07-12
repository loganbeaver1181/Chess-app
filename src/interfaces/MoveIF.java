package interfaces;

import model.Board;
import model.Position;

import java.util.List;

/**
 * An interface for defining chess piece movement
 * @author Josiah Cherbony(100%)
 */

public interface MoveIF{

    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     * @param piece - the piece we are trying to move
     * @param board - the board the piece is trying to move in
     */
    public List<Position> getValidPositions(PieceIF piece, Board board);


}