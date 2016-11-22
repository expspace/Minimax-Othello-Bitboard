import java.util.ArrayList;

/**
 * Created by NSPACE on 11/17/2016.
 */
public class BoardOperations {

    /**
     * Masks for east and west shift are used to prevent bitboard wraparound
     **/
    private static final long MASK_E = 0b11111110_11111110_11111110_11111110_11111110_11111110_11111110_11111110L;
    private static final long MASK_W = 0b01111111_01111111_01111111_01111111_01111111_01111111_01111111_01111111L;

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
     * @returns array of long integer with single bit for each legal move
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
     * Converts bitboard representation of all possible legal moves to an array
     * of a single bit long integers representing a single move
     *
     * @param moves long bitboard representation of all possible legal moves moves
     * @return array of single bit long integers representing a single move
     */

    public long[] toBitMoveArray(long moves) {
        int moveIndex = 0;
        long[] bitMoveArray = new long[Long.bitCount(moves)];

        for (int i = 0; i < 64; i++) {
            if (((moves >> i) & 1) == 1) {
                bitMoveArray[moveIndex] = 1L << i;
                moveIndex++;
            }
        }

        return bitMoveArray;
    }

    /**
     * Updates the players bitboard using given move and turn. Shifts provided move in each direction
     * and compares to enemy bitboard. A check on self bitboard ensures existence of capping stone.
     * Assumes that the move provided is valid.
     *
     * @return updated bitboards for both players
     */

