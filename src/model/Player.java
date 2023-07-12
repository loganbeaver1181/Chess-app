package model;

import enums.GameColor;

/**
 * A class that models a player in a game of chess.
 *
 * @author Josiah Cherbony (100%)
 * @version 3/23/23
 */
public class Player extends BlackAndWhite {

    /** The player's name */
    String name = null;

    /**
     * Constructor for color object.
     *
     * @param color - Black or white.
     */
    public Player(GameColor color) {
        super(color);
    }

    /**
     * Constructor for color object.
     *
     * @param color - Black or white.
     * @param name - The name of the player.
     */
    public Player(GameColor color, String name) {
        super(color);
        this.name = name;
    }

    /**
     * Gets the player's name.
     * @return Name of the player.
     */
    public String getName() {
        if(name == null){ // If the player doesn't have a set name, return its color
            return this.getColor().name();
        }else{
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }
}
