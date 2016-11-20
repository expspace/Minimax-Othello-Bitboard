/**
 * Created by NSPACE on 11/20/2016.
 */
public class positionalEvaluator implements EvaluationFunction{

    /**
     * static evaluation table used is taken from reversi program that was included with past versions of Microsoft Windows
     * source : http://www.samsoft.org.uk/reversi/strategy.htm#position
     *
     * 10 distinct square types used for evaluation : s0 - s9
     *
     *      s9 s8 s6 s3 s3 s6 s8 s9
     *      s8 s7 s5 s2 s2 s5 s7 s8
     *      s6 s5 s4 s1 s1 s4 s5 s6
     *      s3 s2 s1 s0 s0 s1 s2 s3
     *      s3 s2 s1 s0 s0 s1 s2 s3
     *      s6 s5 s4 s1 s1 s4 s5 s6
     *      s8 s7 s5 s2 s2 s5 s7 s8
     *      s9 s8 s6 s3 s3 s6 s8 s9
     *
     *
     * crude.
     */

    //bitboard representation of each square type
    private final long s0_BB = 0b00000000_00000000_00000000_00011000_00011000_00000000_00000000_00000000L;
    private final long s1_BB = 0b00000000_00000000_00011000_00100100_00100100_00011000_00000000_00000000L;
    private final long s2_BB = 0b00000000_00011000_00000000_01000010_01000010_00000000_00011000_00000000L;
    private final long s3_BB = 0b00011000_00000000_00000000_10000001_10000001_00000000_00000000_00011000L;
    private final long s4_BB = 0b00000000_00000000_00100100_00000000_00000000_00100100_00000000_00000000L;
    private final long s5_BB = 0b00000000_00100100_01000010_00000000_00000000_01000010_00100100_00000000L;
    private final long s6_BB = 0b00100100_00000000_10000001_00000000_00000000_10000001_00000000_00100100L;
    private final long s7_BB = 0b00000000_01000010_00000000_00000000_00000000_00000000_01000010_00000000L;
    private final long s8_BB = 0b01000010_10000001_00000000_00000000_00000000_00000000_10000001_01000010L;
    private final long s9_BB = 0b10000001_00000000_00000000_00000000_00000000_00000000_00000000_10000001L;

    //numerical weight assigned to each square type
    private final int s0_WEIGHT = 0;
    private final int s1_WEIGHT = 4;
    private final int s2_WEIGHT = -3;
    private final int s3_WEIGHT = 6;
    private final int s4_WEIGHT = 7;
    private final int s5_WEIGHT = -4;
    private final int s6_WEIGHT = 8;
    private final int s7_WEIGHT = -24;
    private final int s8_WEIGHT = -8;
    private final int s9_WEIGHT = 99;

    private final long[] SQUARE_LIST = {s0_BB,s1_BB,s2_BB,s3_BB,s4_BB,s5_BB,s6_BB,s7_BB,s8_BB,s9_BB};
    private final int[] WEIGHT_LIST = {s0_WEIGHT,s1_WEIGHT,s2_WEIGHT,s3_WEIGHT,s4_WEIGHT,s5_WEIGHT,s6_WEIGHT,s7_WEIGHT,s8_WEIGHT,s9_WEIGHT};

    /**
     * Evaluation function which computes its value based on the fixed positional strength of 10 distinct squares.
     * Counts the number of matches a player has with each square type and multiplies by a fixed weight associated with
     * that square type. The sum over all square types for the player is subtracted by same evaluation for the opponent.
     *
     * @return
     */

    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy) {
        int totalValue = 0;
        for(int i = 0; i < SQUARE_LIST.length; i++) {
            //add self squares
            int numMatch = Long.bitCount(SQUARE_LIST[i] & bbSelf);
            totalValue += (numMatch * WEIGHT_LIST[i]);

            //subtract enemy squares
            numMatch = Long.bitCount(SQUARE_LIST[i] & bbEnemy);
            totalValue -= (numMatch * WEIGHT_LIST[i]);
        }
        return totalValue;
    }
}
