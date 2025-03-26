import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Applicant extends UserCard {
    private String identity;
    public Applicant(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Applicant";
    }
    public String getIdentity() {
        return identity;
    }
}
