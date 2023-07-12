package enums;
/**
 * An enumeration for modeling chess pieces.
 *
 * @author Logan Beaver(33%), Josiah Cherbony(33%), Daniel Aoulou(33%)
 * @version 3/16/23
 */
public enum ChessPieceType {
    /** A king in chess */
    KING('K', "King"),
    /** A queen in chess */
    QUEEN('Q', "Queen"),
    /** A rook in chess */
    ROOK('R', "Rook"),
    /** A bishop in chess */
    BISHOP('B', "Bishop"),
    /** A knight in chess */
    KNIGHT('N', "Knight"),
    /** A pawn in chess */
    PAWN('p', "Pawn");

    /**
     * A piece's type abbreviation
     */
    private final char Abbrv;

    /**
     * The full name of a piece
     */
    private final String Name;

    /**
     * Constructor for a ChessPieceType enum
     * @param Abbrv - the abbreviation for the enum
     * @param Name - the name of the enum
     */
    ChessPieceType(char Abbrv, String Name) {
        this.Abbrv = Abbrv;
        this.Name = Name;
    }

    /**
     * get the name of a chess piece
     * @return name - the name of the enum
     */
    public String getName() {

        return Name;
    }

    /**
     * get the abbreviation of a chess piece
     * @return the abbreviation of the enum
     */
    public char getAbbrv() {
        return Abbrv;
    }
}

