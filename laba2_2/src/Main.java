import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Matrix {
    private int[][] matrix;


    public Matrix(String filename) throws FileNotFoundException {
        readMatrixFromFile(filename);
    }


    private void readMatrixFromFile(String filename) throws FileNotFoundException {
        File inputFile = new File(filename);
        Scanner scanner = new Scanner(inputFile);

        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        scanner.close();
    }

    public int findMaxLocalMin() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        Integer maxLocalMin = null;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isLocalMin(i, j, rows, cols)) {
                    if (maxLocalMin == null || matrix[i][j] > maxLocalMin) {
                        maxLocalMin = matrix[i][j];
                    }
                }
            }
        }
        return maxLocalMin == null ? -1 : maxLocalMin;
    }


    private boolean isLocalMin(int i, int j, int rows, int cols) {
        int current = matrix[i][j];

        for (int x = Math.max(0, i - 1); x <= Math.min(rows - 1, i + 1); x++) {
            for (int y = Math.max(0, j - 1); y <= Math.min(cols - 1, j + 1); y++) {
                if (x == i && y == j) continue;
                if (matrix[x][y] <= current) return false;
            }
        }
        return true;
    }


    public void writeResultToFile(String filename, int maxLocalMin) throws IOException {
        FileWriter outputFile = new FileWriter(filename);

        if (maxLocalMin == -1) {
            outputFile.write("Локальных минимумов не найдено.\n");
        } else {
            outputFile.write("Максимум среди всех локальных минимумов: " + maxLocalMin + "\n");
        }
        outputFile.close();
    }
}

public class Main {
    public static void main(String[] args) {
        try {

            Matrix matrix = new Matrix("input.txt");


            int maxLocalMin = matrix.findMaxLocalMin();


            matrix.writeResultToFile("output.txt", maxLocalMin);

            System.out.println("Результат успешно записан в файл output.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Файл input.txt не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}
