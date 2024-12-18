import java.io.*;
import java.util.*;

class Sportsman {
    private String name;
    private String competition;
    private int degree;

    public Sportsman(String name, String competition, int degree) {
        this.name = name;
        this.competition = competition;
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public String getCompetition() {
        return competition;
    }

    public int getDegree() {
        return degree;
    }

    @Override
    public String toString() {
        return "Фамилия: " + name + ", Соревнование: " + competition + ", Диплом: " + degree;
    }
}

public class Main {
    public static void main(String[] args) {

        String inputFile = "input.txt";
        String outputFile = "output.txt";
        String sortedByDegreeFile = "sorted_by_degree.txt";
        String sortedByNameFile = "sorted_by_name.txt";


        Map<String, List<Sportsman>> competitionMap = new HashMap<>();
        List<Sportsman> allSportsmen = new ArrayList<>();

        // Считываем данные из файла
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                if (data.length == 3) {
                    String name = data[0];                 // Фамилия
                    String competition = data[1];          // Соревнование
                    int degree = Integer.parseInt(data[2]); // Степень диплома

                    //  Sportsman
                    Sportsman sportsman = new Sportsman(name, competition, degree);
                    allSportsmen.add(sportsman); //в общий список

                    //  в список соревнования
                    competitionMap.computeIfAbsent(competition, k -> new ArrayList<>()).add(sportsman);
                } else {
                    System.out.println("Некорректная строка: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        // данные по соревнованиям в исходном порядке
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (Map.Entry<String, List<Sportsman>> entry : competitionMap.entrySet()) {
                String competition = entry.getKey(); //текущее соревнование
                List<Sportsman> sportsmen = entry.getValue(); //текущий список спортсменов

                writer.write("Соревнование: " + competition + "\n");


                for (Sportsman sportsman : sportsmen) {
                    writer.write("  - " + sportsman.toString() + "\n");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }

        //  сортировкой по степени диплома
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sortedByDegreeFile))) {
            for (Map.Entry<String, List<Sportsman>> entry : competitionMap.entrySet()) {
                String competition = entry.getKey();
                List<Sportsman> sportsmen = entry.getValue();


                sportsmen.sort(Comparator.comparingInt(Sportsman::getDegree));

                writer.write("Соревнование: " + competition + "\n");

                for (Sportsman sportsman : sportsmen) {
                    writer.write("  - " + sportsman.toString() + "\n");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }

        //  сортировкой по фамилиям
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sortedByNameFile))) {

            allSportsmen.sort(Comparator.comparing(Sportsman::getName));

            writer.write("Список спортсменов (по фамилиям):\n");

            for (Sportsman sportsman : allSportsmen) {
                writer.write(sportsman.toString() + "\n");
            }

        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }

        System.out.println("Данные успешно обработаны и записаны.");
    }
}
