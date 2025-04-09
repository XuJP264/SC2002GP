import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main{
    private static final String REGEX = "^[ST]\\d{7}[A-Za-z]$";
    private static Initialization initialization;
    private static Scanner mainScanner = new Scanner(System.in);
    static {
        initialization = Initialization.getInstance(); // 正确赋值
        initialization.initialize();
    }

    public static void main(String[] args) {
        Scanner mainScanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Housing Application System ===");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Please enter your choice (1-2): ");

            String input = mainScanner.nextLine();  // 先读取为字符串

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        Login.login();
                        break;
                    case 2:
                        System.out.println("Thank you for using our service!");
                        mainScanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }
    }
}