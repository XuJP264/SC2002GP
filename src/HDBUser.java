public abstract class HDBUser {
    protected String username;
    protected String password;

    public HDBUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login() {
        System.out.println(username + " has logged in.");
        return true;
    }

    public void logout() {
        System.out.println(username + " has logged out.");
    }

    public void viewInquiries() {
        System.out.println(username + " is viewing available inquiries...");
    }

    public void replyInquiry(int inquiryId, String answer) {
        System.out.println(username + " has replied to Inquiry [" + inquiryId + "], answer: " + answer);
    }
}
