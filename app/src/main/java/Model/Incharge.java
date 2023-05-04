package Model;

public class Incharge {

    private String Name,ID;

    public Incharge (String Name,String ID, String Category){
        this.Name = Name;
        this.ID = ID;
    }

    public Incharge(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
