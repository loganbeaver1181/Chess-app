package interfaces;

import enums.GameColor;

/**
 * An interface to define the methods needed to distinguish
 * between black and white chess pieces on a chess board.
 * @author Logan Beaver(100%)
 */
public interface BlackAndWhiteIF{

    /**
     * Get a game color
     * @return a game color
     */
    public GameColor getColor();

    /**
     * Check if an object is black
     * @return true/false
     */
    public boolean isBlack();

    /**
     * Check if an object is white
     * @return true/false
     */
    public boolean isWhite();

}