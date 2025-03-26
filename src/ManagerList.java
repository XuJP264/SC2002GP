import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    // add a new manager
    public void addManager(Manager manager) {
        managers.put(manager.getNRIC(), manager);
    }
}