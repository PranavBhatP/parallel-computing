// 7. Daemon thread prints message every second until main finishes
class DaemonExample {
    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(() -> {
            while (true) {
                System.out.println("Daemon running...");
                try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
            }
        });
        daemon.setDaemon(true);
        daemon.start();
        Thread.sleep(3500); // Main thread runs for 3.5 seconds
        System.out.println("Main thread finished");
    }
}

// This background thread is called a daemon thread because it is 
// created with daemon.setDaemon(true);. In Java, daemon threads are
// service threads that run in the background and do not prevent the
// JVM from exiting when all user (non-daemon) threads have finished. 
// They are typically used for tasks like garbage collection or background monitoring. 
// When the main thread finishes, the JVM automatically stops all daemon threads.
