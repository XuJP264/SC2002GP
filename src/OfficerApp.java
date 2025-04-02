import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class OfficerApp extends ApplicantApp implements Inquiry{
    public static void main(Officer officer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to HDB Officer Portal");
        System.out.println("Logged in as: " + officer.getName() + " (" + officer.getNRIC() + ")");

        officerInitiate(officer);

        while (true) {
            displayMainMenu();
            int choice = getValidChoice(scanner, 0, 16);

            if (choice == 0) {
                System.out.println("Logging out... Goodbye!");
                return;
            }

            processUserChoice(officer, scanner, choice);
        }
    }

    private static void officerInitiate(Officer officer) {
        Initialization initialization = Initialization.getInstance();
        ProjectList projectList = initialization.getProjectList();

        for (Project p : projectList.getProjects().values()) {
            if (ContainsName.containsName(p.getOfficers(), officer.getName())) {
                officer.addProjectInCharge(p);
            }
        }
        System.out.println("Initialization complete");
    }

    protected static void displayMainMenu() {
        System.out.println("\n=== OFFICER MENU ===");

        System.out.println("\n[Applicant Functions]");
        System.out.println("1. View Eligible Projects");
        System.out.println("2. Apply for a Project");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Application");
        System.out.println("5. Submit Project Inquiry");
        System.out.println("6. View Project Inquiries");
        System.out.println("7. Edit Project Inquiry");
        System.out.println("8. Delete Project Inquiry");

        System.out.println("\n[Officer Functions]");
        System.out.println("9. Register to Manage Project");
        System.out.println("10. View Registration Status");
        System.out.println("11. View Managed Project Details");
        System.out.println("12. Assist Applicant Booking");
        System.out.println("13. Generate Booking Receipt");
        System.out.println("14. View Inquiries");
        System.out.println("15. Reply to Inquiries");
        System.out.println("16. Modify Password");
        System.out.println("0. Logout");
    }

    protected static void processUserChoice(Officer officer, Scanner scanner, int choice) {
        switch (choice) {
            case 1 -> viewEligibleProjects(officer);
            case 2 -> applyForProject(officer, scanner);
            case 3 -> viewApplicationStatus(officer);
            case 4 -> withdrawApplication(officer, scanner);
            case 5 -> submitProjectInquiry(officer, scanner);
            case 6 -> viewProjectInquiries(officer);
            case 7 -> editProjectInquiry(officer, scanner);
            case 8 -> deleteProjectInquiry(officer, scanner);
            case 9 -> registerForProject(officer, scanner);
            case 10 -> viewRegistrationStatus(officer);
            case 11 -> viewManagedProjectDetails(officer);
            case 12 -> assistFlatBooking(officer, scanner);
            case 13 -> generateBookingReceipt(officer, scanner);
            case 14 -> viewInquiry(officer, scanner);
            case 15 -> replyInquiry(officer, scanner);
            case 16 -> modifyPassword(officer, scanner);
        }
    }

    // Officer-specific methods
    protected static void registerForProject(Officer officer, Scanner scanner) {
        Initialization initialization = Initialization.getInstance();
        ProjectList projects = initialization.getProjectList();

        System.out.println("\n=== Available Projects ===");
        projects.showProjectList();

        System.out.print("Enter project name to register: ");
        String projectName = scanner.nextLine();
        Project project = projects.getProject(projectName);

        if (project == null) {
            System.out.println("Invalid project name");
            return;
        }

        if (officer.getProjectsHaveApplied().contains(project)) {
            System.out.println("You already applied for this project");
            return;
        }

        if (ContainsName.containsName(project.getOfficers(), officer.getName())) {
            System.out.println("You're already registered for this project");
            return;
        }

        // Check date overlap
        for (Project p : officer.getProjectsInCharge()) {
            if (DateUtils.isOverlapping(
                    project.getOpeningDate(), project.getClosingDate(),
                    p.getOpeningDate(), p.getClosingDate())) {
                System.out.println("Project period overlaps with your existing project: " + p.getProjectName());
                return;
            }
        }

        if (project.getOfficerSlot() - project.getOfficers().size() <= 0) {
            System.out.println("No available officer slots");
            return;
        }

        RegistrationList.addRegistration(officer, project);
        System.out.println("Registration request sent to project manager");
    }

    protected static void viewRegistrationStatus(Officer officer) {
        HashMap<Project, String> registrations = RegistrationList.getRegistrationConditions(officer);

        if (registrations.isEmpty()) {
            System.out.println("No registration records found");
            return;
        }

        System.out.println("\n=== Registration Status ===");
        registrations.forEach((project, condition) ->
                System.out.println(project.getProjectName() + ": " + (condition != null ? condition : "No condition specified"))
        );
    }

    protected static void viewManagedProjectDetails(Officer officer) {
        List<Project> projects = officer.getProjectsInCharge();

        if (projects.isEmpty()) {
            System.out.println("You're not managing any projects");
            return;
        }

        System.out.println("\n=== Managed Projects ===");
        for (Project p : projects) {
            System.out.println("\nProject: " + p.getProjectName());
            System.out.println("Neighborhood: " + p.getNeighborhood());
            System.out.println("Type 1: " + p.getType1() + " (" + p.getType1Units() + " units)");
            System.out.println("Type 2: " + p.getType2() + " (" + p.getType2Units() + " units)");
            System.out.println("Dates: " + p.getOpeningDate() + " to " + p.getClosingDate());
            System.out.println("Officers: " + p.getOfficers().size() + "/" + p.getOfficerSlot());
        }
    }
    protected static void assistFlatBooking(Officer officer, Scanner scanner) {
    }
    private static void generateBookingReceipt(Officer officer, Scanner scanner) {
    }
    private static void viewInquiry(Officer officer, Scanner scanner) {
    }
    private static void replyInquiry(Officer officer, Scanner scanner) {
    }
    private static void modifyPassword(Officer officer, Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        officer.setPassword(newPassword);
        System.out.println("Password updated successfully");
    }
}