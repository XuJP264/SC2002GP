import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
public class ApplicantApp {
    private static Applications applications = new Applications();

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
        System.out.println("5. Submit Project Inquiry");
        System.out.println("6. View Project Inquiries");
        System.out.println("7. Edit Project Inquiry");
        System.out.println("8. Delete Project Inquiry");
        System.out.println("0. Logout");
    }

    // ... (保留原有的getValidChoice方法)
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
            case 4 -> withdrawApplication(applicant, scanner);
            case 5 -> submitProjectInquiry(applicant, scanner);
            case 6 -> viewProjectInquiries(applicant);
            case 7 -> editProjectInquiry(applicant, scanner);
            case 8 -> deleteProjectInquiry(applicant, scanner);
        }
    }

    // ... (保留原有的viewEligibleProjects和isEligible方法)
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
        ProjectList projectList = Initialization.getInstance().getProjectList();
        System.out.print("Enter the name of the project you wish to apply for: ");
        String projectName = scanner.nextLine();
        Project project = projectList.getProject(projectName);

        if (project == null || !isEligible(applicant, project)) {
            System.out.println("Invalid or ineligible project.");
            return;
        }

        // 使用Applications类管理申请
        applications.addApplication(applicant, project, "Pending");
        applicant.setAppliedProject(project);
        System.out.println("You have successfully applied for: " + projectName);
    }

    private static void viewApplicationStatus(Applicant applicant) {
        Project project = applicant.getAppliedProject();
        if (project == null) {
            System.out.println("You have not applied for any project yet.");
            return;
        }

        String status = applications.getApplications()
                .getOrDefault(applicant, new HashMap<>())
                .getOrDefault(project, "Status not found");

        System.out.println("Project: " + project.getProjectName());
        System.out.println("Status: " + status);
    }

    private static void withdrawApplication(Applicant applicant, Scanner scanner) {
        Project project = applicant.getAppliedProject();
        if (project == null) {
            System.out.println("No application found to withdraw.");
            return;
        }

        applications.removeApplication(applicant, project);
        applicant.setAppliedProject(null);
        System.out.println("Application withdrawn successfully.");
    }

    // 项目相关查询方法
    private static void submitProjectInquiry(Applicant applicant, Scanner scanner) {
        System.out.print("Enter project name for inquiry: ");
        String projectName = scanner.nextLine();
        Project project = Initialization.getInstance().getProjectList().getProject(projectName);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        System.out.print("Enter your inquiry: ");
        String inquiry = scanner.nextLine();

        if (!inquiry.isBlank()) {
            applicant.addProjectEnquiry(project, inquiry);
            System.out.println("Inquiry submitted for project: " + projectName);
        } else {
            System.out.println("Inquiry cannot be empty.");
        }
    }

    private static void viewProjectInquiries(Applicant applicant) {
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

    private static void editProjectInquiry(Applicant applicant, Scanner scanner) {
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

    private static void deleteProjectInquiry(Applicant applicant, Scanner scanner) {
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
}