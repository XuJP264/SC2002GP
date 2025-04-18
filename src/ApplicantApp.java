import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ApplicantApp {
    private static ValidPassword validPassword;
    public static void setValidPassword(ValidPassword validator) {
        validPassword = validator;
    }
    public static void main(Applicant applicant) {
        setValidPassword(new PasswordChecker());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Applicant Portal");
        System.out.println("Logged in as: " + applicant.getName() + " (" + applicant.getNRIC() + ") ");

        try {
            while (true) {
                displayMainMenu();
                int choice = ValidChoice.getValidChoice(scanner, 0, 10);

                if (choice == 0) {
                    System.out.println("Logging out... Goodbye!");
                    return;
                }

                processChoice(applicant, scanner, choice);
            }
        } catch (ForceLogoutException e) {
            System.out.println(e.getMessage());
            return; // 退出 main 方法
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== APPLICANT MENU ===");
        System.out.println("1. View Eligible Projects");
        System.out.println("2. Apply for a Project");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Application");
        System.out.println("5. Submit Project Inquiry");
        System.out.println("6. View Project Inquiries");
        System.out.println("7. Edit Project Inquiry");
        System.out.println("8. Delete Project Inquiry");
        System.out.println("9. Choose the Flat When Your Application is Approved");
        System.out.println("10. Modify Password");
        System.out.println("0. Logout");
    }

    protected static void processChoice(Applicant applicant, Scanner scanner, int choice) {
        switch (choice) {
            case 1 -> viewEligibleProjects(applicant);
            case 2 -> applyForProject(applicant, scanner);
            case 3 -> viewApplicationStatus(applicant);
            case 4 -> withdrawApplication(applicant, scanner);
            case 5 -> submitProjectInquiry(applicant, scanner);
            case 6 -> viewProjectInquiries(applicant);
            case 7 -> editProjectInquiry(applicant, scanner);
            case 8 -> deleteProjectInquiry(applicant, scanner);
            case 9 -> chooseFlat(applicant, scanner);
            case 10 -> modifyPassword(applicant, scanner);
        }
    }

    protected static void viewEligibleProjects(Applicant applicant) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        boolean hasProjects = false;
        System.out.println("\n=== Eligible Projects ===");
        for (Project p : projectList.getProjects().values()) {
            if (p.isVisible() && checkEligibility(applicant, p) == 1) {
                hasProjects = true;
                displayProjectDetails(p);
            }
            else if (p.isVisible() && checkEligibility(applicant, p) == 2){
                hasProjects = true;
                displayProjectDetailsForSingle(p);
            }
        }
        if (!hasProjects) {
            System.out.println("No eligible visible projects found based on your profile. ");
        }
    }
    protected static int checkEligibility(Applicant applicant, Project p) {
        if (applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21){
            return 1;
        }
        else if (applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35){
            return 2;
        }
        return 0;
    }
    protected static void displayProjectDetails(Project p) {
        System.out.println("- " + p.getProjectName() + " (" + p.getNeighborhood() + ")");
        System.out.printf("  Type1: %s (%d units) - $%.2f\n", p.getType1(), p.getType1Units(), p.getType1Price());
        System.out.printf("  Type2: %s (%d units) - $%.2f\n", p.getType2(), p.getType2Units(), p.getType2Price());
        System.out.println("  Dates: " + p.getOpeningDate() + " to " + p.getClosingDate());
        System.out.println("  Manager: " + p.getManagerName());
    }
    protected static void displayProjectDetailsForSingle(Project p) {
        System.out.println("- " + p.getProjectName() + " (" + p.getNeighborhood() + ")");
        System.out.printf("  Type1: %s (%d units) - $%.2f\n", p.getType1(), p.getType1Units(), p.getType1Price());
        System.out.println("  Dates: " + p.getOpeningDate() + " to " + p.getClosingDate());
        System.out.println("  Manager: " + p.getManagerName());
    }

    protected static void applyForProject(Applicant applicant, Scanner scanner) {
        viewEligibleProjects(applicant);
        System.out.print("Enter project name to apply: ");
        String projectName = scanner.nextLine();
        ProjectList projectList = Initialization.getInstance().getProjectList();
        Project project = projectList.getProject(projectName);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        if (!project.isVisible()){
            System.out.println("Project is not visible.");
            return;
        }
        if (checkEligibility(applicant, project)==0) {
            System.out.println("You are not eligible for this project.");
            return;
        }
        if (applicant.getAppliedProject() != null) {
            if (Applications.getApplicationAndStatus(applicant.getAppliedProject())!=null
                    && !Applications.getApplications().get(applicant.getAppliedProject()).get(applicant).contains("Rejected")){
            System.out.println("You have already applied for a project.");
            return;
            }
        }
        HashMap<Applicant, String> projectMap = Applications.getApplicationAndStatus(project);
        if (project.getOfficers().contains(applicant.getName())) {
            System.out.println("You are already an officer for this project.");
            return;
        }
        if (checkEligibility(applicant, project) == 1) {
            System.out.println("You are eligible for this project.");
            System.out.println("Choose 1 for 2-room flat or 2 for 3-room flat, 0 to cancel.");
            int choice = ValidChoice.getValidChoice(scanner, 0, 2);
            if (choice == 1) {
                if (project.getType1Units() <= 0) {
                    System.out.println("No units available for 2-room flat.");
                    return;
                }
                Applications.addApplication(project, applicant, "Pending 2-Room");
                applicant.setFlatType("2-Room");
            }
            else if (choice == 2) {
                if (project.getType2Units() <= 0) {
                    System.out.println("No units available for 3-room flat.");
                    return;
                }
                Applications.addApplication(project, applicant, "Pending 3-Room");
                applicant.setFlatType("3-Room");
            }
            else if (choice == 0) {
                System.out.println("Goodbye!");
                return;
            }
            applicant.setAppliedProject(project);
            System.out.println("You have successfully applied for this project.");
        }
        else if (checkEligibility(applicant, project) == 2) {
            System.out.println("You are eligible for this project.");
            System.out.println("Choose 1 for 2-room flat. 0 to cancel.");
            int choice = ValidChoice.getValidChoice(scanner, 0, 1);
            if (choice == 1) {
                if (project.getType1Units() <= 0) {
                    System.out.println("No units available for 2-room flat.");
                    return;
                }
                Applications.addApplication(project, applicant, "Pending 2-Room");
                applicant.setFlatType("2-Room");
                applicant.setAppliedProject(project);
                System.out.println("You have successfully applied for this project.");
            }
            else if (choice == 0) {
                System.out.println("Goodbye!");
                return;
            }
        }
    }

    protected static void viewApplicationStatus(Applicant applicant) {
        Project project = applicant.getAppliedProject();
        if (project == null) {
            System.out.println("You have not applied for any project yet.");
            return;
        }

        String status = Applications.getApplications()
                .getOrDefault(project, new HashMap<>())
                .getOrDefault(applicant, "Status not found");

        System.out.println("Project: " + project.getProjectName());
        System.out.println("Status: " + status);
    }

    protected static void withdrawApplication(Applicant applicant, Scanner scanner) {
        Project project = applicant.getAppliedProject();
        if (project == null) {
            System.out.println("No application found to withdraw.");
            return;
        }
        if (Applications.getApplicationAndStatus(project)!= null){
            if (Applications.getApplications().get(project).get(applicant).contains("Pending")) {
                WithdrawApplication.addWithdrawal(project, applicant);
                System.out.println("Withdraw message has been sent successfully.");
            }
            else{
                System.out.println("the application status is not pending, you cannot withdraw.");
            }
        }
        else{
            System.out.println("there is no application with the project.");
        }
    }

    protected static void submitProjectInquiry(Applicant applicant, Scanner scanner) {
        System.out.print("Enter project name for inquiry: ");
        String projectName = scanner.nextLine();
        Project project = Initialization.getInstance().getProjectList().getProject(projectName);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        if (project.isVisible() == false){
            System.out.println("Project is not visible.");
            return;
        }
        System.out.print("Enter your inquiry: ");
        String inquiry = scanner.nextLine();

        if (!inquiry.isBlank()) {
            Enquiry.addApplicantEnquiry(applicant, project, inquiry);
            System.out.println("Inquiry submitted for project: " + projectName);
        } else {
            System.out.println("Inquiry cannot be empty.");
        }
    }

    protected static void viewProjectInquiries(Applicant applicant) {
        HashMap<Project, ArrayList<String>> enquiries = Enquiry.getEnquiryByApplicant(applicant);

        if (enquiries == null || enquiries.isEmpty()) {
            System.out.println("You have no project inquiries.");
            return;
        }

        System.out.println("=== Your Project Inquiries ===");
        for (Map.Entry<Project, ArrayList<String>> entry : enquiries.entrySet()) {
            System.out.println("\nProject: " + entry.getKey().getProjectName());
            int i = 1;
            for (String inquiry : entry.getValue()) {
                System.out.println(i++ + ". " + inquiry);
            }
        }
    }

    protected static void editProjectInquiry(Applicant applicant, Scanner scanner) {
        viewProjectInquiries(applicant);
        HashMap<Project, ArrayList<String>> enquiries = Enquiry.getEnquiryByApplicant(applicant);

        if (enquiries == null || enquiries.isEmpty()) return;

        System.out.print("Enter project name to edit inquiry: ");
        String projectName = scanner.nextLine();
        Project project = Initialization.getInstance().getProjectList().getProject(projectName);

        if (project == null || !enquiries.containsKey(project)) {
            System.out.println("Invalid project or no inquiries found.");
            return;
        }

        List<String> projectInquiries = enquiries.get(project);
        System.out.print("Enter inquiry number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < projectInquiries.size()) {
                System.out.print("Enter new inquiry: ");
                String newInquiry = scanner.nextLine();
                if (!newInquiry.isBlank()) {
                    Enquiry.upDateApplicantEnquiry(applicant, project,
                            projectInquiries.get(index), newInquiry);
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

    protected static void deleteProjectInquiry(Applicant applicant, Scanner scanner) {
        viewProjectInquiries(applicant);
        HashMap<Project, ArrayList<String>> enquiries = Enquiry.getEnquiryByApplicant(applicant);

        if (enquiries == null || enquiries.isEmpty()) return;

        System.out.print("Enter project name to edit inquiry: ");
        String projectName = scanner.nextLine();
        Project project = Initialization.getInstance().getProjectList().getProject(projectName);

        if (project == null || !enquiries.containsKey(project)) {
            System.out.println("Invalid project or no inquiries found.");
            return;
        }

        List<String> projectInquiries = enquiries.get(project);
        System.out.print("Enter inquiry number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < projectInquiries.size()) {
                Enquiry.removeApplicantEnquiry(applicant, project, projectInquiries.get(index));
                System.out.println("Inquiry removed.");
            } else {
                System.out.println("Invalid inquiry number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    protected static void chooseFlat(Applicant applicant, Scanner scanner) {
        if (applicant.getApplicationStatus().contains("Successful")) {
            System.out.println("You have already been accepted for the project.");
            if (applicant.getFlatType().equalsIgnoreCase("2-Room")) {
                System.out.println("Your flat type is 2-Room.");
                System.out.println("Choose a flat number from 1 to " + applicant.getAppliedProject().getType1Units() + " to continue.");
                int choice = ValidChoice.getValidChoice(scanner, 1,  applicant.getAppliedProject().getType1Units());
                Booking.addBooking(applicant.getAppliedProject(),applicant, "2-Room");
            }
            else if (applicant.getFlatType().equalsIgnoreCase("3-Room")) {
                System.out.println("Your flat type is 3-Room.");
                System.out.println("Choose a flat number from 1 to " + applicant.getAppliedProject().getType2Units() + " to continue.");
                int choice = ValidChoice.getValidChoice(scanner, 1,  applicant.getAppliedProject().getType2Units());
                Booking.addBooking(applicant.getAppliedProject(),applicant, "3-Room");
            }
        }
        else{
            System.out.println("You have not been accepted for the project yet.");
            return;
        }
    }
    protected static void modifyPassword(Applicant applicant, Scanner scanner) {
        System.out.print("\nEnter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Password not changed.");
            return;
        }

        if (!validPassword.isValid(newPassword)) {
            System.out.println("Password does not meet requirements. Password not changed.");
            return;
        }

        applicant.setPassword(newPassword);
        System.out.println("Password updated successfully! Please login again.");
        throw new ForceLogoutException(); // 抛出异常
    }

}
class ForceLogoutException extends RuntimeException {
    public ForceLogoutException() {
        super("Password changed. Please login again.");
    }
}