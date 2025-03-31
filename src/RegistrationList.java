import java.util.HashMap;
public class RegistrationList {
    private static HashMap<Officer,Project> registrationList = new HashMap<>();
    // addRegistration, removeRegistration, getRegistrationList, showRegistrationList
    public static void addRegistration(Officer officer, Project project) {
        registrationList.put(officer, project);
    }
    public static void removeRegistration(Officer officer) {
        registrationList.remove(officer);
    }
    public static HashMap<Officer,Project> getRegistrationList() {
        return registrationList;
    }
    public static void showRegistrationList() {
        if (registrationList.isEmpty()) {
            System.out.println("No registrations found.");
            return;
        }

        System.out.println("=== Registration List ===");
        System.out.println("Total Registrations: " + registrationList.size());
        System.out.println("-------------------------");

        int counter = 1;
        for (HashMap.Entry<Officer, Project> entry : registrationList.entrySet()) {
            Officer officer = entry.getKey();
            Project project = entry.getValue();

            System.out.println(counter + ". Officer: " + officer.getName() +
                    " (NRIC: " + officer.getNRIC() + ")");
            System.out.println("   Project: " + project.getProjectName() +
                    " (" + project.getNeighborhood() + ")");
            System.out.println("   Project Types: " + project.getType1() +
                    " (" + project.getType1Units() + " units), " +
                    project.getType2() + " (" + project.getType2Units() + " units)");
            System.out.println("   Dates: " + project.getOpeningDate() +
                    " to " + project.getClosingDate());
            System.out.println("-------------------------");
            counter++;
        }
    }
}