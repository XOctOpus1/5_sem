
import java.util.Random;
import java.util.concurrent.Semaphore;


public class Main {

    private static final int SMOKERS_COUNT = 3;
    private static final int MAX_SLEEP_TIME = 1000;

    private static final Semaphore semaphore = new Semaphore(1);
    private static final Semaphore[] smokersSemaphores = new Semaphore[SMOKERS_COUNT];
    private static final Semaphore mediatorSemaphore = new Semaphore(0);

    private static final Random random = new Random();

    private static final int[] components = new int[SMOKERS_COUNT];

    public static void main(String[] args) {
        for (int i = 0; i < SMOKERS_COUNT; i++) {
            smokersSemaphores[i] = new Semaphore(0);
        }

        new Thread(new Mediator()).start();

        for (int i = 0; i < SMOKERS_COUNT; i++) {
            new Thread(new Smoker(i)).start();
        }
    }

    private static class Mediator implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    semaphore.acquire();
                    int firstComponent = random.nextInt(SMOKERS_COUNT);
                    int secondComponent = random.nextInt(SMOKERS_COUNT);
                    while (firstComponent == secondComponent) {
                        secondComponent = random.nextInt(SMOKERS_COUNT);
                    }
                    components[firstComponent] = 1;
                    components[secondComponent] = 1;
                    System.out.println("Mediator put " + firstComponent + " and " + secondComponent + " on the table");
                    semaphore.release();
                    mediatorSemaphore.release();
                    Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Smoker implements Runnable {

        private final int id;

        public Smoker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    mediatorSemaphore.acquire();
                    semaphore.acquire();
                    if (components[id] == 0) {
                        System.out.println("Smoker " + id + " is smoking");
                        Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
                        components[0] = 0;
                        components[1] = 0;
                        components[2] = 0;
                    } else {
                        System.out.println("Smoker " + id + " is waiting");
                        smokersSemaphores[id].release();
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}




