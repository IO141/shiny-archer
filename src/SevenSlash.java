import java.util.*;


/**
 * Write a program that takes in a non-empty string of the digits
 * 0 through 9 and prints how they would be shown on a seven-segment
 * display using slashes ("/", "\").
 */

/* Incomplete */
public class SevenSlash {

    private static String[] encode(String num) {

        String[] out = new String[10];
        for(int i = 0; i < num.length(); i++) {
            switch(num.charAt(i)) {
                case 0:
                    out[i] = "110101011";
                case 1:
                    out[i] = "010001000";
                case 2:
                    out[i] = "110010011";
                case 3:
                    out[i] = "110011001";
                case 4:
                    out[i] = "010111000";
                case 5:
                    out[i] = "100111001";
                case 6:
                    out[i] = "100111011";
                case 7:
                    out[i] = "110001000";
                case 8:
                    out[i] = "110111011";
                case 9:
                    out[i] = "110111001";
            }
        }
        return out; //['110101011','010001000','110111001']
    }

    private static String[][] translate(String[] array) {

        int n = array.length*9;
        String[][] out = new String[array.length][9];
        for(int k = 0; k < n; k++) {
            out[Math.round(k/n)][k%n] = "";
        }

        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length(); j++) {
                if(Objects.equals(array[j], "1")) {
                    out[i][j] = "/";
                } else if(Objects.equals(array[j], "0")) {
                    out[i][j] = "\\";
                } else {
                    out[i][j] = " ";
                }
            }
        }
        return out;
    }

    public static void print(String input) {

        System.out.print(Arrays.deepToString(translate(encode(input))));

    }

    public static void main(String[] args) {

        System.out.print("Enter number: ");
        Scanner in = new Scanner(System.in);
        print(in.next());

    }
}
