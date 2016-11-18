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
        for (int i = 0; i < 5; i++) {
            captured |= shiftN(captured) & bbEnemy;
        }
        moves |= shiftN(captured) & open;

        //SOUTH
        captured = shiftS(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftS(captured) & bbEnemy;
        }
        moves |= shiftS(captured) & open;

        //WEST
        captured = shiftW(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftW(captured) & bbEnemy;
        }
        moves |= shiftW(captured) & open;

        //EAST
        captured = shiftE(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftE(captured) & bbEnemy;
        }
        moves |= shiftE(captured) & open;


        //NORTHWEST
        captured = shiftNW(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftNW(captured) & bbEnemy;
        }
        moves |= shiftNW(captured) & open;

        //NORTHEAST
        captured = shiftNE(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftNE(captured) & bbEnemy;
        }
        moves |= shiftNE(captured) & open;

        //SOUTHWEST
        captured = shiftSW(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftSW(captured) & bbEnemy;
        }
        moves |= shiftSW(captured) & open;

        //SOUTHEAST
        captured = shiftSE(bbSelf) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftSE(captured) & bbEnemy;
        }
        moves |= shiftSE(captured) & open;

        return moves;
    }


    /**
     * Updates the players bitboard using given move and turn. Shifts provided move in each direction
     * and compares to enemy bitboard. A check on self bitboard ensures existence of capping stone.
     * Assumes that the move provided is valid.
     *
     * @param move      valid long integer with single bit representing move
     * @param bitboards
     * @param turn
     */

    //TODO optimize make move
    public void makeMove(long move, long[] bitboards, int turn) {
        long captured;
        int selfIndex, enemyIndex;

        //decide which board is self board and which is enemy board
        if (turn % 2 == 0) {
            selfIndex = 0;
            enemyIndex = 1;
        } else {
            selfIndex = 1;
            enemyIndex = 0;
        }

        bitboards[selfIndex] |= move;

        //NORTH
        captured = shiftN(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftN(captured) & bitboards[enemyIndex];
        }
        if ((shiftN(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //SOUTH
        captured = shiftS(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftS(captured) & bitboards[enemyIndex];
        }
        if ((shiftS(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //WEST
        captured = shiftW(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftW(captured) & bitboards[enemyIndex];
        }
        if ((shiftW(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //EAST
        captured = shiftE(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftE(captured) & bitboards[enemyIndex];
        }
        if ((shiftE(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //NORTHWEST
        captured = shiftNW(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftNW(captured) & bitboards[enemyIndex];
        }
        if ((shiftNW(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //NORTHEAST
        captured = shiftNE(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftNE(captured) & bitboards[enemyIndex];
        }
        if ((shiftNE(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //SOUTHWEST
        captured = shiftSW(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftSW(captured) & bitboards[enemyIndex];
        }
        if ((shiftSW(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }

        //SOUTHEAST
        captured = shiftSE(move) & bitboards[enemyIndex];
        for (int i = 0; i < 5; i++) {
            captured |= shiftSE(captured) & bitboards[enemyIndex];
        }
        if ((shiftSE(captured) & bitboards[selfIndex]) > 0) {
            bitboards[selfIndex] |= captured;
            bitboards[enemyIndex] &= ~captured;
        }
    }

    public boolean gameOver(long bbPOne, long bbPTwo, long movesPOne, long movesPTwo) {
        return ((bbPOne | bbPTwo) == MASK_FULL) || // All squares are occupied.
                (movesPOne + movesPTwo == 0) ||    // Neither player has any moves available.
                (bbPOne == 0 || bbPTwo == 0);      // One player has had all chips eliminated.
    }

    public int getBitCount(long bb) {
        return Long.bitCount(bb);
    }


}
