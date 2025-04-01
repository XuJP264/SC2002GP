import java.util.List;
import java.util.Scanner;

public class ApplicantApp{

    public static void main(Applicant applicant) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Applicant Portal");
        System.out.println("Logged in as: " + applicant.getName() + " (" + applicant.getNRIC() + ")");

        while (true) {
            displayMainMenu();
            int choice = getValidChoice(scanner, 0, 8);

            if (choice == 0) {
                System.out.println("Logging out... Goodbye!");
                return;
            }

            processChoice(applicant, scanner, choice);
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== APPLICANT MENU ===");
        System.out.println("1. View Eligible Projects");
        System.out.println("2. Apply for a Project");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Application");
        System.out.println("5. Submit Inquiry");
        System.out.println("6. View Inquiries");
        System.out.println("7. Edit Inquiry");
        System.out.println("8. Delete Inquiry");
        System.out.println("0. Logout");
    }

    private static int getValidChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) return choice;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid input. Try again.");
        }
    }

    private static void processChoice(Applicant applicant, Scanner scanner, int choice) {
        switch (choice) {
            case 1 -> viewEligibleProjects(applicant);
            case 2 -> applyForProject(applicant, scanner);
            case 3 -> viewApplicationStatus(applicant);
            case 4 -> withdrawApplication(applicant);
            case 5 -> submitInquiry(applicant, scanner);
            case 6 -> viewInquiries(applicant);
            case 7 -> editInquiry(applicant, scanner);
            case 8 -> deleteInquiry(applicant, scanner);
        }
    }

    private static void viewEligibleProjects(Applicant applicant) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        boolean hasProjects = false;

        System.out.println("\n=== Eligible Projects ===");
        for (Project p : projectList.getProjects().values()) {
            if (p.isVisible() && isEligible(applicant, p)) {
                displayProjectDetails(p);
                hasProjects = true;
            }
        }

        if (!hasProjects) {
            System.out.println("No eligible visible projects found based on your profile.");
        }
    }

    private static boolean isEligible(Applicant applicant, Project p) {
        String status = applicant.getMaritalStatus();
        int age = applicant.getAge();
        if (status.equalsIgnoreCase("Single") && age >= 35) {
            return p.getType1().equalsIgnoreCase("2-Room") || p.getType2().equalsIgnoreCase("2-Room");
        }
        if (status.equalsIgnoreCase("Married") && age >= 21) {
            return true;
        }
        return false;
    }

    private static void displayProjectDetails(Project p) {
        System.out.println("- " + p.getProjectName() + " (" + p.getNeighborhood() + ")");
        System.out.printf("  Type1: %s (%d units) - $%.2f\n", p.getType1(), p.getType1Units(), p.getType1Price());
        System.out.printf("  Type2: %s (%d units) - $%.2f\n", p.getType2(), p.getType2Units(), p.getType2Price());
        System.out.println("  Dates: " + p.getOpeningDate() + " to " + p.getClosingDate());
        System.out.println("  Manager: " + p.getManagerName());
    }

    private static void applyForProject(Applicant applicant, Scanner scanner) {
        if (applicant.getAppliedProject() != null && !applicant.getApplicationStatus().equals("Unsuccessful")) {
            System.out.println("You already have an active application. Please withdraw first.");
            return;
        }

        ProjectList projectList = Initialization.getInstance().getProjectList();
        System.out.print("Enter the name of the project you wish to apply for: ");
        String projectName = scanner.nextLine();
        Project project = projectList.getProject(projectName);

        if (project == null || !isEligible(applicant, project)) {
            System.out.println("Invalid or ineligible project.");
            return;
        }

        applicant.setAppliedProject(projectName);
        applicant.setApplicationStatus("Pending");
        System.out.println("You have successfully applied for: " + projectName);
    }

    private static void viewApplicationStatus(Applicant applicant) {
        String applied = applicant.getAppliedProject();
        String status = applicant.getApplicationStatus();

        if (applied == null || status.equals("none")) {
            System.out.println("You have not applied for any project yet.");
        } else {
            System.out.println("Project: " + applied);
            System.out.println("Status: " + status);
        }
    }

    private static void withdrawApplication(Applicant applicant) {
        if (applicant.getAppliedProject() == null) {
            System.out.println("No application found to withdraw.");
            return;
        }

        applicant.setAppliedProject(null);
        applicant.setApplicationStatus("Withdrawn");
        System.out.println("Application withdrawn successfully.");
    }

    private static void submitInquiry(Applicant applicant, Scanner scanner) {
        System.out.print("Enter your inquiry: ");
        String inquiry = scanner.nextLine();
        if (!inquiry.isBlank()) {
            applicant.getInquiries().add(inquiry);
            System.out.println("Inquiry submitted.");
        } else {
            System.out.println("Inquiry cannot be empty.");
        }
    }

    private static void viewInquiries(Applicant applicant) {
        List<String> inquiries = applicant.getInquiries();
        if (inquiries.isEmpty()) {
            System.out.println("You have no inquiries.");
            return;
        }

        System.out.println("=== Your Inquiries ===");
        for (int i = 0; i < inquiries.size(); i++) {
            System.out.println((i + 1) + ". " + inquiries.get(i));
        }
    }

    private static void editInquiry(Applicant applicant, Scanner scanner) {
        viewInquiries(applicant);
        List<String> inquiries = applicant.getInquiries();
        if (inquiries.isEmpty()) return;

        System.out.print("Enter the inquiry number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < inquiries.size()) {
                System.out.print("Enter new inquiry: ");
                String newInquiry = scanner.nextLine();
                if (!newInquiry.isBlank()) {
                    inquiries.set(index, newInquiry);
                    System.out.println("Inquiry updated.");
                } else {
                    System.out.println("New inquiry cannot be empty.");
                }
            } else {
                System.out.println("Invalid inquiry number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void deleteInquiry(Applicant applicant, Scanner scanner) {
        viewInquiries(applicant);
        List<String> inquiries = applicant.getInquiries();
        if (inquiries.isEmpty()) return;

        System.out.print("Enter the inquiry number to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < inquiries.size()) {
                inquiries.remove(index);
                System.out.println("Inquiry deleted.");
            } else {
                System.out.println("Invalid inquiry number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}


        

        

        
