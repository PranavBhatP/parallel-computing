import java.util.concurrent.locks.ReentrantLock;

class TryLockDemo {
    public static void main(String[] args){
        ReentrantLock lock = new ReentrantLock();

        Runnable r1 = () -> {
            if(lock.tryLock()){ // tryLock does not care about if the lock is set to be a fair lock.
                try {
                    System.out.println("Thread 1 acquired lock.");
                    Thread.sleep(500);
                } catch (InterruptedException e){}
                finally {
                    lock.unlock();
                }
            } else {
                System.out.println("thread 1 failed to acquire lock.");
            }
        };

        Runnable r2 = () -> {
            if(lock.tryLock()){
                try{
                    System.out.println("Thread 2 acquired the lock.");
                    Thread.sleep(500);
                } catch (InterruptedException e){}
                finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Thread 2 was unable to acquire lock.");
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t2.start();
        t1.start();
    }
}