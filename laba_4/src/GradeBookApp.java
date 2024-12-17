import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.util.*;

public class GradeBookApp {

    static class GradeBook {
        private int studentId;
        private String firstName;
        private String lastName;
        private String middleName;
        private int course;
        private String group;
        private List<Session> sessions = new ArrayList<>();

        public GradeBook(@JsonProperty("studentId") int studentId,
                         @JsonProperty("firstName") String firstName,
                         @JsonProperty("lastName") String lastName,
                         @JsonProperty("middleName") String middleName,
                         @JsonProperty("course") int course,
                         @JsonProperty("group") String group) {
            this.studentId = studentId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.middleName = middleName;
            this.course = course;
            this.group = group;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public int getCourse() {
            return course;
        }

        public String getGroup() {
            return group;
        }

        public List<Session> getSessions() {
            return sessions;
        }

        public void addSession(int sessionNumber) {
            sessions.add(new Session(sessionNumber));
        }

        public Session getSession(int sessionNumber) {
            for (Session session : sessions) {
                if (session.sessionNumber == sessionNumber) {
                    return session;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "Зачетная книжка: " + studentId + ", " + lastName + " " + firstName + " " + middleName +
                    ", Курс: " + course + ", Группа: " + group;
        }

        // Внутренний класс Session
        static class Session {
            private int sessionNumber;
            private Map<String, Integer> exams = new HashMap<>();

            public Session(@JsonProperty("sessionNumber") int sessionNumber) {
                this.sessionNumber = sessionNumber;
            }

            public int getSessionNumber() {
                return sessionNumber;
            }

            public Map<String, Integer> getExams() {
                return exams;
            }

            public void addExam(String subject, int grade) {
                exams.put(subject, grade);
            }

            @Override
            public String toString() {
                return "Сессия " + sessionNumber + ", экзамены: " + exams;
            }
        }
    }

    public static void main(String[] args) {
        try {
            List<GradeBook> gradeBooks = readStudentsFromFile("input.txt");

            // Adding "input3.txt" along with the other session files
            List<String> sessionFiles = Arrays.asList("input2.txt", "sessions.txt", "input3.txt");
            readSessionsFromFiles(sessionFiles, gradeBooks);

            // Запись информации только для первого студента в JSON-файл
            String filename = "first_student.json";
            writeFullStudentInfoToJson(filename, gradeBooks);

            // Чтение и вывод на консоль JSON-файла с данными первого студента
            ObjectMapper mapper = new ObjectMapper();
            GradeBook firstStudent = mapper.readValue(new File(filename), GradeBook.class);
            String jsonOutput = mapper.writeValueAsString(firstStudent);

            System.out.println("\nСодержимое JSON-файла первого студента:");
            System.out.println(jsonOutput);  // Вывод JSON-строки на консоль

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Метод чтения данных о студентах
    private static List<GradeBook> readStudentsFromFile(String filename) throws IOException {
        List<GradeBook> gradeBooks = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length < 6) {
                System.err.println("Неправильный формат строки: " + line);
                continue;
            }

            int studentId = Integer.parseInt(parts[0]);
            String lastName = parts[1];
            String firstName = parts[2];
            String middleName = parts[3];
            int course = Integer.parseInt(parts[4]);
            String group = parts[5];

            GradeBook gradeBook = new GradeBook(studentId, firstName, lastName, middleName, course, group);
            gradeBooks.add(gradeBook);
        }

        reader.close();
        return gradeBooks;
    }

    // Метод для чтения сессий из файлов
    public static void readSessionsFromFiles(List<String> filenames, List<GradeBook> gradeBooks) throws IOException {
        for (String filename : filenames) {
            readSessionsFromFile(filename, gradeBooks);
        }
    }

    public static void readSessionsFromFile(String filename, List<GradeBook> gradeBooks) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        String line;
        String subject = null;
        int sessionNumber = -1;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (!line.isEmpty() && !line.startsWith("Сессия:") && subject == null) {
                subject = line;
            } else if (line.startsWith("Сессия:")) {
                sessionNumber = Integer.parseInt(line.split(":")[1].trim());
            } else if (!line.isEmpty() && subject != null && sessionNumber != -1) {
                String[] parts = line.split(" ");
                int gradeBookNumber = Integer.parseInt(parts[0]);
                int grade = Integer.parseInt(parts[1]);

                for (GradeBook gradeBook : gradeBooks) {
                    if (gradeBook.getStudentId() == gradeBookNumber) {
                        GradeBook.Session session = gradeBook.getSession(sessionNumber);
                        if (session == null) {
                            gradeBook.addSession(sessionNumber);
                            session = gradeBook.getSession(sessionNumber);
                        }

                        session.addExam(subject, grade);
                        break;
                    }
                }
            }

            if (line.isEmpty()) {
                subject = null;
                sessionNumber = -1;
            }
        }

        reader.close();
    }

    // Метод для записи информации о первом студенте в JSON-файл
    private static void writeFullStudentInfoToJson(String filename, List<GradeBook> gradeBooks) throws IOException {
        if (gradeBooks.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Записываем только первую зачетную книжку в JSON-файл
        mapper.writeValue(new File(filename), gradeBooks.get(0));
    }

    // Метод для чтения полной информации из JSON-файла
    private static List<GradeBook> readFullStudentInfoFromJson(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Чтение и десериализация JSON в список объектов GradeBook
        return mapper.readValue(new File(filename), new TypeReference<List<GradeBook>>() {});
    }
}
