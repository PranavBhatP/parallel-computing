import  java.util.concurrent.Semaphore;
public class ReadersWriters {
    static int readCount = 0;
    static Semaphore mutex = new Semaphore(1);
    static Semaphore wrt = new Semaphore(1);
    static int sharedData = 0; // sample shared data to simulate a simple integer variable

    static class Reader extends Thread{

        public void run(){
            try {
                while(true){
                    mutex.acquire();
                    readCount++;
                    if(readCount == 1) wrt.acquire();
                    mutex.release();

                    System.out.println("Reader " + Thread.currentThread().getName() + " reads: " + sharedData);
                    Thread.sleep(200);

                    mutex.acquire();
                    readCount--;
                    if(readCount == 0) wrt.release();
                    mutex.release();

                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {}
        }
    }

    static class Writer extends Thread {
        public void run(){
            try {
                wrt.acquire();
                sharedData++;
                System.out.println("Writing to shared variable: " + sharedData);
                wrt.release();
                Thread.sleep(1000);
            } catch (InterruptedException e){}
        }
    }

    public static void main(String[] args) {
        new Writer().start();
        for(int i = 0; i < 3; i++){
            new Reader().start();
        }
        new Writer().start();
    }
}
