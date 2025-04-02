import java.util.HashMap;
public class Applications {
    private HashMap<Applicant,HashMap<Project,String>> applications = new HashMap<>()
    public HashMap<Applicant, HashMap<Project, String>> getApplications() {
        return applications;
    }
    public void addApplication(Applicant applicant, Project project, String status) {
        HashMap<Project, String> projectMap = applications.get(applicant);
        if (projectMap == null) {
            projectMap = new HashMap<>();
            applications.put(applicant, projectMap);
        }
        projectMap.put(project, status);
    }
    public void updateApplicationStatus(Applicant applicant, Project project, String status) {
        HashMap<Project, String> projectMap = applications.get(applicant);
        if (projectMap != null) {
            projectMap.put(project, status);
        }
    }
    public void removeApplication(Applicant applicant, Project project) {
        HashMap<Project, String> projectMap = applications.get(applicant);
        if (projectMap != null) {
            projectMap.remove(project);
        }
    }
    public void displayApplicationsAndStatus(Applicant applicant) {
        HashMap<Project, String> projectMap = applications.get(applicant);
        if (projectMap == null || projectMap.isEmpty()) {
            System.out.println("No applications found for this applicant.");
            return;
        }

        System.out.println("Application Details for Applicant:");
        System.out.println("=================================");

        for (Project project : projectMap.keySet()) {
            String status = projectMap.get(project);

            // 输出项目详细信息
            System.out.println("\nProject Name: " + project.getProjectName());
            System.out.println("Neighborhood: " + project.getNeighborhood());
            System.out.println("Housing Types:");
            System.out.println("  - " + project.getType1() +
                    ": " + project.getType1Units() + " units" +
                    ", Price: $" + project.getType1Price());
            System.out.println("  - " + project.getType2() +
                    ": " + project.getType2Units() + " units" +
                    ", Price: $" + project.getType2Price());
            System.out.println("Application Period: " +
                    project.getOpeningDate() + " to " +
                    project.getClosingDate());
            System.out.println("Project Manager: " + project.getManagerName());

            // 输出申请状态
            System.out.println("Application Status: " + status);
            System.out.println("---------------------------------");
        }
    }
}
