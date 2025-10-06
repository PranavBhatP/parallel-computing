import java.io.*;
public class TextFileExample {
    public static void main(String[] args){
        String filename = "notes.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Java is powerful.\n");
            writer.write("File I/O is important.\n");
            writer.write("Wrote to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            System.out.println("Reading from file...\n");
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
