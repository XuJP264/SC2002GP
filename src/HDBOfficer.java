public class HDBOfficer extends HDBUser {
    private int officerId;

    public HDBOfficer(String username, String password, int officerId) {
        super(username, password);
        this.officerId = officerId;
    }

    public void manageOfficerTasks() {
        System.out.println("Officer " + username + " is handling officer-specific tasks...");
    }
}
