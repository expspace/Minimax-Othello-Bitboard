package main.drivers;

import main.BitboardHelper;
import main.BoardOperations;
import main.GreedyAlgorithm;
import main.HumanSearch;

import java.util.Scanner;

/**
 * Created by NSPACE on 11/18/2016.
 */
public class DriverHuman {
    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);

        long[] bitboards = {
                0b00000000_00000000_00000000_00001000_00010000_00000000_00000000_00000000L, //initial white stones
                0b00000000_00000000_00000000_00010000_00001000_00000000_00000000_00000000L //initial black stones
        };

        int turn = 0;
        int numMovesPOne = 4;
        int numMovesPTwo = 4;
        boolean passTurn = false;
        boolean parentPassTurn = false;

        BitboardHelper.bbPrintForHumans(bitboards[0], bitboards[1]);
        BoardOperations bbOps = new BoardOperations();


        //PLAYER 1 BLACK - HUMAN
        HumanSearch humanSearch = new HumanSearch(bbOps);

        //PLAYER 2 WHITE - COMPUTER
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(bbOps);

        do {
            System.out.println("TURN: " + turn);
            parentPassTurn = passTurn;

            if(turn % 2 == 0) {
                System.out.println("BLACKS TURN");
                long moves = bbOps.generateMoves(bitboards[0], bitboards[1]);
                numMovesPOne = Long.bitCount(moves);
                passTurn = (moves == 0) ? true : false;
                System.out.println("NUM MOVES: " + numMovesPOne);
                BitboardHelper.printAvailableMoves(moves);
                System.out.println("CHOOSES MOVE: ");
                bitboards = humanSearch.performNextMove(bitboards[0], bitboards[1],turn);
                BitboardHelper.bbPrintForHumans(bitboards[0], bitboards[1]);

            } else {
                System.out.println("WHITES TURN");
                long moves = bbOps.generateMoves(bitboards[1], bitboards[0]);
                numMovesPTwo = Long.bitCount(moves);
                passTurn = (moves == 0) ? true : false;
                System.out.println("NUM MOVES: " + numMovesPTwo);
                System.out.println("CHOOSES MOVE: ");
                bitboards = greedyAlgorithm.performNextMove(bitboards[1], bitboards[0],turn);
                BitboardHelper.bbPrintForHumans(bitboards[0], bitboards[1]);
            }

            turn++;

        } while(!bbOps.gameOver(bitboards[0],bitboards[1],passTurn,parentPassTurn));

        BitboardHelper.printWinner(bitboards[0],bitboards[1]);
    }
}
