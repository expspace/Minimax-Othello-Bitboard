import java.util.Random;

/**
 * Created by NSPACE on 11/21/2016.
 */
public class randomEvaluator implements EvaluationFunction {
    Random rand = new Random();

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy) {
        return rand.nextInt(100);
    }
}
