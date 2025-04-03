import java.util.*;
public class WithdrawApplication {
    private static final HashMap<Project, Applicant> withdrawals= new HashMap<>();
    public static void addWithdrawal(Project project, Applicant applicant) {
        withdrawals.put(project, applicant);
    }
    public static void removeWithdrawal(Project project) {
        withdrawals.remove(project);
    }
    public static HashMap<Project, Applicant> getWithdrawals() {
        return withdrawals;
    }
    public static Applicant getWithdrawalApplicant(Project project) {
        return withdrawals.get(project);
    }
}
