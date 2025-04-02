import java.util.ArrayList;
import java.util.List;
// Officer 继承 UserCard，并实现 ProjectInCharge 接口
class Officer extends UserCard implements ProjectInCharge {
    private String identity;
    private List<Project> projectsHaveApplied = new ArrayList<>();
    private List<Project> projectsInCharge = new ArrayList<>();

    public Officer(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Officer";
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

    public void addProjectHaveApplied(Project project) {
        projectsHaveApplied.add(project);
    }

    public void removeProjectHaveApplied(Project project) {
        projectsHaveApplied.remove(project);
    }

    public List<Project> getProjectsHaveApplied() {
        return new ArrayList<>(projectsHaveApplied);
    }

    public String getIdentity() {
        return identity;
    }

    // 实现 ProjectInCharge 接口的方法
    @Override
    public void addProjectInCharge(Project project) {
        projectsInCharge.add(project);
    }

    @Override
    public void removeProjectInCharge(Project project) {
        projectsInCharge.remove(project);
    }
    @Override
    public List<Project> getProjectsInCharge() {
        return new ArrayList<>(projectsInCharge);
    }
}
