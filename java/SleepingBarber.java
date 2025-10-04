import java.util.concurrent.Semaphore;

public class SleepingBarber {
    static final int CHAIRS = 3; // waiting chairs.
    static Semaphore customers = new Semaphore(0); // semaphore on waiting customers, synchronises the handoff between barber
    // and customer. It ensures that the barber is asleep/blocking when there are no customers.
    static Semaphore barber = new Semaphore(0); // semaphore for allowing customers to acquire the barber and ensures that the barber is used
    // by only one customer at a time.
    static Semaphore mutex = new Semaphore(1); // mutex for the waiting variable, ensures mutex while accessing waiting chairs.
    static int waiting = 0;

    static class Barber extends Thread {
        public void run(){
            while(true){
                if(customers.availablePermits() == 0) {
                    System.out.println("Barber is sleeping zzzzzz...");
                }
                customers.acquireUninterruptibly();
                mutex.acquireUninterruptibly();
                waiting--;
                System.out.println("Barber is working.");
                //customers.release();
                mutex.release();
                try {
                    Thread.sleep(200); // cutting hair.
                } catch (InterruptedException e){}
                barber.release();
            }
        }
    }

    static class Customer extends Thread {
        public void run(){
            mutex.acquireUninterruptibly();
            if(waiting < CHAIRS){
                waiting++;
                System.out.println("Customer " + getName() + " is waiting");
                customers.release(); // increase the count of waiting customers.
                mutex.release(); // releas the mutex on waiting.
                barber.acquireUninterruptibly(); // if barber is not free, then thread blocks
                // until barber is free, reserve barber until haircut is done.
                System.out.println("Customer " + getName() + " got haircut.");
            } else {
                System.out.println("Customer " + getName() +  "left (no avalaile chairs).");
                mutex.release(); // release the mutex semaphore on waiting, since the waiting is not updated
                // after the customer leaves.
            }
        }
    }

    public static void main(String[] args){
        new Barber().start();
        for(int i = 0; i < 8; i++){
            try{
                Thread.sleep((int)(Math.random()*150));
            } catch(InterruptedException e){}
            new Customer().start();
        }
    }

}
