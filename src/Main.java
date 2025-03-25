import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        //the login page, choose 1 to initialize the database or 2 to login
        System.out.println("Welcome to the login page!");
        System.out.println("Please choose an option:");
        System.out.println("1. Initialize the database,if the database has been initialized, please choose another option");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        int option = Integer.parseInt(System.console().readLine());
        switch(option) {
            case 1: //initialize the database
                System.out.println("Initializing the database...");
                //code to initialize the database

                System.out.println("Database initialized successfully!");
                break;
            case 2: //login, user need to use their SingPass account and password to login
                System.out.println("Please enter your SingPass:");
                String singpass = System.console().readLine();
                System.out.println("Please enter your password:");
                String password = System.console().readLine();
                //code to check if the user's password is correct

    }
}