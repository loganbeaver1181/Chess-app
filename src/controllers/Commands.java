package controllers;
import ui_cli.Colors;

/**
 * Class for storing a list of commands and their help documentation.
 * Daniel Aoulou (100%)
 */

public class Commands {
    /** start a game */
    public static final String START = "start";

    /** exit application */
    public static final String EXIT = "exit";



    /** Intro string */
    public static final String INTRO = "Welcome to " + Colors.WHITE_BOLD_BRIGHT + "Chess Meister v1.0.0" +
            Colors.RESET + "!\n" +
            "This release of Chess Meister includes many new features that have\n" +
            "been implemented in the latest development sprint. In this version\n" +
            "you'll find:\n\n" +
            "- New options for the display of the command line interface\n" +
            "- New ways to interact with the game\n" +
            "- Undo and redo\n" +
            "- Saving and loading games\n" +
            "- Two player chess functionality\n\n" +
            "To get started with Chess Meister v1.0.0, type `start` and press enter!\n\n";




    /** help for algebraic notation */
    public static final String AN_HELP = """
            Algebraic notation (AN) is used to describe the positions of pieces and squares on the board.
            It is made up of two pieces, the file and rank.\s
            For example, the AN "b3" describes the square at file A and rank 3 on the board.
            Files go from A to H and ranks go from 1 to 8.
            """;
}
