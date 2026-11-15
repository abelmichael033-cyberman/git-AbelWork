import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * COS 201 - Programming I
 * Student Course Management System (Mini-System)
 *
 * Features:
 * 1. Add Course
 * 2. View All Courses
 * 3. Search Course by Code
 * 4. Compute Total Units
 * 5. Save to File
 * 6. Load from File
 * 7. Exit Program
 */
public class StudentCourseManagementSystem {

    private static final String COURSES_FILE = "courses.txt";
    private final List<Course> courses = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentCourseManagementSystem app = new StudentCourseManagementSystem();
        System.out.println("Welcome to the Miva Open University Student Course Management System.");
        app.runMenu(true);
    }

    public void runMenu(boolean running) {
        if (!running) {
            return;
        }

        printMenu();
        String choice = scanner.nextLine().trim();

        try {
            switch (choice) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    viewAllCourses();
                    break;
                case "3":
                    searchCourse();
                    break;
                case "4":
                    System.out.println("\nTotal registered units: " + totalUnits());
                    break;
                case "5":
                    saveToFile();
                    break;
                case "6":
                    loadFromFile();
                    break;
                case "7":
                    System.out.println("\nThank you for using the Miva Open University Student Course Management System.");
                    runMenu(false);
                    return;
                default:
                    System.out.println("\nInvalid choice. Please enter a number from 1 to 7.");
            }
        } catch (CourseException e) {
            System.out.println("\nInput error: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("\nFile error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("\nFile operation error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error: " + e.getMessage());
        }

        runMenu(true);
    }

    private void printMenu() {
        System.out.println("\n===== STUDENT COURSE MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit Program");
        System.out.print("Enter your choice (1-7): ");
    }

    private void addCourse() {
        String code = getRequiredInput("Enter course code: ").toUpperCase();
        String title = getRequiredInput("Enter course title: ");
        String unitsInput = getRequiredInput("Enter course unit(s): ");

        if (findCourse(code) != null) {
            throw new CourseException("A course with code '" + code + "' already exists.");
        }

        Course course = new Course(code, title, unitsInput);
        courses.add(course);
        System.out.println("\nAdded: " + course);
    }

    private void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("\nNo courses have been recorded yet.");
            return;
        }

        System.out.println("\nCODE       | TITLE                               | UNITS");
        System.out.println("------------------------------------------------------------");
        for (Course course : courses) {
            System.out.println(course);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Total courses: " + courses.size());
    }

    private void searchCourse() {
        String code = getRequiredInput("Enter the course code to search: ").toUpperCase();
        Course course = findCourse(code);

        if (course == null) {
            System.out.println("\nNo course found with code '" + code + "'.");
        } else {
            System.out.println("\nCourse found:");
            System.out.println(course);
        }
    }

    public Course findCourse(String code) {
        return recursiveSearch(code.toUpperCase(), 0);
    }

    private Course recursiveSearch(String code, int index) {
        if (index >= courses.size()) {
            return null;
        }
        if (courses.get(index).getCode().equals(code)) {
            return courses.get(index);
        }
        return recursiveSearch(code, index + 1);
    }

    public int totalUnits() {
        return recursiveTotal(0);
    }

    private int recursiveTotal(int index) {
        if (index >= courses.size()) {
            return 0;
        }
        return courses.get(index).getUnits() + recursiveTotal(index + 1);
    }

    private void saveToFile() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(COURSES_FILE), StandardCharsets.UTF_8)) {
            for (Course course : courses) {
                writer.write(course.getCode() + "|" + course.getTitle() + "|" + course.getUnits());
                writer.newLine();
            }
        }
        System.out.println("\nSaved " + courses.size() + " course(s) to '" + COURSES_FILE + "'.");
    }

    private void loadFromFile() throws IOException {
        Path path = Paths.get(COURSES_FILE);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("'" + COURSES_FILE + "' does not exist.");
        }

        List<Course> loadedCourses = new ArrayList<>();
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\|");
            if (parts.length != 3) {
                throw new CourseException("Invalid record on line " + (i + 1) + ".");
            }

            loadedCourses.add(new Course(parts[0], parts[1], parts[2]));
        }

        courses.clear();
        courses.addAll(loadedCourses);
        System.out.println("\nLoaded " + courses.size() + " course(s) from '" + COURSES_FILE + "'.");
    }

    private String getRequiredInput(String prompt) {
        System.out.print(prompt);
        String value = scanner.nextLine().trim();

        if (value.isEmpty()) {
            throw new CourseException("Input cannot be empty.");
        }
        return value;
    }
}

class Course {
    private final String code;
    private final String title;
    private final int units;

    public Course(String code, String title, String unitsInput) {
        this.code = cleanCode(code);
        this.title = cleanTitle(title);
        this.units = validateUnits(unitsInput);
    }

    private static String cleanCode(String code) {
        code = code == null ? "" : code.trim().toUpperCase();
        if (code.isEmpty()) {
            throw new CourseException("Course code cannot be empty.");
        }
        return code;
    }

    private static String cleanTitle(String title) {
        title = title == null ? "" : title.trim();
        if (title.isEmpty()) {
            throw new CourseException("Course title cannot be empty.");
        }
        return title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
    }

    private static int validateUnits(String unitsInput) {
        try {
            int units = Integer.parseInt(unitsInput.trim());
            if (units <= 0) {
                throw new CourseException("Units must be greater than zero.");
            }
            return units;
        } catch (NumberFormatException e) {
            throw new CourseException("Units must be a whole number.");
        }
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-35s | %d unit(s)", code, title, units);
    }
}

class CourseException extends RuntimeException {
    public CourseException(String message) {
        super(message);
    }
}
