import java.io.*;
import java.util.concurrent.locks.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the name of the file: ");
        String fileName = reader.readLine();
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        System.out.println("Enter the number of readers: ");
        int readers = Integer.parseInt(reader.readLine());
        System.out.println("Enter the number of writers: ");
        int writers = Integer.parseInt(reader.readLine());
        ReadWriteLock lock = new ReentrantReadWriteLock();
        for (int i = 0; i < readers; i++) {
            new Thread(new Reader(lock, file)).start();
        }
        for (int i = 0; i < writers; i++) {
            new Thread(new Writer(lock, file)).start();
        }
    }
}

class Reader implements Runnable {
    private ReadWriteLock lock;
    private File file;

    public Reader(ReadWriteLock lock, File file) {
        this.lock = lock;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            lock.readLock().lock();
            System.out.println("Reader " + Thread.currentThread().getName() + " started");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println("Reader " + Thread.currentThread().getName() + " finished");
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            lock.readLock().unlock();
        }
    }
}

class Writer implements Runnable {
    private ReadWriteLock lock;
    private File file;

    public Writer(ReadWriteLock lock, File file) {
        this.lock = lock;
        this.file = file;
    }

    @Override
    public void run() {
        try {
            lock.writeLock().lock();
            System.out.println("Writer " + Thread.currentThread().getName() + " started");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("Name " + Thread.currentThread().getName() + " - phone " + Thread.currentThread().getName());
            writer.newLine();
            writer.close();
            System.out.println("Writer " + Thread.currentThread().getName() + " finished");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}

// Enter the name of the file:
// file.txt
// Enter the number of readers:
// 3
// Enter the number of writers:
// 3
// Writer Thread-1 started
// Writer Thread-1 finished
// Writer Thread-2 started
// Writer Thread-2 finished
// Writer Thread-3 started
// Writer Thread-3 finished
// Reader Thread-0 started
// Name Thread-1 - phone Thread-1
// Name Thread-2 - phone Thread-2
// Name Thread-3 - phone Thread-3
// Reader Thread-0 finished
// Reader Thread-1 started
// Name Thread-1 - phone Thread-1
// Name Thread-2 - phone Thread-2
// Name Thread-3 - phone Thread-3
// Reader Thread-1 finished
// Reader Thread-2 started
// Name Thread-1 - phone Thread-1
// Name Thread-2 - phone Thread-2
// Name Thread-3 - phone Thread-3
// Reader Thread-2 finished

