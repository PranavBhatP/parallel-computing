class WorkerThread extends Thread {
    public void run(){
        while(!isInterrupted()){
            System.out.println("Working...");
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e){
                break;
            }
        }
        System.out.println("Stopped.");
    }

    // we add throws InterruptedException because the main thread might get interrupted when it is sleeping.
    // we can also use a try catch block for it.
    public static void main(String[] args) {
        WorkerThread t = new WorkerThread();
        t.start();
        // Thread.sleep(3500); // main thread does some work - simply denoted by thread.sleep

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e){}
        t.interrupt();
    }
}