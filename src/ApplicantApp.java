import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ApplicantApp {
    public static void main(Applicant applicant) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Applicant Portal");
        System.out.println("Logged in as: " + applicant.getName() + " (" + applicant.getNRIC() + ")");

        while (true) {
            displayMainMenu();
            int choice = ValidChoice.getValidChoice(scanner, 0, 10);

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
            if (p.isVisible() && isEligible(applicant, p)) {
                displayProjectDetails(p);
                hasProjects = true;
            }
        }

        if (!hasProjects) {
            System.out.println("No eligible visible projects found based on your profile.");
        }
    }

    protected static boolean isEligible(Applicant applicant, Project p) {
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

    protected static void displayProjectDetails(Project p) {
        System.out.println("- " + p.getProjectName() + " (" + p.getNeighborhood() + ")");
        System.out.printf("  Type1: %s (%d units) - $%.2f\n", p.getType1(), p.getType1Units(), p.getType1Price());
        System.out.printf("  Type2: %s (%d units) - $%.2f\n", p.getType2(), p.getType2Units(), p.getType2Price());
        System.out.println("  Dates: " + p.getOpeningDate() + " to " + p.getClosingDate());
        System.out.println("  Manager: " + p.getManagerName());
    }

    protected static void applyForProject(Applicant applicant, Scanner scanner) {
        ProjectList projectList = Initialization.getInstance().getProjectList();
        System.out.print("Enter the name of the project you wish to apply for: ");
        String projectName = scanner.nextLine();
        Project project = projectList.getProject(projectName);

        if (project == null || !isEligible(applicant, project)) {
            System.out.println("Invalid or ineligible project.");
            return;
        }
        if (Applications.getApplications().containsKey(project) &&
                Applications.getApplications().get(project).containsKey(applicant)) {
            if (!"rejected".equals(Applications.getApplications().get(project).get(applicant))) {
                System.out.println("You have already applied for this project.");
                return;
            }
        }
        if(!(applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35)||
                !(applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21)){
            System.out.println("You are not allowed to apply for BTO projects.");
            return;
        }
        if(applicant.getMaritalStatus().equalsIgnoreCase("Single") && applicant.getAge() >= 35){
            System.out.println("You can only apply for 2-Room projects. Choose 1 to continue, 0 to exit.");
            int choice = ValidChoice.getValidChoice(scanner, 0, 1);
            if (choice == 0) {
                return;
            }
            if (choice == 1) {
                Applications.addApplication(project, applicant, "Pending"+" 2-Room");
                applicant.setAppliedProject(project);
                applicant.setFlatType("2-Room");
                System.out.println("You have successfully applied for: " + projectName + " 2-Room");
            }
        }
        if(applicant.getMaritalStatus().equalsIgnoreCase("Married") && applicant.getAge() >= 21){
            System.out.println("You can apply for 2-Room or 3-Room projects.  Choose 1 to 2-Room, 2 to 3-Room, 0 to exit.");
            int choice = ValidChoice.getValidChoice(scanner, 0, 2);
            if (choice == 0) {
                return;
            }
            if (choice == 1) {
                Applications.addApplication(project, applicant, "Pending"+" 2-Room");
                applicant.setAppliedProject(project);
                applicant.setFlatType("2-Room");
                System.out.println("You have successfully applied for: " + projectName + " 2-Room");
            }
            else if (choice == 2) {
                Applications.addApplication(project, applicant, "Pending"+" 3-Room");
                applicant.setAppliedProject(project);
                applicant.setFlatType("3-Room");
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
                System.out.println("Choose a flat number from 1 to "
                        + applicant.getAppliedProject().getType1Units() + " to continue.");
                int choice = ValidChoice.getValidChoice(scanner, 1,  applicant.getAppliedProject().getType1Units());
                Booking.addBooking(applicant.getAppliedProject(),applicant, "2-Room");
            }
            else if (applicant.getFlatType().equalsIgnoreCase("3-Room")) {
                System.out.println("Your flat type is 3-Room.");
                System.out.println("Choose a flat number from 1 to "
                        + (applicant.getAppliedProject().getType1Units() + applicant.getAppliedProject().getType2Units()) + " to continue.");
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

        if (newPassword.equals(confirmPassword)) {
            applicant.setPassword(newPassword);
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Passwords do not match. Password not changed.");
        }
    }
}