package main.drivers;

import main.EvaluationFunction;
import main.PositionalEvaluator;

/**
 * Created by NSPACE on 11/20/2016.
 */
public class DriverPositional {
//    O O O O O O O O
//    O O O O O O O O
//    O O O W B O O O
//    O O O B B O O O
//    O O B B B O O O
//    O O O O O O O O
//    O O O O O O O O
//    O O O O O O O O

    public static void main(String[] args) {
        long[] bitboards = {
                0b00000000_00000000_00001000_00000000_00000000_00000000_00000000_00000000L, //initial black stones
                0b00000000_00000000_00010000_00011000_00111000_00000000_00000000_00000000L //initial white stones
        };

        EvaluationFunction positionalEvaluator = new PositionalEvaluator();

        int posEvaluationVal = positionalEvaluator.evaluateBoard(bitboards[1],bitboards[0]);

        System.out.println(posEvaluationVal); //assert 4

    }

}
