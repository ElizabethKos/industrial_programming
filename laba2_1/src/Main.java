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

    // Нахождение строки с минимальной длиной максимальной серии одинаковых элементов
    public int findRowWithMinMaxSeries() {
        int minSeriesLength = Integer.MAX_VALUE;
        int resultRow = -1;

        for (int i = 0; i < matrix.length; i++) {
            int maxSeriesLength = findMaxSeriesLength(matrix[i]);

            if (maxSeriesLength < minSeriesLength) {
                minSeriesLength = maxSeriesLength;
                resultRow = i;
            }
        }

        return (minSeriesLength == 1) ? -1 : resultRow;
    }

    // Нахождение максимальной длины серии одинаковых элементов в строке
    private int findMaxSeriesLength(int[] row) {
        int maxSeriesLength = 1;
        int currentSeriesLength = 1;

        for (int i = 1; i < row.length; i++) {
            if (row[i] == row[i - 1]) {
                currentSeriesLength++;
            } else {
                currentSeriesLength = 1;
            }
            maxSeriesLength = Math.max(maxSeriesLength, currentSeriesLength);
        }

        return (maxSeriesLength == 1) ? -1 : maxSeriesLength;
    }

    public void writeResultToFile(String filename, int resultRow) throws IOException {
        FileWriter outputFile = new FileWriter(filename);

        if (resultRow == -1) {
            outputFile.write("Нет");
        } else {
            outputFile.write("Строка с минимальной длиной максимальной серии: " + (resultRow + 1) + "\n");
            outputFile.write("Элементы строки: ");
            for (int j = 0; j < matrix[resultRow].length; j++) {
                outputFile.write(matrix[resultRow][j] + " ");
            }
        }
        outputFile.close();
    }
}


public class Main {

    public static void main(String[] args) {
        try {

            Matrix matrix = new Matrix("input.txt");


            int resultRow = matrix.findRowWithMinMaxSeries();


            matrix.writeResultToFile("output.txt", resultRow);

            System.out.println("Результат успешно записан в файл output.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Файл input.txt не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}
