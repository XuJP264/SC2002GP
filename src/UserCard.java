public class UserCard {
    private String name;
    private int age;
    private String NRIC;
    private String marital_status;
    private String password;
    public UserCard(String name, int age, String NRIC, String marital_status, String password) {
        this.name = name;
        this.age = age;
        this.NRIC = NRIC;
        this.marital_status = marital_status;
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public void getNRIC() {
        return NRIC;
    }
    public String getMarital_status() {
        return marital_status;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

