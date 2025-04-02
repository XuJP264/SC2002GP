import java.util.List;
interface ProjectInCharge {
    void addProjectInCharge(Project project);
    void removeProjectInCharge(Project project);
    List<Project> getProjectsInCharge();
}