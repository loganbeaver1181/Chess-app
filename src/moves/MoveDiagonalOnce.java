package moves;
import interfaces.PieceIF;
import model.*;

import interfaces.MoveIF;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to specify a diagonal movement by one square in any direction.
 * Author: Michael Imerman (100%)
 * Version: 03.20.23
 */
public class MoveDiagonalOnce implements MoveIF {

    /**
     * Method that specifies if particular squares are valid and adds them to a list
     * of valid moves.
     *
     * @param piece - The position that we're at.
     * @param board - The board of the chess game.
     * @return validPositions - A list of valid positions.
     */
    @Override
    public List<Position> getValidPositions(PieceIF piece, Board board) {
        Position position = piece.getPosition();
        List<Position> validPositions = new ArrayList<>();

        int rank = position.getRank().getRow();   // gets the position in programming terms
        int file = position.getFile().getColumn();

        int leftOne = rank - 1;
        int upOne = file - 1;
        int rightOne = rank + 1;
        int downOne = file + 1;

        // top left
        addToList(upOne, leftOne, board, validPositions);
        // bottom left
        addToList(downOne, leftOne, board, validPositions);
        // top right
        addToList(upOne, rightOne, board, validPositions);
        // bottom right
        addToList(downOne, rightOne, board, validPositions);
        return validPositions;
    }


    /**
     * Checking if positions of moves are inbound on our board and adds to the
     * list of valid positions.
     *
     * @param rank - Integer that represents the rank number of the new position.
     * @param file  - Integer that represents the file number of the new position.
     * @param board - The board of the game of chess.
     * @param positions - The list of valid positions to be updated and returned later.
     */
    private void addToList(int rank, int file, Board board, List<Position> positions) {
        // Need to check for the rank bounds and then the file bounds with a nested if statement for the columns
        boolean rowCheck = 0 <= file && file <= 7;
        boolean colCheck = 0 <= rank && rank <= 7;
        // boolean tempSwitch = file >= 0 && file <= 7 && rank >= 0 && rank <= 7;
        Square square;
        if (rowCheck) {
            if (colCheck) {
                square = board.getSquare(file, rank);
                positions.add(square.getPosition());
            }
        }
    }
}
