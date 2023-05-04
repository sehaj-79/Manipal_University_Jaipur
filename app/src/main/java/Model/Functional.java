package Model;

public class Functional {
    
    private String Name,About,Designation,Achievements,Position,Image;

    public Functional(String Name, String About, String Designation, String Achievements,String Position, String Image){
        this.Name = Name;
        this.About = About;
        this.Designation = Designation;
        this.Achievements = Achievements;
        this.Position = Position;
        this.Image = Image;
    }

    public Functional() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getAchievements() {
        return Achievements;
    }

    public void setAchievements(String achievements) {
        Achievements = achievements;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
