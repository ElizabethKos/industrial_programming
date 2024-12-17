package org.example;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите размер массива: ");
        int size = scanner.nextInt();


        Integer[] array = generateUniqueRandomArray(size);

        System.out.print("Исходный массив: ");
        printArray(array);

        System.out.print("Введите порядок сортировки (1 для возрастания, 2 для убывания): ");
        String sortOrder = scanner.next();

        // Создание потока для сортировки массива
        Thread sortThread = new Thread(new SortTask(array, sortOrder));
        sortThread.start();

        try {
            sortThread.join();
        } catch (InterruptedException e) {
            System.out.println("Ошибка: поток был прерван.");
        }

        System.out.print("Отсортированный массив: ");
        printArray(array);


        writeArrayToFile("sorted_array.txt", array);

        scanner.close();
    }


    private static Integer[] generateUniqueRandomArray(int size) {
        int[] array = new int[size];


        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }


        shuffleArray(array);

        Integer[] result = new Integer[size];
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }

        return result;
    }


    public static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Меняем элементы местами
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    private static void printArray(Integer[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }


    private static void writeArrayToFile(String fileName, Integer[] array) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int num : array) {
                writer.write(num + " ");
            }
            writer.write("\n");
            System.out.println("Результат записан в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}

class SortTask implements Runnable {
    private Integer[] array;
    private String sortOrder;

    public SortTask(Integer[] array, String sortOrder) {
        this.array = array;
        this.sortOrder = sortOrder;
    }

    @Override
    public void run() {
        if ("1".equalsIgnoreCase(sortOrder)) {
            Arrays.sort(array, Comparator.naturalOrder());
        } else if ("2".equalsIgnoreCase(sortOrder)) {
            Arrays.sort(array, Comparator.reverseOrder());
        } else {
            System.out.println("Ошибка: некорректный порядок сортировки. Используйте '1' или '2'.");
        }
    }
}
