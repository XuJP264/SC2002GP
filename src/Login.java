import java.util.Scanner;

public class Login implements IsLegal, CheckCredentials {
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

            if (!IsLegal.isLegal(username)) {
                System.out.println("Invalid username format! Format should be S/T followed by 7 digits and a letter.");
                attempts++;
                continue;
            }

            // Check user existence and validate password
            if (CheckCredentials.checkCredentials(username, password)) {
                return; // 登录成功直接返回
            }

            attempts++;
            if (attempts < MAX_ATTEMPTS) {
                System.out.println("Remaining attempts: " + (MAX_ATTEMPTS - attempts));
            }
        }

        System.out.println("Maximum login attempts reached. Returning to main menu.");
    }
}
