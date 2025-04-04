import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class Applicant extends UserCard {
    private String identity;
    private Project appliedProject; // 改为直接存储Project对象而非名称
    private String FlatType;
    public Applicant(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Applicant";
        this.appliedProject = null;
        this.FlatType = null;
    }
    public Project getAppliedProject() {
        return appliedProject;
    }
    public void setAppliedProject(Project project) {
        this.appliedProject = project;
    }
    public String getApplicationStatus() {
        HashMap<Applicant,String> applicationStatusMap = Applications.getApplicationAndStatus(appliedProject);
        if (applicationStatusMap == null) {
            return "Project Not Found"; // 项目不存在
        }
        return applicationStatusMap.getOrDefault(this, "Not Applied"); // 申请状态，或默认值
    }
    public String getFlatType() {
        return FlatType;
    }
    public void setFlatType(String FlatType) {
        this.FlatType = FlatType;
    }
    public String getIdentity() {
        return identity;
    }
}