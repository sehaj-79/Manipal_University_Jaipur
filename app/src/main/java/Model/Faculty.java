package Model;

public class Faculty {

    private String Name,Department,School,Designation,Mail,Contact,Image;

    public Faculty(String Name, String Department, String School, String Designation, String Mail, String Contact,String Image){
        this.Name = Name;
        this.Department = Department;
        this.School = School;
        this.Designation = Designation;
        this.Mail = Mail;
        this.Contact = Contact;
        this.Image = Image;
    }

    public Faculty(){
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

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
