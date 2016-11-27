package main;

import java.util.Scanner;

/**
 * Created by NSPACE on 11/22/2016.
 */
public class HumanSearch implements SearchStrategy {
    private BoardOperations boardOperations;

    private Scanner kb = new Scanner(System.in);

    public HumanSearch(BoardOperations boardOperations) {
        this.boardOperations = boardOperations;
    }

    /**
     * Performs next move from taken from a human players input
     */

    @Override
    public long[] performNextMove(long bbSelf, long bbEnemy, int turn) {
        long legalMoves = boardOperations.generateMoves(bbSelf, bbEnemy);
        int[] chosenMove;
        long moveBitboard;

        if(legalMoves == 0) {
            System.out.println("Pass turn - no moves");
            return new long[] {bbSelf,bbEnemy};
        }

        do {
            chosenMove = promptMove();  //gets player move checks valid input
            moveBitboard = rowColToBBArray(chosenMove[0],chosenMove[1]);
        } while(!isValidMove(moveBitboard,legalMoves)); //checks move is legal for player

        return boardOperations.makeMove(moveBitboard,bbSelf,bbEnemy,turn); //execute move return updated
    }

    public int[] promptMove() {
        int rowChoice;
        int colChoice;
        do {
            System.out.print("Enter column and row index \"col,row\" : ");
            String input = kb.nextLine();
            String[] parts = input.split(",");

            try{
                colChoice = Integer.parseInt(parts[0]);
                rowChoice = Integer.parseInt(parts[1]);
            }catch (Exception error)
            {
                //we catch every wrong inputs that could'nt be parsed by our rules
                rowChoice = -1;
                colChoice = -1;
            }

        } while(!isValidInput(rowChoice,colChoice));

        int[] moveChoice = {rowChoice,colChoice};

        return moveChoice;
    }

    public long rowColToBBArray(int row, int col) {

        int bbIndex = 64 - (col+(row*8-8)); ///convert row/col to bitboard index
        long move = 1L << bbIndex;
        return move;
    }

    boolean isValidInput(int rowChoice, int colChoice) {
        return (rowChoice >=1 && rowChoice <=8) && (colChoice >=1 && colChoice <=8);
    }

    public boolean isValidMove(long move, long legalMoves) {
        boolean valid = (move & legalMoves) > 0;
        if(!valid) {
            System.out.println("Not a valid move!");
        }
        return valid;
    }

}
