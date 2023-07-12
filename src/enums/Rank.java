package enums;

/**
 * An enumeration for modeling Rank in chess
 *
 * @author Logan Beaver(33%), Josiah Cherbony(33%), Daniel Aoulou(33%)
 * @version 3/16/23
 */
public enum Rank{

    /** rank 1 in chess */
    R1(1,0),

    /** rank 2 in chess */
    R2(2,1),

    /** rank 3 in chess */
    R3(3,2),

    /** rank 4 in chess */
    R4(4,3),

    /** rank 5 in chess */
    R5(5,4),

    /** rank 6 in chess */
    R6(6,5),

    /** rank 7 in chess */
    R7(7,6),

    /** rank 8 in chess */
    R8(8,7);


    /** initialize the rank value */
    private final int rank;

    /** initialize the programmer value */
    private final int row;

    /**
     * constructor for a Rank enum
     * @param rank - the rank starting at 1
     * @param row - the rank starting at 0
     */
    Rank(int rank, int row){
        this.rank = rank;
        this.row = row;
    }

    /**
     * get the rank of the enum
     * @return the rank
     */
    public int getRank(){

        return this.rank;
    }

    /**
     * get the row of the enum
     * @return the row
     */
    public int getRow(){

        return this.row;
    }

    /**
     * Returns a rank matching it with the specified index
     * @param index - The index of the rank we're getting
     * @return a rank
     */
    public static Rank getRankByIndex(String index){
        Rank rank;
        switch(index){
            case "1": rank = Rank.R1;
                break;
            case "2": rank = Rank.R2;
                break;
            case "3": rank = Rank.R3;
                break;
            case "4": rank = Rank.R4;
                break;
            case "5": rank = Rank.R5;
                break;
            case "6": rank = Rank.R6;
                break;
            case "7": rank = Rank.R7;
                break;
            case "8": rank = Rank.R8;
                break;
            default:
                rank = null;
                break;
        }
        return rank;
    }

    @Override
    public String toString(){
        return String.valueOf(this.rank);
    }


}