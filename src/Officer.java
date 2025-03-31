import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Officer extends UserCard {
    private String identity;
    private ArrayList<Project> projectsHaveApplied = new ArrayList<>();
    private ArrayList<Project> projectsInCharge = new ArrayList<>();
    public Officer(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Officer";
    }
    public String getName(){
        return super.getName();
    }
    public int getAge(){
        return super.getAge();
    }
    public String getNRIC(){
        return super.getNRIC();
    }
    public String getMaritalStatus(){
        return super.getMaritalStatus();
    }
    public String getPassword(){
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
    public void addProjectInCharge(Project project) {
        projectsInCharge.add(project);
    }
    public void removeProjectInCharge(Project project) {
        projectsInCharge.remove(project);
    }
    public ArrayList<Project> getProjectsHaveApplied() {
        return new ArrayList<>(projectsHaveApplied);
    }
    public ArrayList<Project> getProjectsInCharge() {
        return new ArrayList<>(projectsInCharge);
    }
    public String getIdentity() {
        return identity;
    }
}
