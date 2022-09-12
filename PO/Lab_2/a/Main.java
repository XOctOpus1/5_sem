import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private int[][] ft;
    private final AtomicBoolean FOUNDED;
    private final AtomicInteger ROW;
    private final Integer SIZE;
    public final Integer COUNT;
    private Thread[] threads;
    private class threads extends Thread {
        public threads(){

        }
        public void run() {
            while(!FOUNDED.get() && ROW.get() < SIZE) {
                checkRow(ROW.get());
                ROW.set(ROW.get() + 1);
            }
        }
    }
    public Main(Integer SIZE) {
        this.SIZE = SIZE;
        this.COUNT = (int)Math.sqrt(SIZE);
        this.threads = new Thread[COUNT];
        ft = new int[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++) {
                ft[i][j] = 0;
            }
        }

        int column = (int)(Math.random()*SIZE);
        int row = (int)(Math.random()*SIZE);
        System.out.println("Pooh is in row: " + row + " column: " + column);
        ft[row][row] = 1;

        FOUNDED = new AtomicBoolean(false);
        ROW = new AtomicInteger(0);
    }
    private void checkAllForest(){
        for(int i = 0; i < COUNT; i++){
            threads[i] = new threads();
            threads[i].start();
        }
        for(int i = 0; i < COUNT; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void checkRow(int row) {
        if(FOUNDED.get()) { return; }
        System.out.println(Thread.currentThread().getName() + " group of bees in row: " + row);
        for(int i = 0; i < SIZE; i++){
            if(ft[row][i] == 1){
                System.out.println(Thread.currentThread().getName() + " pooh was founded in row: " + row);
                FOUNDED.set(true);
                break;
            }
        }
    }
    public static void main(String[] args) {
        Main BeesFindingWinniePooh = new Main(100);
        BeesFindingWinniePooh.checkAllForest();
    }
}


