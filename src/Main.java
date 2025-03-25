import java.io.IOException;
import java.util.Scanner;
public class Main {
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your SingPass username:");
        String username = scanner.nextLine();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (true) {
            try {
                System.out.println("Welcome to the login page!");
                System.out.println("1. Login");
                System.out.println("2. Exit");
                System.out.print("Please enter your choice (1 or 2): ");

                choice = scanner.nextInt(); // 获取用户输入

                if (choice == 1 || choice == 2) {
                    break; // 输入合法，跳出循环
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                scanner.nextLine(); // 清空输入缓冲区，防止无限循环
            }
        }
        if (choice == 1) {
            Initialization init=new Initialization();
            init.initialize();
            this.login();
        }
        else{
            System.out.println("Thank you for using our service!");
            System.exit(0);
        }
    }

}