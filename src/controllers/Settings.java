package model;

public class Settings {

    /** Whether we show the potential moves when clicking on a piece */
    private boolean show_moves;

    /** The color of the black squares in the chess game */
    private int[] blackSquareColor;

    /** The color of the white squares in the chess game */
    private int[] whiteSquareColor;

    /** Whether undos are enabled in the game */
    private boolean undosEnabled;

    /** Whether the players can undo an unlimited amount of times */
    private boolean unlimitedUndos;

    /** The maximum amount of undos / redos the player can make */
    private int undoMax;

    /**an instance of an options class*/
    private static Settings settings = null;

    /** A value representing having no limit on undos in the game */
    int NOLIMIT = 999;

    private String whiteName = "WHITE";
    private String blackName = "BLACK";

    /**
     * A constructor for Options
     */
    public Settings(){
        // By default, we will show moves to the player when they click on a piece
       show_moves = true;
       // By default, we enable undos
       undosEnabled = true;
       // By default set unlimited undos to true
       unlimitedUndos = true;
       // By default, set no limit for the maximum amount of undos
       undoMax = NOLIMIT;
       // Sets the black and white square color to their default values
       blackSquareColor = new int[]{192, 192, 192};
       whiteSquareColor = new int[]{255, 255, 255};
    }

    /**
     * Get the options object currently in use
     * @return a settings instance object
     */
    public static Settings getOptions() {
        if(settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    /**
     * Returns a boolean value representing whether we display potential moves
     * when clicking on a piece in our Chess game.
     * @return show_moves
     */
    public boolean getShowMoves() {
        return show_moves;
    }

    /**
     * Sets a boolean value representing whether we display potential moves
     * when clicking on a piece in our Chess game.
     * @param show_moves - Whether we display potential piece moves in the game.
     */
    public void setShowMoves(boolean show_moves) {
        this.show_moves = show_moves;
    }

    /**
     * Returns a list of integers representing the color of black squares
     * in the chess game.
     * @return blackSquareColor
     */
    public int[] getBlackSquareColor() {
        return blackSquareColor;
    }

    /**
     * Sets the color of our game's black squares.
     * @param red - An int value representing a red color value
     * @param green - An int value representing a green color value
     * @param blue - An int value representing a blue color value
     */
    public void setBlackSquareColor(int red, int green, int blue) {
        this.blackSquareColor = new int[]{red, green, blue};
    }

    /**
     * Returns a list of integers representing the color of white squares
     * in the chess game.
     * @return whiteSquareColor
     */
    public int[] getWhiteSquareColor() {
        return whiteSquareColor;
    }

    /**
     * Sets the color of our game's white squares.
     * @param red - An int value representing a red color value
     * @param green - An int value representing a green color value
     * @param blue - An int value representing a blue color value
     */
    public void setWhiteSquareColor(int red, int green, int blue) {
        this.whiteSquareColor = new int[]{red, green, blue};
    }

    /**
     * Returns a boolean value representing if undos are enabled in the game.
     * @return undosEnabled
     */
    public boolean getUndosEnabled() {
        return undosEnabled;
    }

    /**
     * Sets a boolean value representing if undos are enabled in the game.
     * @param undosEnabled - A boolean value representing if undos are enabled in the game
     */
    public void setUndosEnabled(boolean undosEnabled) {
        this.undosEnabled = undosEnabled;
    }

    /**
     * Returns an int value representing the maximum amount of undos in the game.
     * @return undoMax
     */
    public int getUndoMax() {
        return undoMax;
    }

    /**
     * Sets an int value representing the maximum amount of undos in the game.
     * @param undoMax the maximum undos enabled
     */
    public void setUndoMax(int undoMax) {
        this.undoMax = undoMax;
    }

    /**
     * Returns a boolean value representing if we have unlimited undos in the game.
     * @return unlimitedUndos
     */
    public boolean getUnlimitedUndos() {
        return unlimitedUndos;
    }

    /**
     * Set a boolean value representing if we have unlimited undos in the game.
     * @param unlimited - A boolean value representing if we have unlimited undos in the game
     */
    public void setUnlimitedUndos(boolean unlimited){
        // If we set unlimited undos to true, set the undos max to no limit
        if(unlimited == true){
            setUndoMax(NOLIMIT);
        }
        unlimitedUndos = unlimited;
    }

    public String getWhiteName() {
        return this.whiteName;
    }
    public String getBlackName() {
        return this.blackName;
    }

    public void setWhiteName(String name) {
        whiteName = name;
    }
    public void setBlackName(String name) {
        blackName = name;
    }

    public void printSettings(){
        System.out.println(show_moves);
        System.out.println(blackSquareColor[0]);
        System.out.println(blackSquareColor[1]);
        System.out.println(blackSquareColor[2]);
        System.out.println(whiteSquareColor[0]);
        System.out.println(whiteSquareColor[1]);
        System.out.println(whiteSquareColor[2]);
        System.out.println(undosEnabled);
        System.out.println(undoMax);
        System.out.println(unlimitedUndos);
    }
}
