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

    /**
     * Displays all projects in a formatted table
     * @param showHidden Whether to include hidden projects in the display
     */
    public void showProjectList(boolean showHidden) {
        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        // Table header
        System.out.println("\n=== PROJECT LIST ===");
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("%-3s %-20s %-15s %-12s %-12s %-10s %-10s %-8s %-15s\n",
                "#", "Project Name", "Neighborhood", "Type 1", "Type 2", "Open Date", "Close Date", "Slots", "Manager");
        System.out.println("------------------------------------------------------------------------------------------------");

        // Display each project
        int index = 1;
        for (Project project : projects.values()) {
            if (!showHidden && !project.isVisible()) {
                continue;  // Skip hidden projects unless explicitly requested
            }

            System.out.printf("%-3d %-20s %-15s %-12s %-12s %-10s %-10s %-8d %-15s\n",
                    index++,
                    project.getProjectName(),
                    project.getNeighborhood(),
                    project.getType1(),
                    project.getType2(),
                    project.getOpeningDate(),
                    project.getClosingDate(),
                    project.getOfficerSlot() - project.getOfficers().size(),  // Available slots
                    project.getManagerName());

            // Display additional project details if needed
            System.out.printf("     %s units @ $%.2f, %s units @ $%.2f\n",
                    project.getType1Units(), project.getType1Price(),
                    project.getType2Units(), project.getType2Price());
        }
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    /**
     * Overloaded method that shows only visible projects by default
     */
    public void showProjectList() {
        showProjectList(false);
    }
}