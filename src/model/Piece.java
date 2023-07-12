package model;
import enums.ChessPieceType;
import enums.GameColor;
import interfaces.MoveIF;
import interfaces.PieceIF;
import moves.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to model a chess piece
 *
 * @author Josiah Cherbony (90%), Michael (10%)
 * @version 3/23/23
 */
public class Piece extends BlackAndWhite implements PieceIF {
    /** A list containing the moves this piece can make */
    private List<MoveIF> moves = new ArrayList<>();

    /** The position of the piece on the board */
    private Position position;

    /** The type of the chess piece */
    private ChessPieceType type;

    /** a count of the moves taken */
    private int movesTaken = 0;

    /**
     * Constructor for color object
     *
     * @param color - black or white
     * @param type - the type of chess piece
     * @param position - the position of the piece
     */
    public Piece(ChessPieceType type, GameColor color, Position position) {
        super(color);
        this.type = type;
        this.position = position;

        switch (this.type) {
            case PAWN:
                moves.add(new MoveForwardOnce());
                break;
            case KING:
                moves.add(new CardinalMovement());
                moves.add(new MoveDiagonalOnce());
                moves.add(new Castling());
                break;
            case ROOK:
                moves.add(new VerHorMovement());
                break;
            case BISHOP:
                moves.add(new DiagonalMovement());
                break;
            case KNIGHT:
                moves.add(new LShapedMovement());
                break;
            case QUEEN:
                moves.add(new VerHorMovement());
                moves.add(new DiagonalMovement());
                break;
        }
    }

    /**
     * Returns the chess piece's moves.
     *
     * @return a list of moves.
     */
    public List<MoveIF> getMoves() {
        return moves;
    }


    /**
     * Returns the chess piece as a string representation
     * @return the chess piece as a string representation
     */
    @Override
    public String toString() {
        return type.getName();
    }


    /**
     * Returns the chess piece's type.
     * @return the chess piece's type
     */
    @Override
    public ChessPieceType getChessPieceType() {
        return this.type;
    }

    /**
     * Sets the chess piece's type
     * @param t The type we're making our piece's type
     */
    @Override
    public void setChessPieceType(ChessPieceType t) {
        this.type = t;
    }

    /**
     * Sets the Piece's list of moves
     * @param moves - the list of moves to give the piece
     */
    public void setMoves(List<MoveIF> moves) {
        this.moves = moves;
    }

    /**
     * Sets the position of the piece
     * @param position - The piece's position on the board
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the piece's position
     * @return the position of the piece
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Increase count of moves by 1
     */
    public void incrementMoves() {
        this.movesTaken++;
    }

    /**
     * Clones a piece and returns it.
     * @return A clone of this piece.
     */
    public PieceIF clone(){
        // NOTE: THINGS NOT CLONED BY THIS METHOD: "moves", type
        Position clonedPosition = this.getPosition().clone();
        return new Piece(this.type, this.getColor(), clonedPosition);
    }
}