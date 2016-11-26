package main.drivers;

import main.BitboardHelper;
import main.BoardOperations;

/**
 * Created by NSPACE on 11/17/2016.
 */
public class DriverMoves {


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

        long[] bitboards = {
                0b00000000_00000000_00000000_00010000_00001000_00000000_00000000_00000000L, //initial black stones
                0b00000000_00000000_00000000_00001000_00010000_00000000_00000000_00000000L //initial white stones
        };

        int turn = 0;

        BoardOperations bbOps = new BoardOperations();

        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);

        long moves = bbOps.generateMoves(bitboards[0], bitboards[1]);
        long[] potMoves = bbOps.toBitMoveArray(moves);
        System.out.println("POSSIBLE MOVES");
        System.out.println("--------------");
        for(long move : potMoves) {
            BitboardHelper.bbPrint(move, 0);
        }
        System.out.println("--------------");


        long moveOne = 0b00000000_00000000_00001000_00000000_00000000_00000000_00000000_00000000L;
        bitboards = bbOps.makeMove(moveOne, bitboards[0], bitboards[1],turn);
        turn++;

        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);

        moves = bbOps.generateMoves(bitboards[1], bitboards[0]);
        long[] potMoves2 = bbOps.toBitMoveArray(moves);

        System.out.println("POSSIBLE MOVES");
        System.out.println("--------------");
        for(long move : potMoves2) {
            BitboardHelper.bbPrint(0, move);
        }
        System.out.println("--------------");

        //long moveTwo = 0b00000000_00000000_00010000_00000000_00000000_00000000_00000000_00000000L;
        long moveTwo = 0b00000000_00000000_00010000_00000000_00000000_00000000_00000000_00000000L;
        bitboards = bbOps.makeMove(moveTwo,bitboards[1], bitboards[0],turn);
        turn++;

        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);


        long moveThree = 0b00000000_00000000_00000000_00000000_00100000_00000000_00000000_00000000L;
        bitboards = bbOps.makeMove(moveThree, bitboards[0], bitboards[1],turn);
        turn++;

        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);

//        long moveThree = bbOps.makeMaxDiskMove(bitboards[0], bitboards[1],turn);
//        bitboards = bbOps.makeMove(moveThree, bitboards[0], bitboards[1],turn);
//        turn++;
//
//        main.BitboardHelper.bbPrint(bitboards[0], bitboards[1]);



    }
}
