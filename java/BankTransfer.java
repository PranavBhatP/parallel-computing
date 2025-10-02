import java.util.concurrent.locks.ReentrantLock;

class Account {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();

    public Account(int balance) {
        this.balance = balance;
    }

    public void deposit(int amt){
        lock.lock();
        try { balance += amt; } finally { lock.unlock(); }
    }

    public void transfer(Account to, int amt){
        lock.lock();
        
        try{
            if(amt > balance){
                System.out.println("Insufficient Balance.");
                // return;
            } else {
                balance-=amt;
                to.deposit(amt);
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance(){
        return balance;
    }
}

class BankTransfer{
    public static void main(String[] args) throws InterruptedException{
        Account a = new Account(1000);
        Account b = new Account(1000);

        Runnable r = () -> {
            for(int i = 0; i < 100; i++){
                a.transfer(b, 1);
            }
        };

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();t2.start();
        t1.join();t2.join();

        System.out.println("Final balance of a: " + a.getBalance());
        System.out.println("Final balance of b: " + b.getBalance());
    }
}