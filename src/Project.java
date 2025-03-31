import java.util.ArrayList;
import java.util.HashMap;

public class Project {
    private String projectName;
    private String neighborhood;
    private String type1;
    private int type1Units;
    private double type1Price;
    private String type2;
    private int type2Units;
    private double type2Price;
    private String openingDate;
    private String closingDate;
    private String managerName;
    private int officerSlot;
    private ArrayList<String> officers;
    private boolean visible = true;

    public Project(String projectName, String neighborhood,
                   String type1, int type1Units, double type1Price,
                   String type2, int type2Units, double type2Price,
                   String openingDate, String closingDate,
                   String managerName, int officerSlot,
                   ArrayList<String> officers) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.type1 = type1;
        this.type1Units = type1Units;
        this.type1Price = type1Price;
        this.type2 = type2;
        this.type2Units = type2Units;
        this.type2Price = type2Price;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.managerName = managerName;
        this.officerSlot = officerSlot;
        this.officers = new ArrayList<>(officers);
    }

    // Getters
    public String getProjectName() { return projectName; }
    public String getNeighborhood() { return neighborhood; }
    public String getType1() { return type1; }
    public int getType1Units() { return type1Units; }
    public double getType1Price() { return type1Price; }
    public String getType2() { return type2; }
    public int getType2Units() { return type2Units; }
    public double getType2Price() { return type2Price; }
    public String getOpeningDate() { return openingDate; }
    public String getClosingDate() { return closingDate; }
    public String getManagerName() { return managerName; }
    public int getOfficerSlot() { return officerSlot; }
    public ArrayList<String> getOfficers() { return officers; }
    public boolean isVisible() { return visible; }

    // Setters
    public void setType1Units(int units) { type1Units = units; }
    public void setType2Units(int units) { type2Units = units; }
    public void addOfficer(String officer) { officers.add(officer); }
    public void removeOfficer(String officer) { officers.remove(officer); }
    public void setVisible(boolean visible) {this.visible = visible;}
    public void setType1Price(double price) {this.type1Price = price;}
    public void setType2Price(double price) {this.type2Price = price;}
    public void setOpeningDate(String openingDate) {this.openingDate = openingDate;}
    public void setClosingDate(String closingDate) {this.closingDate = closingDate;}

}