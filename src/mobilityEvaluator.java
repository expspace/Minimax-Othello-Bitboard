/**
 * Created by NSPACE on 11/20/2016.
 */
public class mobilityEvaluator implements EvaluationFunction{
    BitBoardOps bbOps = new BitBoardOps();

    /**
     * Mobility heuristic which returns the difference between the number of moves the current player
     * can make and the number of moves the enemy can make.
     */

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy) {
        return Long.bitCount(bbOps.generateMoves(bbSelf, bbEnemy)) - Long.bitCount(bbOps.generateMoves(bbEnemy, bbSelf));
    }
}
