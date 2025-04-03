import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class Applicant extends UserCard {
    private String identity;
    private Project appliedProject; // 改为直接存储Project对象而非名称
    public Applicant(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Applicant";
        this.appliedProject = null;
    }
    public Project getAppliedProject() {
        return appliedProject;
    }
    public void setAppliedProject(Project project) {
        this.appliedProject = project;
    }
    public String getApplicationStatus() {
        return Applications.getApplicationAndStatus(appliedProject).get(this);
    }
    public String getIdentity() {
        return identity;
    }
}