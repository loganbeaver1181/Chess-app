package model;


import controllers.Chess;
import enums.ChessPieceType;
import enums.File;
import enums.GameColor;
import enums.Rank;
import interfaces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to model a chess board.
 * A 2-d array of square Objects that can hold 0-1 pieces.
 *
 * @author Logan Beaver(15%), Michael(30%), Laurin(25%), Daniel(10%), Josiah(20%)
 * @version 3/15/23
 */
public class Board implements BoardIF {

    PieceIF forceMovedPiece;

    boolean forceMoved = false;

    /**
     * 2D array representing the chess board
     */
    private Square[][] board;

    /**
     * The width of the board
     */
    private int width;

    /**
     * The height of the board
     */
    private int height;

    /**
     * A list of the captured white pieces
     */
    public List<PieceIF> capturedWhite = new ArrayList<>();

    /**
     * A list of the captured black pieces
     */
    public List<PieceIF> capturedBlack = new ArrayList<>();

    /**
     * A list of moves that would cause a checkmate
     */

    // ArrayList<Position> dangerousKingMoves = new ArrayList<>();
    ArrayList<MoveIF> dangerousKingMoves = new ArrayList<MoveIF>();

    ArrayList<PieceIF> threateningPieces = new ArrayList<>();

    /**
     * constructor for a default chess board
     */
    public Board() {
        this.initBoard();
        this.initializePieces();
    }



    /**
     * retrieve the board
     * @return the board
     */
    public Square[][] getSquares() {
        return board;
    }



    /**
     * retrieve the height of the board
     * @return height - the height of the board
     */
    public int getHeight() {
        return height;
    }

    /**
     * retrieve the width of the board
     * @return width - the width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieve the piece at the specified Rank and File
     *
     * @param r - the rank of the piece we're getting
     * @param f - the file of the piece we're getting
     * @return piece - the desired piece
     */
    public PieceIF getPiece(Rank r, File f) {
        Square square1 = board[r.getRow()][f.getColumn()];
        return square1.getPiece();
    }


    /**
     * Retrieve the piece at the specified row and column
     *
     * @param row - the row of the piece we're getting
     * @param col - the column of the piece we're getting
     * @return piece - the desired piece
     */
    public PieceIF getPiece(int row, int col){
        Square square1 = board[row][col];
        return square1.getPiece();
    }


    /**
     * Set a new board
     * @param board - the new board
     */
    public void setBoard(Square[][] board) {
        this.board = board;
    }

    /**
     * Initialize the chess board with squares and pieces in the correct spots.
     */
    public void initBoard() {
        this.width = 8;
        this.height = 8;
        this.board = new Square[this.width][this.height];
        int counter = 0; // A counter for switching between black and white squares
        GameColor color;

        for (Rank r : Rank.values()) {
            for (File f : File.values()) {
                Position pos = new Position(r, f);
                if(counter % 2 == 0){
                    color = GameColor.WHITE;
                }else{
                    color = GameColor.BLACK;
                }
                this.board[r.getRow()][f.getColumn()] = new Square(pos, color);
                counter++;
            }
            counter++;
        }
    }

    /**
     *For initializing Pieces. Places all pieces on the board.
     */
    public void initializePieces() {
        genPieces(GameColor.WHITE);
        genPieces(GameColor.BLACK);
    }

