package moves;

import interfaces.MoveIF;
import interfaces.PieceIF;
import model.Board;
import model.Position;
import model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent Cardinal movement in chess.
 * @author Daniel (40%), Michael (60%)
 * @version 03.27.23
 */
public class CardinalMovement implements MoveIF{

    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     *
     * @param piece - The piece we're getting valid positions for.
     * @param board - The board we're analyzing for valid positions.
     */
    public ArrayList<Position> getValidPositions(PieceIF piece, Board board) {
        Position position = piece.getPosition();
        ArrayList<Position> validPositions = new ArrayList<>();
        int rank = position.getRank().getRow(); // Getting the position in programming terms
        int file = position.getFile().getColumn();
        int up = rank + 1;
        int down = rank - 1;
        int right = file + 1;
        int left = file - 1;

        // Checks for up movement and adds to the list
        verticalCheckAndAdd(up, file, board, validPositions);

        // Checks for the down movement and adds to the list
        verticalCheckAndAdd(down, file, board, validPositions);

        // check for the left movement and add to list
        horizontalCheckAndAdd(left, rank, board, validPositions);

        // check for the right movement and add to list
        horizontalCheckAndAdd(right, rank, board, validPositions);

        return validPositions;
    }


    /**
     * Checks if a horizontal movement by one is a valid move and adds
     * it to a list of valid moves if the movement is in bounds of
     * the board.
     *
     * @param col - The column letters to a movement left or right by one.
     * @param rank - The constant rank number that the movements should be on.
     * @param board - The board of the game that is being referenced.
     * @param positions - The list of valid moves that is being added to.
     */
    private void horizontalCheckAndAdd(int col, int rank, Board board, List<Position> positions) {
        // Need to check for the column bounds
        boolean colCheck = 0 <= col && col <= 7;
        Square square;
        if (colCheck) {
            square = board.getSquare(rank, col);
            positions.add(square.getPosition());
        }
    }


    /**
     * Checks if a vertical movement by one is a valid move and adds
     * it to a list of valid moves if the movement is in bounds of
     * the board.
     *
     * @param row - The row number to a movement up or down by one.
     * @param file - The constant file letter that the movements should be on.
     * @param board - The board of the game that is being referenced.
     * @param positions - The list of valid moves that is being added to.
     */
    private void verticalCheckAndAdd(int row, int file, Board board, List<Position> positions) {
        // Need to check for the row bounds
        boolean rowCheck = 0 <= row && row <= 7;
        Square square;
        if (rowCheck) {
            square = board.getSquare(row, file);
            positions.add(square.getPosition());
        }
    }
}
