package ui_gui;

import controllers.Chess;
import interfaces.BoardIF;
import interfaces.PieceIF;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

/**
 * A class file for a Game Screen where the game of chess can be played
 * @author Daniel Aoulou (90%), Logan Beaver (10%)
 */
public class GameScreen extends Pane {
    /**
     * The root of the layout
     **/
    private final StackPane root;

    /**
     * responsible for changing screens
     */
    ScreenChangeHandler sch;

    /**
     * the game being represented
     */
    private static Chess game;

    /**
     * the name of the player with the white game color
     */
    private String whiteName;

    /**
     * the name of the player with the black game color
     */
    private String blackName;

    /**
     * a var for keeping track of a piece whose moves are
     * being previewed, and thus might make a move
     */
    private PieceIF startPiece;

    /**
     * the end position of a piece that has moved
     */
    private Position endPos;

    /**
     * various screen elements
     */
    GridPane chessBoard = new GridPane();
    Label p1Name = new Label();
    Label p2Name = new Label();
    VBox player2Pane = new VBox();
    VBox player1Pane = new VBox();
    GridPane player1Captures = new GridPane();
    GridPane player2Captures = new GridPane();

    /** counter for keeping track of mutual draw */
    int drawCnt = 0;

    /**
     * counters for keeping track of the layout of captured
     * pieces
     */
    static int p1CaptureCounter = 0;
    static int p2CaptureCounter = 0;

    /**
     * initializers for the top row of buttons
     */
    private final Button loadButton = new Button("Load");
    private final Button saveButton = new Button("Save");
    private final Button undoButton = new Button("Undo");
    private final Button redoButton = new Button("Redo");
    private final Button drawButton = new Button("Draw");
    private final Button newGameButton = new Button("New Game");
    private final Button settingsButton = new Button("Settings");
    private final Button exitButton = new Button("Exit");

    /**
     * A text element for relaying various game and application
     * information
     */
    Label gameStatus = new Label();


    /**
     * Initializer for a GameScreen object
     * @param chess the chess game being represented
     */
    public GameScreen(Chess chess) {
        super();
        game = chess;

        getWhiteSquareColor();

        whiteName = game.getSettings().getWhiteName();
        blackName = game.getSettings().getBlackName();

        root = new StackPane();
        root.getStylesheets().add(getClass().getResource("/resources/styles/board_screen.css").toExternalForm());

        root.setMinWidth(1000);
        root.setMinHeight(600);

        VBox parent = new VBox();
        parent.setAlignment(Pos.CENTER);
        parent.setSpacing(1);

        HBox upperPane = new HBox();
        upperPane.getChildren().addAll(
                loadButton,
                saveButton,
                undoButton,
                redoButton,
                drawButton,
                newGameButton,
                settingsButton,
                exitButton
        );
        initializeButtons();
        for (Node b :
                upperPane.getChildren()) {
            ((Button) b).setPrefWidth(80);
        }
        upperPane.setAlignment(Pos.CENTER);
        upperPane.setSpacing(50);

        HBox lowerPane = new HBox();
        lowerPane.setSpacing(10);


        HBox bottomPane = new HBox();

        bottomPane.setPrefHeight(20);
        bottomPane.getChildren().add(gameStatus);
        bottomPane.setAlignment(Pos.CENTER);
        gameStatus.setText("Welcome to Chess Meister!");


        initializePlayerPanes();

        chessBoard.getStyleClass().add("chessBoard");
        chessBoard.setAlignment(Pos.CENTER);

        GridPane chessFiles = new GridPane();
        for (int i = 0; i < 8; i++) {
            chessFiles.getColumnConstraints().add(new ColumnConstraints(50));
            Label file = new Label();
            file.setMinSize(40, 40);
            file.setAlignment(Pos.BOTTOM_CENTER);
            file.setText(getFile(i));
            GridPane.setConstraints(file, i, 0);
            chessFiles.getChildren().add(file);
        }
        chessFiles.getRowConstraints().add(new RowConstraints(50));
        chessFiles.setAlignment(Pos.CENTER);

        GridPane chessRanks = new GridPane();
        for (int i = 0; i < 8; i++) {
            chessRanks.getRowConstraints().add(new RowConstraints(50));
            Label rank = new Label();
            rank.setMinSize(40, 40);
            rank.setAlignment(Pos.CENTER_RIGHT);
            rank.setText(String.valueOf(8 - i));
            GridPane.setConstraints(rank, 0, i);
            chessRanks.getChildren().add(rank);
        }
        chessRanks.getColumnConstraints().add(new ColumnConstraints(50));
        chessRanks.setAlignment(Pos.BOTTOM_CENTER);

        BoardIF board = game.getBoard();
        draw(board);

        VBox cB1 = new VBox();
        cB1.getChildren().add(chessFiles);
        cB1.getChildren().add(chessBoard);

        HBox fullBoard = new HBox();
        fullBoard.getChildren().add(chessRanks);
        fullBoard.getChildren().add(cB1);

        fullBoard.setAlignment(Pos.CENTER);

        lowerPane.setAlignment(Pos.CENTER);
        lowerPane.getChildren().add(player1Pane);
        lowerPane.getChildren().add(fullBoard);
        lowerPane.getChildren().add(player2Pane);

        parent.getChildren().add(upperPane);
        parent.getChildren().add(lowerPane);
        parent.getChildren().add(bottomPane);

        StackPane vb1 = this.root;
        vb1.setAlignment(Pos.CENTER);

        vb1.setId("Screen1");
        vb1.getChildren().add(parent);

        this.getChildren().add(vb1);

    }

