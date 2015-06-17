




/* Complete */
public class VertSineWave extends Thread {

    private int time;

    public VertSineWave() {
        super("VertSineWave");
        this.time = 0;
    }

    public void setTime() {
        this.time += 1;
    }

    @Override
    public void run() {
        //PrintThread print = new PrintThread("|");
        for(; time < Integer.MAX_VALUE; setTime()) {
            double formula = 20*Math.sin((2*Math.PI*time)/65);;
            for(int i = 1; i <= ((int) formula); i++) {
                System.out.print("  ");
                if(i >= ((int) formula)) {
                    System.out.println("|"); //print.run()
                }
            }
            try {
                sleep(50);
            } catch(InterruptedException e) {
                System.out.println("Interrupted at " + time);
                e.printStackTrace();
            }
//            double width = FORMULA;
//            format = "%" + width + "f%n";
//            System.out.printf(format, SYMBOL);
        }
    }

//    private class PrintThread extends Thread {
//
//        private String sym;
//
//        private PrintThread(String sym) {
//            super("PrintThread");
//            this.sym = sym;
//        }
//
//        @Override
//        public void run() {
//            System.out.println(sym);
//        }
//
//    }

    public static void main(String[] args) {
        VertSineWave out = new VertSineWave();
        out.start();
    }
}
