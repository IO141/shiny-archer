import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PowerSet {

    private static final String EMPTY_LIST = "[]";

    private static Object getLast(List lst) {
        return lst.get(lst.size()-1);
    }

    public static void main(String[] args) {

        ArrayList<String> outLst = new ArrayList<>(); //[(int) Math.pow(2, args.length)];
        ArrayList<String> arg = new ArrayList<>();
        Collections.addAll(arg, args);
        int last = arg.size()-1;
        String lst = "";
        //int j = 0;

        for(int i = 0; i < arg.size(); i++) {
            outLst.add(arg.get(i));
            if(i == 0) {
                lst = "[ " + arg.get(i) + ", ";
            } else if(i == last) {
                lst += "]";
            } else if(i == last-1) {
                lst += arg.get(i) + " ";
            } else {
                lst += arg.get(i) + ", ";
            }
        }

        while(getLast(outLst) != arg.get(last)) {
            for(int i = 1; i < arg.size(); i++) {
                for(int j = 0; j < i; j++) {

                }
            }
        }

//        for(int i = 0; i < arg.size(); i++) {
//            if(i == arg.size()-1 && outLst != arg) {
//                i = 0;
//            } else {
//                String s = arg.get(i);
//                if(outLst.contains(arg.get(j))) {
//
//                }
//            }
//        }

    }

}
