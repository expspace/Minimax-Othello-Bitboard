/**
 * Created by NSPACE on 11/17/2016.
 */
public class BitBoardHelper {

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
}
