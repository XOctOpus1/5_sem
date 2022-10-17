// Denys Gordiichuk IPS-31 var#3


import java.util.concurrent.Semaphore;

public class task3Java {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i < 6; i++) {
            new Thread(new Car(i, semaphore)).start();
        }
    }
}

class Car implements Runnable {
    private int carNumber;
    private Semaphore semaphore;

    public Car(int carNumber, Semaphore semaphore) {
        this.carNumber = carNumber;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Car №" + carNumber + " is looking for a parking place");
        try {
            semaphore.acquire();
            System.out.println("Car №" + carNumber + " is parked");
            Thread.sleep(5000);
            System.out.println("Car №" + carNumber + " left the parking place");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

