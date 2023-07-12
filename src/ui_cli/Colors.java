package ui_cli;

/**
 * A class to hold color values for manipulating the chess board.
 * @author Daniel Aoulou 100%
 */
public class Colors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK

    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold

    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN


    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE


    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE



    public static final String WHITE_PAWN = "\u2659";   // WHITE PAWN
    public static final String WHITE_ROOK = "\u2656";   // WHITE PAWN
    public static final String WHITE_KNIGHT = "\u2658";   // WHITE PAWN
    public static final String WHITE_BISHOP = "\u2657";   // WHITE PAWN
    public static final String WHITE_QUEEN = "\u2655";   // WHITE PAWN
    public static final String WHITE_KING = "\u2654";   // WHITE PAWN

    public static final String BLACK_PAWN = "\u265F";   // BLACK PAWN
    public static final String BLACK_ROOK = "\u265C";   // BLACK PAWN
    public static final String BLACK_KNIGHT = "\u265E";   // BLACK PAWN
    public static final String BLACK_BISHOP = "\u265D";   // BLACK PAWN
    public static final String BLACK_QUEEN = "\u265B";   // BLACK PAWN
    public static final String BLACK_KING = "\u265A";   // BLACK PAWN

    /**
     * Formats text to be highlighted cyan. Good for displaying commands.
     * @param c - the text to format
     * @return the formatted text
     */
    public static String format_command(String c) {
        return (CYAN_BOLD + c + RESET);
    }

}
