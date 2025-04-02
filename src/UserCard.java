abstract public class UserCard {
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

    public String getName() {  // 改为非静态方法
        return name;
    }

    public int getAge() {  // 改为非静态方法
        return age;
    }

    public String getNRIC() {  // 改为非静态方法
        return NRIC;
    }

    public String getMaritalStatus() {  // 改为非静态方法
        return marital_status;
    }

    public String getPassword() {  // 改为非静态方法
        return password;
    }

    public void setPassword(String password) {  // 改为非静态方法
        this.password = password;
    }
}
