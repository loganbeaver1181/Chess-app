package model;

import enums.ChessPieceType;
import enums.GameColor;
import enums.Rank;
import interfaces.BoardIF;
import interfaces.PieceIF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;

/**
 * A class for shifting through the game's states, as well as saving
 * and loading a game state to a text file.
 * @author Josiah Cherbony (80%), Logan Beaver (20%)
 * @version 5/19/23
 */
public class GameStateEditor {

    /** The players playing this game */
    List<Player> players;

    /** A list of all the game states */
    private List<GameState.State> stateHistory;

    /** Used for moving through the index of the state history */
    int currStateIndex = 0;

    /** The current game state */
    private GameState gameState;

    /** A string that holds a linux path to a directory we'll be saving our files in */
    String FILE_DIR = System.getProperty("user.dir") + "/src/saved_files/"; // Relative path

    /** The maximum amount of undos allowed before disabling */
    private int maxUndos;


    /**
     * Creates a state editor object.
     */
    public GameStateEditor(){
        stateHistory = new ArrayList<>();
        gameState = new GameState();
        players = new ArrayList<>();
        setNewPlayers(null,null); // Creates 2 new players
        setMaxUndos(1); // By default, allows for 1 undo
        // Taking a snapshot of the initial board
        stateHistory.add(getGameState().takeSnapshot());
    }

    /**
     * Adds a player, the placed they moved from, and the place they moved to
     * in our history.
     * @param player - The player who made the move.
     * @param from - The position the piece came from.
     * @param to - The position the piece moved to.
     */
    public void addMoveToHistory(Player player, Position from, Position to){
        // Adds a move to our history in the gameState
        getGameState().addMoveToHistory(player, from, to);
        switchCurrentPlayer(); // Switch the current player
        // Add a snapshot of the state to our state history
        stateHistory.add(getGameState().takeSnapshot());
        incrementStateIndex(); // Increment the state
//        printHistory();

        if(maxUndos == 0){
            maxUndos = 1;
        }

    }

    /**
     * A function that adds a player move string (formatted as PlayerName:fromPosition:toPosition)
     * to our history of moves.
     * @param playerMoveString - A string formatted as PlayerName:fromPosition:toPosition
     */
    public void addToMoveHistory(String playerMoveString){
            // Alternate between black and white for each move
            Player currentPlayer = getCurrentPlayer();
            // Splitting up our string
            String[] moveContents = playerMoveString.split(":");
            // Creating the rank and files
            Rank fromRank = Rank.getRankByIndex(moveContents[1].substring(1, 2));
            enums.File fromFile = enums.File.getFileByIndex(moveContents[1].substring(0, 1));
            Position fromPosition = new Position(fromRank, fromFile);
            // Getting the piece and the rank and file
            PieceIF startPiece = getGameState().boardState.getPiece(fromRank, fromFile);

            Rank toRank = Rank.getRankByIndex(moveContents[2].substring(1, 2));
            enums.File toFile = enums.File.getFileByIndex(moveContents[2].substring(0, 1));
            Position toPosition = new Position(toRank, toFile);

            // Moving the piece
            getGameState().boardState.move(startPiece, toPosition, this);
            addMoveToHistory(currentPlayer, fromPosition, toPosition); // Recreates the history

    }

    /**
     * A method that creates a .txt file with our Game History's
     * information.
     */
    public boolean saveGame(){
        System.out.print("What would you like to call the file?: \n" +
                "Please type a file name with a .txt extension.\n" +
                "Files are saved and loaded from the saved_files directory.\n");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        try { // Creates a new file if it doesn't exist
            FileWriter saveFile = new FileWriter(new File(FILE_DIR, fileName));
            // Writing our data to the file
            saveFile.write(getBoardStateString());
            saveFile.write(getMoveHistoryString());
            saveFile.write(getPlayerString());
            saveFile.close();
            return true;
        } catch (IOException e) { // Else
            return false;
        }
    }

