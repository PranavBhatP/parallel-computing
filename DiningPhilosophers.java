import java.util.concurrent.locks.ReentrantLock;

class DiningPhilosophers{
    public static void main(String[] args){
        int N = 5;
        final ReentrantLock[] chopsticks = new ReentrantLock[N];

        for(int i = 0; i < N; i++){
            chopsticks[i] = new ReentrantLock();
        }

        class Philosopher extends Thread {
            int id;
            Philosopher(int id){
                this.id = id;
            }

            public void run(){
                ReentrantLock left = chopsticks[id];
                ReentrantLock right = chopsticks[(id+1)%N];

                for(int i = 0; i < 3; i++){
                    left.lock();
                    right.lock();
                    try {
                        System.out.println("Philosopher: " + id + " is eating.");
                    } finally {
                        left.unlock();
                        right.unlock();
                    }
                }
            }
        }

        for(int i = 0; i < N; i++){
            new Philosopher(i).start();
        }
    }
}