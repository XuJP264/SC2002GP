import java.util.ArrayList;
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
                        createNewBTOProject(manager);
                        break;
                    case 2:
                        editBTOProject(manager);
                        break;
                    case 3:
                        deleteBTOProject(manager);
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

    // implementation of methods 
    private static void createNewBTOProject(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Create New BTO Project ==="); //prompting project details
        
        System.out.print("Enter Project Name: ");
        String projectName = scanner.nextLine().trim();
        
        System.out.print("Enter Neighbourhood: ");
        String neighbourhood = scanner.nextLine().trim();
        
        //flat type 1:
        System.out.print("Enter Flat Type 1 (e.g., 2-Room): ");
        String type1 = scanner.nextLine().trim();
        System.out.print("Enter number of units for " + type1 + ": ");
        int type1Units = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter price for " + type1 + ": ");
        double type1Price = Double.parseDouble(scanner.nextLine().trim());
        
        //flat type 2
        System.out.print("Enter Flat Type 2 (e.g., 3-Room): ");
        String type2 = scanner.nextLine().trim();
        System.out.print("Enter number of units for " + type2 + ": ");
        int type2Units = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Enter price for " + type2 + ": ");
        double type2Price = Double.parseDouble(scanner.nextLine().trim());
        
        //application window dates
        System.out.print("Enter Application Opening Date (yyyy-mm-dd): ");
        String openingDate = scanner.nextLine().trim();
        System.out.print("Enter Application Closing Date (yyyy-mm-dd): ");
        String closingDate = scanner.nextLine().trim();
        
        // for simplicity's sake i'm setting a default officer slot and list 
        int officerSlot = 10;
        ArrayList<String> officers = new ArrayList<>();
        
        // implementation of code to check for active projects for this specific manager, to-be-implemented
        
        Project newProject = new Project(
                projectName, 
                neighbourhood, 
                type1, 
                type1Units, 
                type1Price, 
                type2, 
                type2Units, 
                type2Price, 
                openingDate, 
                closingDate, 
                manager.getName(), 
                officerSlot, 
                officers);
        

        Initialization init = Initialization.getInstance();  // singleton instance of the initialization adds the project
        init.getProjectList().addProject(newProject);
        
        System.out.println("Project \"" + projectName + "\" created successfully.");
    }

    private static void editBTOProject(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        
        // retrieve projectList first
        ProjectList projectList = Initialization.getInstance().getProjectList();
        
        // filters project unique to this manager
        ArrayList<Project> myProjects = new ArrayList<>();
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                myProjects.add(p);}
        }
        
        if (myProjects.isEmpty()) {
            System.out.println("You don't have projects to edit.");
            return;}
        
        // display projects
        System.out.println("Your Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ". " + myProjects.get(i).getProjectName());}
        
        // gets project to edit
        System.out.println("Enter the number of the project you want to edit:");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;}
        
        Project project = myProjects.get(choice - 1);
        
        // mini-menu for editing specific fields
        System.out.println("Which field would you like to edit?");
        System.out.println("1. Edit Type 1 unit count (e.g., 2-Room)");
        System.out.println("2. Edit Type 2 unit count (e.g., 3-Room)");
        System.out.println("3. Edit both Type 1 and Type 2 unit counts");
        System.out.print("Enter your choice (1-3): ");
        int fieldChoice = Integer.parseInt(scanner.nextLine());
        
        if (fieldChoice == 1 || fieldChoice == 3) {
            System.out.println("Current number of units for " + project.getType1() + ": " + project.getType1Units());
            System.out.print("Enter new number of units for " + project.getType1() + ": ");
            int newType1Units = Integer.parseInt(scanner.nextLine());
            project.setType1Units(newType1Units);}
        if (fieldChoice == 2 || fieldChoice == 3) {
            System.out.println("Current number of units for " + project.getType2() + ": " + project.getType2Units());
            System.out.print("Enter new number of units for " + project.getType2() + ": ");
            int newType2Units = Integer.parseInt(scanner.nextLine());
            project.setType2Units(newType2Units);}
        System.out.println("Project \"" + project.getProjectName() + "\" updated successfully.");
    }
    
    //deletion 
    private static void deleteBTOProject(Manager manager) { 
        Scanner scanner = new Scanner(System.in);
        ProjectList projectList = Initialization.getInstance().getProjectList(); //might turn this into a helper function actually
        ArrayList<Project> myProjects = new ArrayList<>();
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                myProjects.add(p);}
        }
        
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to delete.");
            return;}
        
        System.out.println("Your Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ". " + myProjects.get(i).getProjectName());}
        
        System.out.println("Enter the number of the project you want to delete:");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;}
        
        Project projectToDelete = myProjects.get(choice - 1);
        
        // removes the project using the project name 
        projectList.getProjects().remove(projectToDelete.getProjectName());
        
        System.out.println("Project \"" + projectToDelete.getProjectName() + "\" deleted successfully.");
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