import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ManagerApp {
    private static final DateTimeFormatter DATE_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter DATE_DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static void main(Manager manager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Applicant Portal");
        System.out.println("Logged in as: " + manager.getName() + " (" + manager.getNRIC() + ")");

        try {
            while (true) {
                displayMainMenu();
                int choice = ValidChoice.getValidChoice(scanner, 0, 14);

                if (choice == 0) {
                    System.out.println("Logging out... Goodbye!");
                    return;
                }

                processChoice(manager, scanner, choice);
            }
        } catch (ForceLogoutException e) {
            System.out.println(e.getMessage());
            return; // 退出 main 方法
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
        System.out.println("12. View and reply to enquiries");
        System.out.println("13. Modify password");
        System.out.println("14. View projects by filter");
        System.out.println("0. Logout");
    }

    protected static void processChoice(Manager manager, Scanner scanner, int choice) {
        switch (choice) {
            case 1 -> createNewBTOProject(manager, scanner);
            case 2 -> editBTOProject(manager, scanner);
            case 3 -> deleteBTOProject(manager, scanner);
            case 4 -> toggleProjectVisibility(manager, scanner);
            case 5 -> viewAllProjects();
            case 6 -> viewMyProjects(manager);
            case 7 -> viewPendingOfficerRegistrations(manager,scanner);
            case 8 -> processOfficerRegistration(manager,scanner);
            case 9 -> processBTOApplication(manager, scanner);
            case 10 -> processWithdrawalRequest(manager, scanner);
            case 11 -> generateApplicantsReport(manager, scanner);
            case 12 -> viewAndReplyEnquiries(scanner, manager);
            case 13 -> modifyPassword(manager, scanner);
            case 14 -> viewProjectsByFilter(manager, scanner);
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
                openingDate.format(DATE_DISPLAY_FORMATTER), closingDate.format(DATE_DISPLAY_FORMATTER),
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
        System.out.print("Enter Project Number to be edited: ");
        int choice = ValidChoice.getValidChoice(scanner, 1, myProjects.size());
        Project project = myProjects.get(choice - 1);

        System.out.println("\nEditing Project: " + project.getProjectName());
        System.out.println("Select field to edit:");
        System.out.println("1. Type 1 Units");
        System.out.println("2. Type 2 Units");
        System.out.println("3. Type 1 Price");
        System.out.println("4. Type 2 Price");
        System.out.println("5. Application Dates");

        int fieldChoice = ValidChoice.getValidChoice(scanner, 1, 5);

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
                LocalDate existingOpen = LocalDate.parse(project.getOpeningDate(), DATE_DISPLAY_FORMATTER);
                LocalDate existingClose = LocalDate.parse(project.getClosingDate(), DATE_DISPLAY_FORMATTER);

                LocalDate newOpen = getValidDateInput(scanner,
                        "Current Opening Date: " + existingOpen.format(DATE_DISPLAY_FORMATTER) +
                        "\nEnter new date (yyyy/mm/dd): ");
                LocalDate newClose = getValidDateInput(scanner,
                        "Current Closing Date: " + existingClose.format(DATE_DISPLAY_FORMATTER) +
                        "\nEnter new date (yyyy/mm/dd): ");

                if (newClose.isBefore(newOpen)) {
                    System.out.println("Closing date cannot be before opening date.");
                    return;
                }

                project.setOpeningDate(newOpen.format(DATE_DISPLAY_FORMATTER));
                project.setClosingDate(newClose.format(DATE_DISPLAY_FORMATTER));
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

        System.out.print("Enter Project Number to Delete: ");
        int choice = ValidChoice.getValidChoice(scanner, 1, myProjects.size());
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
        System.out.print("Enter Project Number to Toggle: ");
        int choice = ValidChoice.getValidChoice(scanner, 1, myProjects.size());
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
            p.displayDetails();
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

    private static boolean hasOverlappingProjects(Manager manager, ProjectList projectList,
            LocalDate newOpen, LocalDate newClose) {
    	for (Project p : projectList.getAllProjects()) {
    		if (p.getManagerName().equals(manager.getName())) {
// Parse the stored date strings using your formatter
    			LocalDate existingOpen = LocalDate.parse(p.getOpeningDate(), DATE_INPUT_FORMATTER);
    			LocalDate existingClose = LocalDate.parse(p.getClosingDate(), DATE_INPUT_FORMATTER);

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
            System.out.println("Dates: " + LocalDate.parse(p.getOpeningDate(), DATE_DISPLAY_FORMATTER) +
                    " to " + LocalDate.parse(p.getClosingDate(), DATE_DISPLAY_FORMATTER));
            System.out.println("Officers: " + p.getOfficers().size() + "/" + p.getOfficerSlot());
        }
    }

    protected static void viewPendingOfficerRegistrations(Manager manager, Scanner scanner) {
        // Retrieve the projects created by the manager
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);
        boolean pendingFound = false;
        
        // Iterate over each project created by the manager
        for (Project p : myProjects) {
            // Iterate over all officers that have registrations
            for (Officer officer : RegistrationList.getRegistrationList().keySet()){
                // Retrieve the registration conditions for the officer as a map of <Project, String>
                HashMap<Project, String> conditions = RegistrationList.getRegistrationConditions(officer);
                // Check if this officer's registration for project p is pending (i.e. equals "Pending")
                if (conditions.containsKey(p) && "Pending".equalsIgnoreCase(conditions.get(p))) {
                    pendingFound = true;
                    System.out.println("Pending registration: Officer " + officer.getName() 
                            + " for project " + p.getProjectName());
                }
            }
        }
        if (!pendingFound) {
            System.out.println("No pending officer registrations found for your projects.");
        }
    }


    protected static void processOfficerRegistration(Manager manager, Scanner scanner) {
        // Get the projects created by the manager
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);
        boolean foundPending = false;
        
        // Iterate over each project created by this manager
        for (Project p : myProjects) {
            System.out.println("\nProject: " + p.getProjectName());
            // Get the complete registration list from RegistrationList
            HashMap<Officer, ArrayList<Project>> regList = RegistrationList.getRegistrationList();
            
            // Iterate through each officer in the registration list
            for (Officer officer : regList.keySet()) {
                ArrayList<Project> officerProjects = regList.get(officer);
                // Check if this officer has registered for the current project
                if (officerProjects.contains(p)) {
                    // Retrieve the registration condition (as a String) for this officer and project
                    String condition = RegistrationList.getRegistrationCondition(officer, p);
                    if ("Pending".equalsIgnoreCase(condition)) {
                        foundPending = true;
                        System.out.println("Officer " + officer.getName() + " has requested to register for project " + p.getProjectName());
                        System.out.println("Enter 1 to Approve or 2 to Reject this registration:");
                        int choice = ValidChoice.getValidChoice(scanner, 1, 2);
                        
                        if (choice == 1) {
                            // Approve: update condition to "Approved" and add officer to project if not already added
                            RegistrationList.addRegistrationCondition(officer, p, "Approved");
                            if (!p.getOfficers().contains(officer.getName())) {
                                p.addOfficer(officer.getName());
                            }
                            System.out.println("Officer " + officer.getName() + " approved for project " + p.getProjectName());
                        } else {
                            // Reject: update condition to "Rejected" and remove registration
                            RegistrationList.addRegistrationCondition(officer, p, "Rejected");
                            RegistrationList.removeRegistration(officer, p);
                            System.out.println("Officer " + officer.getName() + " registration rejected for project " + p.getProjectName());
                        }
                    }
                }
            }
        }
        
        if (!foundPending) {
            System.out.println("No pending officer registrations found for your projects.");
        }
    }

    protected static void processBTOApplication(Manager manager, Scanner scanner) {
        if (manager.getMyProject().isEmpty()){
            System.out.println("You don't have any projects with applications.");
            return;
        }
        for (Project p : manager.getMyProject()) {
            System.out.println("\nProject: " + p.getProjectName());
            HashMap<Applicant, String> applicationAndStatus = Applications.getApplicationAndStatus(p);
            if (applicationAndStatus != null && !applicationAndStatus.isEmpty()){
                for (Applicant a : applicationAndStatus.keySet()){
                    String status = applicationAndStatus.get(a);
                    if (status.equalsIgnoreCase("Pending")){
                        // Assume applicant a has a flatType field indicating "2-Room" or "3-Room"
                        System.out.println("Applicant: " + a.getName() + " applied for flat type: " + a.getFlatType());
                        System.out.println("Current status: " + status);
                        System.out.println("Enter 1 to Approve or 2 to Reject this application:");
                        int choice = ValidChoice.getValidChoice(scanner, 1, 2);
                        if (choice == 1) {
                            String flatType = a.getFlatType();
                            if (flatType.equalsIgnoreCase("2-Room")) {
                                if (p.getType1Units() > 0) {
                                    p.setType1Units(p.getType1Units() - 1);
                                    Applications.updateApplicationStatus(p, a, "Successful - 2-Room booked");
                                    System.out.println("Application approved. 2-Room booked for " + a.getName());
                                } else {
                                    Applications.updateApplicationStatus(p, a, "Rejected - No 2-Room units available");
                                    System.out.println("No 2-Room units available. Application rejected.");
                                }
                            } else if (flatType.equalsIgnoreCase("3-Room")) {
                                if (p.getType2Units() > 0) {
                                    p.setType2Units(p.getType2Units() - 1);
                                    Applications.updateApplicationStatus(p, a, "Successful - 3-Room booked");
                                    System.out.println("Application approved. 3-Room booked for " + a.getName());
                                } else {
                                    Applications.updateApplicationStatus(p, a, "Rejected - No 3-Room units available");
                                    System.out.println("No 3-Room units available. Application rejected.");
                                }
                            } else {
                                System.out.println("Unknown flat type for applicant " + a.getName() + ". Skipping.");
                            }
                        } else {
                            Applications.updateApplicationStatus(p, a, "Unsuccessful");
                            System.out.println("Application rejected for " + a.getName());
                        }
                    }
                }
            } else {
                System.out.println("No pending applications for project " + p.getProjectName());
            }
        }
    }


    protected static void processWithdrawalRequest(Manager manager, Scanner scanner) {
        for (Project p : manager.getMyProject()){
            Applicant withdrawalApplicant = WithdrawApplication.getWithdrawalApplicant(p);
            if (withdrawalApplicant != null) {
                System.out.println("\nProject: " + p.getProjectName());
                System.out.println("Withdrawal request from applicant: " + withdrawalApplicant.getName());
                System.out.println("Do you want to approve the withdrawal? (y/n)");
                String choice = scanner.nextLine().trim();
                if (choice.equalsIgnoreCase("y")) {
                    WithdrawApplication.removeWithdrawal(p);
                    Applications.updateApplicationStatus(p, withdrawalApplicant, "Withdrawn");
                    Applications.removeApplication(p, withdrawalApplicant);
                    System.out.println("Application withdrawn successfully.");
                } else if (choice.equalsIgnoreCase("n")) {
                    System.out.println("Withdrawal request not processed.");
                } else {
                    System.out.println("Invalid input. Please enter y or n.");
                }
            }
        }
    }

    protected static void generateApplicantsReport(Manager manager, Scanner scanner) {
        // Get the manager's projects from the global project list
        ProjectList projectList = Initialization.getInstance().getProjectList();
        ArrayList<Project> myProjects = getManagerProjects(manager, projectList);
        
        System.out.println("\n=== Applicants Report ===");
        System.out.print("Would you like to filter by marital status? (y/n): ");
        String filterChoice = scanner.nextLine().trim();
        String filterStatus = "";
        if (filterChoice.equalsIgnoreCase("y")) {
            System.out.print("Enter marital status to filter by (e.g., Married or Single): ");
            filterStatus = scanner.nextLine().trim();}
        
        boolean reportFound = false;
        // Iterate over each project created by the manager,,,
        for (Project p : myProjects) {
            HashMap<Applicant, String> bookingsForProject = Booking.getBookings(p);
            if (bookingsForProject != null && !bookingsForProject.isEmpty()) {
                System.out.println("\nProject: " + p.getProjectName());
                // Iterate over each applicant who has a booking
                for (Applicant a : bookingsForProject.keySet()) {
                    // If filtering is enabled, skip applicants whose marital status dont match
                    if (!filterStatus.isEmpty() && !a.getMaritalStatus().equalsIgnoreCase(filterStatus)) {
                        continue;
                    }
                    String bookingStatus = bookingsForProject.get(a);
                    System.out.println("Applicant: " + a.getName() + " (" + a.getNRIC() + ")");
                    System.out.println("Marital Status: " + a.getMaritalStatus());
                    System.out.println("Booking Status: " + bookingStatus);
                    System.out.println("------------------------------------");
                    reportFound = true;
                }
            }
        }
        if (!reportFound) {
            System.out.println("No booking records found that match the criteria.");
        }
    }


    protected static void viewAndReplyEnquiries(Scanner scanner, Manager manager) {
        ApplicantList applicants = Initialization.getInstance().getApplicantList();
        for (Applicant applicant : applicants.getAllApplicants()){
            for(Project project : manager.getMyProject()){
                if (Enquiry.getEnquiryByApplicant(applicant)!= null){
                    System.out.println("Applicant: " + applicant.getName());
                    System.out.println("Project: " + project.getProjectName());
                    System.out.println("Inquiries: ");
                    List<String> inquiries = Enquiry.getEnquiryByProject(applicant,project);
                    for(int i = 0; i < inquiries.size(); i++){
                        System.out.println(i+1 + ". " + inquiries.get(i));
                    }
                    System.out.println("Choose 1 to reply to inquiry and 2 to go back to main menu" );
                    int choice = ValidChoice.getValidChoice(scanner, 1, 2);
                    if (choice == 1) {
                        System.out.println("Choose a number between 1 and " + inquiries.size() + " to reply to inquiry");
                        choice = ValidChoice.getValidChoice(scanner, 1, inquiries.size());
                        System.out.println("Enter your reply: ");
                        String message = scanner.nextLine();
                        Enquiry.upDateApplicantEnquiry(applicant, project, inquiries.get(choice - 1), inquiries.get(choice - 1) + " Replied by " + manager.getName() + ": " + message);
                    }
                    else if (choice == 2) {
                        return;
                    }
                }
            }
        }
    }

    protected static void modifyPassword(Manager manager, Scanner scanner) {
        System.out.print("\nEnter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (newPassword.equals(confirmPassword)) {
            manager.setPassword(newPassword);
            System.out.println("Password updated successfully! Please login again.");
            throw new ForceLogoutException(); // 抛出异常
        } else {
            System.out.println("Passwords do not match. Password not changed.");
        }
    }
    
    protected static void viewProjectsByFilter(Manager manager, Scanner scanner) {
        System.out.println("\n=== View Projects By Filter ===");
        
        // Ask for filter criteria
        System.out.print("Enter location filter (or press Enter to show all): ");
        String location = scanner.nextLine().trim();
        System.out.print("Enter flat type filter (e.g., 2-Room or 3-Room, or press Enter to show all): ");
        String flatType = scanner.nextLine().trim();
        
        // Retrieve all projects from the global ProjectList
        ProjectList projectList = Initialization.getInstance().getProjectList();
        List<Project> allProjects = projectList.getAllProjects();
        
        // Use ViewBy.filterAndSort to filter and sort projects alphabetically by project name
        List<Project> filteredProjects = ViewBy.filterAndSort(allProjects, p -> {
            boolean locationMatches = location.isEmpty() || p.getNeighborhood().equalsIgnoreCase(location);
            boolean flatTypeMatches = flatType.isEmpty() ||
                    p.getType1().equalsIgnoreCase(flatType) ||
                    p.getType2().equalsIgnoreCase(flatType);
            return locationMatches && flatTypeMatches;
        }, (p1, p2) -> p1.getProjectName().compareToIgnoreCase(p2.getProjectName()));
        
        // Display the filtered results
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match the given criteria.");
        } else {
            System.out.println("Filtered Projects:");
            for (Project p : filteredProjects) {
                p.displayDetails(); // Assumes displayDetails() prints project information
            }
        }
    }
}