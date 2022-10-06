
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 100; i++) {
            new Thread(new Recruit(semaphore)).start();
        }
    }
}

class Recruit implements Runnable {
    private Semaphore semaphore;
    private boolean isLeft = false;
    private boolean isRight = false;

    public Recruit(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                if (isLeft) {
                    isLeft = false;
                    isRight = true;
                    System.out.println("Recruit " + Thread.currentThread().getId() + " turned right");
                } else if (isRight) {
                    isRight = false;
                    isLeft = true;
                    System.out.println("Recruit " + Thread.currentThread().getId() + " turned left");
                } else {
                    isLeft = true;
                    System.out.println("Recruit " + Thread.currentThread().getId() + " turned left");
                }
                semaphore.release();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
