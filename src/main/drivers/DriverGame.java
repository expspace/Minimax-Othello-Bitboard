package main.drivers;


import main.*;
import main.searchstrategy.GreedyAlgorithm;
import main.searchstrategy.HumanSearch;

/**
 * Created by NSPACE on 11/22/2016.
 */
public class DriverGame {
    public static void main(String[] args) {
        BoardOperations bbOps = new BoardOperations();

        /** PLAYER 1 (BLACK) **/

        //greedy search
//        Player p1 = new Player(new GreedyAlgorithm(bbOps));

        //minimax
//        Player p1 = new Player(new Minimax(bbOps,new RandomEvaluator()));
//        Player p1 = new Player(new Minimax(bbOps,new PositionalEvaluator()));
//        Player p1 = new Player(new Minimax(bbOps,new MobilityEvaluator(bbOps)));
//        Player p1 = new Player(new Minimax(bbOps,new FrontierEvaluator(bbOps)));
//        Player p1 = new Player(new Minimax(bbOps,new CombinationEvaluator(bbOps)));
//        Player p1 = new Player(new Minimax(bbOps,new CombinationTurnEvaluator(bbOps)));

        //alpha-beta-minimax
//        Player p1 = new Player(new AlphaBetaMinimax(bbOps,new RandomEvaluator()));
//        Player p1 = new Player(new AlphaBetaMinimax(bbOps,new PositionalEvaluator()));
//        Player p1 = new Player(new AlphaBetaMinimax(bbOps,new MobilityEvaluator(bbOps)));
//        Player p1 = new Player(new AlphaBetaMinimax(bbOps,new FrontierEvaluator(bbOps)));
//        Player p1 = new Player(new AlphaBetaMinimax(bbOps,new CombinationEvaluator(bbOps)));
//        Player p1 = new Player(new AlphaBetaMinimax(bbOps,new CombinationTurnEvaluator(bbOps)));
//
        //human player
        Player p1 = new Player(new HumanSearch(bbOps));


        /** PLAYER 2 (WHITE) **/

        //greedy search
        Player p2 = new Player(new GreedyAlgorithm(bbOps));

        //minimax
//        Player p2 = new Player(new Minimax(bbOps,new RandomEvaluator()));
//        Player p2 = new Player(new Minimax(bbOps,new PositionalEvaluator()));
//        Player p2 = new Player(new Minimax(bbOps,new MobilityEvaluator(bbOps)));
//        Player p2 = new Player(new Minimax(bbOps,new FrontierEvaluator(bbOps)));
//        Player p2 = new Player(new Minimax(bbOps,new CombinationEvaluator(bbOps)));
//        Player p2 = new Player(new Minimax(bbOps,new CombinationTurnEvaluator(bbOps)));

        //alpha-beta-minimax
//        Player p2 = new Player(new AlphaBetaMinimax(bbOps,new RandomEvaluator()));
//        Player p2 = new Player(new AlphaBetaMinimax(bbOps,new PositionalEvaluator()));
//        Player p2 = new Player(new AlphaBetaMinimax(bbOps,new MobilityEvaluator(bbOps)));
//        Player p2 = new Player(new AlphaBetaMinimax(bbOps,new FrontierEvaluator(bbOps)));
//        Player p2 = new Player(new AlphaBetaMinimax(bbOps,new CombinationEvaluator(bbOps)));
//        Player p2 = new Player(new AlphaBetaMinimax(bbOps,new CombinationTurnEvaluator(bbOps)));

        //human player
//        Player p2 = new Player(new HumanSearch(bbOps));

        OthelloGame othelloGame = new OthelloGame(p1,p2,bbOps);

        othelloGame.start();
    }
}
