package main.evaluators;

import main.evaluators.EvaluationFunction;

import java.util.Random;

/**
 * Created by NSPACE on 11/21/2016.
 */
public class RandomEvaluator implements EvaluationFunction {
    Random rand = new Random();

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy, int turn) {
        return rand.nextInt(100);
    }
}
