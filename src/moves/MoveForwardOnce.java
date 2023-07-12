package moves;

/**
 * A class to model a one square movement forward for both black and white chess pieces.
 * @author Josiah(40%), Michael(20%), Daniel(20%), Logan(20%)
 */

import interfaces.MoveIF;
import interfaces.PieceIF;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class MoveForwardOnce implements MoveIF{



    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     *
     * @param piece - The piece we're getting valid positions for.
     * @param board - The board we're analyzing for valid positions.
     */
    public ArrayList<Position> getValidPositions(PieceIF piece, Board board) {
        Position position = piece.getPosition();
        ArrayList<Position> validPositions = new ArrayList<Position>();
        int rank = position.getRank().getRow(); // Getting the position in programming terms
        int file = position.getFile().getColumn();
        int up = rank + 1;
        int down = rank - 1;

        if(piece.isWhite()) {
            checkAndAddToList(up, file, board, piece, rank, validPositions);
            checkCapture(up, file, board, piece, validPositions);
        } else {
            checkAndAddToList(down, file, board, piece, rank, validPositions);
            checkCapture(down, file, board, piece, validPositions);
        }


        return validPositions;
    }


    /**
     * Checks if a capture scenario is possible for a pawn and adds to the list.
     *
     * @param vertical - The movement up or down.
     * @param file - Integer representation for the file being accessed.
     * @param board - The board of the game of chess.
     * @param piece - A piece for the game of chess.
     * @param positions - List of valid positions to be updated and returned.
     */
    private void checkCapture(int vertical, int file, Board board, PieceIF piece, List<Position> positions) {
        Square left = board.getSquare(vertical, file-1);
        Square right = board.getSquare(vertical, file+1);

        if(left != null && left.isOccupied() &&
                (left.getPiece().getColor() != piece.getColor())) {
            positions.add(left.getPosition());
        }
        if(right != null && right.isOccupied() &&
                (right.getPiece().getColor() != piece.getColor())) {
            positions.add(right.getPosition());
        }
    }


    /**
     * Checks if the moves are in bounds and adds them to a list of valid
     * positions.
     *
     * @param upOrDown - Integer representing an up or down movement.
     * @param file - Integer representing the file that is being moved to.
     * @param board - The board for the game of chess.
     * @param piece - A piece for the game of chess.
     * @param rank - Integer representing the rank that is being moved to.
     * @param positions - A list of valid positions that is to be updated and returned later.
     */
    private void checkAndAddToList(int upOrDown, int file, Board board, PieceIF piece, int rank, List<Position> positions) {
        boolean rowCheck = 0 <= upOrDown && upOrDown <= 7;
        Square square;
        if (rowCheck) {
            if (rank == 1 && piece.isWhite()) {
                square = board.getSquare(upOrDown, file);
                if(!square.isOccupied()) {
                    square = board.getSquare(upOrDown + 1, file);
                    if(!square.isOccupied()) positions.add(square.getPosition());
                }
            }
            else if (rank == 6 && piece.isBlack()) {
                square = board.getSquare(upOrDown, file);
                if(!square.isOccupied()) {
                    square = board.getSquare(upOrDown - 1, file);
                    if(!square.isOccupied()) positions.add(square.getPosition());
                }
            }
            square = board.getSquare(upOrDown, file);
            if(!square.isOccupied()) positions.add(square.getPosition());
        }
    }
}
