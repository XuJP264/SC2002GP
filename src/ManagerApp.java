import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManagerApp {
    private static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to HDB Manager Portal");
        System.out.println("Logged in as: " + manager.getName());

        while (true) {
            displayMainMenu();
            int choice = getValidChoice(scanner, 0, 14);

            if (choice == 0) {
                System.out.println("Logging out... Goodbye!");
                return;
            }

            processUserChoice(manager, scanner, choice);
        }
    }

    protected static void displayMainMenu() {
        System.out.println("\n=== MANAGER MENU ===");
        System.out.println("1. Create new BTO project");
        System.out.println("2. Edit existing BTO project");
        System.out.println("3. Delete BTO project");
        System.out.println("4. Toggle project visibility");
        System.out.println("5. View all projects");
        System.out.println("6. View my projects");
        System.out.println("7. View pending officer registrations");
        System.out.println("8. Process officer registrations");
        System.out.println("9. Process BTO applications");
        System.out.println("10. Process withdrawal requests");
        System.out.println("11. Generate applicants report");
        System.out.println("12. View all enquiries");
        System.out.println("13. View and reply to enquiries");
        System.out.println("14. Modify password");
        System.out.println("0. Logout");
    }

    protected static void processUserChoice(Manager manager, Scanner scanner, int choice) {
        switch (choice) {
            case 1 -> createNewBTOProject(manager, scanner);
            case 2 -> editBTOProject(manager, scanner);
            case 3 -> deleteBTOProject(manager, scanner);
            case 4 -> toggleProjectVisibility(manager, scanner);
            case 5 -> viewAllProjects();
            case 6 -> viewMyProjects(manager);
            case 7 -> viewPendingOfficerRegistrations();
            case 8 -> processOfficerRegistration(manager,scanner);
            case 9 -> processBTOApplication(manager, scanner);
            case 10 -> processWithdrawalRequest(scanner);
            case 11 -> generateApplicantsReport();
            case 12 -> viewAllEnquiries();
            case 13 -> viewAndReplyEnquiries(scanner);
            case 14 -> modifyManagerPassword(manager, scanner);
        }
    }

    private static int getValidChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    protected static void createNewBTOProject(Manager manager, Scanner scanner) {
        Initialization init = Initialization.getInstance();
        ProjectList projectList = init.getProjectList();

        System.out.println("\n=== Create New BTO Project ===");
        System.out.print("Enter Project Name: ");
        String projectName = scanner.nextLine().trim();

        if (projectList.getProject(projectName) != null) {
            System.out.println("Project with this name already exists!");
            return;
        }

        System.out.print("Enter Neighbourhood: ");
        String neighbourhood = scanner.nextLine().trim();

        // Flat type 1 details
        System.out.print("Enter Flat Type 1 (e.g., 2-Room): ");
        String type1 = scanner.nextLine().trim();
        int type1Units = getValidIntInput(scanner, "Enter number of units for " + type1 + ": ");
        double type1Price = getValidDoubleInput(scanner, "Enter price for " + type1 + ": ");

        // Flat type 2 details
        System.out.print("Enter Flat Type 2 (e.g., 3-Room): ");
        String type2 = scanner.nextLine().trim();
        int type2Units = getValidIntInput(scanner, "Enter number of units for " + type2 + ": ");
        double type2Price = getValidDoubleInput(scanner, "Enter price for " + type2 + ": ");

        // Date handling with new format
        LocalDate openingDate = getValidDateInput(scanner, "Enter Application Opening Date (yyyy/mm/dd): ");
        LocalDate closingDate = getValidDateInput(scanner, "Enter Application Closing Date (yyyy/mm/dd): ");

        if (closingDate.isBefore(openingDate)) {
            System.out.println("Closing date cannot be before opening date.");
            return;
        }

        // Check for overlapping projects
        if (hasOverlappingProjects(manager, projectList, openingDate, closingDate)) {
            System.out.println("You already have an active project with overlapping dates.");
            return;
        }

        // Create and add project
        Project newProject = new Project(
                projectName, neighbourhood,
                type1, type1Units, type1Price,
                type2, type2Units, type2Price,
                openingDate.toString(), closingDate.toString(),
                manager.getName(), 10, new ArrayList<>());

        projectList.addProject(newProject);
        System.out.println("Project \"" + projectName + "\" created successfully.");
    }

    protected static void editBTOProject(Manager manager, Scanner scanner) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);

        if (myProjects.isEmpty()) {
            System.out.println("You don't have any projects to edit.");
            return;
        }

        System.out.println("\nYour Projects:");
        displayProjectsWithNumbers(myProjects);

        int choice = getValidChoice(scanner, 1, myProjects.size(), "Enter the number of the project to edit: ");
        Project project = myProjects.get(choice - 1);

        System.out.println("\nEditing Project: " + project.getProjectName());
        System.out.println("Select field to edit:");
        System.out.println("1. Type 1 Units");
        System.out.println("2. Type 2 Units");
        System.out.println("3. Type 1 Price");
        System.out.println("4. Type 2 Price");
        System.out.println("5. Application Dates");

        int fieldChoice = getValidChoice(scanner, 1, 5);

        switch(fieldChoice) {
            case 1 -> {
                int newUnits = getValidIntInput(scanner, "Current Type 1 Units: " + project.getType1Units() + "\nEnter new value: ");
                project.setType1Units(newUnits);
            }
            case 2 -> {
                int newUnits = getValidIntInput(scanner, "Current Type 2 Units: " + project.getType2Units() + "\nEnter new value: ");
                project.setType2Units(newUnits);
            }
            case 3 -> {
                double newPrice = getValidDoubleInput(scanner, "Current Type 1 Price: " + project.getType1Price() + "\nEnter new value: ");
                project.setType1Price(newPrice);
            }
            case 4 -> {
                double newPrice = getValidDoubleInput(scanner, "Current Type 2 Price: " + project.getType2Price() + "\nEnter new value: ");
                project.setType2Price(newPrice);
            }
            case 5 -> {
                LocalDate newOpen = getValidDateInput(scanner, "Current Opening Date: " +
                        LocalDate.parse(project.getOpeningDate()).format(DATE_DISPLAY_FORMATTER) +
                        "\nEnter new date (yyyy/mm/dd): ");
                LocalDate newClose = getValidDateInput(scanner, "Current Closing Date: " +
                        LocalDate.parse(project.getClosingDate()).format(DATE_DISPLAY_FORMATTER) +
                        "\nEnter new date (yyyy/mm/dd): ");

                if (newClose.isBefore(newOpen)) {
                    System.out.println("Closing date cannot be before opening date.");
                    return;
                }
                project.setOpeningDate(newOpen.toString());
                project.setClosingDate(newClose.toString());
            }
        }
        System.out.println("Project updated successfully.");
    }

    protected static void deleteBTOProject(Manager manager, Scanner scanner) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);

        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to delete.");
            return;
        }

        System.out.println("\nYour Projects:");
        displayProjectsWithNumbers(myProjects);

        int choice = getValidChoice(scanner, 1, myProjects.size(), "Enter the number of the project to delete: ");
        Project project = myProjects.get(choice - 1);

        projectList.removeProject(project.getProjectName());
        System.out.println("Project \"" + project.getProjectName() + "\" deleted successfully.");
    }

    protected static void toggleProjectVisibility(Manager manager, Scanner scanner) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);

        if (myProjects.isEmpty()) {
            System.out.println("You don't have any projects to toggle.");
            return;
        }

        System.out.println("\nYour Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            String status = myProjects.get(i).isVisible() ? "Visible" : "Hidden";
            System.out.println((i + 1) + ". " + myProjects.get(i).getProjectName() + " - " + status);
        }

        int choice = getValidChoice(scanner, 1, myProjects.size(), "Enter the number of the project to toggle: ");
        Project project = myProjects.get(choice - 1);

        project.setVisible(!project.isVisible());
        System.out.println("Project \"" + project.getProjectName() + "\" is now " +
                (project.isVisible() ? "Visible" : "Hidden"));
    }

    // Helper methods
    private static ArrayList<Project> getManagerProjects(Manager manager, ProjectList projectList) {
        ArrayList<Project> myProjects = new ArrayList<>();
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                myProjects.add(p);
            }
        }
        return myProjects;
    }

    protected static void viewAllProjects() {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        System.out.println("\n=== All Projects ===");
        for (Project p : projectList.getAllProjects()) {
            System.out.println("\nProject: " + p.getProjectName());
            System.out.println("Manager: " + p.getManagerName());
            System.out.println("Status: " + (p.isVisible() ? "Visible" : "Hidden"));
            System.out.println("Dates: " + LocalDate.parse(p.getOpeningDate()).format(DATE_DISPLAY_FORMATTER) +
                    " to " + LocalDate.parse(p.getClosingDate()).format(DATE_DISPLAY_FORMATTER));
        }
    }

    private static void displayProjectsWithNumbers(ArrayList<Project> projects) {
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getProjectName());
        }
    }

    private static int getValidIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double getValidDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static LocalDate getValidDateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().replace("-", "/");
                return LocalDate.parse(input, DATE_INPUT_FORMATTER);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy/mm/dd.");
            }
        }
    }

    private static int getValidChoice(Scanner scanner, int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static boolean hasOverlappingProjects(Manager manager, ProjectList projectList,
                                                  LocalDate newOpen, LocalDate newClose) {
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                LocalDate existingOpen = LocalDate.parse(p.getOpeningDate());
                LocalDate existingClose = LocalDate.parse(p.getClosingDate());

                if (newOpen.isBefore(existingClose) && newClose.isAfter(existingOpen)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static void viewMyProjects(Manager manager) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);

        if (myProjects.isEmpty()) {
            System.out.println("You don't have any projects.");
            return;
        }

        System.out.println("\n=== Your Projects ===");
        for (Project p : myProjects) {
            System.out.println("\nProject: " + p.getProjectName());
            System.out.println("Status: " + (p.isVisible() ? "Visible" : "Hidden"));
            System.out.println("Dates: " + LocalDate.parse(p.getOpeningDate()).format(DATE_DISPLAY_FORMATTER) +
                    " to " + LocalDate.parse(p.getClosingDate()).format(DATE_DISPLAY_FORMATTER));
            System.out.println("Officers: " + p.getOfficers().size() + "/" + p.getOfficerSlot());
        }
    }

    protected static void viewPendingOfficerRegistrations() {
        System.out.println("\nPending officer registrations functionality coming soon");
    }

    protected static void processOfficerRegistration(Manager manager, Scanner scanner) {
        System.out.println("\nProcess officer registrations functionality coming soon");
    }

    protected static void processBTOApplication(Manager manager, Scanner scanner) {
        System.out.println("\nProcess BTO applications functionality coming soon");
        Initialization init = Initialization.getInstance();
        ApplicantList applicantList = init.getApplicantList();
        OfficerList officerList = init.getOfficerList();
        HashMap<Applicant, HashMap<Project, String>> applications =Applications.getApplications();
        for (Applicant a : applicantList.getAllApplicants()){
            if(manager.getMyProject().contains(a.getAppliedProject())){
                System.out.println(a.getName() + " applied for " + a.getAppliedProject().getProjectName());
                System.out.println("Do you want to accept or reject the application?");
                System.out.println("1. Accept");
                System.out.println("2. Reject");
                int choice = getValidChoice(scanner, 1, 2);
                if(choice == 1){
                    System.out.println("Applicant " + a.getName() + " has been accepted for project " + a.getAppliedProject().getProjectName());
                    Applications.updateApplicationStatus(a, a.getAppliedProject(), "Accepted");
                }else{
                    System.out.println("Applicant " + a.getName() + " has been rejected for project " + a.getAppliedProject().getProjectName());
                    Applications.updateApplicationStatus(a, a.getAppliedProject(), "Rejected");
                }
            }
        }
        for (Officer o : officerList.getAllOfficers()) {
            if (manager.getMyProject().contains(o.getAppliedProject())) {
                System.out.println(o.getName() + " applied for " + o.getAppliedProject().getProjectName());
                System.out.println("Do you want to accept or reject the application?");
                System.out.println("1. Accept");
                System.out.println("2. Reject");
                int choice = getValidChoice(scanner, 1, 2);
                if (choice == 1) {
                    System.out.println("Officer " + o.getName() + " has been accepted for project " + o.getAppliedProject().getProjectName());
                    Applications.updateApplicationStatus(o, o.getAppliedProject(), "Accepted");
                } else {
                    System.out.println("Officer " + o.getName() + " has been rejected for project " + o.getAppliedProject().getProjectName());
                    Applications.updateApplicationStatus(o, o.getAppliedProject(), "Rejected");
                }
            }
        }
    }

    protected static void processWithdrawalRequest(Scanner scanner) {
        System.out.println("\nProcess withdrawal requests functionality coming soon");
    }

    protected static void generateApplicantsReport() {
        System.out.println("\nGenerate applicants report functionality coming soon");
    }

    protected static void viewAllEnquiries() {
        System.out.println("\nView all enquiries functionality coming soon");
    }

    protected static void viewAndReplyEnquiries(Scanner scanner) {
        System.out.println("\nView and reply to enquiries functionality coming soon");
    }

    protected static void modifyManagerPassword(Manager manager, Scanner scanner) {
        System.out.print("\nEnter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (newPassword.equals(confirmPassword)) {
            manager.setPassword(newPassword);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Passwords do not match. Password not changed.");
        }
    }
}