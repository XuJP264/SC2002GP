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
    public String getName() {
        return super.getName();
    }
    public int getAge() {
        return super.getAge();
    }
    public String getNRIC() {
        return super.getNRIC();
    }
    public String getMaritalStatus() {
        return super.getMaritalStatus();
    }
    public String getPassword() {
        return super.getPassword();
    }
    public void setPassword(String password) {
        super.setPassword(password);
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

    public void setAppliedProject(String appliedProject) {
        this.appliedProject = appliedProject;
    }
    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }
    
}

