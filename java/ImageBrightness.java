class Image{
    static int N = 5000;
    static int M = 5000;
    int pixels[][] = new int[N][M]; 

    Image(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                pixels[i][j] = (i*j)%20000;
            }
        }
    }

    // Method to calculate total brightness (sum of all pixel values)
    public long calculateTotalBrightness() {
        long totalBrightness = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                totalBrightness += pixels[i][j];
            }
        }
        return totalBrightness;
    }
}

public class ImageBrightness {

    // Single-threaded brightness calculation
    public static long calculateBrightnessSingleThread(Image image) {
        return image.calculateTotalBrightness();
    }

    // Multi-threaded brightness calculation
    public static long calculateBrightnessMultiThread(Image image, int numThreads) throws InterruptedException {
        final long[] partialSums = new long[numThreads];
        Thread[] threads = new Thread[numThreads];
        
        // Divide rows among threads
        int rowsPerThread = Image.N / numThreads;
        int remainingRows = Image.N % numThreads;
        
        for (int t = 0; t < numThreads; t++) {
            final int threadId = t;
            final int startRow = t * rowsPerThread;
            final int endRow = (t == numThreads - 1) ? 
                startRow + rowsPerThread + remainingRows : 
                startRow + rowsPerThread;
            
            threads[t] = new Thread(() -> {
                long sum = 0;
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < Image.M; j++) {
                        sum += image.pixels[i][j];
                    }
                }
                partialSums[threadId] = sum;
            });
            
            threads[t].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Sum partial results
        long totalBrightness = 0;
        for (long partialSum : partialSums) {
            totalBrightness += partialSum;
        }
        
        return totalBrightness;
    }

    // Method to measure execution time
    public static long measureExecutionTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        System.out.println("Image Brightness Calculation Performance Comparison");
        System.out.println("Image size: " + Image.N + "x" + Image.M + " pixels");
        System.out.println("=".repeat(60));
        
        // Create image
        Image image = new Image();
        
        // Number of test iterations for averaging
        int iterations = 100;
        int numThreads = Runtime.getRuntime().availableProcessors(); // Use available CPU cores
        
        System.out.println("Running " + iterations + " iterations...");
        System.out.println("Number of threads for multi-threaded approach: " + numThreads);
        System.out.println();
        
        // Variables to store timing results
        long totalSingleThreadTime = 0;
        long totalMultiThreadTime = 0;
        long singleResult = 0;
        long multiResult = 0;
        
        // Run tests
        for (int i = 0; i < iterations; i++) {
            // Test single-threaded approach
            long singleStartTime = System.nanoTime();
            singleResult = calculateBrightnessSingleThread(image);
            long singleEndTime = System.nanoTime();
            totalSingleThreadTime += (singleEndTime - singleStartTime);
            
            // Test multi-threaded approach
            long multiStartTime = System.nanoTime();
            try {
                multiResult = calculateBrightnessMultiThread(image, numThreads);
            } catch (InterruptedException e) {
                System.err.println("Multi-threaded calculation interrupted: " + e.getMessage());
                return;
            }
            long multiEndTime = System.nanoTime();
            totalMultiThreadTime += (multiEndTime - multiStartTime);
        }
        
        // Calculate averages
        double avgSingleThreadTime = totalSingleThreadTime / (double) iterations;
        double avgMultiThreadTime = totalMultiThreadTime / (double) iterations;
        
        // Display results
        System.out.println("RESULTS:");
        System.out.println("-".repeat(40));
        System.out.println("Total brightness calculated: " + singleResult);
        System.out.println("Results match: " + (singleResult == multiResult));
        System.out.println();
        
        System.out.printf("Single-threaded average time: %.2f ns (%.6f ms)%n", 
                         avgSingleThreadTime, avgSingleThreadTime / 1_000_000);
        System.out.printf("Multi-threaded average time:  %.2f ns (%.6f ms)%n", 
                         avgMultiThreadTime, avgMultiThreadTime / 1_000_000);
        System.out.println();
        
        // Calculate speedup
        double speedup = avgSingleThreadTime / avgMultiThreadTime;
        System.out.printf("Speedup: %.2fx%n", speedup);
        
        if (speedup > 1) {
            System.out.printf("Multi-threaded approach is %.2fx faster%n", speedup);
        } else {
            System.out.printf("Single-threaded approach is %.2fx faster%n", 1/speedup);
        }
        
        // Performance analysis
        System.out.println();
        System.out.println("ANALYSIS:");
        System.out.println("-".repeat(40));
        if (speedup > 1) {
            System.out.println("✓ Multi-threading provided performance benefit");
        } else {
            System.out.println("⚠ Multi-threading overhead exceeded benefits for this problem size");
        }
        
        System.out.println("Note: For small matrices like 50x50, thread creation overhead");
        System.out.println("might outweigh parallelization benefits. Try larger matrices");
        System.out.println("(increase N and M values) to see more significant improvements.");
    }
}
