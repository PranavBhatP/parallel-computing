class MyRunnable implements Runnable { // runnable provide an interface which is implemented by any class whose instances are intended to be executed by a thread.. the class must define a method of no arugment called run.
    public void run(){
        // run defines the code that will execute when the thread starts.
        // a class can be active without subclassing the Thread by implementing Runnable
        // This interface should be used only if you are planning to override run.
        // Classes should not be simply subclassed unless the user intends to fully modify
        // or enhancing the fundamental features of the said class.
        Thread t = Thread.currentThread();
        System.out.println("Thread name " + t.getName());
        System.out.println("Thread priority " + t.getPriority());
    
    }
    public static void main(String [] args){
        Thread t = new Thread(new MyRunnable());
        t.setName("Runnable Thread");
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }
}