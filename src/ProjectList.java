import java.util.ArrayList;
import java.util.HashMap;

public class ProjectList {
    private HashMap<String,Project> projects = new HashMap<>();

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
    
    public void removeProject(String projectName) { //delete functionality for managers
        projects.remove(projectName);
    }

    /**
     * Displays all projects in a formatted table
     * @param showHidden Whether to include hidden projects in the display
     */
    public void showProjectList(boolean showHidden) {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        System.out.println("\n=== Project List (Detailed View) ===");
        System.out.println("Total projects: " + projects.size());
        System.out.println("-----------------------------------------------");

        int index = 1;
        for (Project project : projects.values()) {
            if (!showHidden && !project.isVisible()) continue;

            // 项目基础信息
            System.out.println(index++ + ". Project Name: " + project.getProjectName());
            System.out.println("   Neighborhood: " + project.getNeighborhood());
            System.out.println("   Visibility: " + (project.isVisible() ? "Visible" : "Hidden"));

            // 户型详细信息
            System.out.printf("   Type 1: %-8s | Units: %-4d | Price: $%.2f\n",
                    project.getType1(), project.getType1Units(), project.getType1Price());
            System.out.printf("   Type 2: %-8s | Units: %-4d | Price: $%.2f\n",
                    project.getType2(), project.getType2Units(), project.getType2Price());

            // 时间信息
            System.out.println("   Dates: " + project.getOpeningDate() + " - " + project.getClosingDate());

            // 人员信息
            System.out.println("   Manager: " + project.getManagerName());
            System.out.println("   Officer Slots: " +
                    (project.getOfficerSlot() - project.getOfficers().size()) + "/" +
                    project.getOfficerSlot() + " available");
            System.out.println("   Assigned Officers: " + project.getOfficers());

            System.out.println("-----------------------------------------------");
        }
    }

    // 保留原有的默认方法
    public void showProjectList() {
        showProjectList(false);
    }
}