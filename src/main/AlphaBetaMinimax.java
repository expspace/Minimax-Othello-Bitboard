package main;

import java.util.ArrayList;

/**
 * Created by NSPACE on 11/22/2016.
 */
public class AlphaBetaMinimax implements SearchStrategy {

    public static final int POS_INFINITY_WIN = 1000000000;
    public static final int POS_INFINITY_INIT = Integer.MAX_VALUE;

    public static final int NEG_INFINITY_LOSS = -1000000000;
    public static final int NEG_INFINITY_INIT = Integer.MIN_VALUE;

    public int searchDepth = 7;
    public long nodeCount = 0; //printing purposes


    private BoardOperations boardOperations;
    private EvaluationFunction evaluationFunction;

    public AlphaBetaMinimax(BoardOperations boardOperations, EvaluationFunction evaluationFunction) {
        this.boardOperations = boardOperations;
        this.evaluationFunction = evaluationFunction;
    }

    /**
     * Function which performs action using minimax search strategy. In order to perform action
     * I run minimax at all child nodes of the current node and return the highest minimax
     * value board as the successor board.
     *
     * @return updated board after performing minimax move; same board if no moves possible
     */

    @Override
    public long[] performNextMove(long bbSelf, long bbEnemy, int turn) {
        //get child boards
        ArrayList<long[]> childBoardList = boardOperations.getChildBitboards(bbSelf, bbEnemy, turn);
        nodeCount += childBoardList.size();

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
        for (long[] childBoard : childBoardList) {
            int value;

            //compute minimax at child board who is min player; self board alternates at child board
            if (turn % 2 == 0) {
                value = alphaBetaMinimax(childBoard[1], childBoard[0], searchDepth - 1, false, turn + 1,NEG_INFINITY_INIT,POS_INFINITY_INIT);
            } else {
                value = alphaBetaMinimax(childBoard[0], childBoard[1], searchDepth - 1, false, turn + 1,NEG_INFINITY_INIT,POS_INFINITY_INIT);
            }


//            System.out.println("CHILD BOARD: ");
//            System.out.println("child board minimax value: " + value);
//            main.BitboardHelper.bbPrint(childBoard[0],childBoard[1]);

//            System.out.println("value, max value :"  + value + " " + maxValue);
            if (value > maxValue) {
                updatedBoard = childBoard;
                maxValue = value;
            }
        }

//        System.out.println("maxValue " + maxValue);
        return updatedBoard;
    }

    /**
     * AlphaBetaMinimax search implementation using chosen evaluation function.
     *
     * @return evaluation value of current node
     */

    public int alphaBetaMinimax(long bbSelf, long bbEnemy, int depth, boolean maxPlayer, int turn, int alpha, int beta) {

        //handle maximum tree depth
        if (depth == 0) {
            if (maxPlayer) {
                return evaluationFunction.evaluateBoard(bbSelf, bbEnemy, turn);
            } else { //at a min player node the enemy board is the max players self board (evaluate from max's perspective)
                return evaluationFunction.evaluateBoard(bbEnemy, bbSelf, turn);
            }
        }

        ArrayList<long[]> childBoardList = boardOperations.getChildBitboards(bbSelf, bbEnemy, turn);
        nodeCount += childBoardList.size();

        //handle no moves node (either terminal game over node or pass turn)
        if (childBoardList.size() == 0) {
            if (boardOperations.gameOver(bbSelf, bbEnemy, 1, 1)) {    //reached terminal node
                if (maxPlayer) {
                    if (Long.bitCount(bbSelf) > Long.bitCount(bbEnemy)) {
                        return POS_INFINITY_WIN;
                    } else if (Long.bitCount(bbSelf) < Long.bitCount(bbEnemy)) {
                        return NEG_INFINITY_LOSS;
                    } else { //draw
                        return 0;
                    }
                } else { //at a min player node the enemy board is the max players self board (evaluate from max's perspective)
                    if (Long.bitCount(bbEnemy) > Long.bitCount(bbSelf)) {
                        return POS_INFINITY_WIN;
                    } else if (Long.bitCount(bbEnemy) < Long.bitCount(bbSelf)) {
                        return NEG_INFINITY_LOSS;
                    } else { //draw
                        return 0;
                    }
                }
            } else {    //pass turn - continues minimax with unchanged board
                return alphaBetaMinimax(bbEnemy, bbSelf, depth - 1, !maxPlayer, turn + 1, alpha, beta); //TODO parentPassed = true
            }
        }

        int selfBBIndex = turn % 2;
        int enemyBBIndex = (turn + 1) % 2;

        if (maxPlayer) {
            int bestValue = NEG_INFINITY_INIT;
            for (long[] childBoard : childBoardList) {
                int value = alphaBetaMinimax(childBoard[enemyBBIndex], childBoard[selfBBIndex], depth - 1, false, turn + 1, alpha, beta); //TODO parentPassed
                bestValue = Math.max(bestValue, value);

                alpha = Math.max(alpha, bestValue);
                if(beta <= alpha) {
                    break; //beta cut-off
                }
            }
            return bestValue;
        } else {    //minPlayer
            int bestValue = POS_INFINITY_INIT;
            for (long[] childBoard : childBoardList) {
                int value = alphaBetaMinimax(childBoard[enemyBBIndex], childBoard[selfBBIndex], depth - 1, true, turn + 1, alpha, beta); //TODO parentPassed = false
                bestValue = Math.min(bestValue, value);

                beta = Math.min(beta, bestValue);
                if(beta <= alpha) {
                    break; //alpha cut-off
                }
            }
            return bestValue;
        }
    }

    public int getSearchDepth() {
        return searchDepth;
    }

    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public long getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(long nodeCount) {
        this.nodeCount = nodeCount;
    }
}
