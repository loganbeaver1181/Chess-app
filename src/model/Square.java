package model;

import enums.GameColor;
import interfaces.PieceIF;
import interfaces.SquareIF;

/**
 * A class to model a square on a chess board
 * @author Logan Beaver(100%)
 * @version 3/15/23
 */
public class Square extends BlackAndWhite implements SquareIF {

    /** a piece that occupies a square, null if no piece occupies the square */
    private PieceIF piece = null;

    /** the position of the square on the board */
    private Position position;

    /**
     * Construct a square
     * @param piece - the piece that occupies the square
     * @param position - the position of the square on the chess board
     * @param color- the color of the square
     */
    public Square(Piece piece, Position position, GameColor color) {
        super(color);
        this.piece = piece;
        this.position = position;
    }

    /**
     * Construct a square.
     * @param position - The position of the square on the board.
     * @param color - The color of the square.
     */
    public Square(Position position, GameColor color) {
        super(color);
        this.position = position;
    }

    /**
     * retrieve the position of the square
     * @return position - the current position of the square on the board
     */
    public Position getPosition() {
        return position;
    }


    /**
     * Set a new position of the square on the board
     * @param position - the new position
     */
    public void setPosition(Position position) {

        this.position = position;
    }

    /**
     * retrieve the piece that occupies the square
     *
     * @return - the Piece
     */

    public PieceIF getPiece() {

        return piece;
    }

    /**
     * Set a new piece in the square
     * @param piece - the new piece
     */

    public void setPiece(PieceIF piece) {
        this.piece = piece;
        if (this.piece != null) this.piece.setPosition(this.getPosition()); // Setting the piece's new position
    }

    /**
     * convert object to string representation
     * @return string representation
     */
    @java.lang.Override
    public java.lang.String toString() {
        String character;
        if(piece == null){
            character = " ";
        }else{
            character = String.valueOf(this.piece.getChessPieceType().getAbbrv());
        }
        return character;
    }

    /**
     * Clear the square of it's piece
     */
    public void clear(){

        this.piece = null;
    }

    /**
     * Returns true if the square is occupied
     * @return True if the square is occupied
     */
    public boolean isOccupied() {

        return this.piece != null;
    }

    public Square clone(){
        Piece pieceClone = null;
        if(getPiece() != null){
            pieceClone = (Piece) getPiece().clone();
        }

        Position positionClone = getPosition().clone();

        return new Square(pieceClone, positionClone, this.getColor());
    }

}