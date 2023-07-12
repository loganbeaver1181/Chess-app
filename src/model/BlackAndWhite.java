package model;

import enums.GameColor;
import interfaces.BlackAndWhiteIF;

/**
 * A class to represent the two colors that a square and a piece can be.
 * @author Logan Beaver(100%)
 * @version 3/15/23
 */
public class BlackAndWhite implements BlackAndWhiteIF {

    /** either black or white */
    private GameColor color;

    /**
     * Constructor for color object
     * @param color - black or white
     */
    public BlackAndWhite(GameColor color){

        this.color = color;
    }

    /**
     * retrieve the color of the object
     * @return the color
     */
    public GameColor getColor() {

        return color;
    }

    /**
     * set the color of the object
     * @param color the new color
     */
    public void setColor(GameColor color){

        this.color = color;
    }

    /**
     * check if the objects color is black
     * @return true or false
     */
    public boolean isBlack(){
        if(color == GameColor.BLACK){
            return true;
        }
        return false;
    }

    /**
     * Check if the objects color is white
     * @return true or false
     */
    public boolean isWhite(){
        if(color == GameColor.WHITE){
            return true;
        }
        return false;
    }

}