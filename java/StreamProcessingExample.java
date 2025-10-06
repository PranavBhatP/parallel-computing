import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StreamProcessingExample {
    public static void main(String[] args) {
        String fileName = "students.csv";

        try(BufferedReader br = new BufferedReader(new FileReader((fileName)))){
            br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                System.out.println("Name: " + values[0]);
                System.out.println("Marks: " + values[1]);
                System.out.println("Grades: " + values[2]);
            }
        } catch (IOException e){

        }
    }
}
