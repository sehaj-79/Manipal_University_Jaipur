package Model;

public class Users
{
    private String username, phone, password, email, id, search, designation, course, department, gender, year,section,faculty,school;

    public Users(String username, String phone, String password, String email, String id, String search, String designation, String course, String department, String gender, String year, String section,String faculty, String school) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.email = email;
        this.id = id;
        this.search = search;
        this.designation = designation;
        this.course = course;
        this.department = department;
        this.gender = gender;
        this.year = year;
        this.section = section;
        this.school = school;
        this.faculty = faculty;

    }

    public Users() {

    }

    public String getFaculty() { return faculty; }

    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getSchool() { return school; }

    public void setSchool(String school) { this.school = school; }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}