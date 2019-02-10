package teamup.rivile.com.teamup.Department;

import java.io.Serializable;

public class Department implements Serializable {
    int Id;
    String Name;
//    String Image;
//
//
//    public Department(int id, String Image, String name) {
//        Id = id;
//        this.Image = Image;
//        Name = name;
//    }
//
    public Department(int id, String name) {
        Id = id;
        Name = name;
    }

    public Department() {

    }

    public void setName(String name) {
        Name = name;
    }

//    public void setImage(String image) {
//        Image = image;
//    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

//    public String getImage() {
//        return Image;
//    }

    public String getName() {
        return Name;
    }
}
