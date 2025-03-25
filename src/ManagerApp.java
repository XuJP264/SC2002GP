import java.util.Scanner;

public class ManagerApp {
    public static void main(String[] args) {
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
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.createNewBTOProject();
                        break;
                    case 2:
                        this.editBTOProject();
                        break;
                    case 3:
                        this.deleteBTOProject();
                        break;
                    case 4:
                        this.toggleProjectVisibility();
                        break;
                    case 5:
                        this.viewAllProjects();
                        break;
                    case 6:
                        this.viewMyProjects();
                        break;
                    case 7:
                        this.viewPendingOfficerRegistrations();
                        break;
                    case 8:
                        this.processOfficerRegistration();
                        break;
                    case 9:
                        this.processBTOApplication();
                        break;
                    case 10:
                        this.processWithdrawalRequest();
                        break;
                    case 11:
                        this.generateApplicantsReport();
                        break;
                    case 12:
                        this.viewAllEnquiries();
                        break;
                    case 13:
                        this.viewAndReplyEnquiries();
                        break;
                    case 0:
                        this.System.out.println("Exiting Manager App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 13.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // Ensure loop continues
            }

        } while (choice != 0);

        scanner.close();
    }
}