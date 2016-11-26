package main;

/**
 * Created by NSPACE on 11/23/2016.
 */
public class CombinationEvaluator implements EvaluationFunction {

    private MobilityEvaluator mobilityEvaluator;
    private PositionalEvaluator positionalEvaluator;
    private FrontierEvaluator frontierEvaluator;

    //TODO figure out best weights
    //weights used for linear combination of utility values
    private final int MOBILITY_WEIGHT = 4;
    private final int POSITIONAL_WEIGHT = 1;
    private final int FRONTIER_WEIGHT = 3;

    public CombinationEvaluator(BoardOperations bbOps) {
        this.mobilityEvaluator = new MobilityEvaluator(bbOps);
        this.positionalEvaluator = new PositionalEvaluator();
        this.frontierEvaluator = new FrontierEvaluator(bbOps);
    }

    /**
     * Linear combination of several heuristics each adjusted by weights
     */

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy) {
        int mobilityValue = mobilityEvaluator.evaluateBoard(bbSelf,bbEnemy);
        int normMobilityValue = normalize(mobilityValue);

        int positionalValue = positionalEvaluator.evaluateBoard(bbSelf,bbEnemy);
        int normPositionalValue = normalize(positionalValue);

        int frontierValue = frontierEvaluator.evaluateBoard(bbSelf,bbEnemy);
        int normFrontierValue = normalize(frontierValue);

        return MOBILITY_WEIGHT * normMobilityValue + POSITIONAL_WEIGHT * normPositionalValue + FRONTIER_WEIGHT * normFrontierValue;
    }

    //TODO how to normalize?? use max/min of eval?
    private int normalize(int value) {
        return 0;
    }
}
