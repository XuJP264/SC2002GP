import java.util.Scanner;

public class ApplicantApp{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ApplicantList applicantList = new ApplicantList();

        System.out.println("Welcome to the Applicant App");
        System.out.println("Please enter your details to create an account.");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("NRIC: ");
        String NRIC = scanner.nextLine();

        System.out.print("Marital Status (Single/Married): ");
        String maritalStatus = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Appplicant applicant = new Applicant(name, age, NRIC, maritalStatus, password);
        applicantList.addApplicant(applicant);

        System.out.println("Account created successfully!");

        while (true){
            System.out.println("\nMenu:");
            System.out.println("1. Apply for a Project");
            System.out.println("2. View Application Status");
            System.out.println("3. Withdraw Application");
            System.out.println("4. Submit Inquiry");
            System.out.println("5. View Inquiries");
            System.out.println("6. Update Password");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    System.out.print("Enter Project Name: ");
                    String projectName = scanner.nextLine();
                    applicant.applyForProject(projectName, age, maritalStatus);
                    break;

                case 2:
                    System.out.println("Application Status: " + applicant.getApplicationStatus());
                    break;

                case 3:
                    applicant.withdrawApplication();
                    break;

                case 4:
                    System.out.print("Enter Inquiry: ");
                    String inquiry = scanner.nextLine();
                    applicant.addInquiry(inquiry);
                    break;

                case 5:
                    applicant.viewInquiries();
                    break;

                case 6:
                    applicant.updatePassword();
                    break;

                case 7:
                    System.out.println("Exiting application.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        

        

        
