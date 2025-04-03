import java.util.ArrayList;
import java.util.HashMap;  // 添加这一行

public class Test {
    public static void main(String[] args) {
        Initialization init = Initialization.getInstance();
        init.initialize();

        // 测试ApplicantList
        testApplicantList(init.getApplicantList());

        // 测试ManagerList
        testManagerList(init.getManagerList());

        // 测试OfficerList
        testOfficerList(init.getOfficerList());

        // 测试ProjectList的哈希表
        testProjectList(init.getProjectList());
    }

    private static void testApplicantList(ApplicantList applicantList) {
        System.out.println("=== Applicant List ===");
        System.out.println("Total applicants: " + applicantList.getAllApplicants().size());
        for (Applicant applicant : applicantList.getAllApplicants()) {
            System.out.println("Name: " + applicant.getName() +
                    ", NRIC: " + applicant.getNRIC() +  // 注意：方法名是 getNRIC()，不是 getNric()
                    ", Age: " + applicant.getAge() +
                    ", Status: " + applicant.getMaritalStatus());
        }
        System.out.println();
    }

    private static void testManagerList(ManagerList managerList) {
        System.out.println("=== Manager List ===");
        System.out.println("Total managers: " + managerList.getAllManagers().size());
        for (Manager manager : managerList.getAllManagers()) {
            System.out.println("Name: " + manager.getName() +
                    ", NRIC: " + manager.getNRIC() +  // 注意：方法名是 getNRIC()，不是 getNric()
                    ", Age: " + manager.getAge() +
                    ", Status: " + manager.getMaritalStatus());
        }
        System.out.println();
    }

    private static void testOfficerList(OfficerList officerList) {
        System.out.println("=== Officer List ===");
        System.out.println("Total officers: " + officerList.getAllOfficers().size());
        for (Officer officer : officerList.getAllOfficers()) {
            System.out.println("Name: " + officer.getName() +
                    ", NRIC: " + officer.getNRIC() +  // 注意：方法名是 getNRIC()，不是 getNric()
                    ", Age: " + officer.getAge() +
                    ", Status: " + officer.getMaritalStatus());
        }
        System.out.println();
    }
    private static void testProjectList(ProjectList projectList) {
        System.out.println("=== Project List (Hashtable) ===");
        HashMap<String, Project> projects = projectList.getProjects();
        System.out.println("Total projects: " + projects.size());
        for (String projectName : projects.keySet()) {
            Project project = projects.get(projectName);
            System.out.println("Project Name: " + projectName);
            System.out.println("Neighborhood: " + project.getNeighborhood());
            System.out.println("Type 1: " + project.getType1() +
                    " | Units: " + project.getType1Units() +
                    " | Price: " + project.getType1Price());
            System.out.println("Type 2: " + project.getType2() +
                    " | Units: " + project.getType2Units() +
                    " | Price: " + project.getType2Price());
            System.out.println("Opening Date: " + project.getOpeningDate() +
                    " | Closing Date: " + project.getClosingDate());
            System.out.println("Manager: " + project.getManagerName());
            System.out.println("Officer Slot: " + project.getOfficerSlot() +
                    " | Assigned Officers: " + project.getOfficers());
            System.out.println("Visible: " + project.isVisible());
            System.out.println(project.getOfficers().size() + " officers assigned.");
            System.out.println();
        }
    }
}