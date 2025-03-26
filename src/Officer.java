import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Officer extends UserCard {
    private String identity;
    public Officer(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Officer";
    }
    public String getIdentity() {
        return identity;
    }
}
