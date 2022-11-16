package main.evaluators;

import main.BoardOperations;

/**
 * Created by michal wozniak on 11/27/2016.
 */
public class CombinationTurnEvaluator implements EvaluationFunction {

    private MobilityEvaluator mobilityEvaluator;
    private PositionalEvaluator positionalEvaluator;
    private FrontierEvaluator frontierEvaluator;

    //TODO figure out best weights
    //weights used for linear combination of utility values

    private int maxValue;
    private int minValue;

    public CombinationTurnEvaluator(BoardOperations bbOps) {
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

        return calculateCombineValue(normMobilityValue, normPositionalValue, normFrontierValue, turn);
    }


    /**
     * Normalize value to 0-1 range
     *
     * @param value
     * @return
     */
    private double normalize(int value) {

        return ((double)value - minValue) / ( maxValue - minValue);
    }

    private void setMaxValue(int val1, int val2, int val3)
    {
        this.maxValue = Math.max(Math.max(val1,val2), val3);
    }

    private void setMinValue(int val1, int val2, int val3)
    {
        this.minValue = Math.min(Math.min(val1,val2), val3);
    }

    /**
     *  Game Phase (start, mid, end)
     *
     *  Optimize the weight of each heuristic for each phase of the game
     *
     *
     * @param normMobilityValue
     * @param normPositionalValue
     * @param normFrontierValue
     * @param turn
     * @return
     */
    private int calculateCombineValue(double normMobilityValue, double normPositionalValue, double normFrontierValue, int turn)
    {

        double mobilityWeight = 1.0;
        double frontierWeight = 1.0;
        double positionalWeight = 1.0;

      if(turn <= 20)
        {
            mobilityWeight = 4.0;
            frontierWeight = 3.0;
        }
        if(turn > 20 && turn <= 40)
        {

            mobilityWeight = 4.0;
            frontierWeight = 3.6;
        }
        if(turn > 40)
        {
            mobilityWeight = 3.0;
            frontierWeight = 4.0;
        }


        double mobility = mobilityWeight * normMobilityValue;
        double positional = positionalWeight * normPositionalValue;
        double frontier = frontierWeight * normFrontierValue;


        return  (int)(mobility + positional + frontier);
    }
}
