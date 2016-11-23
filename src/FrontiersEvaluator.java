import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal wozniak on 11/22/2016.
 */
public class FrontiersEvaluator implements EvaluationFunction {

    /**
     * http://www.samsoft.org.uk/reversi/strategy.htm#Frontiers
     * http://www.soongsky.com/en/strategy2/ch3.php
     * http://ai.stanford.edu/~chuongdo/papers/demosthenes.pdf
     * <p>
     * Frontier discs are defined as discs that border one or more empty squares
     * The less frontier discs you have, the less choices your opponent has. That is the key in winning the battle for mobility.
     * <p>
     * Objective :
     * - flip at least one of your opponent’s pieces in order to move,
     * - minimize frontier discs you have
     *
     * @param bbSelf
     * @param bbEnemy
     * @return
     */
    @Override
    public int evaluateBoard(long bbSelf, long bbEnemy) {
        
        List<Integer> selfDiskPositions = new ArrayList<>();
        List<Integer> enemyDiskPositions = new ArrayList<>();

        //find all disks position
        for (int i = 64; i >= 0; i--) {
            if (isDisk(i, bbSelf)) {
                selfDiskPositions.add(i);
            }
            if (isDisk(i, bbEnemy)) {
                enemyDiskPositions.add(i);
            }
        }

        int frontierSelf = getFrontiersValue(selfDiskPositions, bbSelf);
        int frontierEnemy = getFrontiersValue(enemyDiskPositions, bbEnemy);

        System.out.println("self frontiersValue : "+frontierSelf);
        System.out.println("enemy frontierValue :"+frontierEnemy);


        //Todo  flip at least one of your opponent’s pieces in order to move, : must verify that we have gained at 1 disk  if not move isn't not good


        return frontierSelf - frontierEnemy;
    }


    public static int getFrontiersValue(List<Integer> diskPositions, long bitBoard) {
        int frontiersValue = 0;

        for (Integer diskPosition : diskPositions) {
            int position = diskPosition;
            boolean isCurrentDiskFrontier = false;

            List<Integer> surroundingSquarePositionList = getSurroundingSquares(position);
            System.out.println("surroundingSquarePositionList = " + surroundingSquarePositionList);

            for (int neighborPosition : surroundingSquarePositionList) {

                System.out.println(neighborPosition);
                if (!isDisk(neighborPosition, bitBoard)) {
                    isCurrentDiskFrontier = true;
                    //the moment we have one adjacent square empty, it is a frontier disk
                    break;
                }
            }

            if (isCurrentDiskFrontier)
            {
                frontiersValue++;
            }
        }

        return frontiersValue;
    }


    private static boolean isDisk(int index, long bitBoard) {
        if ((bitBoard & (1L << index)) != 0L) {
            //disk exist
            return true;
        }
        //empty space
        return false;
    }


    private static List<Integer> getSurroundingSquares(int squareIndex) {

        List<Integer> surroundingSquares = new ArrayList<>();

        int topIndex = squareIndex + 8;
        int bottomIndex = squareIndex - 8;
        int rightIndex = squareIndex - 1;
        int leftIndex = squareIndex + 1;
        int topRightIndex = squareIndex + 7;
        int topLeftIndex = squareIndex + 9;
        int bottomRightIndex = squareIndex - 9;
        int bottomLeftIndex = squareIndex - 7;

        if (validIndex(topIndex)) {
            surroundingSquares.add(topIndex);
        }
        if (validIndex(bottomIndex)) {
            surroundingSquares.add(bottomIndex);
        }
        if (validIndex(rightIndex)) {
            surroundingSquares.add(rightIndex);
        }
        if (validIndex(leftIndex)) {
            surroundingSquares.add(leftIndex);
        }
        if (validIndex(topRightIndex)) {
            surroundingSquares.add(topRightIndex);
        }
        if (validIndex(topLeftIndex)) {
            surroundingSquares.add(topLeftIndex);
        }
        if (validIndex(bottomRightIndex)) {
            surroundingSquares.add(bottomRightIndex);
        }
        if (validIndex(bottomLeftIndex)) {
            surroundingSquares.add(bottomLeftIndex);
        }

        return surroundingSquares;
    }

    /**
     * Whenever we want to get the index of square surrounding one position, we must verify that it is possible
     *  Not every squares have are surrounded by other squares in all 8 directions.
     *
     * All board squares on the limit of the board ( # squares position)
     *
     *     # # # # # # # #
     *     # O O O O O O #
     *     # O O W B O O #
     *     # O O B B O O #
     *     # O B B B O O #
     *     # O O O O O O #
     *     # O O O O O O #
     *     # # # # # # # #
     *
     * @param index
     * @return
     */
    private static boolean validIndex(int index) {
        return (index >= 0 && index <= 63);
    }

}
