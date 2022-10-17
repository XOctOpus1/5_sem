
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("AABD");
        strings.add("ABCD");
        strings.add("ABCD");
        strings.add("CDCD");
        Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < strings.size(); i++) {
            new Thread(new MyThread(strings, i, semaphore)).start();
        }
    }
}

class MyThread implements Runnable {
    private List<String> strings;
    private int index;
    private Semaphore semaphore;

    public MyThread(List<String> strings, int index, Semaphore semaphore) {
        this.strings = strings;
        this.index = index;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaphore.acquire();
                if (strings.get(index).contains("A") && strings.get(index).contains("B")) {
                    if (strings.get(index).contains("C") && strings.get(index).contains("D")) {
                        if (strings.get(index).length() - strings.get(index).replace("A", "").length() == strings.get(index).length() - strings.get(index).replace("B", "").length()) {
                            System.out.println("Thread " + index + " finished");
                            semaphore.release();
                            break;
                        }
                    }
                }
                if (strings.get(index).contains("A")) {
                    strings.set(index, strings.get(index).replaceFirst("A", "C"));
                } else if (strings.get(index).contains("C")) {
                    strings.set(index, strings.get(index).replaceFirst("C", "A"));
                } else if (strings.get(index).contains("B")) {
                    strings.set(index, strings.get(index).replaceFirst("B", "D"));
                } else if (strings.get(index).contains("D")) {
                    strings.set(index, strings.get(index).replaceFirst("D", "B"));
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}







