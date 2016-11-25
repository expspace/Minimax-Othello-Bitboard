/**
 * Created by NSPACE on 11/23/2016.
 */
public class FrontierEvaluator implements EvaluationFunction {
    BoardOperations bbOps;

    public FrontierEvaluator(BoardOperations bbOps) {
        this.bbOps = bbOps;
    }

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy) {
        return getNumFrontier(bbEnemy,bbSelf) - getNumFrontier(bbSelf,bbEnemy);
    }

    private int getNumFrontier(long bbSelf, long bbEnemy) {
        long frontier = 0L;
        long empty = ~(bbSelf | bbEnemy);
        long matched;

        //NORTH
        matched = bbOps.shiftN(bbSelf) & empty;
        frontier |= bbOps.shiftS(matched);

        //SOUTH
        matched = bbOps.shiftS(bbSelf) & empty;
        frontier |= bbOps.shiftN(matched);

        //WEST
        matched = bbOps.shiftW(bbSelf) & empty;
        frontier |= bbOps.shiftE(matched);

        //EAST
        matched = bbOps.shiftE(bbSelf) & empty;
        frontier |= bbOps.shiftW(matched);

        //NORTHWEST
        matched = bbOps.shiftNW(bbSelf) & empty;
        frontier |= bbOps.shiftSE(matched);

        //NORTHEAST
        matched = bbOps.shiftNE(bbSelf) & empty;
        frontier |= bbOps.shiftSW(matched);

        //SOUTHWEST
        matched = bbOps.shiftSW(bbSelf) & empty;
        frontier |= bbOps.shiftNE(matched);

        //SOUTHEAST
        matched = bbOps.shiftSE(bbSelf) & empty;
        frontier |= bbOps.shiftNW(matched);

        return Long.bitCount(frontier);
    }
}
