import java.util.ArrayList;
import java.util.List;

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

        // Add your project application logic here
        // For example, you might want to check age or marital status requirements
        this.appliedProject = projectName;
        this.applicationStatus = "pending";  // Set initial application status
    }

    public void addInquiry(String inquiry) {
        inquiries.add(inquiry);
    }
}