    //TODO optimize make move
    public long[] makeMove(long move, long bbSelf, long bbEnemy, int turn) {
        long captured;

        bbSelf |= move;

        //NORTH
        captured = shiftN(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftN(captured) & bbEnemy;
        }
        if ((shiftN(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //SOUTH
        captured = shiftS(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftS(captured) & bbEnemy;
        }
        if ((shiftS(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //WEST
        captured = shiftW(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftW(captured) & bbEnemy;
        }
        if ((shiftW(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //EAST
        captured = shiftE(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftE(captured) & bbEnemy;
        }
        if ((shiftE(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //NORTHWEST
        captured = shiftNW(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftNW(captured) & bbEnemy;
        }
        if ((shiftNW(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //NORTHEAST
        captured = shiftNE(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftNE(captured) & bbEnemy;
        }
        if ((shiftNE(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //SOUTHWEST
        captured = shiftSW(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftSW(captured) & bbEnemy;
        }
        if ((shiftSW(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        //SOUTHEAST
        captured = shiftSE(move) & bbEnemy;
        for (int i = 0; i < 5; i++) {
            captured |= shiftSE(captured) & bbEnemy;
        }
        if ((shiftSE(captured) & bbSelf) > 0) {
            bbSelf |= captured;
            bbEnemy &= ~captured;
        }

        if (turn % 2 == 0) {
            return new long[]{bbSelf, bbEnemy};
        } else {
            return new long[]{bbEnemy, bbSelf};
        }
    }

    /**
     * Greedy heuristic which returns whatever move results in the largest number of disk
     * gains.
     *
     * @return updated long bitboards after choosing a move with the most flipping outcome; same bitboard if no moves possible
     */

    public long[] makeMaxDiskMove(long bbSelf, long bbEnemy, int turn) {

        ArrayList<long[]> childBitBoards = getChildBitboards(bbSelf, bbEnemy, turn);

        //return same board and exit if no moves can be made
        if (childBitBoards.size() == 0) {
            if (turn % 2 == 0) {
                return new long[]{bbSelf, bbEnemy};
            } else {
                return new long[]{bbEnemy, bbSelf};
            }
        }

        long[] maxBitBoard = new long[2];
        int maxDiskCount = 0;

        //count max number of disks accrued in each child board; return largest updated one
        for(long[] childBitBoard : childBitBoards) {
            long selfBoard = childBitBoard[turn & 1];
            if (Long.bitCount(selfBoard) > maxDiskCount) {
                maxDiskCount = Long.bitCount(selfBoard);
                maxBitBoard = childBitBoard;
            }
        }

        return maxBitBoard;
    }

    public ArrayList<long[]> getChildBitboards(long bbSelf, long bbEnemy, int turn) {
        ArrayList<long[]> childBitBoards = new ArrayList<>();

        //generate all candidate moves
        long moves = generateMoves(bbSelf, bbEnemy);
        long[] bitMoveArray = toBitMoveArray(moves);

        //make all child boards given candidate moves
        for (int i = 0; i < bitMoveArray.length; i++) {
            childBitBoards.add(makeMove(bitMoveArray[i], bbSelf, bbEnemy, turn));
        }

        return childBitBoards;
    }

    /**
     * Tests each of the three potential conditions for a game over
     */
    //TODO
    public boolean gameOver(long bbPOne, long bbPTwo, int numMovesPOne, int numMovesPTwo) {
        return ((bbPOne | bbPTwo) == -1L) ||             // All squares are occupied.
                (numMovesPOne + numMovesPTwo == 0) ||    // Neither player has any moves available.
                (bbPOne == 0 || bbPTwo == 0);            // One player has had all chips eliminated.
    }

    public static final int POS_INFINITY_WIN = Integer.MAX_VALUE;
    public static final int POS_INFINITY_INIT = Integer.MAX_VALUE - 1;

    public static final int NEG_INFINITY_LOSS = Integer.MIN_VALUE;
    public static final int NEG_INFINITY_INIT = Integer.MIN_VALUE + 1;

    public static int SEARCH_DEPTH = 9;
    public static long NODE_COUNT = 0; //printing purposes


//    EvaluationFunction evaluationFunction = new RandomEvaluator();
//    EvaluationFunction evaluationFunction = new PositionalEvaluator();
    EvaluationFunction evaluationFunction = new MobilityEvaluator(this);


    public long[] makeMinimaxMove(long bbSelf, long bbEnemy, int turn) {
        //get child boards
        ArrayList<long[]> childBoardList = getChildBitboards(bbSelf, bbEnemy, turn);
        NODE_COUNT += childBoardList.size();

        //return same board and exit if no moves can be made
        if (childBoardList.size() == 0) { //TODO turn++?
            if (turn % 2 == 0) {
                return new long[]{bbSelf, bbEnemy};
            } else {
                return new long[]{bbEnemy, bbSelf};
            }
        }

        long[] updatedBoard = new long[2]; //board that will result from minimax based action
        int maxValue = NEG_INFINITY_INIT;

        //run minimax maxPlayer = false on children keep largest score board
        for(long[] childBoard : childBoardList) {
            int value;

            //compute minimax at child board who is min player; self board alternates at child board
            if(turn % 2 == 0) {
                value = minimax(childBoard[1],childBoard[0], SEARCH_DEPTH - 1,false, turn + 1);
            } else {
                value = minimax(childBoard[0],childBoard[1], SEARCH_DEPTH - 1,false, turn + 1);
            }


            System.out.println("CHILD BOARD: ");
            System.out.println("child board minimax value: " + value);
            BitboardHelper.bbPrint(childBoard[0],childBoard[1]);


            if(value > maxValue) {
                updatedBoard = childBoard;
                maxValue = value;
            }
        }

        System.out.println("maxValue " + maxValue);
        return updatedBoard;
    }

    public int minimax(long bbSelf, long bbEnemy,int depth, boolean maxPlayer, int turn) {

        //handle maximum tree depth
        if(depth == 0 ) {
            if(maxPlayer) {
                return evaluationFunction.evaluateBoard(bbSelf,bbEnemy);
            } else { //at a min player node the self board is the min players enemy board (confusing I know)
                return evaluationFunction.evaluateBoard(bbEnemy,bbSelf);
            }
        }

        ArrayList<long[]> childBoardList = getChildBitboards(bbSelf,bbEnemy,turn);
        NODE_COUNT += childBoardList.size();

        //handle no moves node
        if(childBoardList.size() == 0) {
            if(gameOver(bbSelf,bbEnemy,1,1)) {    //reached terminal node
                return maxPlayer? POS_INFINITY_WIN : NEG_INFINITY_LOSS; //TODO false check who wins
            } else {    //pass turn - continues minimax with unchanged board
                return minimax(bbEnemy,bbSelf,depth - 1,!maxPlayer, turn + 1);
            }
        }

        int selfBBIndex = turn % 2;
        int enemyBBIndex = (turn + 1) % 2;

        if(maxPlayer) {
            int bestValue = NEG_INFINITY_INIT;
            for(long[] childBoard : childBoardList) {
                int value = minimax(childBoard[enemyBBIndex],childBoard[selfBBIndex],depth - 1, false, turn + 1);
                bestValue = Math.max(bestValue,value);
            }
            return bestValue;
        } else {    //minPlayer
            int bestValue = POS_INFINITY_INIT;
            for(long[] childBoard : childBoardList) {
                int value = minimax(childBoard[enemyBBIndex],childBoard[selfBBIndex],depth - 1, true, turn + 1);
                bestValue = Math.min(bestValue,value);
            }
            return bestValue;
        }
    }

}
