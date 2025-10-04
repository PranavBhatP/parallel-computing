class BankAccount {
    private double balance;
    private String accountHolder;

    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    // Synchronized deposit method
    public synchronized void deposit(double amount, String threadName) {
        if (amount > 0) {
            System.out.printf("[%s] Depositing $%.2f%n", threadName, amount);
            balance += amount;
            System.out.printf("[%s] Deposit successful. New balance: $%.2f%n", threadName, balance);
        } else {
            System.out.printf("[%s] Invalid deposit amount: $%.2f%n", threadName, amount);
        }
    }

    // Synchronized withdraw method
    public synchronized void withdraw(double amount, String threadName) {
        if (amount > 0 && amount <= balance) {
            System.out.printf("[%s] Withdrawing $%.2f%n", threadName, amount);
            balance -= amount;
            System.out.printf("[%s] Withdrawal successful. New balance: $%.2f%n", threadName, balance);
        } else if (amount > balance) {
            System.out.printf("[%s] Insufficient funds. Balance: $%.2f, Requested: $%.2f%n", 
                             threadName, balance, amount);
        } else {
            System.out.printf("[%s] Invalid withdrawal amount: $%.2f%n", threadName, amount);
        }
    }

    public synchronized double getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}

public class BankAccountDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("John Smith", 1000.00);
        
        System.out.println("Bank Account Demo - Synchronized Operations");
        System.out.println("Account Holder: " + account.getAccountHolder());
        System.out.println("Initial Balance: $" + account.getBalance());
        System.out.println("=".repeat(50));

        // Thread 1: Deposit operations
        Thread depositThread = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                account.deposit(100.0 * i, "DepositThread");
                try {
                    Thread.sleep(100); // Small delay between operations
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "DepositThread");

        // Thread 2: Withdrawal operations
        Thread withdrawThread = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                account.withdraw(150.0 * i, "WithdrawThread");
                try {
                    Thread.sleep(100); // Small delay between operations
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "WithdrawThread");

        // Start both threads
        depositThread.start();
        withdrawThread.start();

        // Wait for both threads to complete
        try {
            depositThread.join();
            withdrawThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("=".repeat(50));
        System.out.println("Final Balance: $" + account.getBalance());
        System.out.println("All transactions completed successfully!");
    }
}