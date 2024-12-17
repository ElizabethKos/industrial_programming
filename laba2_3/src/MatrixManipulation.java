//37.Переставляя ее строки и столбцы, добиться, чтобы наименьший элемент (один из них)
//оказался в нижнем правом углу. Вывести на экран полученную матрицу.

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
    // Метод для перестановки строк
    private void swapRows(int[][] matrix, int row1, int row2) {
        if (row1 != row2) {
            int[] temp = matrix[row1];
            matrix[row1] = matrix[row2];
            matrix[row2] = temp;
        }
    }

    // Метод для перестановки столбцов
    private void swapColumns(int[][] matrix, int col1, int col2) {
        if (col1 != col2) {
            for (int i = 0; i < matrix.length; i++) {
                int temp = matrix[i][col1];
                matrix[i][col1] = matrix[i][col2];
                matrix[i][col2] = temp;
            }
        }
    }

    // Метод для поиска наименьшего элемента и его перестановки в нижний правый угол
    public void moveMinToBottomRight() {
        int minRow = 0;
        int minCol = 0;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] < minValue) {
                    minValue = matrix[i][j];
                    minRow = i;
                    minCol = j;
                }
            }
        }

        // Перестановка строки с минимальным элементом в последнюю строку
        swapRows(matrix, minRow, matrix.length - 1);
        // Перестановка столбца с минимальным элементом в последний столбец
        swapColumns(matrix, minCol, matrix[0].length - 1);
    }

    // Метод для записи матрицы в файл
    public void writeMatrixToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (int[] row : matrix) {
            for (int value : row) {
                writer.write(value + "\t");
            }
            writer.write("\n");
        }
        writer.close();
    }
}

public class MatrixManipulation {

    public static void main(String[] args) {
        try {

            Matrix matrix = new Matrix("input.txt");

// Перемещение минимального элемента в нижний правый угол
            matrix.moveMinToBottomRight();


            matrix.writeMatrixToFile("output.txt");

            System.out.println("Результат успешно записан в файл output.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Файл input.txt не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}

