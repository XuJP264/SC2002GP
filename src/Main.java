import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String REGEX = "^[ST]\\d{7}[A-Za-z]$";
    private static Initialization initialization;
    private static Scanner mainScanner = new Scanner(System.in);

    static {
        initialization = new Initialization();
        initialization.initialize();
    }
    public static boolean isLegal(String username) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Login Menu:");
        int attempts = 0;
        final int MAX_ATTEMPTS = 5;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Please enter your username (NRIC number): ");
            String username = scanner.nextLine().trim();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine().trim();

            if (!isLegal(username)) {
                System.out.println("Invalid username format! Format should be S/T followed by 7 digits and a letter.");
                attempts++;
                continue;
            }

            // Check user existence and validate password
            if (checkCredentials(username, password)) {
                return; // 登录成功直接返回
            }

            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                System.out.println("Remaining attempts: " + (MAX_ATTEMPTS - attempts));
            }
        }

        System.out.println("Maximum login attempts reached. Returning to main menu.");
    }

    private static boolean checkCredentials(String username, String password) {
        // Check Officer
        Officer officer = initialization.getOfficerList().getOfficer(username);
        if (officer != null) {
            if (officer.getPassword().equals(password)) {
                System.out.println("Login successful as Officer!");
                OfficerApp.main(new String[0]);
                return true;
            } else {
                System.out.println("Invalid password for Officer account!");
                return false;
            }
        }

        // Check Applicant
        Applicant applicant = initialization.getApplicantList().getApplicant(username);
        if (applicant != null) {
            if (applicant.getPassword().equals(password)) {
                System.out.println("Login successful as Applicant!");
                ApplicantApp.main(new String[0]);
                return true;
            } else {
                System.out.println("Invalid password for Applicant account!");
                return false;
            }
        }

        // Check Manager
        Manager manager = initialization.getManagerList().getManager(username);
        if (manager != null) {
            if (manager.getPassword().equals(password)) {
                System.out.println("Login successful as Manager!");
                ManagerApp.main(new String[0]);
                return true;
            } else {
                System.out.println("Invalid password for Manager account!");
                return false;
            }
        }

        System.out.println("Username not found in any system!");
        return false;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Housing Application System ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Please enter your choice (1-2): ");

            try {
                int choice = mainScanner.nextInt();
                mainScanner.nextLine(); // 清除换行符

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        System.out.println("Thank you for using our service!");
                        mainScanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                mainScanner.nextLine(); // 清除错误输入
            }
        }
    }
}