import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            List<String> class9 = new ArrayList<>();
            List<String> class10 = new ArrayList<>();
            List<String> class11 = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                int classNumber = Integer.parseInt(parts[0]);
                String lastName = parts[1];
                if (classNumber == 9) {
                    class9.add(lastName);
                } else if (classNumber == 10) {
                    class10.add(lastName);
                } else if (classNumber == 11) {
                    class11.add(lastName);
                }
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));
            for (String name : class9) {
                writer.println("9 " + name);
            }
            for (String name : class10) {
                writer.println("10 " + name);
            }
            for (String name : class11) {
                writer.println("11 " + name);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
