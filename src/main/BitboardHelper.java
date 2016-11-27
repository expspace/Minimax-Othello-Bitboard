package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by NSPACE on 11/17/2016.
 */
public class BitboardHelper {

    public static void bbPrint(long bbPOne, long bbPTwo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 63; i >= 0; i--) {
            if (((bbPOne >> i) & 1) > 0) {
                sb.append("B ");
            } else if (((bbPTwo >> i) & 1) > 0) {
                sb.append("W ");
            } else {
                sb.append("O ");
            }

            if (i % 8 == 0) {
                sb.append('\n');
            }
        }
        System.out.println(sb);
    }


    public static void bbPrintForHumans(long bbPOne, long bbPTwo) {
        int rowIndex = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("  1 2 3 4 5 6 7 8 \n");
        sb.append("  --------------- \n" + rowIndex + "|");
        rowIndex++;
        for (int i = 63; i >= 0; i--) {
            if (((bbPOne >> i) & 1) > 0) {
                sb.append("B ");
            } else if (((bbPTwo >> i) & 1) > 0) {
                sb.append("W ");
            } else {
                sb.append("O ");
            }

            if (i % 8 == 0) {
                if (i == 0) {
                    sb.append('\n');
                } else {
                    sb.append("\n" + rowIndex + "|");
                    rowIndex++;
                }

            }
        }
        System.out.println(sb);
    }

    public static void bbPrintA2Format(long bbPOne, long bbPTwo) {
        StringBuilder sb = new StringBuilder();
        sb.append("(\n");
        for (int i = 63; i >= 0; i--) {
            if (i % 8 == 7) {
                sb.append('(');
            }
                if (((bbPOne >> i) & 1) > 0) {
                    sb.append("B");
                } else if (((bbPTwo >> i) & 1) > 0) {
                    sb.append("W");
                } else {
                    sb.append("0");
                }

            if (i % 8 == 0) {
                sb.append(")\n");
            }
        }
        sb.append(")\n");
        System.out.println(sb);
    }


    public static void printWinner(long bbPOne, long bbPtwo) {
        System.out.println("GAMEOVER");
        if (Long.bitCount(bbPOne) > Long.bitCount(bbPtwo)) {
            System.out.println("PLAYER ONE (BLACK) - WINS");
        } else if (Long.bitCount(bbPOne) < Long.bitCount(bbPtwo)) {
            System.out.println("PLAYER TWO (WHITE) - WINS");
        } else {
            System.out.println("DRAW");
        }
        System.out.println("Black disk count: " + Long.bitCount(bbPOne));
        System.out.println("White disk count: " + Long.bitCount(bbPtwo));
    }

    public static void readMoveFormat() {
        Scanner kb = new Scanner(System.in);
        Pattern stringPattern = Pattern.compile("\\([BW0]{8}\\)");

        ArrayList<String> stringList = new ArrayList<>();

        kb.nextLine();

        while (kb.hasNext(stringPattern)) {
            stringList.add(kb.nextLine());
        }

        kb.nextLine();

        //loop through stringlist add convert to bits
    }

    public static boolean isAvailableMove(int index, long bitBoard) {
        if ((bitBoard & (1L << index)) != 0L) {
            return true;
        }
        //empty space
        return false;
    }

    /**
     * Display available position
     *
     *  (x,y)
     *
     * @param bitBoard
     */
    public static void printAvailableMoves(long bitBoard)
    {
        List<Integer> availableMoveIndex = new ArrayList<>();

        //find all move position
        for (int i = 64; i >= 0; i--) {
            if (isAvailableMove(i, bitBoard)) {
                availableMoveIndex.add(i);
            }

        }
        for(int index : availableMoveIndex)
        {
            for(int i =1; i <8;i++)
            {
                for(int j=1; j <=8 ;j++)
                {
                    int result = 64 - (i+(j*8-8));
                    if(index == result)
                    {
                        System.out.print("("+i+","+j+") ");
                    }
                }
            }

        }
        System.out.println();
    }
}
