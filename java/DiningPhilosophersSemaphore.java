import java.util.concurrent.Semaphore;

class DiningPhilosophersSemaphore {
    static final int N = 5;
    static final Semaphore[] chopsticks = new Semaphore[N];
    static Semaphore room = new Semaphore(N-1);

    static class Philosopher extends Thread {
        int id;
        Philosopher(int id) { this.id = id; };

        public void run(){
            try {
                for(int i = 0; i < 3; i++){ // assumiong we stop execution of thread once the philosopher is done eating thrice.
                    System.out.println("Philosopher " + id + "thinking");
                    Thread.sleep(1000);
                    room.acquire();
                    chopsticks[id].acquire();
                    chopsticks[(id+1)%N].acquire();
                    System.out.println("Philosopher " + id + " eating");
                    Thread.sleep(1000);
                    chopsticks[id].release();
                    chopsticks[(id+1)%N].release();
                    room.release();
                }
            } catch (InterruptedException e){} // you catch an InterruptedException when
            // the thread is performing a blocking operation like sleep or wait.
        }
    }

    public static void main(String[] args){
        for(int i = 0; i < N; i++) chopsticks[i] = new Semaphore(1);
        for(int i = 0; i < N; i++){
            new Philosopher(i).start();
        }
    }
}