    /**
     * populate the two side panes with various player information
     * and layouts
     */
    private void initializePlayerPanes() {
        //initialize player 1's info Pane

        player1Pane.getStyleClass().add("playerPane");
        player1Pane.setPrefWidth(250);
        player1Pane.setPadding(new Insets(10, 10, 10, 10));

        Label p1Label = new Label();
        p1Label.setText("Player 1");
        p1Label.getStyleClass().add("playerLabel");
        player1Pane.getChildren().add(p1Label);
        p1Name.setText(whiteName);
        p1Name.getStyleClass().add("playerName");
        player1Pane.getChildren().add(p1Name);

        VBox p1capturesBox = new VBox();
        Label p1Captures = new Label();
        p1Captures.setText("Captured Pieces:");
        p1Captures.getStyleClass().add("playerName");
        p1capturesBox.setAlignment(Pos.CENTER);

        player1Captures.setAlignment(Pos.CENTER);
        p1capturesBox.getChildren().add(p1Captures);
        p1capturesBox.getChildren().add(player1Captures);
        player1Pane.getChildren().add(p1capturesBox);

        player1Pane.setSpacing(20);


        //initialize player 2's info Pane

        player2Pane.getStyleClass().add("playerPane");
        player2Pane.setPrefWidth(250);
        player2Pane.setPadding(new Insets(10, 10, 10, 10));

        Label p2Label = new Label();
        p2Label.setText("Player 2");
        p2Label.getStyleClass().add("playerLabel");
        player2Pane.getChildren().add(p2Label);
        p2Name.setText(blackName);
        p2Name.getStyleClass().add("playerName");
        player2Pane.getChildren().add(p2Name);

        VBox p2capturesBox = new VBox();
        Label p2Captures = new Label();
        p2Captures.setText("Captured Pieces:");
        p2Captures.getStyleClass().add("playerName");
        p2capturesBox.setAlignment(Pos.CENTER);

        player2Captures.setAlignment(Pos.CENTER);
        p2capturesBox.getChildren().add(p2Captures);
        p2capturesBox.getChildren().add(player2Captures);
        player2Pane.getChildren().add(p2capturesBox);

        player2Pane.setSpacing(20);

        for (int i = 0; i < 4; i++) {
            player1Captures.getColumnConstraints().add(new ColumnConstraints(40));
            player2Captures.getColumnConstraints().add(new ColumnConstraints(40));
        }
        player1Captures.getRowConstraints().add(new RowConstraints(40));
        player2Captures.getRowConstraints().add(new RowConstraints(40));
    }

