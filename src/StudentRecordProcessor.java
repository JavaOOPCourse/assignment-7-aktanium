import java.io.*;
import java.util.*;

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        String path = "data/students.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;

            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        throw new IllegalArgumentException();
                    }

                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    validateScore(score);

                    students.add(new Student(name, score));
                    System.out.println("Valid: " + line);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println("Invalid score: " + line);
                } catch (Exception e) {
                    System.out.println("Invalid format: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        if (students.isEmpty()) {
            System.out.println("No valid students.");
            return;
        }

        // сортировка по убыванию
        students.sort((a, b) -> b.score - a.score);

        double sum = 0;

        for (Student s : students) {
            sum += s.score;
        }

        averageScore = sum / students.size();
        highestStudent = students.get(0);
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        String path = "output/report.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

            bw.write("Average: " + averageScore);
            bw.newLine();

            bw.write("Highest: " + highestStudent.name + " - " + highestStudent.score);
            bw.newLine();
            bw.newLine();

            bw.write("Sorted Students:");
            bw.newLine();

            for (Student s : students) {
                bw.write(s.name + " - " + s.score);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }

    // валидация (Task 6)
    private void validateScore(int score) throws InvalidScoreException {
        if (score < 0 || score > 100) {
            throw new InvalidScoreException("Score must be between 0 and 100");
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

// class Student (name, score)
class Student {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }
}