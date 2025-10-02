import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

class SafeArrayList{
    private final List<Integer> list = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void add(int val){
        lock.lock();
        try {
            list.add(val);
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        SafeArrayList safeList = new SafeArrayList();
        Runnable r = () -> {
            for(int i = 0; i < 100; i++){
                safeList.add(i);
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();t2.start();
        t1.join();t2.join();

        System.out.println("Array has: " + safeList.list.size());
    }
}