    /**
     * Helper function for initializing pieces based on color
     * @param color the color of the pieces
     */
    private void genPieces(GameColor color) {
        Rank rank = (color == GameColor.WHITE) ? (Rank.R1) : (Rank.R8);
        Rank pawnRank = (color == GameColor.WHITE) ? (Rank.R2) : (Rank.R7);

        for (int i = 0; i < 8; i++) {
            PieceIF pawn = new Piece(ChessPieceType.PAWN, color,
                    new Position(pawnRank, File.getFileByIndex(String.valueOf(i))));
            setBoardSquare(pawnRank.getRow(), i, pawn);
        }
        for (int i = 0; i < 8; i++) {
            PieceIF rook = new Piece(ChessPieceType.ROOK, color,
                    new Position(rank, File.getFileByIndex(String.valueOf(i))));
            setBoardSquare(rank.getRow(), i, rook);
            i = i+6;
        }
        for (int i = 1; i < 7; i++) {
            PieceIF knight = new Piece(ChessPieceType.KNIGHT, color,
                    new Position(rank, File.getFileByIndex(String.valueOf(i))));
            setBoardSquare(rank.getRow(), i, knight);
            i = i+4;
        }
        for (int i = 2; i < 6; i++) {
            PieceIF bishop = new Piece(ChessPieceType.BISHOP, color, new Position(rank, File.getFileByIndex(String.valueOf(i))));
            setBoardSquare(rank.getRow(), i, bishop);
            i = i+2;
        }
        PieceIF queen = new Piece(ChessPieceType.QUEEN, color, new Position (rank, File.D));
        setBoardSquare(new Position (rank, File.D), queen);
        PieceIF king = new Piece(ChessPieceType.KING, color, new Position (rank, File.E));
        setBoardSquare(new Position (rank, File.E), king);
    }


    /**
     * Set up the board.
     */
    public void setup() {

    }

