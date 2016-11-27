package main.drivers;

import main.*;

/**
 * Created by NSPACE on 11/20/2016.
 */
public class DriverPositional {
//    O O O O O O O O
//    O O O O O O O O
//    O O O B W O O O
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

        BoardOperations boardOperations = new BoardOperations();

        EvaluationFunction positionalEvaluator = new FrontiersEvaluator();
        EvaluationFunction positionalEvaluator2 = new FrontierEvaluator(boardOperations);

        int turn = 0 ; // turn for testing

        //int posEvaluationVal = positionalEvaluator.evaluateBoard(bitboards[1],bitboards[0]);
        int posEvaluationVal_test = positionalEvaluator.evaluateBoard(bitboards[1],bitboards[0], turn);
        int posEvaluationVal_test2 = positionalEvaluator2.evaluateBoard(bitboards[1],bitboards[0], turn);

        System.out.println(posEvaluationVal_test); //assert 4
        System.out.println(posEvaluationVal_test2); //assert 4

    }

}
