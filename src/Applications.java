import java.util.HashMap;

public final class Applications {
    // Make the map static so it belongs to the class
    private static final HashMap<Applicant, HashMap<Project, String>> APPLICATIONS = new HashMap<>();

    // Private constructor to prevent instantiation
    private Applications() {}

    public static HashMap<Applicant, HashMap<Project, String>> getApplications() {
        return APPLICATIONS;
    }

    public static void addApplication(Applicant applicant, Project project, String status) {
        HashMap<Project, String> projectMap = APPLICATIONS.get(applicant);
        if (projectMap == null) {
            projectMap = new HashMap<>();
            APPLICATIONS.put(applicant, projectMap);
        }
        projectMap.put(project, status);
    }

    public static void updateApplicationStatus(Applicant applicant, Project project, String status) {
        HashMap<Project, String> projectMap = APPLICATIONS.get(applicant);
        if (projectMap != null) {
            projectMap.put(project, status);
        }
    }

    public static void removeApplication(Applicant applicant, Project project) {
        HashMap<Project, String> projectMap = APPLICATIONS.get(applicant);
        if (projectMap != null) {
            projectMap.remove(project);
        }
    }

    public static void displayApplicationsAndStatus(Applicant applicant) {
        HashMap<Project, String> projectMap = APPLICATIONS.get(applicant);
        if (projectMap == null || projectMap.isEmpty()) {
            System.out.println("No applications found for this applicant.");
            return;
        }

        System.out.println("Application Details for Applicant:");
        System.out.println("=================================");

        for (Project project : projectMap.keySet()) {
            String status = projectMap.get(project);

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

            System.out.println("Application Status: " + status);
            System.out.println("---------------------------------");
        }
    }
}