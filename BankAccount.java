class BankAccount{
    private int balance = 100;
    public synchronized void deposit(int amount){ // always use synchronized for shared mutable data.
        int temp = balance;
        temp += amount;
        balance = temp;
    }

    public int getBalance() { return balance; } 

    public static void main(String [] args) throws InterruptedException {
        BankAccount accn = new BankAccount();
        DepositTask d = new DepositTask(accn);
        Thread t1 = new Thread(d);
        Thread t2 = new Thread(d);

        t1.start(); t2.start();
        t1.join(); t2.join(); // best practice to wait for the thread to end before the main thread continues.

        System.out.println("Final balance: " + accn.getBalance());
    }
}

class DepositTask implements Runnable{
    private BankAccount account;
    public DepositTask(BankAccount account){
        this.account = account;
    }
    public void run(){
        for(int i = 0; i < 1000; i++){
            account.deposit(1);
        }
    }
}