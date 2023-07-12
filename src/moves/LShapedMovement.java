package moves;


import interfaces.MoveIF;
import interfaces.PieceIF;
import model.Board;
import model.Position;
import model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to model L shaped movement in chess.
 * @author Daniel(50%), Michael(50%)
 * @version 03.27.23
 */
public class LShapedMovement implements MoveIF {

    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     *
     * @param piece - Represents a piece on the board.
     * @param board - Represents a board for chess.
     */
    @Override
    public ArrayList<Position> getValidPositions(PieceIF piece, Board board) {
        Position position = piece.getPosition();
        ArrayList<Position> validPositions = new ArrayList<>();

        //the original position of the piece in rank and file terms
        int rank = position.getRank().getRow();
        int file = position.getFile().getColumn();

        int up2 = rank + 2;
        int down2 = rank - 2;
        int up1 = rank + 1;
        int down1 = rank - 1;

        int right2 = file + 2;
        int left2 = file - 2;
        int right1 = file + 1;
        int left1 = file - 1;

        // up2 left1
        // up2 right1
        checkAndAddToList(left1, right1, up2, board, validPositions);


        // down2 left1
        // down2 right1
        checkAndAddToList(left1, right1, down2, board, validPositions);


        //up1 left2
        //up1 right2
        checkAndAddToList(left2, right2, up1, board, validPositions);

        // down1 left2
        // down1 right2
        checkAndAddToList(left2, right2, down1, board, validPositions);

        return validPositions;
    }

    /**
     * Make sure the move is in bounds on our board and adds to the list of
     * valid movements.
     *
     * @param left - Integer that represents left movement.
     * @param right - Integer that represents right movement.
     * @param upOrDown - Integer that represents up or down movement.
     * @param board - The board of the game of chess.
     * @param positions - The list of valid positions to be updated and returned later.
     */
    private void checkAndAddToList(int left, int right, int upOrDown, Board board, List<Position> positions) {
        boolean rowCheck = 0 <= upOrDown && upOrDown <= 7;
        boolean leftCheck = 0 <= left && left <= 7;
        boolean rightCheck = 0 <= right && right <= 7;
        Square square;
        if (rowCheck) {
            if (leftCheck) {
                square = board.getSquare(upOrDown, left);
                positions.add(square.getPosition());
            }

            if (rightCheck) {
                square = board.getSquare(upOrDown, right);
                positions.add(square.getPosition());
            }
        }
    }
}
