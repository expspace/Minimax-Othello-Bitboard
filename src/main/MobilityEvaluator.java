package main;

/**
 * Created by NSPACE on 11/20/2016.
 */
public class MobilityEvaluator implements EvaluationFunction{
    BoardOperations bbOps;

    public MobilityEvaluator(BoardOperations bbOps) {
        this.bbOps = bbOps;
    }

    /**
     * Mobility heuristic which returns the difference between the number of moves the current player
     * can make and the number of moves the enemy can make.
     */

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy, int turn) {
        return Long.bitCount(bbOps.generateMoves(bbSelf, bbEnemy)) - Long.bitCount(bbOps.generateMoves(bbEnemy, bbSelf));
    }
}
