import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Manager extends UserCard {
    private String identity;
    public Manager(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Manager";
    }
    public String getIdentity() {
        return identity;
    }
}
