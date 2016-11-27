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

    private int maxValue;
    private int minValue;

    public CombinationEvaluator(BoardOperations bbOps) {
        this.mobilityEvaluator = new MobilityEvaluator(bbOps);
        this.positionalEvaluator = new PositionalEvaluator();
        this.frontierEvaluator = new FrontierEvaluator(bbOps);
    }

    /**
     * Linear combination of several heuristics each adjusted by weights
     */

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy, int turn) {
        int mobilityValue = mobilityEvaluator.evaluateBoard(bbSelf,bbEnemy, turn);
        double normMobilityValue = normalize(mobilityValue);

        int positionalValue = positionalEvaluator.evaluateBoard(bbSelf,bbEnemy, turn);
        double normPositionalValue = normalize(positionalValue);

        int frontierValue = frontierEvaluator.evaluateBoard(bbSelf,bbEnemy, turn);
        double normFrontierValue = normalize(frontierValue);

        setMaxValue(mobilityValue, positionalValue, frontierValue);
        setMinValue(mobilityValue, positionalValue, frontierValue);

        return calculateCombineValue(normMobilityValue, normPositionalValue, normFrontierValue);
    }

    //TODO how to normalize?? use max/min of eval?
    private double normalize(int value) {

        return ((double)value - minValue) / ( maxValue - minValue);
    }

    private void setMaxValue(int val1, int val2, int val3)
    {
       this.maxValue = Math.max(val1, Math.max(val2, val3));
    }

    private void setMinValue(int val1, int val2, int val3)
    {
        this.minValue = Math.min(val1, Math.min(val2, val3));
    }

    private int calculateCombineValue(double normMobilityValue, double normPositionalValue, double normFrontierValue)
    {
        Double result  = MOBILITY_WEIGHT * normMobilityValue + POSITIONAL_WEIGHT * normPositionalValue + FRONTIER_WEIGHT * normFrontierValue;

        return  result.intValue();
    }

}
