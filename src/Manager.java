public class Manager extends UserCard {
    private String identity;
    public Manager(String name, int age, String NRIC, String marital_status, String password, String identity) {
        super(name, age, NRIC, marital_status, password);
        this.identity = identity;
    }
    public String getIdentity() {
        return identity;
    }
}
