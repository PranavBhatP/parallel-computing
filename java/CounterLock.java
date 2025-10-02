import java.util.concurrent.locks.ReentrantLock;

class CounterLock {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock(false);

    public void increment(){
        lock.lock();
        try {  // ensures the lock is released after the counter update even if an exception occurs.
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount(){
        return count;
    }

    public static void main(String[] args) throws InterruptedException{
        CounterLock counter = new CounterLock();
        Runnable r = () -> {
            for(int i = 0; i < 1000; i++){
                counter.increment();
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();t2.start();
        t1.join();t2.join();

        System.out.println("Final count: " + counter.getCount());
    }
}