    /**
     * A method that creates a .txt file with our Game History's
     * information.
     * @param name the name of the file
     */
    public boolean saveGameGUI(String name){
        String fileName = name;

        try { // Creates a new file if it doesn't exist
            FileWriter saveFile = new FileWriter(new File(FILE_DIR, fileName));
            // Writing our data to the file
            saveFile.write(getBoardStateString());
            saveFile.write(getMoveHistoryString());
            saveFile.write(getPlayerString());
            saveFile.close();
            return true;
        } catch (IOException e) { // Else
            return false;
        }
    }

    /**
     * Load in a game from the given file and returns a GameStateEditor
     * object with the game's data.
     * @param fileName - a string holding a path to a file
     */
    public boolean load(String fileName){
        Path filePath = Paths.get(fileName).toAbsolutePath();
        String relativePath = Paths.get("").toAbsolutePath().relativize(filePath).toString();

        String data = getFileAsString(relativePath);
        if(data == null) return false;

        clearStateEditor(); // Clearing all the state editor's data

        // Split string to get individual components below
        String[] contentArr = data.split("#");
        String boardString;
        String playerString;
        String moveString;
        String[] moveList = null;
        if(contentArr.length == 2){ // If the array equal 2, we don't have a move history
            boardString = contentArr[0];
            playerString = contentArr[1];
        }else{ // Else we have a move history
            boardString = contentArr[0];
            moveString = contentArr[1];
            moveList = moveString.split(",");
            playerString = contentArr[2];
        }
        // Loading the player section of data
        loadPlayers(playerString);
        // Creating a new board
        BoardIF boardState = getBoard();
        boardState.initBoard(); // Initializing the board
        boardState.initializePieces(); // Initializing the pieces
        stateHistory.add(getGameState().takeSnapshot()); // Taking a snapshot of the initial board
        // Adding all moves from move section of data and loading it into our new board
        if(moveList != null){
            for(String move : moveList){
                addToMoveHistory(move);
            }
        }
        // Loads the board
        loadBoardState(boardString);
        return true;
    }


    /**
     * Loads two player to a Game State's player list from a string.
     * @param playerString - The content loaded from a .txt file.
     */
    public void loadPlayers(String playerString){
        // Splitting up our string, and creating 2 new players from it
        String[] playerContents = playerString.split(",");
        Player p1 = new Player(GameColor.WHITE, playerContents[0]);
        Player p2 = new Player(GameColor.BLACK, playerContents[1]);
        players.clear(); // Clear the current list of players
        players.add(p1);
        players.add(p2);
        // Set White as the first player
        getGameState().setCurrPlayer(p1);
    }


    /**
     * Loads two player to a Game State's player list from a string.
     * boardString - The string holding the contents of the board state
     */
    public void loadBoardState(String boardString){
        String[] pieces = boardString.split(",");//split string into array of pieces
        BoardIF boardState = getGameState().boardState;
        // Get our squares from the boardState (which should be empty)
        Square[][] squares = boardState.getSquares();
        // For every piece on our string
        for(String s : pieces){
            // Get rank and file
            String file = s.substring(0,1);
            String rank = s.substring(1,2);
            // Create position
            Position pos = new Position(rank, file);
            // Get color
            String color = s.substring(3,8);
            color = color.toUpperCase();
            GameColor c = GameColor.valueOf(color);

            // Get piece
            String type = s.substring(8);
            type = type.toUpperCase();
            ChessPieceType t = ChessPieceType.valueOf(type);
            // Create piece
            Piece piece = new Piece(t, c, pos);
            // Setting the new piece on the board
            squares[pos.getRank().getRow()][pos.getFile().getColumn()].setPiece(piece);
        }
        // Set 2-d array of squares holding chess pieces to our board field
        boardState.setBoard(squares);
    }

