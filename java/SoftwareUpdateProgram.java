public class SoftwareUpdateProgram{
    private static boolean downloadComplete = false;
    private static String fileName = "file_name";

    public static void main(String[] args){
        Thread downloadThread = new Thread(() -> {
            try {
                System.out.println("Downloading: ");
                for(int t = 0; t <= 100; t++){
                    System.out.println(t + "%");
                    Thread.sleep(200);
                }
                downloadComplete = true;
                System.out.println("Download Complete");
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Download failed!");
            }
        }, "DownloadThread");

        downloadThread.start();

        try {
            System.out.println("⏳ Main thread waiting for download to complete...");
            downloadThread.join(); // Wait for download thread to finish
            
            // Main thread continues after download is complete
            if (downloadComplete) {
                System.out.println("\n🔄 Starting software update process...");
                Thread.sleep(1000);
                System.out.println("📖 Reading update file...");
                Thread.sleep(1000);
                System.out.println("📊 Generating update report...");
                Thread.sleep(1000);
                System.out.println("🔧 Applying updates...");
                Thread.sleep(1500);
                System.out.println("✅ Software update completed successfully!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("❌ Update process interrupted!");
        }
    }
}