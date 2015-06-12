
/* Complete */
public class GenBlocks {

    private static final String symbolH = "=";

    public static void main(String[]args){

        assert args.length != 2: "Two args required";
//        if(args.length != 2) {
//            System.out.println("Two args required.");
//            return;
//        }

        int[] matrix = new int[2];
        for(int i = 0; i < 2; i++) {
            matrix[i] = Integer.valueOf(args[i]);
        }

        for(int i = 1; i <= matrix[0]; i++) {
            System.out.println(new String(new char[matrix[1] * 4 + 1]).replace("\0", symbolH));
            for(int k = 1; k <= matrix[1]; k++) {
                System.out.print(symbolH + "   ");
                if(k == matrix[1]) {
                    System.out.println(symbolH);
                }
            }
            if(i == matrix[0]) {
                System.out.println(new String(new char[matrix[1]*4+1]).replace("\0", symbolH));
            }
        }
    }
}

