import java.util.HashMap;
import java.util.ArrayList;

public class Enquiry {
    // A static HashMap that stores enquiries from applicants for specific projects.
    // Key: Applicant object
    // Value: A HashMap where the key is a Project object, and the value is a list of enquiry messages.
    private static HashMap<Applicant, HashMap<Project, ArrayList<String>>> applicantEnquiry = new HashMap<>();
    public static void addApplicantEnquiry(Applicant applicant, Project project, String message) {
        // Ensure the applicant has an entry in the HashMap
        applicantEnquiry.putIfAbsent(applicant, new HashMap<>());

        // Ensure the project has an entry in the inner HashMap
        applicantEnquiry.get(applicant).putIfAbsent(project, new ArrayList<>());

        // Add the enquiry message to the list
        applicantEnquiry.get(applicant).get(project).add(message);
    }
    public static void removeApplicantEnquiry(Applicant applicant, Project project, String message) {
        // Check if the applicant and project exist in the map
        if (applicantEnquiry.containsKey(applicant) && applicantEnquiry.get(applicant).containsKey(project)) {
            // Remove the specific message
            applicantEnquiry.get(applicant).get(project).remove(message);

            // If no more messages exist for this project, remove the project entry
            if (applicantEnquiry.get(applicant).get(project).isEmpty()) {
                applicantEnquiry.get(applicant).remove(project);
            }

            // If the applicant has no more projects, remove the applicant entry
            if (applicantEnquiry.get(applicant).isEmpty()) {
                applicantEnquiry.remove(applicant);
            }
        }
    }
    public static void upDateApplicantEnquiry(Applicant applicant, Project project, String oldMessage, String newMessage) {
        // Check if the applicant and project exist in the map
        if (applicantEnquiry.containsKey(applicant) && applicantEnquiry.get(applicant).containsKey(project)) {
            // Remove the old message
            applicantEnquiry.get(applicant).get(project).remove(oldMessage);
            // Add the new message
            applicantEnquiry.get(applicant).get(project).add(newMessage);
        }
    }
    public static HashMap<Applicant, HashMap<Project, ArrayList<String>>> getApplicantEnquiry() {
        return applicantEnquiry;
    }
    public static HashMap<Project, ArrayList<String>> getEnquiryByApplicant(Applicant applicant) {
        return applicantEnquiry.get(applicant);
    }
}
