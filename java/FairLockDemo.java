import java.util.concurrent.locks.ReentrantLock;

class FairLockDemo {
    public static void main(String[] args){
        ReentrantLock fairLock = new ReentrantLock(true);
        ReentrantLock unfairLock = new ReentrantLock(false);

        Runnable task = () -> {
            for(int i = 0; i < 3; i++){
                fairLock.lock();
                try {
                    System.out.println("Fair: " + Thread.currentThread().getName());
                } finally {
                    fairLock.unlock();
                }

                unfairLock.lock();
                try {
                    System.out.println("Unfair: " + Thread.currentThread().getName());
                } finally {
                    unfairLock.unlock();
                }
            }
        };

        for(int i = 0; i < 3; i++){
            new Thread(task, "Thread" + i).start(); // fair lock follows the order.
            // unfair lock does not, any thread can enter and acquire the lock while other continue to wait.
        }
    }
}