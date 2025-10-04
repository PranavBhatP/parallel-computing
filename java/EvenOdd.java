public class EvenOdd {
    public static void main(String[] args){
        Runnable r1 = () -> {
            for(int i = 2; i <= 20; i+=2){
                System.out.println(i);
            }
        };

        Runnable r2 = () -> {
            for(int i = 1; i <= 19; i+=2){
                System.out.println(i);
            }
        };

        new Thread(r1).start();
        new Thread(r2).start();
    }
}
