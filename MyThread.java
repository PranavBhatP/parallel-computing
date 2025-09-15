class MyThread extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++){
            System.out.println("Message" + (i+1));
        }
    }

    public static void main(String [] args){
        new MyThread().start();
    }
}