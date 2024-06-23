import java.util.ArrayList;
import java.util.Scanner;

public class ExamSystem {
    private ArrayList<User> users;
    private User currentUser;
    private Scanner scanner;

    public ExamSystem() {
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
        seedUsers();
    }

    private void seedUsers() {
        users.add(new User("user1", "1234"));
        users.add(new User("user2", "password2"));
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Online Examination System ---");
            System.out.println("1. Login");
            System.out.println("2. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                login();
            } else if (choice == 2) {
                System.out.println("Exiting the system.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        currentUser = authenticateUser(userId, password);

        if (currentUser != null) {
            System.out.println("Login successful.");
            showUserMenu();
        } else {
            System.out.println("Invalid User ID or Password.");
        }
    }

    private User authenticateUser(String userId, String password) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void showUserMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Update Profile and Password");
            System.out.println("2. Start Exam");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                updateProfile();
            } else if (choice == 2) {
                startExam();
            } else if (choice == 3) {
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateProfile() {
        System.out.print("Enter new User ID: ");
        String newUserId = scanner.nextLine();
        System.out.print("Enter new Password: ");
        String newPassword = scanner.nextLine();

        currentUser.setUserId(newUserId);
        currentUser.setPassword(newPassword);
        System.out.println("Profile updated successfully.");
    }

    private void startExam() {
        Question[] questions = {
            new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 0),
            new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1),
            new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 1)
        };

        int score = 0;
        int timeLimit = 30;  // seconds
        long endTime = System.currentTimeMillis() + (timeLimit * 1000);

        for (Question question : questions) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("Time's up! Auto-submitting your answers.");
                break;
            }

            System.out.println(question.getQuestionText());
            String[] options = question.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Select your answer: ");
            int answer = scanner.nextInt() - 1;

            if (answer == question.getCorrectOption()) {
                score++;
            }
        }

        System.out.println("Exam finished. Your score: " + score + "/" + questions.length);
    }

    public static void main(String[] args) {
        ExamSystem examSystem = new ExamSystem();
        examSystem.start();
    }
}

class User {
    private String userId;
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Question {
    private String questionText;
    private String[] options;
    private int correctOption;

    public Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}