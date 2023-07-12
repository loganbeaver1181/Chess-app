package interfaces;

import controllers.Chess;
import enums.Rank;
import enums.File;
import model.GameStateEditor;
import model.Player;
import model.Position;
import model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to define the necessary methods for
 * a chess board to function as needed.
 * @author Logan Beaver(60%) Michael Imerman (15%) Daniel Aoulou (15%)
 */
public interface BoardIF{
    /**
     * Initiate the board
     */
    public void initBoard();

    /**
     * Set up the board
     */
    public void setup();

    /**
     * Draw the board
     */
    public void draw();

    /**
     * Get a square on the board by rank and file
     * @param rank - the rank of the square
     * @param file - the file of the square
     * @return the square
     */
    public Square getSquare(Rank rank, File file);

    /**
     * Set the strategy the board will use to
     * draw itself
     * @param strategy - Either color or mono
     */
    public void setDrawStrategy(BoardStrategy strategy);

    /**
     * Get the width of the board
     * @return the width of the board
     */
    public int getWidth();

    /**
     * Get the height of the board
     * @return the height of the board
     */
    public int getHeight();

    /**
     * Get a piece on the board by rank and file
     * @param r - the rank
     * @param f - the file
     * @return the piece
     */
    public PieceIF getPiece(Rank r, File f);

    /**
     * Get a piece on the board by row and column
     * @param row - the row
     * @param col - the column
     * @return the piece
     */
    public PieceIF getPiece(int row, int col);


    Square[][] getSquares();


    /**
     * Validate the moves of a piece
     * @param piece piece to check
     * @return a list of possible moves
     */
    List<Position> validateMoves(PieceIF piece);

    /**
     * Set the board
     * @param board new board state
     */
    public void setBoard(Square[][] board);

    /**
     * Move piece to position
     *
     * @param startPiece  piece to move
     * @param position    position trying to move to
     * @param stateEditor the state editor of the game
     * @return true or false
     */
    boolean move(PieceIF startPiece, Position position, GameStateEditor stateEditor);


    /**
     * Clear the list of captured pieces of a player
     * @param list the list to clear
     */
    public void clearCapturedList(List<PieceIF> list);

    /**
     * retrieve the list of captured pieces.
     * @return a list of pieces
     */
    public List<PieceIF> getCapturedWhite();

    /**
     * retrieve the list of captured pieces.
     * @return a list of pieces
     */
    public List<PieceIF> getCapturedBlack();

    /**
     * A list of the captured white pieces
     */
    public List<PieceIF> capturedWhite = new ArrayList<>();

    /**
     * A list of the captured black pieces
     */
    public List<PieceIF> capturedBlack = new ArrayList<>();

    /**
     * create and place pieces on a new board
     */
    public void initializePieces();

    /**
     * copy a board
     * @return A clone of the current board
     */
    public BoardIF clone();

    /**
     * Get the array of squares that makes up a board
     * @return the square array
     */
    Square[][] getBoard();

    /**
     * Validate if a game is in a check situation
     *
     * @param game the game to check
     * @return true/false
     */
    Player check(Chess game);

    /**
     * Validate if a game is in a checkmate situation
     *
     * @param game the game to check
     * @return true/false
     */
    Player checkmate(Chess game);
    //boolean checkKingAllyMoves(Chess game);

    //boolean checkKingMove(Chess game);

}