    /**
     * Draw the board on the console for user to view
     */
    public void draw() {
        int counter = 0;
        Rank[] ranks = Rank.values();
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                System.out.print(board[i][j].toString());
            }
            System.out.print(ranks[counter].getRank());
            System.out.print("\n");
            counter++;
        }
        System.out.println(" H  G  F  E  D  C  B  A");
    }

    /**
     * get a square based on a Rank object and a File object
     * @param rank the rank of the square
     * @param file the file of the square
     * @return the square
     */
    public Square getSquare(Rank rank, File file){
        if ((rank == null || file == null) ||
                !(rank.getRow() >= 0 && rank.getRow() <= 7) ||
                !(file.getColumn() >= 0 && file.getColumn() <= 7)) return null;
        return this.board[rank.getRow()][file.getColumn()];
    }

    /**
     * get a square based on a row and column value (0-7)
     * @param rank the rank of the square
     * @param file the file of the square
     * @return the square
     */
    public Square getSquare(int rank, int file){
        if (!(rank >= 0 && rank <= 7) ||
                !(file >= 0 && file <= 7)) return null;
        return this.board[rank][file];
    }

    /**
     * Puts a piece on a square on the board.
     * @param row The row of the square we're inserting our piece into
     * @param col The column of the square we're inserting our piece into
     * @param piece The piece we're inserting in the specified square
     */
    public void setBoardSquare(int row, int col, PieceIF piece){
        Square square = this.board[row][col];
        square.setPiece(piece);
    }

    /**
     * Sets a piece on the board
     * @param position - The position of the square we're placing our piece
     * @param piece - The piece we're placing on a square
     */
    public void setBoardSquare(Position position, PieceIF piece){
        Rank rank = position.getRank();
        File file = position.getFile();
        Square square = this.board[rank.getRow()][file.getColumn()];
        square.setPiece(piece); // Setting the square
    }

    /**
     * Set the way the board is to be drawn from the options.
     *
     * @param strategy - the option of how to draw the board
     */
    public void setDrawStrategy(BoardStrategy strategy) {

    }

    /**
     * Given a list of a piece's possible moves, narrow that down to
     * moves that are actually possible as it pertains to
     * capturing or running into other pieces.
     * @param piece - the piece to validate
     * @return a list of moves
     */
    public List<Position> validateMoves(PieceIF piece) {
        ArrayList<Position> possiblePositions = new ArrayList<>();
        for (MoveIF move :
                piece.getMoves()) {
            possiblePositions.addAll(move.getValidPositions(piece, this));
        }
        List<Position> validPositions = new ArrayList<>();
        for (Position pos : possiblePositions) {
            Square currentSquare = this.getSquare(pos.getRank(), pos.getFile());
            if(currentSquare.isOccupied()) { //check for occupation
                if(currentSquare.getPiece().getColor() != piece.getColor()) {
                    validPositions.add(pos);
                }
            } else {
                validPositions.add(pos);
            }
        }

        return validPositions;
    }

    /**
     * This function moves the chess pieces if the move is valid,
     * clears the old square space, and displays captured pieces.
     *
     * @param piece - The piece that wants to move
     * @param newPos- The position the piece wants to move to
     * @param stateEditor - Represents an editor of our current state in the game. Needed
     *                      for checking whether the player has gone back a state.
     * @return true or false
     */
    public boolean move(PieceIF piece, Position newPos, GameStateEditor stateEditor) {
        Position oldPos = piece.getPosition();

        if(this.validateMoves(piece).contains(newPos)) {
            //checking to see if the piece is moving onto a square that is already occupied
            if(this.getSquare(newPos.getRank(), newPos.getFile()).isOccupied()){
                if(piece.getColor() == GameColor.WHITE){
                    //add it to black because it will only go into this if statement, if the piece in the newPos is the
                    //opposite color of the piece trying to move
                    capturedBlack.add(this.getSquare(newPos.getRank(), newPos.getFile()).getPiece());
                }
                else{
                    //add it to white
                    capturedWhite.add(this.getSquare(newPos.getRank(), newPos.getFile()).getPiece());
                }
            }
            // If we've shifted (undid to a previous game state) sort the history.
            if (stateEditor.haveShifted()){
                stateEditor.sortStateHistory();
            }

            //now clear the square that the piece was previously in
            //and put the piece in the square that it wants to move to since the move is valid
            this.getSquare(oldPos.getRank(), oldPos.getFile()).clear();
            this.getSquare(newPos.getRank(), newPos.getFile()).setPiece(piece);
            piece.incrementMoves();

            if(piece.getChessPieceType() == ChessPieceType.KING) this.checkCastle(piece);

            return true;
        }
        return false;
    }


    /**
     * Determines if the game is a stalemate.
     * >>>>>>>>UNFINISHED/UNIMPLEMENTED<<<<<<<<<<
     *
     * @param currentState A list of all the board states of the game of chess.
     * @return stalemate Boolean value representing if the game has reached a stalemate
     */
    public boolean stalemate(GameState.State currentState) {
        boolean stalemate = false;

        /**
        Position whiteKingPos = findPiece(GameColor.WHITE, ChessPieceType.KING).get(0);  // gets the positions of each
        Position blackKingPos = findPiece(GameColor.BLACK, ChessPieceType.KING).get(0);  // king on the board
        PieceIF whiteKing = getPiece(whiteKingPos.getRank(), whiteKingPos.getFile());
        PieceIF blackKing = getPiece(blackKingPos.getRank(), blackKingPos.getFile());


        int validMovesWhite = whiteKing.getMoves().size();   // counters to subtract one each time a move wil give a check,
        int validMovesBlack = blackKing.getMoves().size();   // so if they all give a check, there will be no good moves and give a stalemate


        for (int i = 0; i < whiteKing.getMoves().size(); i++) {
            // maybe temporarily move the king here to see it's valid positions there and
            // see if it would put it in check?

            // Not sure how else to test if that spot would make give a check
            if (validMovesWhite == 0) {
                stalemate = true;
            }
        }

        for (int j = 0; j < blackKing.getMoves().size(); j++) {
            // same as the above loop

            if (validMovesBlack == 0) {
                stalemate = true;
            }
        }
         **/
        return stalemate;
    }


    /**
     * Method for checking if the same board state has showed up three times in a row
     * and returns true if it has to indicate that the game should be a draw.
     * >>>>>>>>UNFINISHED/UNIMPLEMENTED<<<<<<<<<<
     *
     * @param states A reference to all of the states of the current game.
     * @return draw Boolean value representing if the game is a draw by repetition.
     */
    public boolean drawByRepetition(List<GameState.State> states) {
        int repeatedStates = 0;
        boolean draw = false;
        if (states.size() > 1) {
            for (int i = 0; i < states.size() - 1; i++) {
                if (states.get(i).equals(states.get(i + 1))) {
                    repeatedStates++;
                }

                if (repeatedStates == 3) {  // where 3 is the number of repeated states of the board it takes to give a draw
                    draw = true;
                }
            }
        }
        return draw;
    }

    /**
     * Determines if the game is a draw by counting the number of moves made that
     * have no real effect on the game.
     * >>>>>>>>UNFINISHED/UNIMPLEMENTED<<<<<<<<<<
     *
     * @param states A reference to all of the states of the current game.
     * @return draw Boolean value representing if the game is a draw.
     */
    public boolean drawByFiftyMoveRule(List<GameState.State> states) {
        boolean draw = false;
        //int unaffectingMoves = 0;
        //int movesToDraw = 25; // a player must make 25 unaffecting moves to cause a draw by fifty move rule
        //int initSizeOfCapturedBlack = capturedBlack.size();
        //int initSizeOfCapturedWhite = capturedWhite.size();

        /**
        // finds all the pawns for the white pieces
        List<Position> whitePawns = findPiece(GameColor.WHITE, ChessPieceType.PAWN);
        // finds all the pawns for the black pieces
        List<Position> blackPawns = findPiece(GameColor.BLACK, ChessPieceType.PAWN);


        for (GameState.State state : states) {
            // will need getter methods for captured pieces
            if (state.getBoardState().getCapturedBlack().size() == initSizeOfCapturedBlack &&
                    state.getBoardState().getCapturedWhite().size() == initSizeOfCapturedWhite) {
                if (state.getBoardState().findPiece(GameColor.BLACK, ChessPieceType.PAWN) == blackPawns
                        && state.getBoardState().findPiece(GameColor.WHITE, ChessPieceType.PAWN) == whitePawns) {
                    unaffectingMoves++;
                }
            }

            if (unaffectingMoves == movesToDraw) {    // movesToDraw = 25 because a player needs to make 25 moves for
                                                      // the draw rule to be applicable.
                draw = true;
            }
        }
         **/
        return draw;
    }


    /**
     * Looks for a check in the game.
     *
     * @param game the game to check
     * @return the player who is in check, or null if there is no player in check
     */
    public Player check(Chess game){
        Player checkedPlayer = null;

        Player currentPlayer = game.stateEditor.getCurrentPlayer();
        Player oppositePlayer = game.stateEditor.getOppositePlayer();

        ArrayList<PieceIF> oppositePieces = new ArrayList<>();
        PieceIF king = this.getPiece(ChessPieceType.KING, currentPlayer.getColor());

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                PieceIF currPiece = this.getPiece(i, j);

                //if the piece is the same color as the king, add it to the list
                if(currPiece != null && !(currPiece.getColor().equals(currentPlayer.getColor()))){
                    oppositePieces.add(currPiece);
                }
            }
        }

        for (PieceIF p :
                oppositePieces) {
            if(validateMoves(p).contains(king.getPosition())) checkedPlayer = currentPlayer;
        }


        oppositePieces = new ArrayList<>();
        king = this.getPiece(ChessPieceType.KING, oppositePlayer.getColor());

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                PieceIF currPiece = this.getPiece(i, j);

                //if the piece is the same color as the king, add it to the list
                if(currPiece != null && !(currPiece.getColor().equals(currentPlayer.getColor()))){
                    oppositePieces.add(currPiece);
                }
            }
        }

        for (PieceIF p :
                oppositePieces) {
            if(validateMoves(p).contains(king.getPosition())) checkedPlayer = oppositePlayer;
        }

        return checkedPlayer;
    }

    /**
     * A method for checking for checkmate based on a specific king rather than both at the same time
     * @param board
     * @param king
     * @return
     */
    private boolean checkByBoard(BoardIF board, PieceIF king){
        boolean check = false;

        ArrayList<PieceIF> oppositePieces = new ArrayList<>();

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                PieceIF currPiece = board.getPiece(i, j);

                if(currPiece != null && !(currPiece.getColor().equals(king.getColor()))){
                    oppositePieces.add(currPiece);
                }
            }
        }

        for (PieceIF p :
                oppositePieces) {
            if(validateMoves(p).contains(king.getPosition())) check = true;
        }

        return check;
    }


    /**
     * Method that allows the finding of any piece on the board and returns a list
     * of all of the pieces that are associated with the specified piece type and
     * color.
     *
     * @param color The color of the type of piece that you are searching for.
     * @param pieceType The type of piece that you're searching for.
     * @return pieces A list of all the pieces that apply to the given arguments.
     */
    private ArrayList<PieceIF> findPiece(GameColor color, ChessPieceType pieceType){
        //stateHistory.length

       // System.out.println("IN FINDPIECE PRINTING THE COLOR PARAM");
       // System.out.println(color);
       // System.out.println("IN FINDPIECE PRINTING THE PIECE TYPE PARAM");
       // System.out.println(pieceType);

        //a list that will contain the pieces / piece we are looking for
        ArrayList<PieceIF> pieces = new ArrayList<>();

        //the loops iterate through the board looking for a piece that matches the color and piece type from the params
        for(int i = 0; i < this.height; i++){
            for(int j = 0; j < this.width; j++){
                //if we find a piece that matches the color and piece type from the params, we add that piece
                //to the list
                if (this.getSquare(i , j).isOccupied()) {
                    if (this.getPiece(i, j).getColor().equals(color) && this.getPiece(i, j).getChessPieceType().equals(pieceType)) {
                        pieces.add(this.getPiece(i, j));
                    }
                }
            }
        }

       // System.out.println("IN FIND PIECE FUNCTION CHECKING THE PIECES IT IS RETURNING");
        //for(int k = 0; k < pieces.size(); k++){
          //  System.out.println(pieces.get(k));
       // }

        return pieces;
    }

    /**
     * Check if the game is in a checkmate state
     * @param game the game to check
     * @return the player who is in checkmate.
     */
    public Player checkmate(Chess game){
        Player matedPlayer = null;
        Player checkedPlayer = check(game);
        if (checkedPlayer == null) return null;

        PieceIF checkedKing = findPiece(checkedPlayer.getColor(), ChessPieceType.KING).get(0);
//        System.out.println(checkedKing.getChessPieceType() + " " + checkedKing.getColor());
        // three conditions:
            // if the king can move out of the way
                // force move of the king and see if check
                // then force move back
        boolean moveToSafety = kingCanMoveToSafety(game, checkedKing);

            // if a piece can capture threatening piece
                // see if any of the current player's pieces can move to the position of threatening piece
        boolean captureThreateningPiece = canCaptureThreat(game, checkedKing);

            // if current piece can move in the way of the lane of the threatening piece
        boolean blockCheck = checkForBlockingMoves(game, checkedKing);


        // if all false, player in checkmate
        Player currPlayer = game.getCurrentPlayer();
        PieceIF king = this.getPiece(ChessPieceType.KING, currPlayer.getColor());

        if(!moveToSafety && !captureThreateningPiece && !blockCheck) matedPlayer = checkedPlayer;

        return matedPlayer;
    }


    /**
     * Helper method that determines if the king can move to a safe square without
     * putting itself in check.
     *
     * @param game A reference to the current state of the game.
     * @param king The king to look at
     * @return Boolean that represents if the king can move to a safe square.
     */
    private boolean kingCanMoveToSafety(Chess game, PieceIF king) {
        boolean moveToSafety = false;
        Board clonedBoard = (Board) game.getBoard().clone();
        PieceIF clonedKing = clonedBoard.findPiece(king.getColor(), ChessPieceType.KING).get(0);

        List<Position> kingMoves = clonedBoard.validateMoves(clonedKing);
        Position originalPosition = clonedKing.getPosition();

        for (Position p :
                kingMoves) {
            Square oldSquare = clonedBoard.getSquare(p.getRank(), p.getFile());
            PieceIF oldPiece = oldSquare.getPiece();
            clonedBoard.forceMove(clonedKing, p);
            if (!clonedBoard.checkByBoard(clonedBoard,
                    clonedBoard.findPiece(clonedKing.getColor(), clonedKing.getChessPieceType()).get(0))) {
                moveToSafety = true;
            }
            clonedBoard.forceMove(clonedKing, originalPosition);
            if(oldPiece != null) clonedBoard.setBoardSquare(p, oldPiece);
        }

        return moveToSafety;
    }


    /**
     * Helper method that determines if a threatening piece can be captured.
     *
     * @param game A reference to the current state of the game
     * @return captureThreat Boolean that represents if the threatening piece can be captured.
     */
    private boolean canCaptureThreat(Chess game, PieceIF king) {
        boolean captureThreat = false;
        Board clonedBoard = (Board) game.getBoard().clone();
        PieceIF clonedKing = clonedBoard.findPiece(king.getColor(), ChessPieceType.KING).get(0);

        ArrayList<PieceIF> oppositePieces = new ArrayList<>();

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                PieceIF currPiece = this.getPiece(i, j);

                if(currPiece != null && !(currPiece.getColor().equals(king.getColor()))){
                    oppositePieces.add(currPiece);
                }
            }
        }

        ArrayList<PieceIF> threateningPieces = new ArrayList<>();

        for (PieceIF p :
                oppositePieces) {
            if (validateMoves(p).contains(king.getPosition())) {
                threateningPieces.add(p);
            }
        }

        ArrayList<PieceIF> allyPieces = new ArrayList<>();

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                PieceIF currPiece = this.getPiece(i, j);
                //if the piece is the same color as the king, add it to the list
                if(currPiece != null && currPiece.getColor().equals(king.getColor())){
                    allyPieces.add(currPiece);
                }
            }
        }

        for (PieceIF piece :
                allyPieces) {
            for (PieceIF oP :
                    threateningPieces) {
                if(validateMoves(piece).contains(oP.getPosition())) {
                    Position originalPosition = piece.getPosition();
                    for (Position pos :
                            validateMoves(piece)) {
                        Square oldSquare = clonedBoard.getSquare(pos.getRank(), pos.getFile());
                        PieceIF oldPiece = oldSquare.getPiece();
                        clonedBoard.forceMove(piece, pos);
                        if (!clonedBoard.checkByBoard(clonedBoard,
                                clonedBoard.findPiece(clonedKing.getColor(), clonedKing.getChessPieceType()).get(0))) {
                            captureThreat = true;
                        }
                        clonedBoard.forceMove(piece, originalPosition);
                        if(oldPiece != null) clonedBoard.setBoardSquare(pos, oldPiece);
                    }
                }
            }
        }

        return captureThreat;
    }


    /**
     * This function gets a piece based on piece type and piece color
     * @param type The type of piece
     * @param color The color of the piece
     * @return null if the piece is not found, otherwise it will return the piece you were looking for
     */
    public PieceIF getPiece(ChessPieceType type, GameColor color ){
        PieceIF wantedPiece = null;
        for(int i = 0; i < this.width; i ++){
            for(int j = 0; j < this.height; j++){
                PieceIF p = this.getPiece(i, j);
                if(p != null && p.getChessPieceType().equals(type) && p.getColor().equals(color)){
                    wantedPiece = this.getPiece(i,j);
                }
            }
        }
        return wantedPiece;
    }



    /**
     * This function will check to see if the current player has any pieces that can block the king from checkmate.
     *
     * @param game A reference to the piece that was just moved to get the color from.
     *
     * @return True if there is a piece that can block the king from being checkmated, false otherwise.
     */
    private boolean checkForBlockingMoves(Chess game, PieceIF king){
        boolean block = false;
        Board clonedBoard = (Board) game.getBoard().clone();
        PieceIF clonedKing = clonedBoard.findPiece(king.getColor(), ChessPieceType.KING).get(0);

        ArrayList<PieceIF> allyPieces = new ArrayList<>();

        for(int i = 0; i < this.width; i++){
            for(int j = 0; j < this.height; j++){
                PieceIF currPiece = this.getPiece(i, j);
                //if the piece is the same color as the king, add it to the list
                if(currPiece != null && currPiece.getColor().equals(king.getColor())){
                    allyPieces.add(currPiece);
                }
            }
        }

        for (PieceIF piece :
                allyPieces) {
            Position originalPosition = piece.getPosition();
            for (Position pos :
                    validateMoves(piece)) {
                Square oldSquare = clonedBoard.getSquare(pos.getRank(), pos.getFile());
                PieceIF oldPiece = oldSquare.getPiece();
                clonedBoard.forceMove(piece, pos);
                if (!clonedBoard.checkByBoard(clonedBoard,
                        clonedBoard.findPiece(clonedKing.getColor(), clonedKing.getChessPieceType()).get(0))) {
                    block = true;
                }
                clonedBoard.forceMove(piece, originalPosition);

                if(oldPiece != null) clonedBoard.setBoardSquare(pos, oldPiece);
            }
        }

        return block;

    }


    /**
     * If a king makes a move, check if it's a castling move.
     * If so, also move the appropriate rook
     * @param piece - The piece we're attempting to castle with.
     */
    private void checkCastle(PieceIF piece) {
//        //qualify move count
//        if(piece.getMoveCount() > 1) return;
//
//        //qualify position
//        if((piece.isWhite() && piece.getPosition().getRank() != Rank.R1) ||
//           (piece.isBlack() && piece.getPosition().getRank() != Rank.R8) ||
//                (piece.getPosition().getFile() != File.G) ||
//                (piece.getPosition().getFile() != File.C)) return;
//
//        Piece rook;
//        Rank rank = (piece.isWhite()) ? Rank.R1 : Rank.R8;
//
//        //kingside castling
//        if(piece.getPosition().getFile() == File.G) {
//            rook = this.getPiece(rank, File.H);
//            if(rook == null ||
//                    rook.getChessPieceType() != ChessPieceType.ROOK ||
//                    rook.getMoveCount() >= 1) return;
//            this.forceMove(rook, new Position(rank, File.F));
//        } else
//        //queenside castling
//        {
//            rook = this.getPiece(rank, File.A);
//            if(rook == null ||
//                    rook.getChessPieceType() != ChessPieceType.ROOK ||
//                    rook.getMoveCount() >= 1) return;
//            this.forceMove(rook, new Position(rank, File.D));
//        }
    }

    /**
     * Function for forcefully moving a piece regardless of move legality
     * Helpful for castling and demonstrating board scenarios
     * @param piece - The piece that's moving.
     * @param newPos - The piece's new position.
     */
    private void forceMove(PieceIF piece, Position newPos) {
        Position oldPos = piece.getPosition();

        this.getSquare(oldPos.getRank(), oldPos.getFile()).clear();
        this.getSquare(newPos.getRank(), newPos.getFile()).setPiece(piece);
        piece.setPosition(newPos);
    }

    /**
     * Clear the list of captured pieces of a player
     * @param list the list to clear
     */
    public void clearCapturedList(List<PieceIF> list){
        list.clear();
    }

    /**
     * retrieve the list of captured pieces.
     * @return a list of pieces
     */
    public List<PieceIF> getCapturedWhite() {
        return capturedWhite;
    }

    /**
     * retrieve the list of captured pieces.
     * @return a list of pieces
     */
    public List<PieceIF> getCapturedBlack() {
        return capturedBlack;
    }

    /**
     * Clones the board and returns it.
     * @return A clone of this board.
     */
    public BoardIF clone(){
        Board newBoard = new Board();
        Square[][] oldSquares = getSquares();
        for(int i = 0; i < oldSquares.length; i++){
            for(int j = 0; j < oldSquares[i].length; j++){
                Square squareClone = oldSquares[i][j].clone();
                newBoard.board[i][j] = squareClone;
            }
        }
        return newBoard;
    }

    /**
     * @return  the array of squares that comprise the board
     */
    @Override
    public Square[][] getBoard() {
        return this.board;
    }
}