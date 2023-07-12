package enums;
/**
 * An enumeration for modeling the color of chess pieces
 *
 * @author Josiah Cherbony(50%), Daniel Aoulou(50%)
 * @version 3/16/23
 */
public enum GameColor {
    /** the color black */
    BLACK("black"),
    /** the color white */
    WHITE("white");

    /**
     * field for the name of a color
     */
    private final String name;

    /**
     * constructor for a gamecolor enum
     * @param name - the name of the color
     */
    GameColor(String name){
        this.name = name;
    }

    /**
     * return the name of the gamecolor
     * @return the name of the color
     */
    public String toString() {
        return this.name;
    }
}