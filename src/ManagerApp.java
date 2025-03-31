import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
                        toggleProjectVisibility(manager);
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
        Initialization init = Initialization.getInstance(); //// singleton instance of the initialization adds the project
        ProjectList projectList = init.getProjectList(); 
        
        System.out.println("=== Create New BTO Project ==="); //prompting project details
        System.out.print("Enter Project Name: ");
        String projectName = scanner.nextLine().trim();
        System.out.print("Enter Neighbourhood: ");
        String neighbourhood = scanner.nextLine().trim();
        
        //flat type 1
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
        
        //validation for the dates, i coudlnt get it working so im just gonna fix this when i can haha
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d"); 
        //LocalDate openDate, closeDate;
        //try {
        	//openDate = LocalDate.parse(openingDate, formatter);
        	//closeDate = LocalDate.parse(closingDate,formatter);
        	//if (closeDate.isBefore(openDate)) {
               //System.out.println("Closing date cannot be before opening date.");
                //return;}
        //}catch (Exception e) {
        	//System.out.println("Invalid date format. Please use yyyy/M/d instead.");
        	//return;}
        
        // implementation of code to check for active projects for this specific manager, to-be-implemented
        boolean overlapExists = false;
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                if (DateUtils.isOverlapping(p.getOpeningDate(), p.getClosingDate(), openingDate, closingDate)) {
                    overlapExists = true;
                    break;}}}
        if (overlapExists) {
            System.out.println("You already have an active project with an overlapping application window. Cannot create another.");
            return;}
        
        // for simplicity's sake i'm setting a default officer slot and list 
        int officerSlot = 10;
        ArrayList<String> officers = new ArrayList<>();
        
        
        Project newProject = new Project(
                projectName, neighbourhood, 
                type1, type1Units, 
                type1Price, type2, 
                type2Units, type2Price, 
                openingDate, closingDate, 
                manager.getName(), officerSlot, officers);

        init.getProjectList().addProject(newProject);
        
        System.out.println("Project \"" + projectName + "\" created successfully.");
    }

    //edit projects
    private static void editBTOProject(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = new ArrayList<>();
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                myProjects.add(p);}
        }
        if (myProjects.isEmpty()) {
            System.out.println("You don't have any projects to edit.");
            return;}
        System.out.println("Your Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ". " + myProjects.get(i).getProjectName());
            }
        System.out.print("Enter the number of the project you want to edit: ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;
}
        Project project = myProjects.get(choice - 1);
        
        System.out.println("Editing Project: " + project.getProjectName());
        System.out.println("Select the field to edit:");
        System.out.println("1. Type 1 Units");
        System.out.println("2. Type 2 Units");
        System.out.println("3. Type 1 Price");
        System.out.println("4. Type 2 Price");
        System.out.println("5. Application Dates");
        System.out.print("Enter your choice (1-5): ");
        int fieldChoice = Integer.parseInt(scanner.nextLine());
        
        switch(fieldChoice) {
            case 1:
                System.out.println("Current Type 1 Units: " + project.getType1Units());
                System.out.print("Enter new Type 1 Units: ");
                int newUnits1 = Integer.parseInt(scanner.nextLine());
                project.setType1Units(newUnits1);
                break;
            case 2:
                System.out.println("Current Type 2 Units: " + project.getType2Units());
                System.out.print("Enter new Type 2 Units: ");
                int newUnits2 = Integer.parseInt(scanner.nextLine());
                project.setType2Units(newUnits2);
                break;
            case 3:
                System.out.println("Current Type 1 Price: " + project.getType1Price());
                System.out.print("Enter new Type 1 Price: ");
                double newPrice1 = Double.parseDouble(scanner.nextLine());
                project.setType1Price(newPrice1);
                break;
            case 4:
                System.out.println("Current Type 2 Price: " + project.getType2Price());
                System.out.print("Enter new Type 2 Price: ");
                double newPrice2 = Double.parseDouble(scanner.nextLine());
                project.setType2Price(newPrice2);
                break;
            case 5:
                System.out.println("Current Opening Date: " + project.getOpeningDate());
                System.out.println("Current Closing Date: " + project.getClosingDate());
                System.out.print("Enter new Opening Date (yyyy/M/d): ");
                String newOpeningDate = scanner.nextLine().trim();
                System.out.print("Enter new Closing Date (yyyy/M/d): ");
                String newClosingDate = scanner.nextLine().trim();
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
                LocalDate newOpenDate, newCloseDate;
                try {
                    newOpenDate = LocalDate.parse(newOpeningDate, formatter);
                    newCloseDate = LocalDate.parse(newClosingDate, formatter);
                    if (newCloseDate.isBefore(newOpenDate)) {
                        System.out.println("Closing date cannot be before opening date.");
                        return;
                    }
                } catch(Exception e) {
                    System.out.println("Invalid date format. Use yyyy/M/d.");
                    return;
                }
                project.setOpeningDate(newOpeningDate);
                project.setClosingDate(newClosingDate);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        System.out.println("Project updated successfully.");
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


    private static void toggleProjectVisibility(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = new ArrayList<>();
        for (Project p : projectList.getAllProjects()) {
            if (p.getManagerName().equals(manager.getName())) {
                myProjects.add(p);}}
        if (myProjects.isEmpty()) {
            System.out.println("You don't have any projects to toggle.");
            return;}
        System.out.println("Your Projects:");
        for (int i = 0; i < myProjects.size(); i++) {
            String status = myProjects.get(i).isVisible() ? "Visible" : "Hidden";
            System.out.println((i + 1) + ". " + myProjects.get(i).getProjectName() + " - " + status);}
        System.out.print("Enter the number of the project to toggle visibility: ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;}
        Project project = myProjects.get(choice - 1);
        project.setVisible(!project.isVisible());
        System.out.println("Project \"" + project.getProjectName() + "\" visibility toggled to " +
                (project.isVisible() ? "Visible" : "Hidden") + ".");}


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