    /**
     * A method that takes the name of a file, and returns its contents
     * as a string.
     * @param fileName - The name of the file with game data.
     * @return data The file contents in the form of a string
     */
    public String getFileAsString(String fileName){
        String data = null;
        Scanner reader;
        // Testing to see if the file exists.
        try{
            File file = new File(FILE_DIR + fileName);
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                data = reader.nextLine();
            }
        } catch (FileNotFoundException e){
            System.out.println("An error occurred.");
//            e.printStackTrace();
        }
        return data;
    }

    /**
     * Gets the board state in the form of a string.
     * @return boardString
     */
    public String getBoardStateString(){
        StringBuilder boardString = new StringBuilder();
        // Getting the state's board
        Square[][] board = getBoard().getSquares();
        // For every square in our board.
        for(Square[] row : board){
            for(Square square : row){
                // If the square has a piece on it
                if(square.isOccupied()){
                    Position position = square.getPosition();
                    // Adding the position rank and file (for example "A7") to the boardString
                    boardString.append(position.getPositionAbbv());
                    boardString.append(":");
                    Piece piece = (Piece) square.getPiece();
                    String color = piece.getColor().toString();
                    // Adding the piece abbreviation to the boardString
                    boardString.append(color).append(piece);
                    boardString.append(",");
                }
            }
        }
        // Removes the final comma from the list (as it's unnecessary).
        boardString = new StringBuilder(boardString.substring(0, boardString.length() - 1));
        return boardString.toString();
    }

    /**
     * Returns the Game History's move history in string form.
     * @return historyString
     */
    public String getMoveHistoryString(){
        StringBuilder historyString = new StringBuilder("#");
        // For every move in our list of moves
        for(PlayerMove move : gameState.moveList){
            // Get the player, and the positions they moved from/to.
            Player player = move.getPlayer();
            Position fromPosition = move.getFromPosition();
            Position toPosition = move.getToPosition();
            // Adding the player's color (Black or White)
            historyString.append(player.getColor()).append(":");
            historyString.append(fromPosition.getPositionAbbv()).append(":");
            historyString.append(toPosition.getPositionAbbv()).append(",");
        }
        // Removes the final comma from the list (as it's unnecessary).
        historyString = new StringBuilder(historyString.substring(0, historyString.length() - 1));
        return historyString.toString();
    }

    /**
     * Gets the players' names in the form of strings.
     * @return playerString
     */
    public String getPlayerString(){
        StringBuilder playerString = new StringBuilder("#");
        // For every player in our player list
        for(int index = 0 ; index < players.size() ; index++){
            Player p = players.get(index);
            if(p.getName().isEmpty()) {
                if (index == 0) p.setName("WHITE");
                if (index == 1) p.setName("BLACK");
            }
            playerString.append(p.getName()); // Get their name

            // Adds commas between names except the last
            if(index < players.size() - 1){
                playerString.append(",");
            }
        }
        return playerString.toString();
    }

    /**
     * Creates two new players and add them to the game's
     * player list. Used for starting a new game.
     */
    public void setNewPlayers(String name1, String name2){
        // Create a new black and white player
        Player playerBlack;
        Player playerWhite;
        if(name1 != null) {
            if(name1.isEmpty()) {
                playerWhite = new Player(GameColor.WHITE, "WHITE");
            } else {
                playerWhite = new Player(GameColor.WHITE,name1);
            }
        } else {
            playerWhite = new Player(GameColor.WHITE, "WHITE");
        }

        if(name2 != null) {
            if(name2.isEmpty()) {
                playerBlack = new Player(GameColor.BLACK, "BLACK");
            } else {
                playerBlack = new Player(GameColor.BLACK,name2);
            }
        } else {
            playerBlack = new Player(GameColor.BLACK,"BLACK");
        }
        // Get the current list of players
        List<Player> playerList = getPlayers();

        if(!playerList.isEmpty()){
            playerList.clear(); // Clearing the current players from the list
        }
        // Adding the new players to the list.
        playerList.add(playerWhite);
        playerList.add(playerBlack);
        setPlayers(playerList); // Sets the StateEditor's player list
        gameState.setCurrPlayer(playerWhite); // Sets white as the player to make the 1st move
    }

    /**
     * A method that switches between two players. White moves first,
     * then black. We'll assume we have 2 players, with the first player
     * in the player list playing as white, and the second player playing as black.
     */
    public void switchCurrentPlayer(){
        // Getting the players, and the current player
        List<Player> players = getPlayers();
        Player playerWhite = players.get(0);
        Player playerBlack = players.get(1);
        Player currPlayer = gameState.getCurrPlayer();

        // If the current player hasn't been set, set the current player to white
        if(currPlayer == null) {
            gameState.setCurrPlayer(playerWhite);
        }
        else {
            GameColor currentPlayerColor = currPlayer.getColor();// Get the player's color
            // If the current player is playing black, change the new current player to white
            if (currentPlayerColor.equals(GameColor.BLACK)) {
                gameState.setCurrPlayer(playerWhite);

            } // If the current player is playing white, change the new current player to black
            else if (currentPlayerColor.equals(GameColor.WHITE)) {
                gameState.setCurrPlayer(playerBlack);
            }
        }
    }

    /**
     * A method that goes back one previous game state,
     * if the state exist, and sets that as the current game state
     * @param stateIndex - An integer representing how many states we'd like to go back.
     * @return True if we've moved back to a different state, otherwise false.
     */
    public boolean undo_move(int stateIndex){
        // Setting the new state index
        int newStateIndex = currStateIndex - stateIndex;

        // If the currStateIndex is less than 0, we have no more states to go back to.
        if(newStateIndex < 0 || newStateIndex > stateHistory.size() - 1){
            System.out.println("Can't go back that far!");
            return false;
        }
        // Restoring the old state
        gameState.restore(stateHistory.get(newStateIndex));
        return true;

    }

    /**
     * A method that goes back one previous game state,
     * if the state exist, and sets that as the current game state.
     * @return True if we've forward back to a different state, otherwise false.
     */
    public boolean redo_move(int stateIndex){
        // Setting the new state index
        int newStateIndex = stateIndex + currStateIndex;

        // If the newStateIndex is greater than our stateHistory size, then
        // we have no more states ahead of us to get.
        if(newStateIndex < 0 || newStateIndex > stateHistory.size() - 1){
            System.out.println("Can't go that far ahead!");

            return false;
        }
        if(gameState.getMoveList().size() == stateHistory.get(newStateIndex).getMoveList().size()){
            System.out.println("Can't go that far ahead!");
            return false;
        }

        // Get the new state and restore it
        gameState.restore(stateHistory.get(newStateIndex));
        return true;
    }

    /**
     * A method that calls undo once, setting us back one game state.
     * @return True if we've moved back one state, otherwise false.
     */
    public boolean undo_once(){
        boolean undid = false;
        if(this.maxUndos != 0){

            undid = undo_move(1);
            // If we've moved back a state, decrement our state index (in context of our state history)
            if(undid){
                decrementStateIndex();
                maxUndos--;
            }

        }else{
            System.out.println("Maximum undos reached");
        }
        return undid;
    }

    /**
     * A method that calls redo once, setting us ahead one game state.
     * @return True if we've moved forward one state, otherwise false.
     */
    public boolean redoOnce(){
        boolean redid = redo_move(1);
        // If we've moved forward, increment our state index (in context of our state history)
        if(redid == true){
            incrementStateIndex();
            maxUndos++;
            // If we're back at the top of our list
            if(currStateIndex == stateHistory.size() - 1){
                redoBugFix();
            }
        }
        return redid;
    }

    /**
     * Resorts the state history to it's correct order, only used if a player makes
     * a move from a previous game state. A method specifically meant to fix a bug
     * encountered while undoing/redoing. Though this method seems confusing, it
     * is the result of 8+ HOURS OF DEBUGGING, and so far, has fixed the bug.
     */
    public void sortStateHistory(){

        GameState gameState = getGameState();
        List<GameState.State> stateHistory = getStateHistory();
        // If the player has undone to a previous state, get a sublist of all the
        // states up until the current state we're in.
        List<GameState.State> subHistory = stateHistory.subList(0, getCurrStateIndex());
        // Insert the current gameState snapshot into our history
        subHistory.add(getCurrStateIndex(), gameState.takeSnapshot());
        // Setting the subHistory as our new state history
        setStateHistory(subHistory);
        //incrementStateIndex();
        // Setting the new size of our state history.
        setCurrStateIndex(subHistory.size() - 1);
    }

    public void redoBugFix(){
            GameState gameState = getGameState();
            List<GameState.State> stateHistory = getStateHistory();
            stateHistory.add(gameState.clone().takeSnapshot());
    }

    /**
     * Gets the current player of the state we're currently in.
     * @return The player whose turn it currently is.
     */
    public Player getCurrentPlayer(){
        GameState currState = getGameState();
        return currState.getCurrPlayer();
    }

    /**
     * Gets the opposite player of the state we're currently in.
     * @return The player whose turn it currently is not.
     */
    public Player getOppositePlayer(){
        GameState currState = getGameState();
        if(this.players.get(0) == currState.getCurrPlayer()) {
            return this.players.get(1);
        }
        return this.players.get(0);
    }


    /**
     * Returns the current state from our state history list (not
     * the CURRENT current state, the state we're in after undos/redos).
     * @return The current state of our game.
    */
    public GameState.State getCurrentState(){
        return stateHistory.get(currStateIndex);
    }

    /**
     * Gets the player playing this game.
     * @return players
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * Sets the player list.
     * @param players - The list of players playing this game.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setPlayers(String name1, String name2){
        Player p1 = new Player(GameColor.WHITE,name1);
        if(!name1.isEmpty()) {
            Settings.getOptions().setWhiteName(name1);
        }

        Player p2 = new Player(GameColor.BLACK,name2);
        if(!name2.isEmpty()) {
            Settings.getOptions().setBlackName(name2);
        }

        List<Player> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        setPlayers(list);

    }

    /**
     * Returns the current index we're in our state history.
     * @return currStateIndex
     */
    public int getCurrStateIndex(){
        return this.currStateIndex;
    }

    /**
     * Gets the game state.
     * @return gameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the current state index in our history of states.
     * @param index - Where we are in the state history.
     */
    public void setCurrStateIndex(int index) {
        this.currStateIndex = index;
    }

    /**
     * Returns the game's board.
     * @return boardState
     */
    public BoardIF getBoard(){
        return gameState.boardState;
    }

    /**
     * Sets the state history of our game.
     * @param stateHistory - A list of sequential states.
     */
    public void setStateHistory(List<GameState.State> stateHistory){
        this.stateHistory = stateHistory;
    }

    /**
     * Sets the board for the game.
     * @param board - The chess board.
     */
    public void setBoard(BoardIF board){
        gameState.boardState = board;
    }

    /**
     * Returns the history of our states.
     * @return stateHistory
     */
    public List<GameState.State> getStateHistory() {
        return this.stateHistory;
    }

    /**
     * Increments the stateHistory's index by 1.
     */
    public void incrementStateIndex(){
        this.currStateIndex = this.currStateIndex + 1;
    }

    /**
     * Decrements the stateHistory's index by 1.
     */
    public void decrementStateIndex(){
        this.currStateIndex = this.currStateIndex - 1;
    }

    /**
     * Decrements the stateHistory's index by 1.
     */
    public void setMaxUndos(int maxUndos){
        this.maxUndos = maxUndos;
    }

    /**
     * Called when we're loading in a file and want to clear the current
     * State editor's data.
     */
    public void clearStateEditor(){
        players.clear();
        stateHistory.clear();
        gameState = null;
        gameState = new GameState();
        currStateIndex = 0;
    }

    /**
     * Returns a boolean value representing whether
     * the players have undone/redone to a previous state.
     * @return False if we haven't shifted, otherwise true.
     */
    public boolean haveShifted(){
        return stateHistory.get(stateHistory.size()- 1) != getCurrentState();
    }

//    public void printHistory(){
//        for(int i = 0; i < stateHistory.size() - 1; i++){
//            System.out.println(stateHistory.get(i).getMoveList().size());
//        }
//        System.out.println("----------------------");
//    }


}
