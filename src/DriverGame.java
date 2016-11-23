/**
 * Created by NSPACE on 11/22/2016.
 */
public class DriverGame {
    public static void main(String[] args) {
        BoardOperations boardOperations = new BoardOperations();

        Player p1 = new Player(new GreedyAlgorithm(boardOperations));
//        Player p2 = new Player(new GreedyAlgorithm(boardOperations));
//        Player p2 = new Player(new Minimax(boardOperations,new RandomEvaluator()));
//        Player p2 = new Player(new Minimax(boardOperations,new PositionalEvaluator()));
        Player p2 = new Player(new Minimax(boardOperations,new MobilityEvaluator(boardOperations)));

        OthelloGame othelloGame = new OthelloGame(p1,p2,boardOperations);

        othelloGame.start();
    }
}
