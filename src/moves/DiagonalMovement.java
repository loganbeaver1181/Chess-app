package moves;

import interfaces.MoveIF;
import interfaces.PieceIF;
import model.Board;
import model.Position;
import model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent diagonal movement in chess.
 * @author Daniel(50%), Laurin(50%)
 * @version 03.27.23
 */
public class DiagonalMovement implements MoveIF {

    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     *
     * @param piece - The chess piece that the player wants to move
     * @param board - The chess board 
     * @return validPosition - A list of valid positions that the piece can move to
     */
    @Override
    public List<Position> getValidPositions(PieceIF piece, Board board) {
        Position position = piece.getPosition();
        List<Position> validPositions = new ArrayList<Position>();
        int rank = position.getRank().getRow();
        int file = position.getFile().getColumn();

        Square square;

        // Each of these for loops checks one diagonal direction
        // and breaks the loop if the line goes out of bounds or runs
        // into a piece.
        // Checks all positions to the Right Upward Diagonal position of the piece.
        for (int radius = 1; radius < 8; radius++) {
            // If the radius is outside our bounds, this move isn't allowed.
            if ((rank + radius > 7 || rank + radius < 0) || (file + radius > 7 || file + radius < 0)) {
                break;
            }
            square = board.getSquare(rank + radius, file + radius);

            if (square == null) break;

            if (square.isOccupied()) {
                // Check the piece to ensure we're capturing an opponent's piece
                if (square.getPiece().getColor() != piece.getColor()) {
                    validPositions.add(square.getPosition());
                }
                break;
            }
            validPositions.add(square.getPosition());

        }
        // Checks all positions to the Left Downward Diagonal position of the piece.
        for (int radius = 1; radius < 8; radius++) {
            if ((rank - radius > 7 || rank - radius < 0) || (file - radius > 7 || file - radius < 0)) {
                break;
            }
            square = board.getSquare(rank - radius, file - radius);
            if (square == null) break;
            if (square.isOccupied()) {
                // Check the piece to ensure we're capturing an opponent's piece
                if (square.getPiece().getColor() != piece.getColor()) {
                    validPositions.add(square.getPosition());
                }
                break;
            }
            validPositions.add(square.getPosition());

        }
        // Checks all positions to the Left Upwards Diagonal position of the piece.
        for (int radius = 1; radius < 8; radius++) {
            if ((rank - radius > 7 || rank - radius < 0) || (file + radius > 7 || file + radius < 0)) {
                break;
            }
            square = board.getSquare(rank - radius, file + radius);
            if (square == null) break;
            if (square.isOccupied()) {
                // Check the piece to ensure we're capturing an opponent's piece
                if (square.getPiece().getColor() != piece.getColor()) {
                    validPositions.add(square.getPosition());
                }
                break;
            }
            validPositions.add(square.getPosition());

        }
        // Checks all positions to the Right Downward Diagonal position of the piece.
        for (int radius = 1; radius < 8; radius++) {
            if ((rank + radius > 7 || rank + radius < 0) || (file - radius > 7 || file - radius < 0)) {
                break;
            }
            square = board.getSquare(rank + radius, file - radius);
            if (square == null) break;
            if (square.isOccupied()) {
                // Check the piece to ensure we're capturing an opponent's piece
                if (square.getPiece().getColor() != piece.getColor()) {
                    validPositions.add(square.getPosition());
                }
                break;
            }
            validPositions.add(square.getPosition());

        }
        return validPositions;
    }
}
