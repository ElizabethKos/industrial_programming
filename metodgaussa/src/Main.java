import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


class Matrix {
    private double[][] matrix;


    public Matrix(String filename) throws FileNotFoundException {
        readMatrixFromFile(filename);
    }


    private void readMatrixFromFile(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);

        int n = scanner.nextInt();
        matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        scanner.close();
    }


    public double calculateDeterminant() {
        int n = matrix.length;
        double det = 1;

        double[][] tempMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, tempMatrix[i], 0, n);
        }

        for (int i = 0; i < n; i++) {
            if (tempMatrix[i][i] == 0) {
                boolean swapped = false;
                for (int j = i + 1; j < n; j++) {
                    if (tempMatrix[j][i] != 0) {
                        swapRows(tempMatrix, i, j);
                        det *= -1;
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    return 0;
                }
            }

            for (int j = i + 1; j < n; j++) {
                double factor = tempMatrix[j][i] / tempMatrix[i][i];
                for (int k = i; k < n; k++) {
                    tempMatrix[j][k] -= factor * tempMatrix[i][k];
                }
            }

            det *= tempMatrix[i][i];
        }

        return det;
    }


    private void swapRows(double[][] matrix, int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    public void writeDeterminantToFile(String fileName, double determinant) throws IOException {
        FileWriter outputFile = new FileWriter(fileName);
        outputFile.write("Определитель матрицы: " + determinant + "\n");
        outputFile.close();
    }
}


public class Main {
    public static void main(String[] args) {
        try {

            Matrix matrix = new Matrix("input.txt");


            double determinant = matrix.calculateDeterminant();


            matrix.writeDeterminantToFile("output.txt", determinant);

            System.out.println("Результат записан в файл output.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Файл input.txt не найден.");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}