    /**
     * a helper method for getting the letters for the files
     * of the chess board
     * @param i the file index
     * @return the file letter
     */
    private String getFile(int i) {
        return switch (i) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "G";
            case 7 -> "H";
            default -> "";
        };
    }

    /**
     * a helper method for getting the image files for pieces
     * @param piece the piece being searched for
     * @return a relative path to the piece icon
     */
    private String getPieceIcon(PieceIF piece) {
        if (piece.isWhite()) {
            return switch (piece.getChessPieceType()) {
                case KING -> "/src/resources/chess_piece_icons/king_w.png";
                case QUEEN -> "/src/resources/chess_piece_icons/queen_w.png";
                case BISHOP -> "/src/resources/chess_piece_icons/bishop_w.png";
                case KNIGHT -> "/src/resources/chess_piece_icons/knight_w.png";
                case ROOK -> "/src/resources/chess_piece_icons/rook_w.png";
                case PAWN -> "/src/resources/chess_piece_icons/pawn_w.png";
            };
        } else {
            return switch (piece.getChessPieceType()) {
                case KING -> "/src/resources/chess_piece_icons/king_b.png";
                case QUEEN -> "/src/resources/chess_piece_icons/queen_b.png";
                case BISHOP -> "/src/resources/chess_piece_icons/bishop_b.png";
                case KNIGHT -> "/src/resources/chess_piece_icons/knight_b.png";
                case ROOK -> "/src/resources/chess_piece_icons/rook_b.png";
                case PAWN -> "/src/resources/chess_piece_icons/pawn_b.png";
            };
        }
    }

    /**
     * the GridPane object technically has its 0th row
     * at the top; in drawing the board, it is necessary to
     * invert the representation of ranks using this method
     * @param i the back end value of the rank or row
     * @return the front end value
     */
    private int b2fRank(int i) {
        return switch (i) {
            case 0 -> 7;
            case 1 -> 6;
            case 2 -> 5;
            case 3 -> 4;
            case 4 -> 3;
            case 5 -> 2;
            case 6 -> 1;
            case 7 -> 0;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    /**
     * Draw the board and add an event listener to each square
     * to listen for being clicked on, in which case trigger previewMoves
     * @param board the board to be drawn
     */
    public void draw(BoardIF board) {
        chessBoard.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square boardSquare = board.getSquares()[i][j];
                StackPane square = new StackPane();
                EventHandler<MouseEvent> previewMoves = event -> {
                    if (boardSquare.getPiece() != null) {
                        if (boardSquare.getPiece().getColor() ==
                                game.stateEditor.getCurrentPlayer().getColor()) {
                            startPiece = boardSquare.getPiece();
                            previewMoves(game.getBoard(), boardSquare.getPiece());
                        } else {
                            gameStatus.setText("That isn't your piece! It is currently " +
                                    getCurrentName() +
                                    "'s turn.");
                        }
                    } else {
                        gameStatus.setText("There isn't a piece there! Select one of your pieces " +
                                "to preview its moves");
                    }

                };
                if (boardSquare.isOccupied()) {
                    Image pieceImg;
                    try {
                        pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") +
                                getPieceIcon(boardSquare.getPiece())));
                    } catch (FileNotFoundException e) {
                        try {
                            pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") + "/Chess Meister" +
                                    getPieceIcon(boardSquare.getPiece())));
                        } catch (FileNotFoundException f) {
                            throw new RuntimeException(f);
                        }
                    }
                    ImageView pieceView = new ImageView(pieceImg);
                    pieceView.setFitHeight(40);
                    pieceView.setFitWidth(40);
                    square.getChildren().add(pieceView);
                }
                square.setPrefSize(50, 50);
                square.setOnMouseEntered(event -> {
                    square.setStyle("-fx-background-color: #ecf2f8;"); // Change the background color on hover
                });
                if (boardSquare.isWhite()) {
                    square.setStyle("-fx-background-color: " + getWhiteSquareColor() + ";");
                    square.setOnMouseExited(event -> {
                        square.setStyle("-fx-background-color: " + getWhiteSquareColor() + ";"); // Change the background color when the mouse exits
                    });
                } else {
                    square.setStyle("-fx-background-color: " + getBlackSquareColor() + ";");
                    square.setOnMouseExited(event -> {
                        square.setStyle("-fx-background-color: " + getBlackSquareColor() + ";"); // Change the background color when the mouse exits
                    });
                }

                square.setAlignment(Pos.CENTER);
                chessBoard.add(square, j, b2fRank(i));
                square.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, previewMoves);


            }
        }
    }

    /**
     * Draw the board and add an event listener to each square
     * to listen for being clicked on, in which case trigger previewMoves.
     * Add styling to a piece and its possible moves.
     * @param board the board to be drawn
     * @param piece the piece to be previewed
     */
    public void previewMoves(BoardIF board, PieceIF piece) {
        List<Position> validMoves = board.validateMoves(piece);
        chessBoard.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square boardSquare = board.getSquares()[i][j];
                StackPane square = new StackPane();
                EventHandler<MouseEvent> previewMoves = event -> {
                    if (boardSquare.getPiece() != null && boardSquare.getPiece().getColor() ==
                            game.stateEditor.getCurrentPlayer().getColor()) {
                        startPiece = boardSquare.getPiece();
                        previewMoves(game.getBoard(), boardSquare.getPiece());
                    } else {
                        endPos = boardSquare.getPosition();
                        boolean didCapturedPiece = (boardSquare.getPiece() != null);
                        PieceIF capturedPiece = boardSquare.getPiece();
                        Position oldPos = startPiece.getPosition();

                        if (game.getBoard().move(startPiece, endPos, game.stateEditor)) {
                            if (didCapturedPiece) {
                                if (game.stateEditor.getCurrentPlayer().isWhite()) {
                                    updateWhiteCaptures(capturedPiece);
                                } else {
                                    updateBlackCaptures(capturedPiece);
                                }
                            }

                            game.stateEditor.addMoveToHistory(game.stateEditor.getCurrentPlayer(),
                                    oldPos, endPos);

                            String endPos = boardSquare.getPosition().getFile() + "" +
                                    boardSquare.getPosition().getRank().getRank();

                            draw(game.getBoard());
                            gameStatus.setText(startPiece.getColor().toString().toUpperCase() +
                                    " " + startPiece.getChessPieceType() +
                                    " moves to " + endPos + ". " + getCurrentName() + "'s turn.");

                            Player checkedPlayer = game.getBoard().check(game);
                            if (checkedPlayer != null) {
                                gameStatus.setText(startPiece.getColor().toString().toUpperCase() +
                                        " " + startPiece.getChessPieceType() +
                                        " moves to " + endPos + ". " +
                                        checkedPlayer.getName() + " is in check!");
                            }

                            Player matedPlayer = game.getBoard().checkmate(game);

                            if (matedPlayer != null) {
                                String oppositeName = (Objects.equals(matedPlayer.getName(), getCurrentName()) ?
                                        getOppositeName() : getCurrentName());
                                gameStatus.setText(startPiece.getColor().toString().toUpperCase() +
                                        " " + startPiece.getChessPieceType() +
                                        " moves to " + endPos + ". " +
                                        matedPlayer.getName() + " is in checkmate! " + oppositeName
                                        + " wins! To start a new game, click New Game.");
                                drawFinal(game.getBoard());
                            }

                        } else {
                            draw(game.getBoard());
                            gameStatus.setText("That piece cannot make that move!");
                        }
                    }


                };
                if (boardSquare.isOccupied()) {
                    Image pieceImg;
                    try {
                        pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") +
                                getPieceIcon(boardSquare.getPiece())));
                    } catch (FileNotFoundException e) {
                        try {
                            pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") + "/Chess Meister" +
                                    getPieceIcon(boardSquare.getPiece())));
                        } catch (FileNotFoundException f) {
                            throw new RuntimeException(f);
                        }
                    }
                    ImageView pieceView = new ImageView(pieceImg);
                    pieceView.setFitHeight(40);
                    pieceView.setFitWidth(40);
                    square.getChildren().add(pieceView);
                }

                square.setOnMouseEntered(event -> {
                    square.setStyle("-fx-background-color: #ecf2f8;"); // Change the background color on hover
                });
                if (boardSquare.isWhite()) {
                    square.setStyle("-fx-background-color: " + getWhiteSquareColor() + ";");
                    square.setOnMouseExited(event -> {
                        square.setStyle("-fx-background-color: " + getWhiteSquareColor() + ";"); // Change the background color when the mouse exits
                    });
                } else {
                    square.setStyle("-fx-background-color: " + getBlackSquareColor() + ";");
                    square.setOnMouseExited(event -> {
                        square.setStyle("-fx-background-color: " + getBlackSquareColor() + ";"); // Change the background color when the mouse exits
                    });
                }

                if(game.getSettings().getShowMoves()) {
                    if (validMoves.contains(boardSquare.getPosition())) {
                        square.setStyle("-fx-background-color: #ff5f5f;");
                        square.setOnMouseEntered(event -> {
                            square.setStyle("-fx-background-color: #ec5858;"); // Change the background color when the mouse exits
                        });
                        square.setOnMouseExited(event -> {
                            square.setStyle("-fx-background-color: #ff5f5f;"); // Change the background color when the mouse exits
                        });
                    } else {
                        if (boardSquare.getPiece() == piece) {
                            square.setStyle("-fx-background-color: #fcdfb1;");
                            square.setOnMouseEntered(event -> {
                                square.setStyle("-fx-background-color: #e8cea4;"); // Change the background color when the mouse exits
                            });
                            square.setOnMouseExited(event -> {
                                square.setStyle("-fx-background-color: #fcdfb1;"); // Change the background color when the mouse exits
                            });
                        }
                    }
                }


                square.setPrefSize(50, 50);

                square.setAlignment(Pos.CENTER);
                chessBoard.add(square, j, b2fRank(i));
                square.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, previewMoves);
            }
        }
    }

    /**
     * Draw a board with no event listeners. View only.
     * Useful for checkmate when no other moves can be made.
     * @param board the board to be drawn
     */
    private void drawFinal(BoardIF board) {
        chessBoard.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square boardSquare = board.getSquares()[i][j];
                StackPane square = new StackPane();
                if (boardSquare.isOccupied()) {
                    Image pieceImg;
                    try {
                        pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") +
                                getPieceIcon(boardSquare.getPiece())));
                    } catch (FileNotFoundException e) {
                        try {
                            pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") + "/Chess Meister" +
                                    getPieceIcon(boardSquare.getPiece())));
                        } catch (FileNotFoundException f) {
                            throw new RuntimeException(f);
                        }
                    }
                    ImageView pieceView = new ImageView(pieceImg);
                    pieceView.setFitHeight(40);
                    pieceView.setFitWidth(40);
                    square.getChildren().add(pieceView);
                }
                square.setPrefSize(50, 50);
                square.getStyleClass().add("boardSquare");
                if (boardSquare.isWhite()) {
                    square.getStyleClass().add("whiteBoardSquare");
                    square.setStyle("-fx-background-color: " + getWhiteSquareColor() + ";");
                } else {
                    square.getStyleClass().add("blackBoardSquare");
                    square.setStyle("-fx-background-color: " + getBlackSquareColor() + ";");
                }
                square.setAlignment(Pos.CENTER);
                chessBoard.add(square, j, b2fRank(i));
            }
        }
    }

    /**
     * Add a piece to the white player's capture list
     * @param p the piece to be added
     */
    private void updateWhiteCaptures(PieceIF p) {
        Image pieceImg;
        try {
            pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") +
                    getPieceIcon(p)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView pieceView = new ImageView(pieceImg);
        pieceView.setFitHeight(40);
        pieceView.setFitWidth(40);

        player1Captures.getChildren().add(pieceView);
        GridPane.setConstraints(pieceView, p1CaptureCounter, player1Captures.getRowCount() - 1);
        p1CaptureCounter++;
        if (p1CaptureCounter == 4) {
            p1CaptureCounter = 0;
            player1Captures.getRowConstraints().add(new RowConstraints(40));
        }
    }

    /**
     * Add a piece to the black player's capture list
     * @param p the piece to be added
     */
    private void updateBlackCaptures(PieceIF p) {
        Image pieceImg;
        try {
            pieceImg = new Image(new FileInputStream(System.getProperty("user.dir") +
                    getPieceIcon(p)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ImageView pieceView = new ImageView(pieceImg);
        pieceView.setFitHeight(40);
        pieceView.setFitWidth(40);

        player2Captures.getChildren().add(pieceView);
        GridPane.setConstraints(pieceView, p2CaptureCounter, player2Captures.getRowCount() - 1);
        p2CaptureCounter++;
        if (p2CaptureCounter == 4) {
            p2CaptureCounter = 0;
            player2Captures.getRowConstraints().add(new RowConstraints(40));
        }
    }

    /**
     * @return the name of the current player
     */
    private String getCurrentName() {
        whiteName = game.getSettings().getWhiteName();
        blackName = game.getSettings().getBlackName();

        return (game.stateEditor.getCurrentPlayer().isWhite() ? whiteName : blackName);
    }

    /**
     * @return the name of the player whose turn it is not
     */
    private String getOppositeName() {
        whiteName = game.getSettings().getWhiteName();
        blackName = game.getSettings().getBlackName();

        return (game.stateEditor.getCurrentPlayer().isWhite() ? blackName : whiteName);
    }

    /**
     * Set the handler for screen changes
     **/
    public void setScreenChangeHandler(ScreenChangeHandler sch) {
        this.sch = sch;
    }//end

    /**
     * get the root of the screen
     *
     * @return the root pane
     */
    public Pane getRoot() {
        return this.root;
    }

    /**
     * initialize the buttons of the screen with their various event handlers
     */
    private void initializeButtons() {
        undoButton.setOnAction(event -> undo());
        redoButton.setOnAction(event -> redo());
        loadButton.setOnAction(event -> loadGame());
        saveButton.setOnAction(event -> saveGame());
        newGameButton.setOnAction(event -> startNewGame());
        drawButton.setOnAction(event -> promptDraw());
        exitButton.setOnAction(event -> {
            sch.switchScreen(ScreenFactory.Screen.SCREEN0);// should be main screen
        });
        settingsButton.setOnAction(event -> {
            sch.switchToFromSettings(ScreenFactory.Screen.SCREEN3, getRoot());// should be main screen
        });
    }

    /**
     * Start a new game
     */
    private void startNewGame() {
        game.stateEditor.load(".new-game.txt");
        gameStatus.setText("Started a new game!");
        draw(game.getBoard());
        updateNames();
        drawCnt = 0;

        player1Captures.getChildren().clear();
        p1CaptureCounter = 0;
        player2Captures.getChildren().clear();
        p2CaptureCounter = 0;

    }

    /**
     * Prompt a mutual draw
     */
    private void promptDraw() {
        drawCnt++;
        if (drawCnt == 2) {
            gameStatus.setText("The game has ended by mutual draw! " +
                    "To start a new game, press the New Game button.");
            drawFinal(game.getBoard());
            return;
        }
        gameStatus.setText(getCurrentName() + " has initiated a draw. " +
                "If " + getOppositeName() + " agrees, click the draw button again " +
                "to end the game.");

    }

    /**
     * Update the displayed names of the players
     */
    private void updateNames() {
        whiteName = game.getSettings().getWhiteName();
        blackName = game.getSettings().getBlackName();

        p1Name.setText(whiteName);
        p1Name.setText(blackName);
    }

    /**
     * Update all the captured pieces
     */
    private void updateCaptures() {
        player1Captures.getChildren().clear();
        p1CaptureCounter = 0;
        player2Captures.getChildren().clear();
        p2CaptureCounter = 0;

        List<PieceIF> p1CapturesArr = game.getBoard().getCapturedBlack();
        for (PieceIF piece :
                p1CapturesArr) {
            updateWhiteCaptures(piece);
        }

        List<PieceIF> p2CapturesArr = game.getBoard().getCapturedWhite();
        for (PieceIF piece :
                p2CapturesArr) {
            updateBlackCaptures(piece);
        }
    }

    /**
     * Open a file chooser and load a file from the saved_files folder.
     */
    private void loadGame() {
        FileChooser fileChooser = new FileChooser();
        File initialDirectory = new File(System.getProperty("user.dir") + "/src/saved_files");

        fileChooser.setTitle("Load a Game");
        fileChooser.setInitialDirectory(initialDirectory);
        Stage fileStage = new Stage();
        File file = fileChooser.showOpenDialog(fileStage);
        if (file != null) {
            if (file.getName().equals(".new-game.txt")) {
                gameStatus.setText("That is an application file! Can't load this file.");
                return;
            }
            if (game.stateEditor.load(file.getName())) {
                gameStatus.setText("Loaded game " + file.getName() + ". "
                        + getCurrentName() + "'s turn.");

                whiteName = game.stateEditor.getPlayers().get(0).getName();
                blackName = game.stateEditor.getPlayers().get(1).getName();

                p1Name.setText(whiteName);
                p2Name.setText(blackName);

                updateCaptures();

                draw(game.stateEditor.getBoard());
            } else {
                gameStatus.setText("Error occurred in loading game. "
                        + getCurrentName() + "'s turn.");
            }
        } else {
            gameStatus.setText("No file selected, game not loaded. "
                    + getCurrentName() + "'s turn.");
        }
    }

    /**
     * Prompt the user for a name and save a new file with that name
     */
    private void saveGame() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Save File");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the name to save this game as:");
        String fileName = dialog.showAndWait().orElse(null);

        if (fileName != null) {
            fileName += ".txt";
            if (game.stateEditor.saveGameGUI(fileName)) {
                gameStatus.setText("Saved game as " + fileName + ".");
            } else {
                gameStatus.setText("Error occurred in saving game. "
                        + getCurrentName() + "'s turn.");
            }
        }
    }

    /**
     * Undo a move
     */
    private void undo() {
        boolean undid = game.stateEditor.undo_once();
        if (undid) {
            gameStatus.setText("Undid "
                    + getCurrentName() + "'s move.");
        } else if (!game.getSettings().getUnlimitedUndos()){
            gameStatus.setText("You can't undo that many moves! Set Unlimited Undos " +
                    "in the settings to change this.");
        } else {
            gameStatus.setText("An error occurred in undoing that move.");
        }
        draw(game.getBoard());
        updateCaptures();
    }

    /**
     * Redo a move
     */
    private void redo() {
        boolean redid = game.stateEditor.redoOnce();
        if (redid) {
            gameStatus.setText("Redid the previous move.");
        } else {
            gameStatus.setText("Can't go that far ahead!");
        }
        draw(game.getBoard());
        updateCaptures();
    }

    /**
     * @return the hex code of the selected white squares color
     */
    private String getWhiteSquareColor() {
        int[] whiteSquareColor = game.getSettings().getWhiteSquareColor();
        return String.format("#%02X%02X%02X", whiteSquareColor[0], whiteSquareColor[1], whiteSquareColor[2]);
    }

    /**
     * @return the hex code of the selected black squares color
     */
    private String getBlackSquareColor() {
        int[] blackSquareColor = game.getSettings().getBlackSquareColor();
        return String.format("#%02X%02X%02X", blackSquareColor[0], blackSquareColor[1], blackSquareColor[2]);
    }


}