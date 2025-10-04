public class ECommerceOrderProcessor {
    public static void main(String[] args) {
        // Create tasks for order processing
        Runnable paymentProcessing = () -> {
            System.out.println("Payment Processing started - Priority: " + Thread.currentThread().getPriority());
            try {
                Thread.sleep(1000); // Simulate payment processing time
                System.out.println("✓ Payment validated and processed successfully");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable inventoryUpdate = () -> {
            System.out.println("Inventory Update started - Priority: " + Thread.currentThread().getPriority());
            try {
                Thread.sleep(800); // Simulate inventory update time
                System.out.println("✓ Stock quantity reduced for ordered items");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable emailNotification = () -> {
            System.out.println("Email Notification started - Priority: " + Thread.currentThread().getPriority());
            try {
                Thread.sleep(500); // Simulate email sending time
                System.out.println("✓ Order confirmation email sent to customer");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Create threads with different priorities
        Thread paymentThread = new Thread(paymentProcessing, "PaymentThread");
        Thread inventoryThread = new Thread(inventoryUpdate, "InventoryThread");
        Thread emailThread = new Thread(emailNotification, "EmailThread");

        // Set priorities
        // NOTE: this is not for setting the actuale execution order.
        // provides priorities mainly when the process is long-running
        // and CPU-intensive.
        paymentThread.setPriority(Thread.MAX_PRIORITY);    // 10
        emailThread.setPriority(Thread.NORM_PRIORITY);     // 5
        inventoryThread.setPriority(Thread.MIN_PRIORITY);  // 1

        System.out.println("Starting order processing tasks...");
        System.out.println("=".repeat(50));

        // Start all threads
        paymentThread.start();
        inventoryThread.start();
        emailThread.start();

        // Wait for all threads to complete
        try {
            paymentThread.join();
            inventoryThread.join();
            emailThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("=".repeat(50));
        System.out.println("All order processing tasks completed!");
    }
}