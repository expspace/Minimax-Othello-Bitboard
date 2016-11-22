import java.util.Scanner;

/**
 * Created by NSPACE on 11/18/2016.
 */
public class DriverGreedy {
    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);

        long[] bitboards = {
                0b00000000_00000000_00000000_00010000_00001000_00000000_00000000_00000000L, //initial black stones
                0b00000000_00000000_00000000_00001000_00010000_00000000_00000000_00000000L //initial white stones
        };

        int turn = 0;
        int numMovesPOne = 4;
        int numMovesPTwo = 4;

        BitboardHelper.bbPrint(bitboards[0], bitboards[1]);

        BoardOperations bbOps = new BoardOperations();
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(bbOps);

        do {
            System.out.println("TURN: " + turn);

            if(turn % 2 == 0) {
                System.out.println("BLACKS TURN");
                long moves = bbOps.generateMoves(bitboards[0], bitboards[1]);
                numMovesPOne = Long.bitCount(moves);
                System.out.println("NUM MOVES: " + numMovesPOne);
                System.out.println("CHOOSES MOVE: ");
                bitboards = greedyAlgorithm.performNextMove(bitboards[0], bitboards[1],turn);
                BitboardHelper.bbPrint(bitboards[0], bitboards[1]);

            } else {
                System.out.println("WHITES TURN");
                long moves = bbOps.generateMoves(bitboards[1], bitboards[0]);
                numMovesPTwo = Long.bitCount(moves);
                System.out.println("NUM MOVES: " + numMovesPTwo);
                System.out.println("CHOOSES MOVE: ");
                bitboards = greedyAlgorithm.performNextMove(bitboards[1], bitboards[0],turn);
                BitboardHelper.bbPrint(bitboards[0], bitboards[1]);
            }

            turn++;
            kb.nextInt(); //pauses iteration

        } while(!bbOps.gameOver(bitboards[0],bitboards[1],numMovesPOne,numMovesPTwo));

        BitboardHelper.printWinner(bitboards[0],bitboards[1]);
    }
}
