public class WebServerLogDemo {
    public static void main(String[] args) {
        System.out.println("Web Server Security Log Monitor");
        System.out.println("=".repeat(60));

        // Background thread that continuously logs failed access attempts
        Thread loggerThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("com.example.UserService – User 'john.doe' attempted to access unauthorized resource /admin/settings");
                    Thread.sleep(1000); // Log every second
                }
            } catch (InterruptedException e) {
                // Thread was interrupted, clean up and exit gracefully
                System.out.println("\n🛑 Security logging thread interrupted - stopping gracefully");
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }, "SecurityLogger");

        // Firewall thread that will stop the logger after 10 seconds
        Thread firewallThread = new Thread(() -> {
            try {
                System.out.println("🔥 Firewall monitoring started...");
                Thread.sleep(10000); // Wait for 10 seconds
                
                System.out.println("\n🚨 FIREWALL ALERT: Too many failed attempts detected!");
                System.out.println("🔒 Initiating security protocol - stopping unauthorized access logging");
                
                // Interrupt the logger thread
                loggerThread.interrupt();
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Firewall thread interrupted");
            }
        }, "FirewallMonitor");

        // Start both threads
        loggerThread.start();
        firewallThread.start();

        // Wait for both threads to complete
        try {
            loggerThread.join();
            firewallThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🛡️ Security monitoring completed - system secured");
    }
}