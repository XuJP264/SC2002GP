import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
class Manager extends UserCard{
    private String identity;
    public Manager(String name, int age, String NRIC, String marital_status, String password) {
        super(name, age, NRIC, marital_status, password);
        this.identity = "Manager";
    }
    public String getIdentity() {
        return identity;
    }
    public String getName() {
        return super.getName();
    }
    public int getAge() {
        return super.getAge();
    }
    public String getNRIC() {
        return super.getNRIC();
    }
    public String getMaritalStatus() {
        return super.getMaritalStatus();
    }
    public String getPassword() {
        return super.getPassword();
    }
    public void setPassword(String password) {
        super.setPassword(password);
    }
}
