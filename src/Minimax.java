import java.util.ArrayList;

/**
 * Created by NSPACE on 11/18/2016.
 */
public class Minimax {

//    public long[] makeMinimaxMove() {
//        //get child boards
//        ArrayList<long[]> childBitBoards = getChildBitboards(bbSelf, bbEnemy, turn);
//
//        //return same board and exit if no moves can be made
//        if (childBitBoards.size() == 0) {
//            if (turn % 2 == 0) {
//                return new long[]{bbSelf, bbEnemy};
//            } else {
//                return new long[]{bbEnemy, bbSelf};
//            }
//        }
//
//        //run minimax maxPlayer = false on children keep largest score board
//    }

    public int minimax(long[] bitboards) {


//        01 function minimax(node, depth, maximizingPlayer)
//        02     if depth = 0 or node is a terminal node
//        03         return the heuristic value of node
//
//        04     if maximizingPlayer
//        05         bestValue := −∞
//        06         for each child of node
//        07             v := minimax(child, depth − 1, FALSE)
//        08             bestValue := max(bestValue, v)
//        09         return bestValue
//
//        10     else    (* minimizing player *)
//        11         bestValue := +∞
//        12         for each child of node
//        13             v := minimax(child, depth − 1, TRUE)
//        14             bestValue := min(bestValue, v)
//        15         return bestValue
        return 0;
    }
}
