package main.v1.drivers;

import main.v1.BitboardHelper;
import main.v1.BoardOperations;
import main.v1.evaluators.RandomEvaluator;
import main.v1.searchstrategy.AlphaBetaMinimax;

/**
 * Created by NSPACE on 11/17/2016.
 */
public class DriverMinimax {


    public static void main(String[] args) {

        /**
         * othello bitboard encoding - https://github.com/denkspuren/BitboardC4/blob/master/BitboardDesign.md
         *
         * bitboard[0] - player one (black) bitboard
         * bitboard[1] - player two (white) bitboard
         *
         *  63 62 61 60 59 58 57 56
         *  55 54 53 52 51 50 49 48
         *  47 46 45 44 43 42 41 40
         *  39 38 37 36 35 34 33 32
         *  31 30 29 28 27 26 25 24
         *  23 22 21 20 19 18 17 16
         *  15 14 13 12 11 10  9  8
         *   7  6  5  4  3  2  1  0
         */

        //init
        long[] bitboards = {
                0b00000000_00000000_00000000_00010000_00001000_00000000_00000000_00000000L, //initial black stones
                0b00000000_00000000_00000000_00001000_00010000_00000000_00000000_00000000L //initial white stones
        };

        int turn = 0;
        BoardOperations bbOps = new BoardOperations();
//        Minimax minimax = new Minimax(bbOps,new RandomEvaluator());
        AlphaBetaMinimax minimax = new AlphaBetaMinimax(bbOps,new RandomEvaluator());

        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);


        //turn 1
        long moveOne = 0b00000000_00000000_00001000_00000000_00000000_00000000_00000000_00000000L;
        bitboards = bbOps.makeMove(moveOne, bitboards[0], bitboards[1],turn);
        turn++;
        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);
        System.out.println();

        int searchDepth = 2;
        long moveTwo[] = new long[2];

        //MINIMAX BENCHMARK TESTING
        while(searchDepth <= 14) {
            minimax.setSearchDepth(searchDepth);

            long startTime = System.nanoTime();

            moveTwo = minimax.performNextMove(bitboards[1], bitboards[0],turn);

            long estimatedTime = System.nanoTime() - startTime;
            System.out.println("Estimated millisecond execution time: " + estimatedTime / 1000000.0);

            //print depth
            System.out.println("Depth of minimax search: " + minimax.getSearchDepth());
            //print #nodes generated
            System.out.println("Number nodes generated: " + minimax.getNodeCount());
            System.out.println();

            searchDepth++;
            minimax.setNodeCount(0);
        }

        System.out.println("UPDATED BOARD: ");
        System.out.println();
        BitboardHelper.bbPrintA2Format(moveTwo[0], moveTwo[1]);

    }
}
