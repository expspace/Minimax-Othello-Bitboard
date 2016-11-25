/**
 * Created by NSPACE on 11/22/2016.
 */
public class OthelloGame {
    private Player playerOne;
    private Player playerTwo;
    private int numMovesPOne;
    private int numMovesPTwo;
    private BoardOperations boardOperations;
    private int turn;
    private long[] bitboards;

    public OthelloGame(Player playerOne, Player playerTwo, BoardOperations boardOperations) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.boardOperations = boardOperations;
        this.turn = 0;
        this.bitboards = new long[]{
                0b00000000_00000000_00000000_00010000_00001000_00000000_00000000_00000000L, //initial black stones
                0b00000000_00000000_00000000_00001000_00010000_00000000_00000000_00000000L  //initial white stones
        };
    }

    public void start() {
        System.out.println("INITIAL BOARD: ");
        BitboardHelper.bbPrintForHumans(bitboards[0], bitboards[1]);

        do {
            System.out.println("TURN: " + turn);

            if(turn % 2 == 0) {
                System.out.println("PLAYER 1 - BLACKS TURN \n");
                long moves = boardOperations.generateMoves(bitboards[0], bitboards[1]);
                numMovesPOne = Long.bitCount(moves);
                bitboards = playerOne.performNextMove(bitboards[0], bitboards[1],turn);
                System.out.println("MOVE PERFORMED: \n");
                BitboardHelper.bbPrintForHumans(bitboards[0], bitboards[1]);

            } else {
                System.out.println("PLAYER 2 - WHITES TURN \n");
                long moves = boardOperations.generateMoves(bitboards[1], bitboards[0]);
                numMovesPTwo = Long.bitCount(moves);
                bitboards = playerTwo.performNextMove(bitboards[1], bitboards[0],turn);
                System.out.println("MOVE PERFORMED: \n");
                BitboardHelper.bbPrintForHumans(bitboards[0], bitboards[1]);
            }

            turn++;

        } while(!boardOperations.gameOver(bitboards[0],bitboards[1],numMovesPOne,numMovesPTwo));

        BitboardHelper.printWinner(bitboards[0],bitboards[1]);
    }
}
