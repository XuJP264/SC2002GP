import java.util.HashMap;
import java.util.ArrayList;

public class Reply {
    // A static HashMap that stores replies from officers or applicants for specific projects
    // Key: Enquirer (either Officer or Applicant object)
    // Value: A HashMap where key is Project object, and value is a list of reply messages
    private static HashMap<Object, HashMap<Project, ArrayList<String>>> replies = new HashMap<>();

    /**
     * Adds a reply to the system
     * @param replier The person replying (Officer or Applicant)
     * @param project The project being replied about
     * @param message The reply message
     */
    public static void addReply(Object replier, Project project, String message) {
        // Verify the replier is either Officer or Applicant
        if (!(replier instanceof Officer) && !(replier instanceof Applicant)) {
            throw new IllegalArgumentException("Replier must be either Officer or Applicant");
        }

        replies.putIfAbsent(replier, new HashMap<>());
        replies.get(replier).putIfAbsent(project, new ArrayList<>());
        replies.get(replier).get(project).add(message);
    }

    /**
     * Removes a specific reply
     * @param replier The person who made the reply
     * @param project The project the reply is about
     * @param message The reply message to remove
     */
    public static void removeReply(Object replier, Project project, String message) {
        if (replies.containsKey(replier) && replies.get(replier).containsKey(project)) {
            replies.get(replier).get(project).remove(message);

            // Clean up empty structures
            if (replies.get(replier).get(project).isEmpty()) {
                replies.get(replier).remove(project);
            }
            if (replies.get(replier).isEmpty()) {
                replies.remove(replier);
            }
        }
    }

    /**
     * Gets all replies for a specific project
     * @param project The project to get replies for
     * @return HashMap of repliers and their messages
     */
    public static HashMap<Object, ArrayList<String>> getRepliesForProject(Project project) {
        HashMap<Object, ArrayList<String>> projectReplies = new HashMap<>();

        replies.forEach((replier, projectMap) -> {
            if (projectMap.containsKey(project)) {
                projectReplies.put(replier, projectMap.get(project));
            }
        });

        return projectReplies;
    }

    /**
     * Gets all replies made by a specific person
     * @param replier The person (Officer or Applicant) to get replies for
     * @return HashMap of projects and their reply messages
     */
    public static HashMap<Project, ArrayList<String>> getRepliesByPerson(Object replier) {
        return replies.getOrDefault(replier, new HashMap<>());
    }

    /**
     * Gets the entire reply database
     * @return Complete HashMap of all replies
     */
    public static HashMap<Object, HashMap<Project, ArrayList<String>>> getAllReplies() {
        return replies;
    }

    /**
     * Links a reply to a specific enquiry
     * @param enquiryMessage The original enquiry message
     * @param replyMessage The reply message
     * @param project The project they relate to
     */
    public static void linkReplyToEnquiry(String enquiryMessage, String replyMessage, Project project) {
        // This could be enhanced to maintain a direct link between enquiries and replies
        // Implementation would depend on how you want to track these relationships
    }
}