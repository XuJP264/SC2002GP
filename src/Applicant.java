import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Applicant extends UserCard {
    private String identity;
    private String appliedProject;
    private String applicationStatus;
    private List<String> inquiries;

    public Applicant(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Applicant";
        this.appliedProject = null;
        this.applicationStatus = "none";  // Changed from none (undefined) to "none" (string)
        this.inquiries = new ArrayList<>();  // Corrected initialization
    }

    public String getIdentity() {
        return identity;
    }

    public String getAppliedProject() {
        return appliedProject;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public List<String> getInquiries() {
        return inquiries;
    }

    public void applyForProject(String projectName, int applicantAge, String maritalStatus) {
        if (this.appliedProject != null) {
            System.out.println("You have already applied for a project and cannot apply for another.");
            return;
        }

        this.appliedProject = projectName;
        this.applicationStatus = "pending";  
        System.out.println("Application submitted for project: " + projectName);
    }

    public void withdrawApplication(){
        if(this.appliedProject == null){
            System.out.println("No active application to withdraw.");
            return;
        }

        if (maritalStatus.equals("Single") && applicantAge >= 35){
            if(projectName.equals("2-Room")){
                this.appliedProject = projectName;
                this.applicationStatus = "pending";
                System.out.println("Application submitted for project: " + projectName);
            }else{
                System.out.println("Singles 35 years old and above can only apply for the 2-Room project.");
            }
        }else if (maritalStatus.equals("Married") && applicantAge >= 21){
            this.appliedProject = projectName;
            this.applicationStatus = "pending";
            System.out.println("Application submitted for project: " + projectName);
        } else {
            System.out.println("You do not meet the eligibility criteria for this project.");
        }
    }

    public void withdrawApplication(){
        if (this.appliedProject == null) {
            System.out.println("No active application to withdraw.");
            return;
        }

        this.appliedProject = null;
        this.applicationStatus = "none";
        System.out.println("Application has been successfully withdrawn.");
    }

    public void addInquiry(String Inquiry){
        inquiries.add(inquiry);
        System.out.println("Inquiry submitted: " + inquiry);
    }

    public void viewInquiries(){
        if(inquiries.isEmpty()){
            System.out.println("No inquiries found.");
        }else{
            System.out.println("Your inquiries:");
            for(String inquiry : inquiries){
                System.out.println("- " + inquiry);
            }
        }
    }

    public void updatePassword(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Please enter your new password:");
            String newPassword = scanner.nextLine();
            System.out.println("Please confirm your new password:");
            String confirmPassword = scanner.nextLine();
            if (newPassword.equals(confirmPassword)){
                setPassword(newPassword);
                System.out.println("Password has been successfully changed!");
                break;
            }else{
                System.out.println("Passwords do not match! Please try again.");
            }
        }
    }
}
