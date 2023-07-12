package interfaces;

import controllers.Chess;

/**
 * Simple interface that the Main Menu pulls from
 *
 * @author Michael Imerman 100%
 */
public interface guiIF {

    /**
     * Set a game of chess to be represented by a UI or CLI
     * @param chess the chess game to be represented
     */
    void setGame(Chess chess);
}
