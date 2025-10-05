import java.util.concurrent.Semaphore;

public class CigaretteSmokers {
    static Semaphore agent = new Semaphore(1);
    static Semaphore tobacco = new Semaphore(0);
    static Semaphore paper = new Semaphore(0);
    static Semaphore match = new Semaphore(0);

    // The agent places two random items on the table
    static class Agent extends Thread {
        public void run() {
            try {
                while (true) {
                    agent.acquire(); // Wait until smoker signals table is free

                    int choice = (int)(Math.random() * 3);
                    switch (choice) {
                        case 0:
                            System.out.println("\nAgent puts TOBACCO and PAPER on the table.");
                            tobacco.release();
                            paper.release();
                            break;
                        case 1:
                            System.out.println("\nAgent puts PAPER and MATCH on the table.");
                            paper.release();
                            match.release();
                            break;
                        case 2:
                            System.out.println("\nAgent puts TOBACCO and MATCH on the table.");
                            tobacco.release();
                            match.release();
                            break;
                    }

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Each smoker waits for the ingredients they need
    static class Smoker extends Thread {
        private final String name;
        private final Semaphore firstIngredient;
        private final Semaphore secondIngredient;

        Smoker(String name, Semaphore first, Semaphore second) {
            this.name = name;
            this.firstIngredient = first;
            this.secondIngredient = second;
        }

        public void run() {
            try {
                while (true) {
                    firstIngredient.acquire();
                    secondIngredient.acquire();

                    System.out.println(name + " has all ingredients and is now SMOKING!");
                    Thread.sleep(1000);
                    System.out.println(name + " finished smoking and cleared the table.");

                    agent.release(); // Let agent place new items
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Agent().start();

        // Three smokers, each with one ingredient
        new Smoker("Smoker with MATCHES", tobacco, paper).start();
        new Smoker("Smoker with PAPER", tobacco, match).start();
        new Smoker("Smoker with TOBACCO", paper, match).start();
    }
}
