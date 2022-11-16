package main.v1;

import main.v1.searchstrategy.SearchStrategy;

/**
 * Created by NSPACE on 11/22/2016.
 */
public class Player {
    private SearchStrategy searchStrategy;

    public Player(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public long[] performNextMove(long bbSelf, long bbEnemy, int turn) {
        return searchStrategy.performNextMove(bbSelf,bbEnemy,turn);
    }
}
