import java.util.HashMap;
import java.util.Map;
public class Applications {
    // Make the map static so it belongs to the class,the project lead to the status of the application and the applicant to the project and status.
    private static final HashMap<Project, HashMap<Applicant, String>> APPLICATIONS = new HashMap<>();

    // Private constructor to prevent instantiation
    private Applications() {}

    public static HashMap<Project, HashMap<Applicant, String>> getApplications() {
        return APPLICATIONS;
    }

    public static void addApplication(Project project, Applicant applicant, String status) {
        APPLICATIONS
                .computeIfAbsent(project, k -> new HashMap<>())
                .put(applicant, status);
    }
    public static void updateApplicationStatus(Project project, Applicant applicant, String status) {
        Map<Applicant, String> projectMap = APPLICATIONS.get(project);
        if (projectMap != null && projectMap.containsKey(applicant)) {
            projectMap.put(applicant, status);
        }
    }
    public static void removeApplication(Project project, Applicant applicant) {
        HashMap<Applicant, String> projectMap = APPLICATIONS.get(project);
        if (projectMap != null) {
            projectMap.remove(applicant);
        }
    }
    public static HashMap<Applicant, String> getApplicationAndStatus(Project project) {
        HashMap<Applicant, String> projectMap = APPLICATIONS.get(project);
        if (projectMap != null) {
            return projectMap;
        }
        return null;
    }
}