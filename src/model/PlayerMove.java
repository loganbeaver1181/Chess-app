package model;

/**
 * A class for modeling a player move. Which includes the player
 * who made the move, the placed they moved from, and the place they moved to.
 * @author Josiah Cherbony(100%)
 * @version 4/2/23
 */
public class PlayerMove {

    /** The player who made the move */
    Player player;

    /** The position a piece came from. */
    Position fromPosition;

    /** The position a piece moved to. */
    Position toPosition;

    /**
     * A constructor for a player's move. Has a player, the placed they moved from,
     * and the place they moved to.
     * @param player - The player who made the move.
     * @param fromPosition - The position a piece came from.
     * @param toPosition - The position a piece moved to.
     */
    public PlayerMove(Player player, Position fromPosition, Position toPosition){
        this.player = player;
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    /**
     * Gets the player
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the fromPosition
     * @return fromPosition
     */
    public Position getFromPosition() {
        return fromPosition;
    }

    /**
     * Gets the toPosition
     * @return toPosition
     */
    public Position getToPosition() {
        return toPosition;
    }

    /**
     * Returns a cloned playerMove object.
     * @return a cloned playerMove object.
     */
    public PlayerMove clone(){
        return new PlayerMove(this.player, this.fromPosition, this.toPosition);
    }
}
