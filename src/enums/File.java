package enums;

/**
 * An enumeration for modeling File in chess
 *
 * @author Logan Beaver(33%), Josiah Cherbony(33%), Daniel Aoulou(33%)
 * @version 3/16/23
 */
public enum File {

    /** the H file in chess */
    H('H', 7),

    /** the G file in chess */
    G('G', 6),

    /** the F file in chess */
    F('F',5),

    /** the E file in chess */
    E('E', 4),

    /** the D file in chess */
    D('D', 3),

    /** the C file in chess */
    C('C', 2),

    /** the B file in chess */
    B('B', 1),

    /** the A file in chess */
    A('A', 0);

    /** Initializes the file field */
    private final char file;

    /** Initializes the int field associated with the file for traversing the board */
    private final int column;

    /**
     * Construct a new file enum.
     * @param file - The letter representation.
     * @param column - tTe column representation in our 2D array.
     */
    File(char file, int column){
        this.file = file;
        this.column = column;
    }

    /**
     * Retrieve the file value for the enum.
     * @return the file
     */
    public char getFile(){

        return this.file;
    }

    /**
     * Retrieve the column value for the enum.
     * @return the column
     */
    public int getColumn(){

        return this.column;
    }

    /**
     * Returns a file matching it with the specified index
     * @param index - The index of the file we're getting
     * @return a file
     */
    public static File getFileByIndex(String index) {
        index = index.toUpperCase(); // Makes letters uppercase
        File file;
        switch (index) {
            case "0":
            case "A": file = File.A;
                break;
            case "1":
            case "B": file = File.B;
                break;
            case "2":
            case "C": file = File.C;
                break;
            case "3":
            case "D": file = File.D;
                break;
            case "4":
            case "E": file = File.E;
                break;
            case "5":
            case "F": file = File.F;
                break;
            case "6":
            case "G": file = File.G;
                break;
            case "7":
            case "H": file = File.H;
                break;
            default: file = null;
            break;
        }
        return file;
    }
}