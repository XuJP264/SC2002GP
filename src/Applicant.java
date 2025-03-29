import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Applicant extends UserCard {
    private String identity;
    private String appliedPorject;
    private String applicationStatus;
    private List<String> inquiries;
    
    public Applicant(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Applicant";
        this.appliedProject = null;
        this.applicationStatus = none;
        this.inquires = new Array<List>;
    }
    public String getIdentity() {
        return identity;
    }

    public String getAppliedProject(){
        return appliedProject;
    }

    public String getApplicationStatus(){
        return applicationStatus;
    }

    public void applyForProject(String projectName, int applicantAge, String maritalStatus){
        if(this.appliedProject != null){
            System.out.println("You have already applied for a project and cannot apply for another.")
            return;
        }

        if maritalStatus
}
