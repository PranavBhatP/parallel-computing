public class TotalSales {
    static int[] prices = {3250, 1250, 455};
    static int[] quantities = {1200, 2300, 500};
    
    public static long calculateTotalSalesSingleThread(){
        int totalCharge = 0;
        for(int i = 0; i < 400; i++){
            totalCharge += (i*5)%100;
        }

        for(int i = 0; i < 3; i++){
            totalCharge += prices[i]*quantities[i];
        }

        return totalCharge;
    }

    public static long calculateTotalSalesMultipleThread(int num_threads) throws InterruptedException{
        //int totalSales = 0;
        Thread[] threads = new Thread[num_threads];
        long[] partialSums = new long[num_threads];

        int itemsPerThread = 400/num_threads;
        int remainingItems = 400 % num_threads;

        for(int i = 0; i < num_threads; i++){
            int threadIdx = i;
            int startIdx = i * itemsPerThread;
            int endIdx = (i == num_threads-1) ? (
                startIdx + itemsPerThread + remainingItems 
            ) : startIdx + itemsPerThread;
            

            threads[i] = new Thread(() -> {
                for(int idx = startIdx; idx < endIdx; idx++){
                    partialSums[threadIdx] += (idx * 5) % 100; 
                }
            });

            threads[i].start();
        }

        for(Thread thread:threads){
            thread.join();
        }

        long totalSales = 0;
        for(int i = 0; i < num_threads; i++){
            totalSales += partialSums[i];
        }

         for(int i = 0; i < 3; i++){
            totalSales += prices[i]*quantities[i];
        }

        return totalSales;
    }

    public static void main(String[] args) throws InterruptedException{
        int numThreads = 2;

        long totalSingleThreadTime = 0;
        long totalMultiThreadTime = 0;
        long singleResult = 0;
        long multiResult = 0;

        long singleStartTime = System.nanoTime();
        long totalSalesSingle = calculateTotalSalesSingleThread();
        long singleEndTime = System.nanoTime();
        
        long multiStartTime = System.nanoTime();
        long totalSalesMulti = calculateTotalSalesMultipleThread(numThreads);
        long multiEndTime = System.nanoTime();

        long totalSingleTime = singleEndTime - singleStartTime;
        long totalMultiTime = multiEndTime - multiStartTime;

        System.out.println("Single Thread Time: " + totalSingleTime + " " + totalSalesSingle);
        System.out.println("Multi Threaded Time: " + totalMultiTime + " " + totalSalesMulti);
    }

    //single thread is better due to multi-threading overhead.
}
