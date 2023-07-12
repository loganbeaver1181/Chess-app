package model;

import enums.File;
import enums.Rank;


/**
 * A class that represents a position on a chess board
 * @author Logan Beaver(100%)
 * @version 3/15/23
 */
public class Position {

    /** the rank (x) of the position */
    private Rank rank;

    /** the file (y) of the postion */
    private File file;

    /**
     * retrieve the rank of the position
     * @return the rank
     */
    public Rank getRank() {

        return rank;
    }


    /**
     * set the rank of the position
     * @param rank - the new rank
     */
    public void setRank(Rank rank) {

        this.rank = rank;
    }

    /**
     * retrieve the file of the position.
     * @return the file of position
     */
    public File getFile() {

        return file;
    }


    /**
     * Set the file of the position
     * @param file - the new file
     */
    public void setFile(File file) {

        this.file = file;
    }

    /**
     * Constructor for a position object
     * @param rank - the row
     * @param file - the column
     */
    public Position(Rank rank, File file){
        this.rank = rank;
        this.file = file;
    }

    /**
     * Constructor for a position object
     * @param rank1 - Designates the rank we're setting our position to
     * @param file1 - Designates the file we're setting our position to
     */
    public Position(String rank1, String file1){
        Rank rank = Rank.getRankByIndex(rank1);
        File file = File.getFileByIndex(file1);
        this.rank = rank;
        this.file = file;
    }


    /**
     * compare to position objects to see if they are the same.
     * @param object - position to compare
     * @return -1, 0 , 1
     */
    public boolean equals(Object object){
        boolean equal = false;
        if(object instanceof Position){
            Position otherPos = (Position) object;
            if(otherPos.file.getFile() == this.file.getFile() &&
                    otherPos.rank.getRank() == this.rank.getRank()){
                equal = true;
            }
        }
        return equal;
    }

    /**
     * create string representation of position.
     * @return - position in string form.
     */
    @Override
    public String toString() {
        return "Rank: " + this.getRank() + ", File: " + this.getFile();
    }

    /**
     * Returns a rank and file in the format "A1", "H7", "D3", etc.
     * @return an abbreviated rank and file
     */
    public String getPositionAbbv(){
        return file.getFile() + rank.toString();
    }

    public Position clone(){
        return new Position(this.getRank(), this.getFile());
    }
}