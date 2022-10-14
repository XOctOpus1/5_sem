import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class Main {
    public static void main(String[] args) throws IOException {
        int n = 10;
        int m = 10;
        int[][] garden = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                garden[i][j] = 0;
            }
        }
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Thread gardener = new Thread(new Gardener(garden, lock));
        Thread nature = new Thread(new Nature(garden, lock));
        Thread monitor1 = new Thread(new Monitor1(garden, lock));
        Thread monitor2 = new Thread(new Monitor2(garden, lock));
        gardener.start();
        nature.start();
        monitor1.start();
        monitor2.start();
    }
}

class Gardener implements Runnable {
    private int[][] garden;
    private ReentrantReadWriteLock lock;

    public Gardener(int[][] garden, ReentrantReadWriteLock lock) {
        this.garden = garden;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.writeLock().lock();
            for (int i = 0; i < garden.length; i++) {
                for (int j = 0; j < garden[i].length; j++) {
                    if (garden[i][j] == 1) {
                        garden[i][j] = 2;
                    }
                }
            }
            lock.writeLock().unlock();
        }
    }
}

class Nature implements Runnable {
    private int[][] garden;
    private ReentrantReadWriteLock lock;

    public Nature(int[][] garden, ReentrantReadWriteLock lock) {
        this.garden = garden;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.writeLock().lock();
            Random random = new Random();
            int i = random.nextInt(garden.length);
            int j = random.nextInt(garden[i].length);
            garden[i][j] = 1;
            lock.writeLock().unlock();
        }
    }
}

class Monitor1 implements Runnable {
    private int[][] garden;
    private ReentrantReadWriteLock lock;

    public Monitor1(int[][] garden, ReentrantReadWriteLock lock) {
        this.garden = garden;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.readLock().lock();
            try (FileWriter writer = new FileWriter("garden.txt", true)) {
                for (int i = 0; i < garden.length; i++) {
                    for (int j = 0; j < garden[i].length; j++) {
                        writer.write(garden[i][j] + " ");
                    }
                    writer.write("\n");
                }
                writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            lock.readLock().unlock();
        }
    }
}

class Monitor2 implements Runnable {
    private int[][] garden;
    private ReentrantReadWriteLock lock;

    public Monitor2(int[][] garden, ReentrantReadWriteLock lock) {
        this.garden = garden;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.readLock().lock();
            for (int i = 0; i < garden.length; i++) {
                for (int j = 0; j < garden[i].length; j++) {
                    System.out.print(garden[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
            lock.readLock().unlock();
        }
    }
}
