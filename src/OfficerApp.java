import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Locale;
import java.util.Iterator;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
public class OfficerApp extends ApplicantApp {
    public static void main(Officer officer) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to Officer App");
        do {
            System.out.println("\nPlease select an option:");
            // Inherited applicant capabilities
            System.out.println("1. View available projects");
            System.out.println("2. Apply for a project");
            System.out.println("3. View my application status");
            System.out.println("4. Request application withdrawal");
            System.out.println("5. Submit enquiry");
            System.out.println("6. View my enquiries");
            System.out.println("7. Edit enquiry");
            System.out.println("8. Delete enquiry");

            // Officer-specific capabilities
            System.out.println("9. Register to join project");
            System.out.println("10. View registration status");
            System.out.println("11. View managed project details");
            System.out.println("12. Process BTO applications (Approve/Reject)");
            System.out.println("13. Process withdrawal requests (Approve/Reject)");
            System.out.println("14. Assist applicant in booking a unit");
            System.out.println("15. View all applications");
            System.out.println("16. Generate applicants report");
            System.out.println("17. Generate booking receipt");
            System.out.println("18. View and reply to enquiries");
            System.out.println("19. Update remaining flat units");
            System.out.println("20. Retrieve applicant by NRIC");
            System.out.println("21. Update application status to booked");
            System.out.println("22. Update applicant's flat type");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    // Inherited applicant functions
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

                    // Officer-specific functions
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
                        processBTOApplications(officer);
                        break;
                    case 13:
                        processWithdrawalRequests(officer);
                        break;
                    case 14:
                        assistFlatBooking(officer);
                        break;
                    case 15:
                        viewAllApplications(officer);
                        break;
                    case 16:
                        generateApplicantsReport(officer);
                        break;
                    case 17:
                        generateBookingReceipt(officer);
                        break;
                    case 18:
                        viewAndReplyEnquiries(officer);
                        break;
                    case 19:
                        updateRemainingUnits(officer);
                        break;
                    case 20:
                        retrieveApplicantByNRIC(officer);
                        break;
                    case 21:
                        updateStatusToBooked(officer);
                        break;
                    case 22:
                        updateApplicantFlatType(officer);
                        break;
                    case 0:
                        System.out.println("Exiting Officer App. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 22.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1;
            }

        } while (choice != 0);
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
        if(project.getOfficers().contains(officer.getName())){
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
    private static void viewRegistrationStatus(Officer officer) {}
    private static void viewManagedProjectDetails(Officer officer) {}
    private static void processBTOApplications(Officer officer) {}
    private static void processWithdrawalRequests(Officer officer) {}
    private static void assistFlatBooking(Officer officer) {}
    private static void viewAllApplications(Officer officer) {}
    private static void generateApplicantsReport(Officer officer) {}
    private static void generateBookingReceipt(Officer officer) {}
    private static void viewAndReplyEnquiries(Officer officer) {}
    private static void updateRemainingUnits(Officer officer) {}
    private static void retrieveApplicantByNRIC(Officer officer) {}
    private static void updateStatusToBooked(Officer officer) {}
    private static void updateApplicantFlatType(Officer officer) {}
}