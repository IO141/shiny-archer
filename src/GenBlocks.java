import java.util.Scanner;


public class GenBlocks {

    private static final String symbol = "=";

    public static void main(String[]args){

        if(args.length != 2) {
            System.out.println("Two args required.");
            return;
        }

        int[] matrix = new int[1];
        Scanner in = new Scanner(System.in);
        for(int i = 0; i < 2; i++) {
            matrix[i] = in.nextInt();
        }

        for(int i = 1; i <= matrix[0]; i++) {
            for(int j = 1; j <= matrix[1]; j++) {
                System.out.println(new String(new char[4]).replace("\0", symbol));
                if(j == matrix[1]) {
                    System.out.println(symbol + "\n");
                    for(int k = 1; k <= matrix[1]; k++) {
                        System.out.println(symbol + "   ");
                        if(k == matrix[1]) {
                            System.out.println(symbol + "\n");
                        }
                    }
                }
                if(i == matrix[0]) {
                    System.out.println(new String(new char[4]).replace("\0", symbol));
                }
            }
        }
    }
}

