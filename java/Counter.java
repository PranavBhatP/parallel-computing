class Counter implements Runnable{
    int count = 0;
    public synchronized void run(){ // we need the synchronized keyword here, or else the threads will act
        for(int i = 0; i < 5; i++){
            count++;
            System.out.println(Thread.currentThread().getName() + ": " + count);
        }
    }

    public static void main(String[] args){
        Counter counter = new Counter();
        // if two threads are acting on the same runnable 
        Thread t1 = new Thread(counter, "T1");
        Thread t2 = new Thread(counter, "T2");

        t1.start();t2.start();
    }
}