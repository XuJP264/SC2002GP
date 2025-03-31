import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class OfficerApp extends ApplicantApp {
    public static void main(Officer officer) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to HDB Officer Portal");
        System.out.println("Logged in as: " + officer.getName() + " (" + officer.getNRIC() + ")");

        while (true) {
            displayMainMenu();

            int choice = getValidChoice(scanner, 0, 16);

            if (choice == 0) {
                System.out.println("Logging out... Goodbye!");
                scanner.close();
                return;
            }
            processUserChoice(officer, choice);
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");

        System.out.println("\n[Applicant Functions]");
        System.out.println("1. View available projects");
        System.out.println("2. Apply for a project");
        System.out.println("3. View my application status");
        System.out.println("4. Request application withdrawal");
        System.out.println("5. Submit enquiry");
        System.out.println("6. View my enquiries");
        System.out.println("7. Edit enquiry");
        System.out.println("8. Delete enquiry");

        System.out.println("\n[Officer Functions]");
        System.out.println("9. Register to manage a project");
        System.out.println("10. View registration status");
        System.out.println("11. View managed project details");
        System.out.println("12. Process withdrawal requests");
        System.out.println("13. Assist applicant in booking");
        System.out.println("14. View all applications");
        System.out.println("15. Generate booking receipt");
        System.out.println("16. View and reply to enquiries");
        System.out.println("\n0. Logout");
    }

    private static int getValidChoice(Scanner scanner, int min, int max) {
        while (true) {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void processUserChoice(Officer officer, int choice) {
        switch (choice) {
            case 1:
                viewAvailableProjects(officer);
                break;
            case 2:
                applyForProject(officer);
                break;
            case 3:
                viewApplicationStatus(officer);
                break;
            case 4:
                requestWithdrawal(officer);
                break;
            case 5:
                submitEnquiry(officer);
                break;
            case 6:
                viewEnquiries(officer);
                break;
            case 7:
                editEnquiry(officer);
                break;
            case 8:
                deleteEnquiry(officer);
                break;
            case 9:
                registerForProject(officer);
                break;
            case 10:
                viewRegistrationStatus(officer);
                break;
            case 11:
                viewManagedProjectDetails(officer);
                break;
            case 12:
                processWithdrawalRequests(officer);
                break;
            case 13:
                assistFlatBooking(officer);
                break;
            case 14:
                viewAllApplications(officer);
                break;
            case 15:
                generateBookingReceipt(officer);
                break;
            case 16:
                viewAndReplyEnquiries(officer);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    // Inherited from ApplicantApp
    private static void viewAvailableProjects(Officer officer) {}
    private static void applyForProject(Officer officer) {}
    private static void viewApplicationStatus(Officer officer) {}
    private static void requestWithdrawal(Officer officer) {}
    private static void submitEnquiry(Officer officer) {}
    private static void viewEnquiries(Officer officer) {}
    private static void editEnquiry(Officer officer) {}
    private static void deleteEnquiry(Officer officer) {}

    // Officer-specific methods
    private static void registerForProject(Officer officer) {
        //show the list of available projects and let the officer choose one
        Scanner scanner = new Scanner(System.in);
        Initialization initialization = Initialization.getInstance();
        ProjectList projects = initialization.getProjectList();
        projects.showProjectList();
        System.out.print("Enter the name of the project you want to register for: ");
        String projectName = scanner.nextLine();
        Project project = projects.getProject(projectName);
        if(project == null) {
            System.out.println("Invalid project name. Please try again.");
            return;
        }
        //check if the project has been applied by you before or after
        if(officer.getProjectsHaveApplied().contains(project)){
            System.out.println("You have already applied for this project.");
            return;
        }
        //check if the project has already been registered by you
        ArrayList<String> officersName = project.getOfficers();
        if(containsName(officersName, officer.getName())) {
            System.out.println("You are already registered for this project.");
            return;
        }
        //check if the period is overlapped with any other projects
        String openingDate,closingDate;
        openingDate = project.getOpeningDate();
        closingDate = project.getClosingDate();
        for (Project p : officer.getProjectsInCharge()){
            if(DateUtils.isOverlapping(openingDate,closingDate,p.getOpeningDate(),p.getClosingDate())){
                System.out.println("The period for this project has been overlapped with another project.");
                return;
            }
        }
        //check if the officer has enough officer slots
        if(project.getOfficerSlot()-project.getOfficers().size() == 0){
            System.out.println("You do not have any officer slots left.");
            return;
        }
        //send the registration request to the project manager
        RegistrationList.addRegistration(officer,project);
        System.out.println("Registration request sent to the project manager.");
    }
    public static void viewRegistrationStatus(Officer officer) {
        //show the list of projects you are registered for and their status
        HashMap<Project,Boolean> projects = RegistrationList.getRegistrationCondition(officer);
        for (Project p : projects.keySet()) {
            System.out.println(p.getProjectName() + " - " + (projects.get(p) ? "Accepted" : "Unaccepted"));
        }
    }
    public static void viewManagedProjectDetails(Officer officer) {
        ArrayList<Project> projects = officer.getProjectsInCharge();
        for (Project p : projects){
            System.out.println(p.getProjectName());
            System.out.println("Neighborhood: " + p.getNeighborhood());
            System.out.println("Type 1: " + p.getType1() + " - " + p.getType1Units() + " units - " + p.getType1Price() + " per unit");
            System.out.println("Type 2: " + p.getType2() + " - " + p.getType2Units() + " units - " + p.getType2Price() + " per unit");
            System.out.println("Opening date: " + p.getOpeningDate());
            System.out.println("Closing date: " + p.getClosingDate());
            System.out.println("Officer slot: " + p.getOfficerSlot());
            System.out.println("Officers: " + p.getOfficers());
            System.out.println();
        }
    }
    public static void processWithdrawalRequests(Officer officer) {}
    public static void assistFlatBooking(Officer officer) {}
    public static void viewAllApplications(Officer officer) {}
    public static void generateBookingReceipt(Officer officer) {}
    public static void viewAndReplyEnquiries(Officer officer) {}
    public static void modifyPassword(Officer officer) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter your new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Please confirm your new password:");
            String confirmPassword = scanner.nextLine();
            if (newPassword.equals(confirmPassword)) {
                officer.setPassword(newPassword);
                System.out.println("Password has been successfully changed!");
                break;
            } else {
                System.out.println("Passwords do not match!");
                System.out.println("Please try again.");
                continue;
            }
        }
    }
    public static boolean containsName(List<String> list, String target) {
        for (String str : list) {
            if (str.contains(target)) { // 判断 target 是否是 str 的子串
                return true;
            }
        }
        return false;
    }
}