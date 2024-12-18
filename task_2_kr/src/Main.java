import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int m= scanner.nextInt();
        int w = scanner.nextInt();

        int[][] field = new int[n][m];

        for (int i=0; i< w; i++) {
            int mine_n= scanner.nextInt() - 1;
            int mine_m= scanner.nextInt() - 1;

            field[mine_n][mine_m]= -1;

            for (int r = mine_n - 1; r <= mine_n + 1; r++) {
                for (int c = mine_m - 1; c <= mine_m + 1; c++) {
                    if (r >= 0 && r < n && c >= 0 && c < m && field[r][c] != -1) {
                        field[r][c] += 1;
                    }
                }
            }
        }
        printField(field);
    }
    private static void printField(int[][] field) {
        for (int[] row : field) {
            for (int cell : row) {
                if (cell == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }
}