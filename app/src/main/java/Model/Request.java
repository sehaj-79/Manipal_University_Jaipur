package Model;

public class Request {

    private String Name,ID,Category;

    public Request (String Name,String ID, String Category){
        this.Name = Name;
        this.ID = ID;
        this.Category = Category;

    }

    public Request(){}


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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

}
