/**
 * Created by NSPACE on 11/17/2016.
 */
public class BitBoardOps {

    /**
     * Masks for east and west shift are used to prevent bitboard wraparound
     **/
    private static final long MASK_E = 0b11111110_11111110_11111110_11111110_11111110_11111110_11111110_11111110L;
    private static final long MASK_W = 0b01111111_01111111_01111111_01111111_01111111_01111111_01111111_01111111L;

    private static final long MASK_FULL = 0b11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111L;

    /**
     * Methods to shifts a players in one of the 8 possible directions (N,S,E,W,NW,NE,SW,SE)
     */

    private long shiftN(long bb) {
        return bb << 8;
    }

    private long shiftS(long bb) {
        return bb >> 8;
    }

    private long shiftE(long bb) {
        return (bb & MASK_E) >> 1;
    }

    private long shiftW(long bb) {
        return (bb & MASK_W) << 1;
    }

    private long shiftNW(long bb) {
        return shiftN(shiftW(bb));
    }

    private long shiftNE(long bb) {
        return shiftN(shiftE(bb));
    }

    private long shiftSW(long bb) {
        return shiftS(shiftW(bb));
    }

    private long shiftSE(long bb) {
        return shiftS(shiftE(bb));
    }


    /**
     * Shifts a players bitboard in each possible direction and compares to the enemies.
     * When a capture is possible further shifts and compares with non zero bit arrays occur.
     * Final check ensures move is an open square.
     *
     * @returns bitboard representation of possible moves
     */

    public long generateMoves(long bbSelf, long bbEnemy) {
        long moves = 0L;
        long open = ~(bbSelf | bbEnemy);
        long captured;

        //NORTH
        captured = shiftN(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftN(captured) & bbEnemy;
        }
        moves |= shiftN(captured) & open;

        //SOUTH
        captured = shiftS(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftS(captured) & bbEnemy;
        }
        moves |= shiftS(captured) & open;

        //WEST
        captured = shiftW(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftW(captured) & bbEnemy;
        }
        moves |= shiftW(captured) & open;

        //EAST
        captured = shiftE(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftE(captured) & bbEnemy;
        }
        moves |= shiftE(captured) & open;


        //NORTHWEST
        captured = shiftNW(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftNW(captured) & bbEnemy;
        }
        moves |= shiftNW(captured) & open;

        //NORTHEAST
        captured = shiftNE(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftNE(captured) & bbEnemy;
        }
        moves |= shiftNE(captured) & open;

        //SOUTHWEST
        captured = shiftSW(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftSW(captured) & bbEnemy;
        }
        moves |= shiftSW(captured) & open;

        //SOUTHEAST
        captured = shiftSE(bbSelf) & bbEnemy;
        for(int i =0; i<5; i++) {
            captured |= shiftSE(captured) & bbEnemy;
        }
        moves |= shiftSE(captured) & open;

        return moves;
    }

//    public long setMove(long move,long bbSelf, long bbEnemy) {
//        bbSelf |= move;
//
//    }

    public boolean gameOver(long bbPOne, long bbPTwo, long movesPOne, long movesPTwo) {
        return ((bbPOne | bbPTwo) == MASK_FULL) || // All squares are occupied.
                (movesPOne + movesPTwo == 0) ||    // Neither player has any moves available.
                (bbPOne == 0 || bbPTwo == 0);      // One player has had all chips eliminated.
    }


}
