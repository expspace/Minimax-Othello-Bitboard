package main.v1.searchstrategy;

/**
 * Created by NSPACE on 11/22/2016.
 */
public interface SearchStrategy {

    /**
     * Performs chosen search strategy in order to find and execute the next move.
     *
     * @return updated board after found move is performed
     */

     long[] performNextMove(long bbSelf, long bbEnemy, int turn);
}
