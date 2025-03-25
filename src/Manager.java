public class Manager extends UserCard{
    private String identity;
    Manager(name, age, NRIC, marital_status, password, identity) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "HBD Manager";
    }
    public String getIdentity() {
        return identity;
    }
}
