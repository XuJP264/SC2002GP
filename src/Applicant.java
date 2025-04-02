import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
public class Applicant extends UserCard {
    private String identity;
    private List<String> personalInquiries; // 个人直接提出的问题
    private Project appliedProject; // 改为直接存储Project对象而非名称
    private String applicationStatus;

    public Applicant(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Applicant";
        this.appliedProject = null;
        this.applicationStatus = "None";
        this.personalInquiries = new ArrayList<>();
    }

    // 移除冗余的getter/setter（继承自UserCard的无需重复定义）

    // 新增或修改的方法
    public Project getAppliedProject() {
        return appliedProject;
    }

    public void setAppliedProject(Project project) {
        this.appliedProject = project;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

    public String getIdentity() {
        return identity;
    }

    public List<String> getPersonalInquiries() {
        return personalInquiries;
    }

    // 添加项目相关查询的方法
    public void addProjectEnquiry(Project project, String message) {
        Enquiry.addApplicantEnquiry(this, project, message);
    }

    public List<String> getProjectEnquiries(Project project) {
        HashMap<Project, ArrayList<String>> enquiries = Enquiry.getEnquiryByApplicant(this);
        return enquiries != null ? enquiries.getOrDefault(project, new ArrayList<>()) : new ArrayList<>();
    }
}