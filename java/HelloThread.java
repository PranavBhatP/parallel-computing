// 4. Thread prints "Hello" every 2 seconds, 5 times
class HelloThread extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
        }
    }
    public static void main(String[] args) {
        new HelloThread().start();
    }
}