import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Manager extends UserCard{
    private String identity;
    private List<Project> myProject = new ArrayList<Project>();
    public Manager(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Manager";
    }
    public String getIdentity() {
        return identity;
    }
    public List<Project> getMyProject() {
        return myProject;
    }
    public void addProject(Project project) {
        myProject.add(project);
    }
    public void removeProject(Project project) {
        myProject.remove(project);
    }
}
