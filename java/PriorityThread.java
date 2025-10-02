public class PriorityThread extends Thread {
    public PriorityThread(String name){
        super(name);
    }

    public void run(){
        System.out.println(getName() + " with priority" + getPriority());
    }

    public static void main(String[] args){
        PriorityThread p1 = new PriorityThread("MAX");
        PriorityThread p2 = new PriorityThread("NORM");
        PriorityThread p3 = new PriorityThread("MIN");

        p1.setPriority(Thread.MAX_PRIORITY);
        p2.setPriority(Thread.NORM_PRIORITY);
        p3.setPriority(Thread.MIN_PRIORITY);

        // the order of execution of threads is handled by the JVM.
        p1.start();
        p3.start();
        p2.start();
    }
}
