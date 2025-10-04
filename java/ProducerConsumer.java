import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class ProducerConsumer {
    static final int BUFFER_SIZE = 5;
    static Queue<Integer> buffer = new LinkedList<>();
    static Semaphore empty = new Semaphore(BUFFER_SIZE);
    static Semaphore full = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1); // mutex for buffer.

    static class Producer extends Thread {
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    empty.acquire();
                    mutex.acquire();
                    buffer.add(i+1);
                    System.out.println("Produced: " + (i+1));
                    mutex.release();
                    full.release();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {}
        }
    }

    static class Consumer extends Thread {
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    full.acquire();
                    mutex.acquire();
                    int item = buffer.poll();
                    System.out.println("Consumed: " + item);
                    mutex.release();
                    empty.release();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args){
        new Producer().start();
        new Producer().start();

        new Consumer().start();
        new Consumer().start();
    }
}