/**
 * Created by NSPACE on 11/17/2016.
 */
public class BitboardHelper {

    public static void bbPrint(long bbPOne, long bbPTwo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 63; i >= 0; i--) {
            if (((bbPOne >> i) & 1) > 0) {
                sb.append("B ");
            } else if (((bbPTwo >> i) & 1) > 0) {
                sb.append("W ");
            } else {
                sb.append("O ");
            }

            if (i % 8 == 0) {
                sb.append('\n');
            }
        }
        System.out.println(sb);
    }

    public static void printWinner(long bbPOne, long bbPtwo) {
        System.out.println("GAMEOVER");
        if(Long.bitCount(bbPOne) > Long.bitCount(bbPtwo)) {
            System.out.println("PLAYER ONE (BLACK) - WINS");
        } else if (Long.bitCount(bbPOne) < Long.bitCount(bbPtwo) ){
            System.out.println("PLAYER TWO (WHITE) - WINS");
        } else {
            System.out.println("DRAW");
        }
        System.out.println("Black disk count: " + Long.bitCount(bbPOne));
        System.out.println("White disk count: " + Long.bitCount(bbPtwo));
    }

    //TODO adapter/converter for input (array index to bitboard)
}
