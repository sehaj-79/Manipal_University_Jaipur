package Model;

public class Program {

    private String Name,Department,School,Duration,Description;

    public Program(String Name, String Department, String School, String Duration,String Description){
        this.Name = Name;
        this.Department = Department;
        this.School = School;
        this.Duration = Duration;
        this.Description = Description;
    }

    public Program() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}