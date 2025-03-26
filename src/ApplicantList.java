import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ApplicantList {
    private HashMap<String, Applicant> applicants;
    public ApplicantList() {
        applicants = new HashMap<>();
    }
    //  via NRIC get Applicant
    public Applicant getApplicant(String NRIC) {
        return applicants.get(NRIC);
    }
    // get all Applicants
    public List<Applicant> getAllApplicants() {
        return new ArrayList<>(applicants.values());
    }
    // add a new Applicant
    public void addApplicant(Applicant applicant) {
        applicants.put(applicant.getNRIC(), applicant);
    }
}