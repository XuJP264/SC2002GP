import java.util.*;
public class WithdrawApplication {
    private static final HashMap<Applicant, Project> withdrawals= new HashMap<>();
    public static void addWithdrawal(Applicant applicant, Project project) {
        withdrawals.put(applicant, project);
    }
    public static void removeWithdrawal(Applicant applicant) {
        withdrawals.remove(applicant);
    }
    public static HashMap<Applicant, Project> getWithdrawals() {
        return withdrawals;
    }
}
