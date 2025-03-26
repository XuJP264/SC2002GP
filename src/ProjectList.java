import java.util.ArrayList;
import java.util.HashMap;

public class ProjectList {
    private HashMap<String, Project> projects = new HashMap<>();

    public void addProject(Project project) {
        projects.put(project.getProjectName(), project);
    }

    public Project getProject(String projectName) {
        return projects.get(projectName);
    }

    public ArrayList<Project> getAllProjects() {
        return new ArrayList<>(projects.values());
    }

    public HashMap<String, Project> getProjects() {
        return new HashMap<>(projects);
    }

    public boolean containsProject(String projectName) {
        return projects.containsKey(projectName);
    }
}