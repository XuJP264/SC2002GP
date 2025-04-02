import java.util.ArrayList;
import java.util.HashMap;

public class RegistrationList {
    // A HashMap to store the registration list
    private static HashMap<Officer, ArrayList<Project>> registrationList = new HashMap<>();
    // A HashMap to store the registration requests and officer conditions
    private static HashMap<Officer, HashMap<Project, String>> registrationConditions = new HashMap<>();

    // Adds a registration of an officer to a project
    public static void addRegistration(Officer officer, Project project) {
        if (!registrationList.containsKey(officer)) {
            registrationList.put(officer, new ArrayList<>());
        }
        registrationList.get(officer).add(project);
        addRegistrationCondition(officer, project, "Pending"); // Changed from false to default string
    }

    // Removes all registrations for a specific officer
    public static void removeRegistration(Officer officer) {
        registrationList.remove(officer);
        registrationConditions.remove(officer); // Also remove conditions
    }

    // Removes a specific project registration for an officer
    public static void removeRegistration(Officer officer, Project project) {
        if (registrationList.containsKey(officer)) {
            registrationList.get(officer).remove(project);
            if (registrationList.get(officer).isEmpty()) {
                registrationList.remove(officer);
            }
        }
        removeRegistrationCondition(officer, project);
    }

    // Returns the complete registration list
    public static HashMap<Officer, ArrayList<Project>> getRegistrationList() {
        return new HashMap<>(registrationList); // Return a copy for encapsulation
    }

    // Returns all projects registered by a specific officer
    public static ArrayList<Project> getOfficerRegistrations(Officer officer) {
        return new ArrayList<>(registrationList.getOrDefault(officer, new ArrayList<>()));
    }

    // Checks if an officer is registered for a specific project
    public static boolean isOfficerRegistered(Officer officer, Project project) {
        return registrationList.containsKey(officer) &&
                registrationList.get(officer).contains(project);
    }

    // Adds a registration condition with String value
    public static void addRegistrationCondition(Officer officer, Project project, String condition) {
        if (!registrationConditions.containsKey(officer)) {
            registrationConditions.put(officer, new HashMap<>());
        }
        registrationConditions.get(officer).put(project, condition);
    }

    // Removes a registration condition
    public static void removeRegistrationCondition(Officer officer, Project project) {
        if (registrationConditions.containsKey(officer)) {
            registrationConditions.get(officer).remove(project);
            if (registrationConditions.get(officer).isEmpty()) {
                registrationConditions.remove(officer);
            }
        }
    }

    // Gets all registration conditions for an officer
    public static HashMap<Project, String> getRegistrationConditions(Officer officer) {
        // Get the officer's registration conditions (may be null)
        HashMap<Project, String> conditions = registrationConditions.get(officer);

        // Return a copy if not null, otherwise empty HashMap
        return conditions != null ? new HashMap<>(conditions) : new HashMap<>();
    }

    // Gets a specific registration condition for an officer and project
    public static String getRegistrationCondition(Officer officer, Project project) {
        if (registrationConditions.containsKey(officer)) {
            return registrationConditions.get(officer).get(project);
        }
        return null;
    }

    // Displays all registrations in a formatted way
    public static void displayRegistrations() {
        // Implementation can be added here
    }
}