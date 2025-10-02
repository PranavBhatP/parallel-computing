class LetterThread extends Thread{
    public void run(){
        for(char i = 'A' ; i <= 'E'; i++){
            System.out.println(i);
        }
    }

    public static void main(String[] args){
        new NumberThread().start();
        new LetterThread().start();
    }
}

class NumberThread extends Thread {
    public void run(){
        for(int i = 0; i < 5; i++){
            System.out.println(i);
        }
    } // output is interleaved, because both thread execute simultaneously.
}