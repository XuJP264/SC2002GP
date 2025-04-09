public interface CheckCredentials {
    public static boolean checkCredentials(String username, String password) {
        Initialization initialization = Initialization.getInstance();
        // Check Officer
        Officer officer = initialization.getOfficerList().getOfficer(username);
        if (officer != null) {
            if (officer.getPassword().equals(password)) {
                System.out.println("Login successful as Officer!");
                OfficerApp.main(officer);
                return true;
            } else {
                System.out.println("Invalid password for Officer account!");
                return false;
            }
        }

        // Check Applicant
        Applicant applicant = initialization.getApplicantList().getApplicant(username);
        if (applicant != null) {
            if (applicant.getPassword().equals(password)) {
                System.out.println("Login successful as Applicant!");
                ApplicantApp.main(applicant);
                return true;
            } else {
                System.out.println("Invalid password for Applicant account!");
                return false;
            }
        }

        // Check Manager
        Manager manager = initialization.getManagerList().getManager(username);
        if (manager != null) {
            if (manager.getPassword().equals(password)) {
                System.out.println("Login successful as Manager!");
                ManagerApp.main(manager);
                return true;
            } else {
                System.out.println("Invalid password for Manager account!");
                return false;
            }
        }

        System.out.println("Username not found in any system!");
        return false;
    }
}
