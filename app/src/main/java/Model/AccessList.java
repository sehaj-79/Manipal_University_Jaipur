package Model;

public class AccessList {

    private String Name,ID;

    public AccessList (String Name,String ID, String Category){
        this.Name = Name;
        this.ID = ID;

    }

    public AccessList(){}

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
