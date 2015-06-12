
import java.util.HashMap;
import java.util.Scanner;

/* Incomplete */
public class TotalBases {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter number: ");
        int num = in.nextInt();
        HashMap<String, String> baseMap = new HashMap<>();

        for(int d = 1; d <= num; d++) {
            String s = "";
            int c = num;
            for(; c > 0; s = c % d + s, c /= d) {
                if(c % d > 1) {
                    baseMap.put("Base " + d + ": ", s);
                }
            }
        }

        for(String s : baseMap.keySet()) {
            System.out.println(s + ": " + baseMap.get(s));
        }
    }
}
