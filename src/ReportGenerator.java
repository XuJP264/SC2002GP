import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class ReportGenerator {

    public static void generateReport(Manager manager, Scanner scanner) {
        System.out.println("\nGenerate Applicant Report");
        System.out.println("1. All Applicants");
        System.out.println("2. Filter by Marital Status");
        System.out.println("3. Filter by Flat Type");
        System.out.println("4. Filter by Age Range");
        System.out.println("5. Filter by Project");
        System.out.print("Select filter option (1-5): ");

        int choice = ValidChoice.getValidChoice(scanner, 1, 5);
        scanner.nextLine(); // consume newline

        HashMap<Project, HashMap<Applicant, String>> allApplications = Applications.getApplications();

        System.out.println("\nAPPLICANT REPORT");
        System.out.println("==============================================");

        switch (choice) {
            case 1:
                // All applicants
                generateAllApplicantsReport(allApplications);
                break;

            case 2:
                // Filter by marital status
                System.out.print("Enter marital status to filter (SINGLE/MARRIED): ");
                String maritalStatus = scanner.nextLine().toUpperCase();
                generateMaritalStatusReport(allApplications, maritalStatus);
                break;

            case 3:
                // Filter by flat type
                System.out.print("Enter flat type to filter: ");
                String flatType = scanner.nextLine();
                generateFlatTypeReport(allApplications, flatType);
                break;

            case 4:
                // Filter by age range
                System.out.print("Enter minimum age: ");
                int minAge = ValidChoice.getValidChoice(scanner, 18, 120);
                System.out.print("Enter maximum age: ");
                int maxAge = ValidChoice.getValidChoice(scanner, 18, 120);
                generateAgeRangeReport(allApplications, minAge, maxAge);
                break;

            case 5:
                // Filter by project
                System.out.print("Enter project name to filter: ");
                String projectName = scanner.nextLine();
                generateProjectReport(allApplications, projectName);
                break;

            default:
                System.out.println("Invalid option selected.");
        }

        System.out.println("==============================================");
        System.out.println("End of Report\n");
    }

    private static void generateAllApplicantsReport(HashMap<Project, HashMap<Applicant, String>> allApplications) {
        int totalCount = 0;

        for (Entry<Project, HashMap<Applicant, String>> projectEntry : allApplications.entrySet()) {
            Project project = projectEntry.getKey();

            for (Entry<Applicant, String> applicantEntry : projectEntry.getValue().entrySet()) {
                Applicant applicant = applicantEntry.getKey();
                if(Applications.getApplicationAndStatus(project)!= null && Applications.getApplicationAndStatus(project).containsKey(applicant)){
                    printApplicantDetails(applicant, project);
                    totalCount++;
                }
            }
        }

        System.out.println("\nTotal Applicants: " + totalCount);
    }

    private static void generateMaritalStatusReport(HashMap<Project, HashMap<Applicant, String>> allApplications, String maritalStatus) {
        int count = 0;

        for (Entry<Project, HashMap<Applicant, String>> projectEntry : allApplications.entrySet()) {
            Project project = projectEntry.getKey();

            for (Entry<Applicant, String> applicantEntry : projectEntry.getValue().entrySet()) {
                Applicant applicant = applicantEntry.getKey();

                if (applicant.getMaritalStatus().equalsIgnoreCase(maritalStatus)) {
                    if(Applications.getApplicationAndStatus(project)!= null && Applications.getApplicationAndStatus(project).containsKey(applicant)){
                        printApplicantDetails(applicant, project);
                        count++;
                    }
                }
            }
        }

        System.out.println("\nTotal " + maritalStatus + " Applicants: " + count);
    }

    private static void generateFlatTypeReport(HashMap<Project, HashMap<Applicant, String>> allApplications, String flatType) {
        int count = 0;

        for (Entry<Project, HashMap<Applicant, String>> projectEntry : allApplications.entrySet()) {
            Project project = projectEntry.getKey();

            for (Entry<Applicant, String> applicantEntry : projectEntry.getValue().entrySet()) {
                Applicant applicant = applicantEntry.getKey();

                if (applicant.getFlatType().equalsIgnoreCase(flatType)) {
                    if(Applications.getApplicationAndStatus(project)!= null && Applications.getApplicationAndStatus(project).containsKey(applicant)){
                        printApplicantDetails(applicant, project);
                        count++;
                    }
                }
            }
        }

        System.out.println("\nTotal Applicants for " + flatType + " flats: " + count);
    }

    private static void generateAgeRangeReport(HashMap<Project, HashMap<Applicant, String>> allApplications, int minAge, int maxAge) {
        int count = 0;

        for (Entry<Project, HashMap<Applicant, String>> projectEntry : allApplications.entrySet()) {
            Project project = projectEntry.getKey();

            for (Entry<Applicant, String> applicantEntry : projectEntry.getValue().entrySet()) {
                Applicant applicant = applicantEntry.getKey();
                int age = applicant.getAge();

                if (age >= minAge && age <= maxAge) {
                    if(Applications.getApplicationAndStatus(project)!= null && Applications.getApplicationAndStatus(project).containsKey(applicant)){
                        printApplicantDetails(applicant, project);
                        count++;
                    }
                }
            }
        }

        System.out.println("\nTotal Applicants aged " + minAge + "-" + maxAge + ": " + count);
    }

    private static void generateProjectReport(HashMap<Project, HashMap<Applicant, String>> allApplications, String projectName) {
        int count = 0;

        for (Entry<Project, HashMap<Applicant, String>> projectEntry : allApplications.entrySet()) {
            Project project = projectEntry.getKey();

            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                for (Entry<Applicant, String> applicantEntry : projectEntry.getValue().entrySet()) {
                    Applicant applicant = applicantEntry.getKey();
                    if(Applications.getApplicationAndStatus(project)!= null && Applications.getApplicationAndStatus(project).containsKey(applicant)){
                        printApplicantDetails(applicant, project);
                        count++;
                    }
                }
            }
        }

        System.out.println("\nTotal Applicants for " + projectName + ": " + count);
    }

    private static void printApplicantDetails(Applicant applicant, Project project) {
        System.out.println("Name: " + applicant.getName());
        System.out.println("Age: " + applicant.getAge());
        System.out.println("Marital Status: " + applicant.getMaritalStatus());
        System.out.println("Project: " + project.getProjectName());
        System.out.println("Flat Type: " + applicant.getFlatType());
        System.out.println("Status: " + Applications.getApplicationAndStatus(project).get(applicant));
        System.out.println("----------------------------------------------");
    }
}