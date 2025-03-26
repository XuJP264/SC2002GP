import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class OfficerList {
    private HashMap<String, Officer> officers;
    public OfficerList() {
        officers = new HashMap<>();
    }
    //  via NRIC get Officer
    public Officer getOfficer(String NRIC) {
        return officers.get(NRIC);
    }
    // get all officers
    public List<Officer> getAllOfficers() {
        return new ArrayList<>(officers.values());
    }
    // add a new officer
    public void addOfficer(Officer officer) {
        officers.put(officer.getNRIC(), officer);
    }
}