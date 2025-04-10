import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
public class OfficerApp extends ApplicantApp{
    public static void main(Officer officer) {
        System.out.println(officer.getProjectsInCharge().isEmpty());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the HDB Applicant Portal");
        System.out.println("Logged in as: " + officer.getName() + " (" + officer.getNRIC() + ")");

        try {
            while (true) {
                displayMainMenu();
                int choice = ValidChoice.getValidChoice(scanner, 0, 16);

                if (choice == 0) {
                    System.out.println("Logging out... Goodbye!");
                    return;
                }

                processChoice(officer, scanner, choice);
            }
        } catch (ForceLogoutException e) {
            System.out.println(e.getMessage());
            return; // 退出 main 方法
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

        System.out.println("\n=== APPLICANT MENU ===");
        System.out.println("1. View Eligible Projects");
        System.out.println("2. Apply for a Project");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Application");
        System.out.println("5. Submit Project Inquiry");
        System.out.println("6. View Project Inquiries");
        System.out.println("7. Edit Project Inquiry");
        System.out.println("8. Delete Project Inquiry");
        System.out.println("9. Choose the Flat Type");
        System.out.println("\n[Officer Functions]");
        System.out.println("10. Register to Manage Project");
        System.out.println("11. View Registration Status");
        System.out.println("12. View Managed Project Details");
        System.out.println("13. Assist Applicant Booking");
        System.out.println("14. Generate Booking Receipt");
        System.out.println("15. View and Reply Inquiries");
        System.out.println("16. Modify Password");
        System.out.println("0. Logout");
    }

    protected static void processChoice(Officer officer, Scanner scanner, int choice) {
        switch (choice) {
            case 1 -> viewEligibleProjects(officer);
            case 2 -> applyForProject(officer, scanner);
            case 3 -> viewApplicationStatus(officer);
            case 4 -> withdrawApplication(officer, scanner);
            case 5 -> submitProjectInquiry(officer, scanner);
            case 6 -> viewProjectInquiries(officer);
            case 7 -> editProjectInquiry(officer, scanner);
            case 8 -> deleteProjectInquiry(officer, scanner);
            case 9 -> chooseFlat(officer, scanner);
            case 10 -> registerForProject(officer, scanner);
            case 11 -> viewRegistrationStatus(officer);
            case 12 -> viewManagedProjectDetails(officer);
            case 13 -> assistFlatBooking(officer, scanner);
            case 14 -> generateBookingReceipt(officer, scanner);
            case 15 -> viewAndReplyInquiry(officer, scanner);
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
           p.displayDetails();
        }
    }
    protected static void assistFlatBooking(Officer officer, Scanner scanner) {
       for(Project project : officer.getProjectsInCharge()){
           if(Booking.getBookings(project)!= null){
               for(Applicant applicant : Booking.getBookings(project).keySet()) {
                   System.out.println("Project: " + project.getProjectName());
                   System.out.println("Applicant: " + applicant.getName());
                   System.out.println("Booking Type: " + Booking.getBookingType(project, applicant));
                   System.out.println("Enter 1 to finish booking or 0 to ignore applicant");
                   int choice = ValidChoice.getValidChoice(scanner, 0, 1);
                   if (choice == 1) {
                       Applications.updateApplicationStatus(project, applicant, "Booked");
                       if (Booking.getBookingType(project, applicant).equals("2-Room")) {
                           project.setType1Units(project.getType1Units() - 1);
                       }
                       else if (Booking.getBookingType(project, applicant).equals("3-Room")){
                           project.setType2Units(project.getType2Units() - 1);
                       }
                       System.out.println("Booking removed and application status updated");
                   }
               }
           }
           else {
               System.out.println("No bookings found for this project"+project.getProjectName());
           }
       }
    }

    private static void generateBookingReceipt(Officer officer, Scanner scanner) {
        for (Project project : officer.getProjectsInCharge()){
            if(Applications.getApplicationAndStatus(project)!= null){
                for(Applicant applicant : Applications.getApplicationAndStatus(project).keySet()){
                    if(Applications.getApplicationAndStatus(project).get(applicant).equals("Booked")){
                        String fileName = project.getProjectName() + "_" + applicant.getName() +".txt";
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                            writer.write("===== receipt =====\n");
                            writer.write("Project: " + project.getProjectName() + "\n");
                            writer.write("Neighborhood: " + project.getNeighborhood() + "\n");
                            writer.write("Applicant: " + applicant.getName() + "\n");
                            writer.write("Applicant NRIC: " + applicant.getNRIC() + "\n");
                            writer.write("Booking Type: " + Booking.getBookingType(project, applicant) + "\n");
                            writer.write("===== receipt over =====\n");
                            System.out.println("receipt generated: " + fileName);
                        } catch (IOException e) {
                            System.err.println("receipt generation failed: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }
    private static void viewAndReplyInquiry(Officer officer, Scanner scanner) {
        ApplicantList applicants = Initialization.getInstance().getApplicantList();
        for (Applicant applicant : applicants.getAllApplicants()){
            for(Project project : officer.getProjectsInCharge()){
                List<String> inquiries = Enquiry.getEnquiryByProject(applicant, project);
                if (inquiries != null && !inquiries.isEmpty()) {
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
                        Enquiry.upDateApplicantEnquiry(applicant, project, inquiries.get(choice - 1), inquiries.get(choice - 1) + " Replied by " + officer.getName() + ": " + message);
                    }
                    else if (choice == 2) {
                        return;
                    }
                }
            }
        }
    }
    protected static void modifyPassword(Officer officer, Scanner scanner) {
        System.out.print("\nEnter new password: ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();

        if (newPassword.equals(confirmPassword)) {
            officer.setPassword(newPassword);
            System.out.println("Password updated successfully! Please login again.");
            throw new ForceLogoutException(); // 抛出异常
        } else {
            System.out.println("Passwords do not match. Password not changed.");
        }
    }
}