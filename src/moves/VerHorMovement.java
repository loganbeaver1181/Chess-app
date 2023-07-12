package moves;

import interfaces.MoveIF;
import interfaces.PieceIF;
import model.Board;
import model.Position;
import model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to model vertical and horizontal movement.
 * @author Josiah(100%)
 * @version 03.27.23
 */
public class VerHorMovement implements MoveIF {

    /**
     * A method for getting a list of legal Positions our
     * piece can move to. If no valid moves, return null.
     *
     * @param piece - The piece we're getting valid positions for.
     * @param board - The board we're analyzing for valid positions.
     */
    @Override
    public List<Position> getValidPositions(PieceIF piece, Board board) {
        List<Position> validPositions = new ArrayList<Position>();

        // Getting all legal horizontal and vertical positions
        validPositions.addAll(getVerticalUpPositions(piece, board));
        validPositions.addAll(getVerticalDownPositions(piece, board));
        validPositions.addAll(getHorizontalLeftPositions(piece, board));
        validPositions.addAll(getHorizontalRightPositions(piece, board));

        return validPositions;
    }

    /**
     * Gets legal horizontal upward positions for the specified piece.
     * @param piece - The piece we're getting legal positions for.
     * @param board - The board we're moving the piece on.
     * @return A list of legal horizontal upward positions.
     */
    private List<Position> getVerticalUpPositions(PieceIF piece, Board board){
        Position position = piece.getPosition();
        List<Position> validPositions = new ArrayList<Position>();
        int rank = position.getRank().getRow(); // Getting the position in programming terms
        int file = position.getFile().getColumn(); // Getting the column

        while (rank - 1 >= 0 && file >= 0){// Getting vertical upwards
            rank--; // Move the rank up by 1
            Square square = board.getSquare(rank, file);
            if(square.isOccupied()){
                PieceIF other_piece = square.getPiece();
                // If we're moving to a square with our opponent's piece on it
                //We can't move past this piece
                if(!(other_piece.getColor() == piece.getColor())){
                    validPositions.add(square.getPosition());
                }
                break; // We can capture this piece, but not move past it
            }
            else{//The square isn't occupied
                validPositions.add(square.getPosition());
            }
        }
        return validPositions;
    }

    /**
     * A method to get the possible vertical positions going down the board.
     * @param piece - the piece we are trying to move.
     * @param board - the current state of the board.
     * @return A list of possible positions for the piece.
     */
    private List<Position> getVerticalDownPositions(PieceIF piece, Board board){
        Position position = piece.getPosition();
        List<Position> validPositions = new ArrayList<Position>();
        int rank = position.getRank().getRow(); // Getting the position in programming terms
        int file = position.getFile().getColumn(); // Getting the column

        while (rank + 1 <= 7 && file <= 7){// Getting vertical downwards
            rank++; // Move the rank down by 1
            Square square = board.getSquare(rank, file);
            if(square.isOccupied()){
                PieceIF other_piece = square.getPiece();
                // If we're moving to a square with our opponent's piece on it
                //We can't move past this piece
                if(!(other_piece.getColor() == piece.getColor())){
                    validPositions.add(square.getPosition());
                }
                break; // We can capture this piece, but not move past it
            }
            else{//The square isn't occupied
                validPositions.add(square.getPosition());
            }
        }
        return validPositions;
    }

    /**
     * A method to get the possible horizontal positions going down the board.
     * @param piece - the piece we are trying to move.
     * @param board - the current state of the board.
     * @return A list of possible positions for the piece.
     */
    private List<Position> getHorizontalLeftPositions(PieceIF piece, Board board){
        Position position = piece.getPosition();
        List<Position> validPositions = new ArrayList<Position>();
        int rank = position.getRank().getRow(); // Getting the position in programming terms
        int file = position.getFile().getColumn(); // Getting the column

        while (rank >= 0 && file - 1 >= 0){// Getting horizontal to the left
            file--; // Move the file to the left
            Square square = board.getSquare(rank, file);
            if(square.isOccupied()){
                PieceIF other_piece = square.getPiece();
                // If we're moving to a square with our opponent's piece on it
                //We can't move past this piece
                if(!(other_piece.getColor() == piece.getColor())){
                    validPositions.add(square.getPosition());
                }
                break; // We can capture this piece, but not move past it
            }
            else{//The square isn't occupied
                validPositions.add(square.getPosition());
            }
        }
        return validPositions;
    }
    /**
     * A method to get the possible horizontal positions going right on the board.
     * @param piece - the piece we are trying to move.
     * @param board - the current state of the board.
     * @return A list of possible positions for the piece.
     */
    private List<Position> getHorizontalRightPositions(PieceIF piece, Board board){
        Position position = piece.getPosition();
        List<Position> validPositions = new ArrayList<Position>();
        int rank = position.getRank().getRow(); // Getting the position in programming terms
        int file = position.getFile().getColumn(); // Getting the column

        while (rank <= 7 && file + 1 <= 7){// Getting horizontal to the right
            file++;
            Square square = board.getSquare(rank, file);
            if(square.isOccupied()){
                PieceIF other_piece = square.getPiece();
                // If we're moving to a square with our opponent's piece on it
                //We can't move past this piece
                if(!(other_piece.getColor() == piece.getColor())){
                    validPositions.add(square.getPosition());
                }
                break; // We can capture this piece, but not move past it
            }
            else{//The square isn't occupied
                validPositions.add(square.getPosition());
            }
        }
        return validPositions;
    }


}
