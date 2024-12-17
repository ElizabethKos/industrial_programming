import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Main mainInstance = new Main();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter start character(s): ");
        String startChar = scanner.nextLine();

        System.out.print("Enter end character(s): ");
        String endChar = scanner.nextLine();
        scanner.close();
        File file = new File("input.txt");
        mainInstance.processFile(file, startChar, endChar);
    }


    private void processFile(File file, String startChar, String endChar) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String modifiedLine = removeTextBetween(line, startChar, endChar);
                System.out.println(modifiedLine);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private String removeTextBetween(String line, String startChar, String endChar) {
        int startIndex = line.indexOf(startChar);
        if (startIndex == -1) {
            return line;
        }
        int endIndex = line.lastIndexOf(endChar);
        if (endIndex == -1 || endIndex <= startIndex) {
            return line;
        }
        String beforeStart = line.substring(0, startIndex);
        String afterEnd = line.substring(endIndex + endChar.length());
        return beforeStart + afterEnd;
    }
}
