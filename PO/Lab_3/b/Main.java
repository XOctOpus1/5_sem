import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private final AtomicBoolean isWorkFinished;
    private final AtomicBoolean isBarberBusy;
    private final Lock barberLock;
    private final Lock queueLock;
    private final Condition barberCondition;
    private final Condition queueCondition;
    private final AtomicInteger queueSize;

    Main() {
        barberLock = new ReentrantLock();
        queueLock = new ReentrantLock();
        barberCondition = barberLock.newCondition();
        queueCondition = queueLock.newCondition();
        isWorkFinished = new AtomicBoolean(false);
        isBarberBusy = new AtomicBoolean(false);
        queueSize = new AtomicInteger(0);
    }
    private class Barber implements Runnable {
        @Override
        public void run() {
            barberLock.lock();
            System.out.println(Thread.currentThread().getName() + ": day is started");

            while(!isWorkFinished.get() || queueSize.get() != 0) {
                if(queueSize.get() == 0){
                    try {
                        System.out.println(Thread.currentThread().getName() + ": barber falling asleep");
                        isBarberBusy.set(false);
                        barberCondition.await();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                } else {
                    queueSize.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + ": getting next client");
                    queueCondition.signal();
                    startShaving();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": day is finished");
            barberLock.unlock();
        }
        private void startShaving() {
            System.out.println(Thread.currentThread().getName() + ": shaving started");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class Client extends Thread {
        private boolean isShaved;
        Client() {
            isShaved = false;
        }

        @Override
        public void run(){
            queueLock.lock();
            System.out.println(Thread.currentThread().getName() + ": now in the queue");
            queueSize.incrementAndGet();
            while(!isShaved) {
                if(!isBarberBusy.get()){
                    barberLock.lock();
                    barberCondition.signal();
                    isBarberBusy.set(true);
                    System.out.println(Thread.currentThread().getName() + ": awaking barber");
                    barberLock.unlock();
                } else {
                    try {
                        System.out.println(Thread.currentThread().getName() + ": falling asleep");
                        queueCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            queueLock.unlock();
        }
    }

    public void barbershopWork() {
        Thread barber = new Thread(new Barber());
        barber.start();
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        for(int i = 0; i < 3; i++) {
            Client client = new Client();
            client.start();
            System.out.println(Thread.currentThread().getName() + ":comes to barbershop");
        }
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Client client = new Client();
        client.start();
        System.out.println(Thread.currentThread().getName() + ":comes to barbershop");

        isWorkFinished.set(true);
    }

    public static void main(String[] args) {
        Main bbShop = new Main();
        bbShop.barbershopWork();
    }

}













