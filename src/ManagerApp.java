import java.util.Scanner;

public class ManagerApp {
    public static void main(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to Manager App");
        do {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Create new BTO project");
            System.out.println("2. Edit existing BTO project");
            System.out.println("3. Delete BTO project");
            System.out.println("4. Toggle project visibility");
            System.out.println("5. View all projects");
            System.out.println("6. View my projects");
            System.out.println("7. View pending HDB Officer registrations");
            System.out.println("8. Approve/Reject HDB Officer registration");
            System.out.println("9. Approve/Reject Applicant's BTO application");
            System.out.println("10. Approve/Reject application withdrawal");
            System.out.println("11. Generate applicants report");
            System.out.println("12. View all enquiries");
            System.out.println("13. View and reply to project enquiries");
            System.out.println("14. Modify Manager password");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        createNewBTOProject();
                        break;
                    case 2:
                        editBTOProject();
                        break;
                    case 3:
                        deleteBTOProject();
                        break;
                    case 4:
                        toggleProjectVisibility();
                        break;
                    case 5:
                        viewAllProjects();
                        break;
                    case 6:
                        viewMyProjects();
                        break;
                    case 7:
                        viewPendingOfficerRegistrations();
                        break;
                    case 8:
                        processOfficerRegistration();
                        break;
                    case 9:
                        processBTOApplication();
                        break;
                    case 10:
                        processWithdrawalRequest();
                        break;
                    case 11:
                        generateApplicantsReport();
                        break;
                    case 12:
                        viewAllEnquiries();
                        break;
                    case 13:
                        viewAndReplyEnquiries();
                        break;
                    case 14:
                        modifyManagerPassword(manager);
                        break;
                    case 0:
                        System.out.println("Exiting Manager App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 13.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // Ensure loop continues
            }

        } while (choice != 0);
    }

    // You'll need to implement all these methods as static methods
    private static void createNewBTOProject() {
        // Implementation here
    }

    private static void editBTOProject() {
        // Implementation here
    }

    private static void deleteBTOProject() {
        // Implementation here
    }

    private static void toggleProjectVisibility() {
        // Implementation here
    }

    private static void viewAllProjects() {
        // Implementation here
    }

    private static void viewMyProjects() {
        // Implementation here
    }

    private static void viewPendingOfficerRegistrations() {
        // Implementation here
    }

    private static void processOfficerRegistration() {
        // Implementation here
    }

    private static void processBTOApplication() {
        // Implementation here
    }

    private static void processWithdrawalRequest() {
        // Implementation here
    }

    private static void generateApplicantsReport() {
        // Implementation here
    }

    private static void viewAllEnquiries() {
        // Implementation here
    }

    private static void viewAndReplyEnquiries() {
        // Implementation here
    }
    private static void modifyManagerPassword(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter your new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Please confirm your new password:");
            String confirmPassword = scanner.nextLine();
            if (newPassword.equals(confirmPassword)) {
                manager.setPassword(newPassword);
                System.out.println("Password has been successfully changed!");
                break;
            }
            else  {
                System.out.println("Passwords do not match!");
                System.out.println("Please try again.");
                continue;
            }
        }
    }
}