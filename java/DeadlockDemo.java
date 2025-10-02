import java.util.concurrent.locks.ReentrantLock;

class DeadlockDemo {
    public static void main(String[] args) {
        ReentrantLock lockA = new ReentrantLock();
        ReentrantLock lockB = new ReentrantLock();

        Runnable r1 = () -> {
            lockA.lock();
            try {
                Thread.sleep(100);
                lockB.lock();
                try { System.out.println("Thread 1 acquired both locks"); }
                finally { lockB.unlock(); }
            } catch (InterruptedException e) {}
            finally { lockA.unlock(); }
        };

        Runnable r2 = () -> {
            lockA.lock();
            try {
                Thread.sleep(100);
                lockB.lock();
                try { System.out.println("Thread 2 acquired both locks"); }
                finally { lockB.unlock(); }
            } catch (InterruptedException e) {}
            finally { lockA.unlock(); }
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }
}