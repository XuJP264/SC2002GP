import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class Initialization {
	//Singleton Instance
	private static Initialization instance = new Initialization();
	
    // Storage classes to hold the initialized data
    private ApplicantList applicantList;
    private ManagerList managerList;
    private OfficerList officerList;
    private ProjectList projectList;

    private Initialization() {
        this.applicantList = new ApplicantList();
        this.managerList = new ManagerList();
        this.officerList = new OfficerList();
        this.projectList = new ProjectList();
    }
    public static Initialization getInstance() {
        return instance;}
    public void initialize() {
        System.out.println("Initializing...");
        this.applicantInitialize();
        this.projectInitialize();
        this.officerInitialize();
        this.managerInitialize();
        System.out.println("Initialization complete.");
    }

    public ApplicantList getApplicantList() {
        return applicantList;
    }

    public ManagerList getManagerList() {
        return managerList;
    }

    public OfficerList getOfficerList() {
        return officerList;
    }

    public ProjectList getProjectList() {
        return projectList;
    }
    // Helper methods to initialize the data
    private static void officerProjectInitiate(Officer officer) {
        Initialization initialization = Initialization.getInstance();
        ProjectList projectList = initialization.getProjectList();
        for (Project p : projectList.getProjects().values()) {
            if (ContainsName.containsName(p.getOfficers(), officer.getName())) {
                officer.addProjectInCharge(p);
            }
        }
        System.out.println("Officer project initialization complete");
    }
    private static void managerProjectInitiate(Manager manager) {
        Initialization initialization = Initialization.getInstance();
        ProjectList projectList = initialization.getProjectList();
        for (Project p : projectList.getProjects().values()) {
            if (p.getManagerName().equals(manager.getName())) {
                manager.addProject(p);
            }
        }
        System.out.println("Manager project initialization complete");
    }
    private void applicantInitialize() {
        String filePath = "data/ApplicantList.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String name = data[0].trim();
                    String nric = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    String maritalStatus = data[3].trim();
                    String password = data[4].trim();

                    Applicant applicant = new Applicant(name, age, nric, maritalStatus, password);
                    applicantList.addApplicant(applicant);
                }
            }
            System.out.println("Applicant list has been initialized");
        } catch (IOException e) {
            System.err.println("Error reading Applicant CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing age in Applicant data: " + e.getMessage());
        }
    }

    private void managerInitialize() {
        String filePath = "data/ManagerList.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 5) {
                    String name = data[0].trim();
                    String nric = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    String maritalStatus = data[3].trim();
                    String password = data[4].trim();

                    Manager manager = new Manager(name, age, nric, maritalStatus, password);
                    managerList.addManager(manager);
                }
            }
            System.out.println("Manager list has been initialized");

        } catch (IOException e) {
            System.err.println("Error reading Manager CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing age in Manager data: " + e.getMessage());
        }
    }

    private void officerInitialize() {
        String filePath = "data/OfficerList.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 5) {
                    String name = data[0].trim();
                    String nric = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    String maritalStatus = data[3].trim();
                    String password = data[4].trim();

                    Officer officer = new Officer(name, age, nric, maritalStatus, password);
                    officerList.addOfficer(officer);
                    officerProjectInitiate(officer);
                }
            }
            System.out.println("Officer list has been initialized");

        } catch (IOException e) {
            System.err.println("Error reading Officer CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing age in Officer data: " + e.getMessage());
        }
    }

    private void projectInitialize() {
        String filePath = "data/ProjectList.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length >= 12) {
                    String projectName = data[0].trim();
                    String neighborhood = data[1].trim();

                    // Type 1 details
                    String type1 = data[2].trim();
                    int type1Units = Integer.parseInt(data[3].trim());
                    double type1Price = Double.parseDouble(data[4].trim());

                    // Type 2 details
                    String type2 = data[5].trim();
                    int type2Units = Integer.parseInt(data[6].trim());
                    double type2Price = Double.parseDouble(data[7].trim());

                    // Dates
                    String openingDate = data[8].trim();
                    String closingDate = data[9].trim();

                    // Manager
                    String managerName = data[10].trim();

                    // Officer info
                    int officerSlot = Integer.parseInt(data[11].trim());
                    String[] officers = data[12].trim().split(" ");
                    ArrayList<String> officerList = new ArrayList<>(Arrays.asList(officers));
                    Project project = new Project(projectName, neighborhood,
                            type1, type1Units, type1Price,
                            type2, type2Units, type2Price,
                            openingDate, closingDate,
                            managerName, officerSlot, officerList);

                    projectList.addProject(project);
                    System.out.println("Project list has been initialized");
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading Project CSV file: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numeric values in Project data: " + e.getMessage());
        }
    }
}