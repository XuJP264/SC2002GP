import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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

public class ManagerList {
    private HashMap<String, Manager> managers;
    public ManagerList() {
        managers = new HashMap<>();
    }
    //  via NRIC get Manager
    public Manager getManager(String NRIC) {
        return managers.get(NRIC);
    }
    // get all managers
    public List<Manager> getAllManagers() {
        return new ArrayList<>(managers.values());
    }
}
