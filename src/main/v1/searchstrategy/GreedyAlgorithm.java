package main.v1.searchstrategy;

import main.v1.BoardOperations;

import java.util.ArrayList;

/**
 * Created by NSPACE on 11/22/2016.
 */
public class GreedyAlgorithm implements SearchStrategy {

    private BoardOperations boardOperations;

    public GreedyAlgorithm(BoardOperations boardOperations) {
        this.boardOperations = boardOperations;
    }

    /**
     * Search strategy using a greedy hill-climbing algorithm that always maximizes the
     * number of reversals in each move.
     *
     * @return updated board after performing greedy move; same board if no moves possible
     */

    @Override
    public long[] performNextMove(long bbSelf, long bbEnemy, int turn) {

        ArrayList<long[]> childBitBoards = boardOperations.getChildBitboards(bbSelf, bbEnemy, turn);

        //return same board and exit if no moves can be made
        if (childBitBoards.size() == 0) {
            if (turn % 2 == 0) {
                return new long[]{bbSelf, bbEnemy};
            } else {
                return new long[]{bbEnemy, bbSelf};
            }
        }

        long[] maxBitBoard = new long[2];
        int maxDiskCount = 0;

        //count max number of disks accrued in each child board; return largest updated one
        for(long[] childBitBoard : childBitBoards) {
            long selfBoard = childBitBoard[turn & 1];
            if (Long.bitCount(selfBoard) > maxDiskCount) {
                maxDiskCount = Long.bitCount(selfBoard);
                maxBitBoard = childBitBoard;
            }
        }

        return maxBitBoard;
    }
}
